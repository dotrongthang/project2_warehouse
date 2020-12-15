package com.example.project2_warehouse.Retrofit;

import com.example.project2_warehouse.Model.Admin;
import com.example.project2_warehouse.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DataClient {

    @GET("getProduct.php")
    Call<List<Product>> getProduct();

    @FormUrlEncoded
    @POST("getAccount.php")
    Call<List<Admin>> getAccount(@Field("user") String user,
                                 @Field("password") String password);

}
