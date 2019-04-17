package com.example.awarddialdemo.utilV1;

import springbootmavenprav2.demo.entity.Pocker;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * new Random().nextInt(52)是0-51随机
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-17 11:39
 * @return
 */
public class FlushPckers {
    public static void main(String[] args) {
        List<Pocker> pockerList = createPockers();
        flushPockers(pockerList);
        showPockers(pockerList);
    }

    /**
     * 创造pockers
     * @return
     */
    public static List<Pocker> createPockers(){
        List<Pocker> pockerList = new LinkedList<>();
        String[] colorArray = new String[]{"红桃","方片","梅花","黑桃"};
        String[] numArray = new String[13];
        for(int i = 2;i <=10;i++ ){
            numArray[i - 2] = Integer.toString(i);
        }
        numArray[12] = "A";
        numArray[11] = "K";
        numArray[10] = "Q";
        numArray[9] = "J";
//        for(int i = 0;i < numArray.length;i++){
//            System.out.print(numArray[i] + ",");
//        }
//        System.out.println();
        for(int i = 0;i < colorArray.length;i++){
            for(int j = 0;j < numArray.length;j++){
                pockerList.add(new Pocker(colorArray[i],numArray[j]));
            }
        }
//        for(Pocker pocker : pockerList){
//            System.out.println(pocker);
//        }
//        System.out.println(pockerList.size());
        return pockerList;
    }

    /**
     *
     * @param pockerList
     */
    public static void flushPockers(List<Pocker> pockerList){
        //默认洗50次牌
        for(int i = 0;i < 50;i++){
//            System.out.println("iA:" + i);
            //round : 0-51
            Integer n = new Random().nextInt(52);
            Integer m = new Random().nextInt(52);
            Pocker pocker = new Pocker();
            if(!n.equals(m)){
                pocker = pockerList.get(n);
                pockerList.set(n,pockerList.get(m));
                pockerList.set(m,pocker);
            }else{
//                System.out.println("---------------------------");
                i -= 1;
            }
//            System.out.println("iB:" + i);
        }
    }

    public static void showPockers(List<Pocker> pockerList){
        for(int i = 0;i < pockerList.size();i++){
            System.out.print(pockerList.get(i).getColor() + pockerList.get(i).getNum() + "  ");
//            换行
            if(i%10 == 9){
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("总牌数:" + pockerList.size());
    }




}
