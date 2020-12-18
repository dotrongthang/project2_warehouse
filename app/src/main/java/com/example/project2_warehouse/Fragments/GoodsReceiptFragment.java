package com.example.project2_warehouse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project2_warehouse.Adapters.ListGoodsReceiptAdapter;
import com.example.project2_warehouse.Adapters.ListProductAdapter;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoodsReceiptFragment extends Fragment {
    private RecyclerView rvListGoodsReceipt;
    List<GoodsReceipt> listGoodsReceipt;
    ListGoodsReceiptAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_receipt, container, false);
        rvListGoodsReceipt = (RecyclerView) view.findViewById(R.id.rvListGoodsReceipt);
        listGoodsReceipt = new ArrayList<>();
        GetData();
        return view;
    }

    private void GetData() {
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsReceipt>> call = dataClient.getGoodsReceipt();
        call.enqueue(new Callback<List<GoodsReceipt>>() {
            @Override
            public void onResponse(Call<List<GoodsReceipt>> call, Response<List<GoodsReceipt>> response) {
                ArrayList<GoodsReceipt> list = (ArrayList<GoodsReceipt>) response.body();
                Log.e("GetData", "onResponse: " + new Gson().toJson(list));
                for (int i = 0; i < list.size(); i++) {
                    GoodsReceipt goodsReceipt = list.get(i);
                    listGoodsReceipt.add(goodsReceipt);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvListGoodsReceipt.setHasFixedSize(true);
                rvListGoodsReceipt.setLayoutManager(layoutManager);
                adapter = new ListGoodsReceiptAdapter(listGoodsReceipt, getContext());
                adapter.notifyDataSetChanged();
                rvListGoodsReceipt.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<GoodsReceipt>> call, Throwable t) {

            }
        });
    }

}
