package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleWiseDefect {
    @SerializedName("defect_entry_id")
    @Expose
    private String defectEntryId;
    @SerializedName("defect_product_image")
    @Expose
    private String defectProductImage;

    public String getDefectEntryId() {
        return defectEntryId;
    }

    public void setDefectEntryId(String defectEntryId) {
        this.defectEntryId = defectEntryId;
    }

    public String getDefectProductImage() {
        return defectProductImage;
    }

    public void setDefectProductImage(String defectProductImage) {
        this.defectProductImage = defectProductImage;
    }
}
