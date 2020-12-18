package com.example.project2_warehouse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project2_warehouse.Adapters.ListGoodsIssueAdapter;
import com.example.project2_warehouse.Adapters.ListGoodsReceiptAdapter;
import com.example.project2_warehouse.Model.GoodsIssue;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodsIssueFragment extends Fragment {
    private RecyclerView rvListGoodsIssue;
    List<GoodsIssue> listGoodsIssue;
    ListGoodsIssueAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_issue, container, false);
        rvListGoodsIssue = (RecyclerView) view.findViewById(R.id.rvListGoodsIssue);
        listGoodsIssue = new ArrayList<>();
        GetData();
        return view;
    }

    private void GetData() {
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsIssue>> call = dataClient.getGoodsIssue();
        call.enqueue(new Callback<List<GoodsIssue>>() {
            @Override
            public void onResponse(Call<List<GoodsIssue>> call, Response<List<GoodsIssue>> response) {
                ArrayList<GoodsIssue> list = (ArrayList<GoodsIssue>) response.body();
                listGoodsIssue.addAll(list);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rvListGoodsIssue.setHasFixedSize(true);
                rvListGoodsIssue.setLayoutManager(layoutManager);
                adapter = new ListGoodsIssueAdapter(listGoodsIssue, getContext());
                adapter.notifyDataSetChanged();
                rvListGoodsIssue.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<List<GoodsIssue>> call, Throwable t) {

            }
        });
    }
}
