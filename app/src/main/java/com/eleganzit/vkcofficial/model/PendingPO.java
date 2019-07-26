package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingPO {
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("doc_date")
    @Expose
    private String docDate;
    @SerializedName("complete")
    @Expose
    private Integer complete;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }
}
