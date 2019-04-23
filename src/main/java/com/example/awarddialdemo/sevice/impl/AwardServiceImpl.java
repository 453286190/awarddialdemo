package com.example.awarddialdemo.sevice.impl;

import com.example.awarddialdemo.dto.AwardAddInfo;
import com.example.awarddialdemo.entity.Award;
import com.example.awarddialdemo.entity.AwardBase;
import com.example.awarddialdemo.entity.User;
import com.example.awarddialdemo.entity.UserAward;
import com.example.awarddialdemo.exception.MessageException;
import com.example.awarddialdemo.mapper.AwardBaseMapper;
import com.example.awarddialdemo.mapper.AwardMapper;
import com.example.awarddialdemo.mapper.UserAwardMapper;
import com.example.awarddialdemo.mapper.UserMapper;
import com.example.awarddialdemo.sevice.AwardService;
import com.example.awarddialdemo.util.AliasMethod;
import com.example.awarddialdemo.util.EntryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 17:04
 * @return
 */
@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardBaseMapper awardBaseMapper;
    @Autowired
    private AwardMapper awardMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAwardMapper userAwardMapper;

    /**
     * 新增奖品
     * @param awardAddInfo
     */
    @Override
    @Transactional
    public void addAward(AwardAddInfo awardAddInfo) throws MessageException {
        //奖品等级总数
        if(!awardAddInfo.getAwardLevelSum().equals(awardAddInfo.getAwardDTOList().size())){
            throw new MessageException("奖品等级总数与各等级奖品数量信息不符");
        }

        String awardTotleType = "一二三四五六七八九十";
        List<AwardBase> awardBaseList = new LinkedList<>();
        for(int i = 0;i < awardAddInfo.getAwardLevelSum();i++){
            AwardBase awardBase = new AwardBase();
            //奖品类型
            awardBase.setAwardLevel(i + 1);
            //奖品说明
            awardBase.setDescription(awardTotleType.charAt(i) + "等奖");
            //中奖概率
            awardBase.setAwardOdd(0.0);
            awardBase.setAwardSend(0);
            awardBase.setAwardTotal(awardAddInfo.getAwardDTOList().get(i).getAwardNum());
            awardBaseList.add(awardBase);
        }
//        添加等级基础表
        awardBaseMapper.insertList(awardBaseList);

        for(int i = 0;i < awardAddInfo.getAwardDTOList().size(); i++){
            List<Award> awardList = new LinkedList<>();
            //奖品总数
            for(int j = 0;j < awardAddInfo.getAwardDTOList().get(i).getAwardNum();j++){
                Award award = new Award();
                award.setAwardLevel(i + 1);
                award.setAwardKey("");
                award.setIsSend(false);
                awardList.add(award);
            }
            awardMapper.insertList(awardList);
        }
        //新添加的奖品
        Example example = new Example(Award.class);
        example.createCriteria().andEqualTo("awardKey","");
        List<Award> newAddAward = awardMapper.selectByExample(example);
        for(Award award : newAddAward){
            award.setAwardKey(EntryUtil.encrypt(award.getId().toString()));
            awardMapper.updateByPrimaryKeySelective(award);
            System.out.println(award.toString());
        }

    }

    /**
     * 发奖
     * 此案例奖品15个，用户总数500个，中奖概率是15 / 500 = 0.03
     * 中奖之后，再按各奖品的比率分几等奖
     */
    @Override
    @Transactional
    public void sendAward() throws MessageException {
        List<AwardBase> awardBaseList = awardBaseMapper.selectAll();
        List<User> userList = userMapper.selectAll();
        List<Double> oddList = new LinkedList<>();
        for(AwardBase awardBase : awardBaseList){
            oddList.add(awardBase.getAwardOdd());
        }
        Double allAdds = 0.0;
        for(Double d : oddList){
            allAdds += d;
        }
        if(allAdds != 1.0 && allAdds != 0.0){
            for(int i = 0;i < oddList.size();i++){
                oddList.set(i,1 / allAdds * oddList.get(i));
            }
        }else{
            throw new MessageException("allAdds = " + allAdds + "有误");
        }
        //是否中奖，0.03为中奖概率，0.97为未中奖概率
        List<Double> isAwardList = new LinkedList<>();
        isAwardList.add(0.03);
        isAwardList.add(0.97);
        //是否中奖采样
        AliasMethod aliasMethodIsAward = new AliasMethod(isAwardList);
        //中几等奖采样
        AliasMethod aliasMethodOdd = new AliasMethod(oddList);
        //中奖统计
        Integer awardYes = 0;
        //未中奖统计
        Integer awardNo = 0;
        //中奖人
        Vector<UserAward> userAwardListAwardYes = new Vector<>(new LinkedList<>());
        //未中奖人
        Vector<UserAward> userAwardListAwardNo = new Vector<>(new LinkedList<>());
        for(User user : userList){
            //中奖人数已满
            if(awardYes >= 15){
                userAwardListAwardNo.add(addUserAwardNo(user));
                awardNo ++;
            }else if(awardYes < 15 && awardYes >= 0){
                Integer isAward = aliasMethodIsAward.next();
//              中奖
                if(isAward.equals(0)){
                    //抽奖
                    List<AwardBase> awardBaseList1 = rollAward(aliasMethodOdd);
                    if(awardBaseList1 != null && awardBaseList1.size() > 0){
                        AwardBase awardBase = awardBaseList1.get(0);
                        Integer awardNotSend = awardBase.getAwardTotal() - awardBase.getAwardSend();
//                        该等级奖品未发完
                        if(awardNotSend > 0){
                            //获取奖品
                            Award award = getAward(awardBase);
                            userAwardListAwardYes.add(addUserAwardYes(user,award));
                            awardYes++;
//                        该等级奖品已发完
                        }else{
                            List<Integer> awardLevelNotSendList = getAwardLevelNotSend();
                            if(awardLevelNotSendList != null && awardLevelNotSendList.size() > 0){
                                a:while (true){
                                    Integer awardLevel = aliasMethodOdd.next();
                                    if(awardLevelNotSendList.contains(awardLevel)){
                                        Award award = getAward(getAwardBaseByAwardLevel(awardLevel));
                                        userAwardListAwardYes.add(addUserAwardYes(user,award));
                                        awardYes++;
                                        System.out.println("awardLevel:" + awardLevel);
                                        System.out.println("awardYes:" + awardYes);
                                        break a;
                                    }else if(awardLevelNotSendList.size() == 1){
                                        Award award = getAward(getAwardBaseByAwardLevel(awardLevelNotSendList.get(0)));
                                        userAwardListAwardYes.add(addUserAwardYes(user,award));
                                        awardYes++;
                                        break a;
                                    }
                                }
//                                所有等级的奖品都发完了
                            }else{
                                userAwardListAwardNo.add(addUserAwardNo(user));
                                awardNo ++;
                            }
                        }
                    }
//              未中奖
                }else if(isAward.equals(1)){
                    userAwardListAwardNo.add(addUserAwardNo(user));
                    awardNo ++;
                }

            }
        }
        if(userAwardListAwardYes.size() > 0){
            userAwardMapper.insertList(userAwardListAwardYes);
        }
        if(userAwardListAwardNo.size() > 0){
            userAwardMapper.insertList(userAwardListAwardNo);
        }
        System.out.println("awardNo:" + awardNo + ";  awardYes:" + awardYes + ";  total:" + (awardNo + awardYes));

    }

    /**
     * 清除中奖信息
     */
    @Override
    public void clearAward() {
        List<AwardBase> awardBaseList = awardBaseMapper.selectAll();
        for(AwardBase awardBase : awardBaseList){
            awardBase.setAwardSend(0);
            awardBaseMapper.updateByPrimaryKeySelective(awardBase);
        }
        List<Award> awardList = awardMapper.selectAll();
        for(Award award : awardList){
            award.setIsSend(false);
            awardMapper.updateByPrimaryKeySelective(award);
        }
    }

    /**
     * 根据奖品等级获取奖品base
     * @param awardLevel
     * @return
     * @throws MessageException
     */
    public AwardBase getAwardBaseByAwardLevel(Integer awardLevel) throws MessageException {
        Example example = new Example(AwardBase.class);
        example.createCriteria().andEqualTo("awardLevel",awardLevel);
        List<AwardBase> awardBaseList = awardBaseMapper.selectByExample(example);
        if(awardBaseList != null && awardBaseList.size()> 0){
            return awardBaseList.get(0);
        }else{
            throw new MessageException("奖品等级：" + awardLevel + "有误");
        }
    }

    /**
     * 查看有哪些未发完的奖品等级
     * @return
     */
    public List<Integer> getAwardLevelNotSend(){
        List<Integer> awardLevelList = new LinkedList<>();
        List<AwardBase> awardBaseList = awardBaseMapper.selectAll();
        for(AwardBase awardBase : awardBaseList){
            //未发完
            if(awardBase.getAwardTotal() - awardBase.getAwardSend() > 0){
                awardLevelList.add(awardBase.getAwardLevel());
            }
        }
        return awardLevelList;
    }

    /**
     * 获取奖品
     * @param awardBase
     * @return
     * @throws MessageException
     */
    public Award getAward(AwardBase awardBase) throws MessageException {
        Integer awardLevel = awardBase.getAwardLevel();
        Example example = new Example(Award.class);
        example.createCriteria().andEqualTo("awardLevel",awardLevel).andEqualTo("isSend",false);
        List<Award> awardNotSendList = awardMapper.selectByExample(example);
        if(awardNotSendList !=null && awardNotSendList.size()> 0){
            Award award = awardNotSendList.get(0);
            award.setIsSend(true);
            awardMapper.updateByPrimaryKeySelective(award);
            awardBase.setAwardSend(awardBase.getAwardSend() + 1);
            awardBaseMapper.updateByPrimaryKeySelective(awardBase);
            return award;
        }else {
            throw new MessageException("奖品表未发数量与奖品base表记录不符");
        }
    }


    /**
     * 抽奖
     * @param aliasMethodOdd
     * @return
     */
    public List<AwardBase> rollAward(AliasMethod aliasMethodOdd){
        Integer awardLevel = aliasMethodOdd.next();
        Example example = new Example(AwardBase.class);
        example.createCriteria().andEqualTo("awardLevel",awardLevel);
        List<AwardBase> awardBaseList = awardBaseMapper.selectByExample(example);
        return awardBaseList;
    }

    /**
     * 添加中奖用户
     * @param user
     * @return
     */
    public UserAward addUserAwardYes(User user,Award award){
        UserAward userAward = new UserAward();
        userAward.setUserId(user.getId());
        userAward.setAwardId(award.getId());
        userAward.setAwardKey(award.getAwardKey());
        return userAward;
    }

    /**
     * 添加未中奖用户
     * @param user
     * @return
     */
    public UserAward addUserAwardNo(User user){
        UserAward userAward = new UserAward();
        userAward.setUserId(user.getId());
        userAward.setAwardId(0L);
        userAward.setAwardKey("未中奖");
        return userAward;
    }

    public static void main(String[] args) {
        List<Double> doubleList = new LinkedList<>();
        doubleList.add(0.06);
        doubleList.add(0.12);
        doubleList.add(0.18);
        doubleList.add(0.24);
        doubleList.add(0.3);
        Double allAdds = 0.0;
        for(Double d : doubleList){
            allAdds += d;
        }
        if(allAdds != 1.0){
            for(int i = 0;i < doubleList.size();i++){
                doubleList.set(i,1 / allAdds * doubleList.get(i));
            }
        }
//        for(Double d : doubleList){
//            System.out.println(d);//除不尽的话就小数点后添加多位，以求最大精度
//        }
        List<Double> isAwardList = new LinkedList<>();
        isAwardList.add(0.03);
        isAwardList.add(0.97);
        //是否中奖采样
        AliasMethod aliasMethodIsAward = new AliasMethod(isAwardList);
        //中几等奖采样
        AliasMethod aliasMethodOdd = new AliasMethod(doubleList);
        Integer awardTrue = 0;
        Integer awardFalse = 0;
        for(int i = 0; i< 500;i++){
            Integer isAward = aliasMethodIsAward.next();
            if(isAward.equals(0)){
                awardTrue ++;
            }else if(isAward.equals(1)){
                awardFalse ++;
            }
        }
        System.out.println("awardTrue:" + awardTrue + ";awardFalse:" + awardFalse);
        AliasMethod aliasMethod = new AliasMethod(doubleList);
//        while(true){
//            System.out.println("aliasMethod.next()" + aliasMethod.next());
//        }
    }
}
