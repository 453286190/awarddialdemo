package com.example.awarddialdemo.util;

import com.sun.crypto.provider.SunJCE;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * DES加密类
 * DES全称为Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法
 * 加密字节数组：普通字节数组直接加密encryptCipher.doFinal(byteArray)，返回加密的字节数组
 * 加密字符串：先将普通字符串转成普通字节数组，再加密普通的字节数组，再将加密的字节数组转换成（16进制化的）16进制的字符串，返回加密的16进制字符串
 * 解密字节数组：直接解密decryptCipher.doFianl(byteArray)，返回解密的字节数组
 * 解密字符串：先将16进制的字符串转换成（去16进制化的）加密的字节数组，再解密加密的字节数组，再转成普通字符串，返回解密的字符串
 * 加密的字节数组互转加密的16进制字符串：字符串的长度是字节数组的2倍
 * @param
 * @author: 闫沛鑫
 * @date: 2019-04-18 18:00
 * @return
 */
public class DES {

    //默认秘钥
    private static String strDefaultKey = "abcDEF789";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;


    /**
     * 默认构造,使用默认秘钥
     */
    public DES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        this(strDefaultKey);
    }

    /**
     * 指定秘钥构造方法
     * @param strKey
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public DES(String strKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Security.addProvider(new SunJCE());
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * 加密字符串
     * @param str  加密前的字符串
     * @return     加密后的字符串
     */
    public String encryptStr(String str) throws BadPaddingException, IllegalBlockSizeException {
        return byteArr2HexStr(encryptByteArray(str.getBytes()));
    }

    /**
     * 加密字节数组
     * @param byteArray
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] encryptByteArray(byte[] byteArray) throws BadPaddingException, IllegalBlockSizeException {
        return encryptCipher.doFinal(byteArray);
    }

    /**
     * 解密字节数组
     * @param byteArray
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] decryptByteArray(byte[] byteArray) throws BadPaddingException, IllegalBlockSizeException {
        return decryptCipher.doFinal(byteArray);
    }

    /**
     * 解密字符串
     * @param str
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public String decryptStr(String str) throws BadPaddingException, IllegalBlockSizeException {
        return new String(decryptByteArray(hexStr2ByteArr(str)));
    }



    /**
     * 根据字符串字节数组生成秘钥，只取8位
     * 不足8位后面补0，超出8位后面舍去
     * @param arrByte
     * @return
     */
    private Key getKey(byte[] arrByte){
        byte[] arr = new byte[8];
        for(int i = 0; i < arr.length && i < arrByte.length; i++){
            arr[i] = arrByte[i];
        }
        Key key = new SecretKeySpec(arr,"DES");
        return key;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串，
     * 如：byte[]{8,18}转换为：0813，
     * 和public static byte[] hexStr2ByteArr(String strIn)
     * 互为可逆的转换过程
     * @param byteArrIn 需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] byteArrIn){
        int len = byteArrIn.length;
        StringBuffer sb = new StringBuffer(len * 2);
        for(int i = 0;i < len;i++){
            int intTmp = byteArrIn[i];
            while (intTmp < 0){
                intTmp += 256;
            }
            if(intTmp < 16){
                sb.append("0");
            }
//            16进制的字符串
            sb.append(Integer.toString(intTmp,16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组，
     * 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param hexStrIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String hexStrIn){
        byte[] byteArrIn = hexStrIn.getBytes();
        int lenIn = byteArrIn.length;
        byte[] byteArrOut = new byte[lenIn / 2];
        for(int i = 0;i < lenIn;i = i + 2){
            String strTmp = new String(byteArrIn,i,2);
            byteArrOut[i / 2] = (byte)Integer.parseInt(strTmp,16);
        }
        return byteArrOut;
    }




}
