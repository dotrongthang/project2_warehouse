package com.example.project2_warehouse.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoodsIssue {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("Customer")
    @Expose
    private String customer;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Date")
    @Expose
    private String date;

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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

}
