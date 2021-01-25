package cn.gaoh.map;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= 1 << 30) ? 1 << 30 : n + 1;
    }

    //GTKStyle com.sun.java.swing.plaf.gtk.GTKStyle
    public static void main(String[] args) {
        /*System.out.println(Main.tableSizeFor(6));
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
        System.out.println(63 & "564646".hashCode());
        System.out.println("564646".hashCode() & 63);*/

        /*System.out.println("78623 & (32-1) =" + (78623 & (32-1)) + "    78623 & (32-1) =" + (78623 & (16-1))+"     78623 & 16=" + (78623 & 16));
        System.out.println(" 9812 & (32-1) =" + (9812 & (32-1)) +  "     9812 & (32-1) =" + (9812 & (16-1))+ "       9812 & 16=" + (9812 & 16));
        System.out.println(" 6798 & (32-1) =" + (6798 & (32-1)) +  "     6798 & (32-1) =" + (6798 & (16-1))+ "      6798 & 16=" + (6798 & 16));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("i" + i + " j:" + j);
                if (i == 2 && j == 2) {
                    break;
                }
            }
        }*/

        System.out.println(Main.class.getName());

    }

    @Test
    public void test() {
        System.out.println("".hashCode());
    }

}
