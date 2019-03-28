package com.wuxiaolong.androidmvpsample.design.singleCase;

public class Signle1 {

    public static Signle1 getInsatnce(){
        return Holder.mInstance;
    }

    public static class Holder {
        static Signle1 mInstance = new Signle1();
    }

    private Signle1() { }
}
