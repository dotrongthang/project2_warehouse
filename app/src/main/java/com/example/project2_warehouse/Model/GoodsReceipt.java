package com.example.project2_warehouse.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoodsReceipt {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Note")
    @Expose
    private String note;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}