package com.rangamer.mysatoken.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jesse
 * @date 2021年12月09日 14:38
 */
public class BIJsonUtil {

    public static Map<String, String> getMap(String tmp) {
        //去掉所有空格
        String params = StringUtils.deleteWhitespace(tmp);

        //有序
        HashMap<String, String> map = new LinkedHashMap<>();

        int start = 0, len = params.length();

        while (start < len) {
            int i = params.indexOf(';', start);

            if (i == -1) {
                i = params.length(); // 此时处理最后的键值对
            }

            String keyValue = params.substring(start, i);

            int j = keyValue.indexOf('=');
            String key = keyValue.substring(0, j);
            String value = keyValue.substring(j + 1, keyValue.length());

            map.put(key, value);

            if (i == params.length()) {
                break;
            }
            start = i + 1; // index+1 为下一个键值对的起始位置
        }

        return map;
    }

}
