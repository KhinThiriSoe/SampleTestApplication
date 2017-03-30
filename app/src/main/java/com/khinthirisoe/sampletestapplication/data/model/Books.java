
package com.khinthirisoe.sampletestapplication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Books {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
