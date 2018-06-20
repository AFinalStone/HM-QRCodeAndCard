package com.hm.iou.qrcode.bean;

import java.io.Serializable;

/**
 * Created by AFinalStone on 2018/1/22.
 */
public class IOUBriefMoney implements Serializable {


    /**
     * id : 8417339447a043eebc9c531fd7448e21
     * iouKind : 1
     * thingsName : null
     * thingsType : null
     * brokerageName : null
     * senderName : null
     * opDate : null
     * memo : null
     */
    private String id;
    private int iouKind;
    private String thingsName;
    private int thingsType;
    private String brokerageName;
    private String senderName;
    private String opDate;
    private String memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIouKind() {
        return iouKind;
    }

    public void setIouKind(int iouKind) {
        this.iouKind = iouKind;
    }

    public String getThingsName() {
        return thingsName;
    }

    public void setThingsName(String thingsName) {
        this.thingsName = thingsName;
    }

    public int getThingsType() {
        return thingsType;
    }

    public void setThingsType(int thingsType) {
        this.thingsType = thingsType;
    }

    public String getBrokerageName() {
        return brokerageName;
    }

    public void setBrokerageName(String brokerageName) {
        this.brokerageName = brokerageName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
