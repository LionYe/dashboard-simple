/*
 *
 *  *  奇象理财
 *  *  Copyright (c) 2015-2015 qixianglicai.com,Inc.All Rights Reserved.
 *
 */
package com.harbor.dashboardsimple.util;

/**
 *
 */
public class MD5Util {
    /**
     * MD5加密
     */
    public static String toMd5Hex32(String pwd) {
        return MD5.md5Hex32(pwd, "iso-8859-1");
    }

    /**
     * MD5加密Dual
     */
    public static String toMd5Hex32Dual(String pwd) {
        return MD5.md5Hex32(MD5.md5Hex32(pwd, "iso-8859-1"),"iso-8859-1");
    }

    /**
     * MD5加密
     */
    public static String toMd5Hex16(String pwd) {
        return MD5.md5Hex16(pwd, "iso-8859-1");
    }


}
