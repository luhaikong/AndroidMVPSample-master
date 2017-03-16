package com.wuxiaolong.androidmvpsample.mvp.main;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lhk on 2016/9/9.
 */
public class Result implements Serializable {
    private String company;

    private String com;

    private String no;

    private String status;

    private List<Item> item;

    public void setCompany(String company){
        this.company = company;
    }
    public String getCompany(){
        return this.company;
    }
    public void setCom(String com){
        this.com = com;
    }
    public String getCom(){
        return this.com;
    }
    public void setNo(String no){
        this.no = no;
    }
    public String getNo(){
        return this.no;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public void setItem(List<Item> item){
        this.item = item;
    }
    public List<Item> getItem(){
        return this.item;
    }

    @Override
    public String toString() {
        return "Result{" +
                "company='" + company + '\'' +
                ", com='" + com + '\'' +
                ", no='" + no + '\'' +
                ", status='" + status + '\'' +
                ", item=" + item +
                '}';
    }
}
