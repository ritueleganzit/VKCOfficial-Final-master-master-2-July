package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("offical_id")
    @Expose
    private String officalId;
    @SerializedName("offical_name")
    @Expose
    private String officalName;
    @SerializedName("offical_phone_no")
    @Expose
    private String officalPhoneNo;
    @SerializedName("offical_email_id")
    @Expose
    private String officalEmailId;
    @SerializedName("offical_status")
    @Expose
    private String officalStatus;

    public String getOfficalId() {
        return officalId;
    }

    public void setOfficalId(String officalId) {
        this.officalId = officalId;
    }

    public String getOfficalName() {
        return officalName;
    }

    public void setOfficalName(String officalName) {
        this.officalName = officalName;
    }

    public String getOfficalPhoneNo() {
        return officalPhoneNo;
    }

    public void setOfficalPhoneNo(String officalPhoneNo) {
        this.officalPhoneNo = officalPhoneNo;
    }

    public String getOfficalEmailId() {
        return officalEmailId;
    }

    public void setOfficalEmailId(String officalEmailId) {
        this.officalEmailId = officalEmailId;
    }

    public String getOfficalStatus() {
        return officalStatus;
    }

    public void setOfficalStatus(String officalStatus) {
        this.officalStatus = officalStatus;
    }
}
