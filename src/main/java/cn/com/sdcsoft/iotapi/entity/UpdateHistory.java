package cn.com.sdcsoft.iotapi.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class UpdateHistory implements Serializable {
    private int id,type;
    private String simNo, rawState,nowState;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRawState() {
        return rawState;
    }

    public void setRawState(String rawState) {
        this.rawState = rawState;
    }

    public String getNowState() {
        return nowState;
    }

    public void setNowState(String nowState) {
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
