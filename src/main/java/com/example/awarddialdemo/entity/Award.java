package com.example.awarddialdemo.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 奖品
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 14:51
 * @return
 */
@Data
public class Award {

    static {
//        奖品等级，奖品总数
        Map<Integer,Integer> awardNumMap = new HashMap<>();
        awardNumMap.put(1,1);
        awardNumMap.put(2,2);
        awardNumMap.put(3,3);
        awardNumMap.put(4,4);
//        奖品等级，奖品概率
        Map<Integer,Double> awardOddMap = new HashMap<>();
        awardOddMap.put(1,0.1);
        awardOddMap.put(2,0.2);
        awardOddMap.put(3,0.3);
        awardOddMap.put(4,0.4);

        String awardTotleType = "一二三四五六七八九十";
        String awardNoTotleType = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Award> awardList = new LinkedList<>();
        awardList.add(new Award(0,"未中奖","00"));
        int awardId = 1;
        for(int i = 1;i <= 4;i++){
            Integer awardTotal = awardNumMap.get(i);
            for(int j = 0;j < awardTotal;j++){
                awardList.add(new Award(awardId,awardTotleType.charAt(i - 1) + "等奖", "" + awardNoTotleType.charAt(i - 1) + (j + 1)));
                awardId ++;
            }
        }
//        重新设置奖品概率


    }

    public Award(Integer awardId,String awardType,String awardNo){
        this.awardId = awardId;
        this.awardType = awardType;
        this.awardNo = awardNo;
    }

    private Integer awardId;
//   奖品类型
    private String awardType;
//   中奖概率
    private Double odd;
//   奖品编号
    private String awardNo;


}
