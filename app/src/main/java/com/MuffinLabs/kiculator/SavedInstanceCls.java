package com.MuffinLabs.kiculator;

public class SavedInstanceCls {

    private String appName;
    private String baseFee;
    private String feePerMin;
    private String baseTime;

    public SavedInstanceCls() {
        this.appName = "";
        this.baseFee = "";
        this.baseTime = "";
        this.feePerMin = "";
    }

    public SavedInstanceCls(String appName, String baseFee, String baseTime, String feePerMin) {
        this.appName = appName;
        this.baseFee = baseFee;
        this.baseTime = baseTime;
        this.feePerMin = feePerMin;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(String baseFee) {
        this.baseFee = baseFee;
    }

    public String getFeePerMin() {
        return feePerMin;
    }

    public void setFeePerMin(String feePerMin) {
        this.feePerMin = feePerMin;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }
}
