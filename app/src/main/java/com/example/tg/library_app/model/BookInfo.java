package com.example.tg.library_app.model;



/**
 * Created by tg on 2017/4/23 0023.
 *
 * 图书信息类
 */

public class BookInfo {
    private String id;  //图书id
    private String name;    //书名
    private String barCode; //条型码
    private String borrow;  //借书时间
    private String revert;  //还书时间
    private int times;  //续借次数
    private String address; //分配地址
    private Boolean state;  //是否超期
    private String xjurl;   //续借url地址


    public BookInfo(String id, String name, String barCode, String borrow, String revert, int times, String address, Boolean state) {
        this.id = id;
        this.name = name;
        this.barCode = barCode;
        this.borrow = borrow;
        this.revert = revert;
        this.times = times;
        this.address = address;
        this.state = state;
    }

    public BookInfo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBorrow() {
        return borrow;
    }

    public void setBorrow(String borrow) {
        this.borrow = borrow;
    }

    public String getRevert() {
        return revert;
    }

    public void setRevert(String revert) {
        this.revert = revert;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getXjurl() {
        return xjurl;
    }

    public void setXjurl(String xjurl) {
        this.xjurl = xjurl;
    }

    @Override
    public String toString() {
        return "BookInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", barCode='" + barCode + '\'' +
                ", borrow='" + borrow + '\'' +
                ", revert='" + revert + '\'' +
                ", times=" + times +
                ", address='" + address + '\'' +
                ", state=" + state +
                ", xjurl='" + xjurl + '\'' +
                '}';
    }
}

