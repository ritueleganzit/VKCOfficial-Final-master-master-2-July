package com.eleganzit.vkcofficial.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkPOComplete {

    @SerializedName("mark_po_complete_image_id")
    @Expose
    private String markPoCompleteImageId;
    @SerializedName("image")
    @Expose
    private String image;

    public String getMarkPoCompleteImageId() {
        return markPoCompleteImageId;
    }

    public void setMarkPoCompleteImageId(String markPoCompleteImageId) {
        this.markPoCompleteImageId = markPoCompleteImageId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
