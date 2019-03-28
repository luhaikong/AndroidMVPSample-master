package com.wuxiaolong.androidmvpsample.design.dynamicAgent;

public class Student implements IPeople {

    public Student() {
    }

    @Override
    public void learn() {
        System.out.println("Students learning mathematics");
    }

    @Override
    public void sleep() {
        System.out.println("Students sleeping");
    }

}
