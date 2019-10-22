package cn.com.sdcsoft.iotapi.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class UpdateHistory implements Serializable {
    private int id, rawState,nowState;
    private String simNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRawState() {
        return rawState;
    }

    public void setRawState(int rawState) {
        this.rawState = rawState;
    }

    public int getNowState() {
        return nowState;
    }

    public void setNowState(int nowState) {
        this.nowState = nowState;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
}
