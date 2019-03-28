package com.wuxiaolong.androidmvpsample.design.dynamicAgent;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler {
    private Object target;
    private ProxyHandlerListener listener;

    /**
     * 绑定委托对象，并返回代理类
     * @param obj
     * @return
     */
    public Object bind(Object obj){
        this.target = obj;
        //绑定该类实现的所有接口，取得代理类
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    /**
     * 设置切面回调函数
     * @param listener
     */
    public void setListener(ProxyHandlerListener listener) {
        this.listener = listener;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Object objectProxy = null;
        if (listener!=null){
            listener.onFrontDo();
            objectProxy = method.invoke(target,objects);
            listener.onNextDo();
        } else {
            objectProxy = method.invoke(target,objects);
        }
        return objectProxy;
    }
}
