package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllPO {
    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("date")
    @Expose
    private String date;

    public String getPurDocNum() {
        return purDocNum;
    }

    public void setPurDocNum(String purDocNum) {
        this.purDocNum = purDocNum;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
