package com.lit.harukong.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TB_GroupBean extends Observable implements Observer,Serializable {

    private int id;
    private int pid;
    private String name;
    private boolean isChecked;
    private List<TB_ChildBean> childList = new ArrayList<>();

    public TB_GroupBean() {
        super();
    }


    public TB_GroupBean(int id, int pid, String name, List<TB_ChildBean> childList) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.childList = childList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public List<TB_ChildBean> getChildList() {
        return childList;
    }

    public void setChildList(List<TB_ChildBean> childList) {
        this.childList = childList;
    }

    public void changeChecked() {
        isChecked = !isChecked;
        setChanged();
        notifyObservers(isChecked);
    }

    @Override
    public void update(Observable observable, Object data) {
//        boolean flag = true;
//        for (TB_ChildBean child : childList) {
//            if (child.isChecked() == false) {
//                flag = false;
//            }
//        }
//        this.isChecked = flag;

        if (data instanceof Boolean) {
            this.isChecked = (Boolean) data;
        }
    }
}
