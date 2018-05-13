package com.hm.iou.qrcode.bean;

import java.io.Serializable;

public class IOUBriefMoney implements Serializable {


    /**
     * id : 44611e5a891645abab18a770e0e48ac1
     * createTime : 0
     * borrowerName : 石要磊
     * loanerName : *方超
     * scheduleReturnDate : 1522857600000
     * amount : 66544
     * interest : 0
     * borrowerRecvWay : AliPay
     * borrowerAccount : 557**
     * loanerAccount : y76**
     * transDeadLine : 0
     * valueDate : 0
     * expiryDate4Interest : 0
     * amountStr : 6*****
     */

     String id;
     int createTime;
     String borrowerName;
     String loanerName;
     long scheduleReturnDate;
     int amount;
     int interest;
     String borrowerRecvWay;
     String borrowerAccount;
     String loanerAccount;
     int transDeadLine;
     int valueDate;
     int expiryDate4Interest;

 public String getId() {
  return id;
 }

 public void setId(String id) {
  this.id = id;
 }

 public int getCreateTime() {
  return createTime;
 }

 public void setCreateTime(int createTime) {
  this.createTime = createTime;
 }

 public String getBorrowerName() {
  return borrowerName;
 }

 public void setBorrowerName(String borrowerName) {
  this.borrowerName = borrowerName;
 }

 public String getLoanerName() {
  return loanerName;
 }

 public void setLoanerName(String loanerName) {
  this.loanerName = loanerName;
 }

 public long getScheduleReturnDate() {
  return scheduleReturnDate;
 }

 public void setScheduleReturnDate(long scheduleReturnDate) {
  this.scheduleReturnDate = scheduleReturnDate;
 }

 public int getAmount() {
  return amount;
 }

 public void setAmount(int amount) {
  this.amount = amount;
 }

 public int getInterest() {
  return interest;
 }

 public void setInterest(int interest) {
  this.interest = interest;
 }

 public String getBorrowerRecvWay() {
  return borrowerRecvWay;
 }

 public void setBorrowerRecvWay(String borrowerRecvWay) {
  this.borrowerRecvWay = borrowerRecvWay;
 }

 public String getBorrowerAccount() {
  return borrowerAccount;
 }

 public void setBorrowerAccount(String borrowerAccount) {
  this.borrowerAccount = borrowerAccount;
 }

 public String getLoanerAccount() {
  return loanerAccount;
 }

 public void setLoanerAccount(String loanerAccount) {
  this.loanerAccount = loanerAccount;
 }

 public int getTransDeadLine() {
  return transDeadLine;
 }

 public void setTransDeadLine(int transDeadLine) {
  this.transDeadLine = transDeadLine;
 }

 public int getValueDate() {
  return valueDate;
 }

 public void setValueDate(int valueDate) {
  this.valueDate = valueDate;
 }

 public int getExpiryDate4Interest() {
  return expiryDate4Interest;
 }

 public void setExpiryDate4Interest(int expiryDate4Interest) {
  this.expiryDate4Interest = expiryDate4Interest;
 }
}