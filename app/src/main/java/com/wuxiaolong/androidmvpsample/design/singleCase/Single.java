package com.wuxiaolong.androidmvpsample.design.singleCase;

public class Single {

    private static volatile Single mInsatnce;

    private Single() { }

    public static Single getInstance() {
        if (mInsatnce==null){
            synchronized (Single.class){
                if (mInsatnce==null){
                    mInsatnce = new Single();
                }
            }
        }
        return mInsatnce;
    }
}
