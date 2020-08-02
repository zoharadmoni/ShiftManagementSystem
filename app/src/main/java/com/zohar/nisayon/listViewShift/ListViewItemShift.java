package com.zohar.nisayon.listViewShift;

public class ListViewItemShift {
    String employeeAmount, startShift, finishShift;
    boolean check=false;
    String shiftKey="",key;

    public String getShiftKey() {
        return shiftKey;
    }

    public void setShiftKey(String shiftKey) {
        this.shiftKey = shiftKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setEmployeeAmount(String employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public void setStartShift(String startShift) {
        this.startShift = startShift;
    }

    public void setFinishShift(String finishShift) {
        this.finishShift = finishShift;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


    public String getEmployeeAmount() {
        return employeeAmount;
    }
    public String getStartShift() {
        return startShift;
    }

    public String getFinishShift() {
        return finishShift;
    }

    public boolean isCheck() {
        return check;
    }



}
