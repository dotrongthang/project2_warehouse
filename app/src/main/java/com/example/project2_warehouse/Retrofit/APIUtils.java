package com.example.project2_warehouse.Retrofit;

public class APIUtils {

    public static final String Base_Url = "http://192.168.1.9:8080/project2_QLK/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
