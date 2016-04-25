package com.lit.harukong.bean;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class TB_ChildBean extends Observable implements Observer,Serializable {

    private int id;
    private int pid;
    private String name;
    private boolean isChecked;

    public TB_ChildBean() {
        super();
    }

    public TB_ChildBean(int id, int pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
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

    public void changeChecked() {
        isChecked = !isChecked;
        setChanged();
        notifyObservers();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (data instanceof Boolean) {
            this.isChecked = (Boolean) data;
        }
    }
}
