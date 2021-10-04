package com.dsa;

import java.util.Arrays;

public class Queue {
    private final int size;
    private final int[] queue;
    private int front = 0;
    private int rear = 0;

    public Queue(int size) {
        this.size = size+1;
        this.queue = new int[this.size];
    }

    public Queue() {
        this.size = 2;
        queue = new int[size+1];
    }

    public void enqueue(int num) throws Throwable {
        if (!this.isFull()) {
            this.queue[this.rear++] = num;
            if (this.rear >= this.size) {
                this.rear = 0;
            }
        } else {
            throw new Throwable("Cannot Enqueue Queue ! Queue is Full");
        }

    }

    public int dequeue() throws Throwable {
        int temp;
        if (!this.isEmpty()) {
            temp = this.queue[this.front++];
            if (this.front >= size) {
                this.front = 0;
            }
            return temp;
        } else {
            throw new Throwable("Cannot Dequeue Queue ! Queue is Empty");
        }
    }

    public boolean isFull() {
        return (this.rear + 1) % this.size == this.front;
    }

    public boolean isEmpty() {
        return front == rear;
    }

    public int getFront() {
        return front;
    }

    public int getSize() {
        return size-1;
    }

    public int getRear() {
        return rear;
    }

    @Override
    public String toString() {

        String qStr = "";

        for (int i = this.front; i < this.rear; i = (i + 1) % this.size) {
            qStr += this.queue[i] + "   ";
        }
        return "Queque{" +
                "size=" + (size -1)+
                ", queue=" + qStr +
                ", front=" + front +
                ", rear=" + rear +
                '}';
    }

    public void flush(){
        this.front=0;
        this.rear=0;
    }
}

