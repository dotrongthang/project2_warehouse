package com.example.project2_warehouse.Retrofit;

public class APIUtils {

    public static final String Base_Url = "http://172.20.10.3:8080/project2_QLK/";

    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
