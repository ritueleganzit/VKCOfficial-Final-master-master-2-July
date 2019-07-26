package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalVendorDefect {
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("totalDefect")
    @Expose
    private Integer totalDefect;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getTotalDefect() {
        return totalDefect;
    }

    public void setTotalDefect(Integer totalDefect) {
        this.totalDefect = totalDefect;
    }

}
