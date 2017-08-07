package com.xtelsolution.xmec.common;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper {

    public static String getEncode(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }

    public static String getDecode(String s) {
        return new String(Base64.decodeBase64(s.getBytes()));
    }
}