package cn.gaoh;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    //GTKStyle com.sun.java.swing.plaf.gtk.GTKStyle
    public static void main(String[] args) {
        // write your code here
        Map<String, Integer> map = new HashMap<>(6);
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 4);
        map.put("4", 4);

        System.out.println(new BigDecimal(8 >>> 3).setScale(100, BigDecimal.ROUND_DOWN).toString());
        System.out.println(1 << 3);
        System.out.println(1 << 10);
        System.out.println("===============");
        System.out.println(8 >> 3);
        //1000

        System.out.println("======================================");


        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        for (int i = 1; i < 17; i++) {
            hashMap.put(i, "a" + i);
        }
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println("key= " + entry.getKey() + "  value= " + entry.getValue());
        }
        System.out.println("***************************************");

        for (int i = 0; i < 100; i++) {
            System.out.println((i * 100) & 17);
            System.out.println("==============");
            System.out.println(8 & (i * 100));
        }
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("", 1);
        System.out.println(concurrentHashMap);


        System.out.println("=====================================================");
        System.out.println(63&"564646".hashCode());
        System.out.println("564646".hashCode()&63);

    }

}
