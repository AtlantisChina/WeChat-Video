package com.atlantis.utils;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class MD5Utils {
    /**
     * @Description: 对字符串进行md5加密
     */
    public static String getMD5Str(String strValue) throws Exception {
        String resultStr = null;
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        resultStr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
        return resultStr;
    }

    /**
     * @Description: MD5加密测试
     */
    public static void main(String[] args) {
        try {
            String md5 = getMD5Str("example");
            System.out.println(md5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
