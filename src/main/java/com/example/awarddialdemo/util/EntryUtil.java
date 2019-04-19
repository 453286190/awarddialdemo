package com.example.awarddialdemo.util;

/**
 * 加密解密工具类
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 17:36
 * @return
 */
public class EntryUtil {

    /**
     * 使用默认秘钥进行DES加密
     */
    public static String encrypt(String str){
        try {
//            使用默认key进行DES加密
            return new DES().encryptStr(str);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * 使用默认密钥进行DES解密
     */
    public static String decrypt(String str){
        try {
//            使用默认key进行DES解密
            return new DES().decryptStr(str);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * 使用指定秘钥进行DES加密
     */
    public static String encrypt(String str,String key){
        try {
//            使用指定key进行DES加密
            return new DES(key).encryptStr(str);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * 使用默认密钥进行DES解密
     */
    public static String decrypt(String str,String key){
        try {
//            使用指定key进行DES解密
            return new DES(key).decryptStr(str);
        }catch (Exception e){
            return e.getMessage();
        }
    }


}
