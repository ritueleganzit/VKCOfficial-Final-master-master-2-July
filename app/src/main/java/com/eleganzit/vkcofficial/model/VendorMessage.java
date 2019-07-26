package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorMessage {
    @SerializedName("vendor_id")
    @Expose
    private String vendorId;  @SerializedName("vendor_name")
    @Expose
    private String vendor_name;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
