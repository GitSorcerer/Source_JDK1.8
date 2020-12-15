package cn.gaoh.rbtree;

import java.util.Scanner;


public class RedBlackTreeTest {
    public static void main(String[] args) {
        //新增节点
//        insertOpt();
        //删除节点
        deleteOpt();
    }

    /**
     * 插入操作
     */
    public static void insertOpt(){
        Scanner scanner=new Scanner(System.in);
        RedBlackTree<String,Object> rbt=new RedBlackTree<>();
        while (true){
            System.out.println("请输入你要插入的节点:");
            String key=scanner.next();
            System.out.println();
            rbt.put(key.length()==1?("0"+key):key,null);
            RedBlackTreeOperation.show(rbt.getRoot());
        }
    }

    /**
     * 删除操作
     */
    public static void deleteOpt(){
        RedBlackTree<String,Object> rbt=new RedBlackTree<>();
        //预先造10个节点（1-10）
        for (int i = 1; i <11 ; i++) {
            rbt.put((i+"").length()==1?"0"+i:i+"",null);
        }
        RedBlackTreeOperation.show(rbt.getRoot());
        //以下开始删除
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("请输入你要删除的节点:");
            String key=scanner.next();
            System.out.println();
            rbt.remove(key.length()==1?"0"+key:key);
            RedBlackTreeOperation.show(rbt.getRoot());
        }
    }
}
