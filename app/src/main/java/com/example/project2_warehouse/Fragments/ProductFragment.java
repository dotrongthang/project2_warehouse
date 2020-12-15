package com.example.project2_warehouse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project2_warehouse.Adapters.ListProductAdapter;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductFragment extends Fragment {
    private RecyclerView rvListProduct;
    List<Product> listProduct;
    ListProductAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        rvListProduct = (RecyclerView) view.findViewById(R.id.rvListProduct);
        listProduct = new ArrayList<>();
//        changeInfo = (ChangeInfo) getActivity();
        GetData();
        return view;
    }

    private void GetData(){
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> callback = dataClient.getProduct();
        callback.enqueue(new Callback<List<Product>>() {
           @Override
           public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> listP = (ArrayList<Product>) response.body();
               for (int i = 0; i < listP.size() ; i++) {
                   Product product = listP.get(i);
                   listProduct.add(product);
                   rvListProduct.hasFixedSize();
                   LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                   rvListProduct.setLayoutManager(layoutManager);
                   adapter = new ListProductAdapter(listProduct, getContext());
                   rvListProduct.setAdapter(adapter);
               }
           }

           @Override
           public void onFailure(Call<List<Product>> call, Throwable t) {

           }
       });
    }
}
