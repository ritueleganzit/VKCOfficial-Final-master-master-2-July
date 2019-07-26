package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchData
{

    @SerializedName("vendor_id")
    @Expose
    private String vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;

    public String getVendorId() {
        return vendorId;
    }

    public SearchData(String vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}
