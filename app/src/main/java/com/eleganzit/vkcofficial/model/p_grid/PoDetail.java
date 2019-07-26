package com.eleganzit.vkcofficial.model.p_grid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PoDetail {

    @SerializedName("pur_doc_num")
    @Expose
    private String purDocNum;
    @SerializedName("article")
    @Expose
    private String article;
    @SerializedName("line_number")
    @Expose
    private String lineNumber;
    @SerializedName("number_of_stichers")
    @Expose
    private String numberOfStichers;
    @SerializedName("number_of_helpers")
    @Expose
    private String numberOfHelpers;
    @SerializedName("vendor_start_time")
    @Expose
    private String vendor_start_time;
    @SerializedName("vendor_end_time")
    @Expose
    private String vendor_end_time;
    @SerializedName("gridData")
    @Expose
    private List<GridDatum> gridData = null;

    public String getVendor_start_time() {
        return vendor_start_time;
    }

    public void setVendor_start_time(String vendor_start_time) {
        this.vendor_start_time = vendor_start_time;
    }

    public String getVendor_end_time() {
        return vendor_end_time;
    }

    public void setVendor_end_time(String vendor_end_time) {
        this.vendor_end_time = vendor_end_time;
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

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getNumberOfStichers() {
        return numberOfStichers;
    }

    public void setNumberOfStichers(String numberOfStichers) {
        this.numberOfStichers = numberOfStichers;
    }

    public String getNumberOfHelpers() {
        return numberOfHelpers;
    }

    public void setNumberOfHelpers(String numberOfHelpers) {
        this.numberOfHelpers = numberOfHelpers;
    }

    public List<GridDatum> getGridData() {
        return gridData;
    }

    public void setGridData(List<GridDatum> gridData) {
        this.gridData = gridData;
    }

}
