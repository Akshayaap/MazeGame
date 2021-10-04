package com.main;

import com.dsa.Queue;

public class Test {
    public static void main(String[] args) throws Throwable {
        Queue q=new Queue(4);
        System.out.println("======================================");
        q.enqueue(5);
        System.out.println(q);
        System.out.println("======================================");
        q.enqueue(3);
        q.enqueue(1);
        System.out.println("======================================");
        System.out.println(q);
        System.out.println("======================================");
        System.out.println(q.isEmpty());
        System.out.println(q.isFull());
        System.out.println("======================================");
        System.out.println(q);
        System.out.println("======================================");
        System.out.println(q.dequeue());
        System.out.println(q.dequeue());
        System.out.println("======================================");
        System.out.println(q);
        System.out.println(q.dequeue());
        System.out.println("======================================");
        System.out.println(q.dequeue());
        System.out.println("======================================");
    }
}
