package com.example.project2_warehouse.Retrofit;

import com.example.project2_warehouse.Model.Admin;
import com.example.project2_warehouse.Model.GoodsIssue;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.Model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataClient {

    @GET("getProduct.php")
    Call<List<Product>> getProduct();

    @GET("sortProductByName.php")
    Call<List<Product>> sortProductByName();

    @GET("sortProductByQuantity.php")
    Call<List<Product>> sortProductByQuantity();

    @GET("getGoodsReceipt.php")
    Call<List<GoodsReceipt>> getGoodsReceipt();

    @GET("getGoodsIssue.php")
    Call<List<GoodsIssue>> getGoodsIssue();

    @FormUrlEncoded
    @POST("getAccount.php")
    Call<List<Admin>> getAccount(@Field("user") String user,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("addProduct.php")
    Call<String> addProduct(@Field("productName") String name,
                                    @Field("productUnit") String unit,
                                    @Field("productDescription") String description);
}
