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

    @GET("sortCustomerByName.php")
    Call<List<Customer>> sortCustomerByName();

    @GET("sortCustomerByAddress.php")
    Call<List<Customer>> sortCustomerByAddress();

    @GET("sortProductByName.php")
    Call<List<Product>> sortProductByName();

    @GET("sortGoodsReceiptByName.php")
    Call<List<GoodsReceipt>> sortGoodsReceiptByName();

    @GET("sortGoodsIssueByName.php")
    Call<List<GoodsIssue>> sortGoodsIssueByName();

    @GET("sortProductByQuantity.php")
    Call<List<Product>> sortProductByQuantity();

    @GET("sortGoodsReceiptByQuantity.php")
    Call<List<GoodsReceipt>> sortGoodsReceiptByQuantity();

    @GET("sortGoodsIssueByQuantity.php")
    Call<List<GoodsIssue>> sortGoodsIssueByQuantity();

    @GET("getGoodsReceipt.php")
    Call<List<GoodsReceipt>> getGoodsReceipt();

    @GET("getGoodsIssue.php")
    Call<List<GoodsIssue>> getGoodsIssue();

    @FormUrlEncoded
    @POST("searchProduct.php")
    Call<List<Product>> searchProduct(@Field("productName") String name);

    @FormUrlEncoded
    @POST("searchGoodsReceipt.php")
    Call<List<GoodsReceipt>> searchGoodsReceipt(@Field("productName") String name);

    @FormUrlEncoded
    @POST("searchGoodsIssue.php")
    Call<List<GoodsIssue>> searchGoodsIssue(@Field("productName") String name);

    @FormUrlEncoded
    @POST("searchCustomer.php")
    Call<List<Customer>> searchCustomer(@Field("customerName") String name);

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
    @POST("addCustomer.php")
    Call<String> addCustomer(@Field("customerName") String name,
                             @Field("phoneNumber") String phone,
                             @Field("address") String address);

    @FormUrlEncoded
    @POST("updateCustomer.php")
    Call<String> UpdateCustomer(@Field("IdCustomer") String id,
                             @Field("PhoneNumber") String phone,
                             @Field("Address") String address);

    @FormUrlEncoded
    @POST("updateProduct.php")
    Call<String> UpdateProduct(@Field("idProduct") String id,
                            @Field("Quantity") String quantity,
                            @Field("Description") String description,
                            @Field("Unit") String unit);

    @FormUrlEncoded
    @POST("updateGoodsReceipt.php")
    Call<String> UpdateGoodsReceipt(@Field("idGoodsReceipt") String id,
                               @Field("Quantity") String quantity,
                               @Field("Note") String note);

    @FormUrlEncoded
    @POST("updateGoodsIssue.php")
    Call<String> UpdateGoodsIssue(@Field("idGoodsIssue") String id,
                                    @Field("Quantity") String quantity);

    @FormUrlEncoded
    @POST("deleteGoodsReceipt.php")
    Call<String> DeleteGoodsReceipt(@Field("ID") String id);

    @FormUrlEncoded
    @POST("deleteGoodsIssue.php")
    Call<String> DeleteGoodsIssue(@Field("ID") String id);

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
