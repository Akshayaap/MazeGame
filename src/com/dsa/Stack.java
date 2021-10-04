package com.dsa;


public class Stack {


    private final int stack[];
    private final int n;
    private int top=-1;
    public Stack(int n){
        this.n=n;
        this.stack=new int[n];
    }

    public void push(int item) throws Throwable {
        if(isFull()){
            throw new Throwable("Cannot push on stack! Stack is full.");
        }
        top++;
        this.stack[top]=item;
    }


    public boolean isEmpty(){
        return top==-1;
    }

    public boolean isFull(){
        return top==n-1;
    }

    public int pop() throws Throwable {
        if(isEmpty()){
            throw new Throwable("Cannot pop from stack! Stack is Empty.");
        }
        return this.stack[top--];
    }

    public int top() throws Throwable {
        if(isEmpty()){
            throw new Throwable("Stack is Empty.");
        }
        return stack[top];
    }
    public  void flush(){
        this.top=-1;
    }
}
