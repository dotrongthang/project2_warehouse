package com.example.project2_warehouse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project2_warehouse.Adapters.ListCustomerAdapter;
import com.example.project2_warehouse.Adapters.ListProductAdapter;
import com.example.project2_warehouse.Model.Customer;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerFragment extends Fragment {

    RecyclerView rvCustomer;
    List<Customer> customerList;
    ListCustomerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        rvCustomer = (RecyclerView) view.findViewById(R.id.rvListCustomer);
        customerList = new ArrayList<>();
        GetData();
        return view;
    }

    private void GetData() {
        DataClient dataClient = APIUtils.getData();
        Call<List<Customer>> call = dataClient.getCustomer();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                ArrayList<Customer> list = (ArrayList<Customer>) response.body();
                customerList.addAll(list);
                rvCustomer.hasFixedSize();
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvCustomer.setLayoutManager(layoutManager);
                adapter = new ListCustomerAdapter(customerList, getContext());
                rvCustomer.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }
}
