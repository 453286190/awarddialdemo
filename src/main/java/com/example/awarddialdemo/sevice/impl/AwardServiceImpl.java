package com.example.awarddialdemo.sevice.impl;

import com.example.awarddialdemo.entity.Award;
import com.example.awarddialdemo.entity.AwardBase;
import com.example.awarddialdemo.sevice.AwardService;
import com.example.awarddialdemo.util.EntryUtil;
import org.springframework.stereotype.Service;

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

    /**
     *
     * @param awardLevelSum 奖品数量
     */
    @Override
    public void addAward(String awardLevelSum) {
        String awardTotleType = "一二三四五六七八九十";
        List<AwardBase> awardBaseList = new LinkedList<>();
        List<Award> awardList = new LinkedList<>();
        for(int i = 0;i < Integer.parseInt(awardLevelSum);i++){
            AwardBase awardBase = new AwardBase();
            //奖品类型
            awardBase.setAwardType(i + 1 + "");
            //中奖概率
            awardBase.setAwardOdd(0.0);
            awardBaseList.add(awardBase);

            Award award = new Award();
            award.setIsSend(false);
            award.setAwardKey("");
            awardList.add(award);
        }
        for (Award award : awardList){
            System.out.println(award);
        }
    }
}
