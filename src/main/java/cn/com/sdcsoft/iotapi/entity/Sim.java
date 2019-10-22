package cn.com.sdcsoft.iotapi.entity;

import java.io.Serializable;


public class Sim implements Serializable {
    private int id, lifecycle,state;
    private String simNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(int lifecycle) {
        this.lifecycle = lifecycle;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }
}
