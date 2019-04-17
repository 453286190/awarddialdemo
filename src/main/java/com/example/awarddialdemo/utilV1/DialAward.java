package com.example.awarddialdemo.utilV1;

import springbootmavenprav2.demo.entity.Award;
import springbootmavenprav2.demo.entity.JoinAwardPeople;

import java.util.*;

/**
 * 转盘抽奖
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 12:17
 * @return
 */
public class DialAward {
    public static void main(String[] args) {
        /*
        奖品等级，奖品总数
         */
        Map<Integer,Integer> awardMap = new HashMap<>();
        awardMap.put(1,1);
        awardMap.put(2,2);
        awardMap.put(3,3);
        awardMap.put(4,4);

        sendAward(addAward(awardMap),addJoinPeople(10));

    }



    /**
     * 添加奖品
     */
    public static List<Award> addAward( Map<Integer,Integer> awardMap){
        String awardTotleType = "一二三四五六七八九十";
        String awardNoTotleType = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Award> awardList = new LinkedList<>();
        awardList.add(new Award(0,"未中奖","00"));
        int awardId = 1;
        for(int i = 1;i <= 4;i++){
            Integer awardTotal = awardMap.get(i);
            for(int j = 0;j < awardTotal;j++){
                awardList.add(new Award(awardId,awardTotleType.charAt(i - 1) + "等奖", "" + awardNoTotleType.charAt(i - 1) + (j + 1)));
                awardId ++;
            }
        }
        for (Award award : awardList){
            System.out.println(award);
        }
        return awardList;
    }

    /**
     * 添加抽奖人
     * @param totlePeopleNum
     * @return
     */
    public static List<JoinAwardPeople> addJoinPeople(Integer totlePeopleNum){
        List<JoinAwardPeople> joinAwardPeopleList = new LinkedList<>();
        for(int i = 0;i < totlePeopleNum;i++){
            joinAwardPeopleList.add(new JoinAwardPeople(i + 1,"usernameV" + Integer.toString(i + 1),""));
        }
        for(JoinAwardPeople people : joinAwardPeopleList){
            System.out.println(people);
        }
        return joinAwardPeopleList;
    }

    /**
     * 发奖
     * @param awardList
     * @param joinAwardPeopleList
     */
    public static void sendAward(List<Award> awardList,List<JoinAwardPeople> joinAwardPeopleList){
        for(JoinAwardPeople joinAwardPeople : joinAwardPeopleList){
            //参与人数大于奖品总数
            if(joinAwardPeopleList.size() > awardList.size()){
                Integer randomNum = new Random().nextInt(joinAwardPeopleList.size());
            }else{

            }

        }
    }




}
