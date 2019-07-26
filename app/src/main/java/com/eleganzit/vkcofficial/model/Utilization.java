package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Utilization {
    @SerializedName("capacity")
    @Expose
    private Integer capacity;
    @SerializedName("exp")
    @Expose
    private String exp;
    @SerializedName("inputpair")
    @Expose
    private String inputpair;
    @SerializedName("utilization")
    @Expose
    private String utilization;

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getInputpair() {
        return inputpair;
    }

    public void setInputpair(String inputpair) {
        this.inputpair = inputpair;
    }

    public String getUtilization() {
        return utilization;
    }

    public void setUtilization(String utilization) {
        this.utilization = utilization;
    }

}
