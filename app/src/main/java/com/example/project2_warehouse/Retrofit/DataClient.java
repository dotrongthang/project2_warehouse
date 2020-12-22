package com.example.project2_warehouse.Retrofit;

import com.example.project2_warehouse.Model.Admin;
import com.example.project2_warehouse.Model.Customer;
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

    @GET("getCustomer.php")
    Call<List<Customer>> getCustomer();

    @GET("sortProductByName.php")
    Call<List<Product>> sortProductByName();

    @GET("sortProductByQuantity.php")
    Call<List<Product>> sortProductByQuantity();

    @GET("getGoodsReceipt.php")
    Call<List<GoodsReceipt>> getGoodsReceipt();

    @GET("getGoodsIssue.php")
    Call<List<GoodsIssue>> getGoodsIssue();

    @FormUrlEncoded
    @POST("searchProduct.php")
    Call<List<Product>> searchProduct(@Field("productName") String name);

    @FormUrlEncoded
    @POST("getAccount.php")
    Call<List<Admin>> getAccount(@Field("user") String user,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST("addProduct.php")
    Call<String> addProduct(@Field("productName") String name,
                            @Field("productUnit") String unit,
                            @Field("productDescription") String description);

    @FormUrlEncoded
    @POST("insertGoodsReceipt.php")
    Call<String> AddGoodsReceipt(@Field("ProductId") String id,
                                 @Field("Quantity") String quantity,
                                 @Field("Note") String note);

    @FormUrlEncoded
    @POST("insertGoodsIssue.php")
    Call<String> AddGoodsIssue(@Field("ProductId") String productId,
                                 @Field("CustomerId") String customerId,
                                 @Field("Quantity") String quantity);
}
