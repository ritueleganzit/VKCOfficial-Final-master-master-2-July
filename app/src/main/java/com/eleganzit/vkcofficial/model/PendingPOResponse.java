package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingPOResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("assign_count")
    @Expose
    private Object assignCount;
    @SerializedName("data")
    @Expose
    private List<PendingPO> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getAssignCount() {
        return assignCount;
    }

    public void setAssignCount(Object assignCount) {
        this.assignCount = assignCount;
    }

    public List<PendingPO> getData() {
        return data;
    }

    public void setData(List<PendingPO> data) {
        this.data = data;
    }
}
