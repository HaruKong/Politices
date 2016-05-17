package com.lit.harukong.bean;

/**
 * Created by haru on 2016/5/17.
 */
public class CountBean {
    private String name;// 部门
    private int total;// 问政总数
    private int state0;// 0未回应
    private int state1;// 1已关注
    private int s1_in_time0;// 0关注及时
    private int s1_in_time1;// 1关注超时
    private int state2;// 2已回复
    private int s2_in_time0;// 0回复及时
    private int s2_in_time1;// 1回复超时
    private int bid;

    public CountBean() {
        super();
    }


    public CountBean(String name, int total, int state0, int state1, int s1_in_time0, int s1_in_time1, int state2, int s2_in_time0, int s2_in_time1, int bid) {
        this.name = name;
        this.total = total;
        this.state0 = state0;
        this.state1 = state1;
        this.s1_in_time0 = s1_in_time0;
        this.s1_in_time1 = s1_in_time1;
        this.state2 = state2;
        this.s2_in_time0 = s2_in_time0;
        this.s2_in_time1 = s2_in_time1;
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getState0() {
        return state0;
    }

    public void setState0(int state0) {
        this.state0 = state0;
    }

    public int getState1() {
        return state1;
    }

    public void setState1(int state1) {
        this.state1 = state1;
    }

    public int getS1_in_time0() {
        return s1_in_time0;
    }

    public void setS1_in_time0(int s1_in_time0) {
        this.s1_in_time0 = s1_in_time0;
    }

    public int getS1_in_time1() {
        return s1_in_time1;
    }

    public void setS1_in_time1(int s1_in_time1) {
        this.s1_in_time1 = s1_in_time1;
    }

    public int getState2() {
        return state2;
    }

    public void setState2(int state2) {
        this.state2 = state2;
    }

    public int getS2_in_time0() {
        return s2_in_time0;
    }

    public void setS2_in_time0(int s2_in_time0) {
        this.s2_in_time0 = s2_in_time0;
    }

    public int getS2_in_time1() {
        return s2_in_time1;
    }

    public void setS2_in_time1(int s2_in_time1) {
        this.s2_in_time1 = s2_in_time1;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }
}
