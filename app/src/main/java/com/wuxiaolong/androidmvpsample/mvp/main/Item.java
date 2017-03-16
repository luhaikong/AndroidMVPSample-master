package com.wuxiaolong.androidmvpsample.mvp.main;

import java.io.Serializable;

/**
 * Created by lhk on 2016/9/9.
 */
public class Item implements Serializable{
    private String datetime;

    private String remark;

    private String zone;

    public void setDatetime(String datetime){
        this.datetime = datetime;
    }
    public String getDatetime(){
        return this.datetime;
    }
    public void setRemark(String remark){
        this.remark = remark;
    }
    public String getRemark(){
        return this.remark;
    }
    public void setZone(String zone){
        this.zone = zone;
    }
    public String getZone(){
        return this.zone;
    }

    @Override
    public String toString() {
        return "Item{" +
                "datetime='" + datetime + '\'' +
                ", remark='" + remark + '\'' +
                ", zone='" + zone + '\'' +
                '}';
    }
}
