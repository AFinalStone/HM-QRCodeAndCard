package com.hm.iou.qrcode.bean;

import java.io.Serializable;

/**
 * Created by AFinalStone on 2018/1/22.
 */
public class IOUBriefMoney implements Serializable {


    /**
     * iouId : b043c06c4dcc451b81f59e730122410d
     * iouKind : 1
     * amount : 1***
     * borrowerName : 石要磊
     * borrowerAccount : 602**
     * loanerName : *磊
     * loanerAccount : 152**
     * scheduleReturnDate : 2019.01
     */

    private String iouId;
    private int iouKind;
    private String amount;
    private String borrowerName;
    private String borrowerAccount;
    private String loanerName;
    private String loanerAccount;
    private String scheduleReturnDate;

    public String getIouId() {
        return iouId;
    }

    public void setIouId(String iouId) {
        this.iouId = iouId;

    }

    public int getIouKind() {
        return iouKind;
    }

    public void setIouKind(int iouKind) {
        this.iouKind = iouKind;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getBorrowerAccount() {
        return borrowerAccount;
    }

    public void setBorrowerAccount(String borrowerAccount) {
        this.borrowerAccount = borrowerAccount;
    }

    public String getLoanerName() {
        return loanerName;
    }

    public void setLoanerName(String loanerName) {
        this.loanerName = loanerName;
    }

    public String getLoanerAccount() {
        return loanerAccount;
    }

    public void setLoanerAccount(String loanerAccount) {
        this.loanerAccount = loanerAccount;
    }

    public String getScheduleReturnDate() {
        return scheduleReturnDate;
    }

    public void setScheduleReturnDate(String scheduleReturnDate) {
        this.scheduleReturnDate = scheduleReturnDate;
    }
}
