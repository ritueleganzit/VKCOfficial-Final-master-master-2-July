package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletePoNotification {@SerializedName("vendor_name")
@Expose
private String vendorName;
    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getPurDocNum() {
        return purDocNum;
    }

    public void setPurDocNum(String purDocNum) {
        this.purDocNum = purDocNum;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
