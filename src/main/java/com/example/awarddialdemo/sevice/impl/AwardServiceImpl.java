package com.example.awarddialdemo.sevice.impl;

import com.example.awarddialdemo.dto.AwardAddInfo;
import com.example.awarddialdemo.entity.Award;
import com.example.awarddialdemo.entity.AwardBase;
import com.example.awarddialdemo.entity.User;
import com.example.awarddialdemo.entity.UserAward;
import com.example.awarddialdemo.exception.MessageException;
import com.example.awarddialdemo.mapper.AwardBaseMapper;
import com.example.awarddialdemo.mapper.AwardMapper;
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

    /**
     * 新增奖品，中奖比率改库
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
    public void sendAward() {
        List<AwardBase> awardBaseList = awardBaseMapper.selectAll();
        List<Award> awardList = awardMapper.selectAll();
        List<User> userList = userMapper.selectAll();
        List<Double> oddList = new LinkedList<>();
        for(AwardBase awardBase : awardBaseList){
            oddList.add(awardBase.getAwardOdd());
        }
        Double allAdds = 0.0;
        for(Double d : oddList){
            allAdds += d;
        }
        if(allAdds != 1.0){
            for(int i = 0;i < oddList.size();i++){
                oddList.set(i,1 / allAdds * oddList.get(i));
            }
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
        Integer awardTrue = 0;
        //未中奖统计
        Integer awardFalse = 0;
        //中奖人
        List<UserAward> userAwardListAwardYes = new LinkedList<>();
        //未中奖人
        List<UserAward> userAwardListAwardNo = new LinkedList<>();
        for(User user : userList){
            //中奖人数已满
            if(awardTrue >= 15){
                userAwardListAwardNo.add(addUserAwardNo(user));
                awardFalse ++;
            }else if(awardTrue < 15 && awardTrue > 0){
                Integer isAward = aliasMethodIsAward.next();
//              中奖
                if(isAward.equals(0)){
                    Integer awardLevel = aliasMethodOdd.next();
//              未中奖
                }else if(isAward.equals(1)){
                    userAwardListAwardNo.add(addUserAwardNo(user));
                    awardFalse ++;
                }
                awardTrue ++;
            }
        }

    }

    /**
     * 添加中奖用户
     * @param user
     * @return
     */
    public UserAward addUserAwardYes(User user){
        UserAward userAward = new UserAward();
        userAward.setUserId(user.getId());
        userAward.setAwardId(0L);
        userAward.setAwardKey("未中奖");
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
