package com.wuxiaolong.androidmvpsample.design.dynamicAgent;

public class Doctor implements IPeople {


    public Doctor() {
    }

    @Override
    public void learn() {
        System.out.println("Doctors learning medicine");
    }

    @Override
    public void sleep() {
        System.out.println("Doctors sleeping");
    }
}
