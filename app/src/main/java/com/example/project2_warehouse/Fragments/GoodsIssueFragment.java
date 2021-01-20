package com.example.project2_warehouse.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_warehouse.Adapters.ListGoodsIssueAdapter;
import com.example.project2_warehouse.Interfaces.IChangeIP;
import com.example.project2_warehouse.Activities.MainActivity;
import com.example.project2_warehouse.Model.GoodsIssue;
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
    IChangeIP iChangeIP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_issue, container, false);
        rvListGoodsIssue = (RecyclerView) view.findViewById(R.id.rvListGoodsIssue);
        listGoodsIssue = new ArrayList<>();
        iChangeIP = (IChangeIP) getActivity();
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
                adapter = new ListGoodsIssueAdapter(listGoodsIssue, getContext(), iChangeIP);
                adapter.notifyDataSetChanged();
                rvListGoodsIssue.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<List<GoodsIssue>> call, Throwable t) {

            }
        });
    }

    public void SortGoodsIssue(int key) {
        switch (key){
            case 1: GetDataSortName();
                break;
            case 2: GetDataSortQuantity();
                break;

        }
    }

    private void GetDataSortQuantity() {    //sort by quantity
        listGoodsIssue.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsIssue>> call = dataClient.sortGoodsIssueByQuantity();
        call.enqueue(new Callback<List<GoodsIssue>>() {
            @Override
            public void onResponse(Call<List<GoodsIssue>> call, Response<List<GoodsIssue>> response) {
                ArrayList<GoodsIssue> list = (ArrayList<GoodsIssue>) response.body();
                listGoodsIssue.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GoodsIssue>> call, Throwable t) {

            }
        });
    }

    private void GetDataSortName() {    //sort by name
        listGoodsIssue.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsIssue>> call = dataClient.sortGoodsIssueByName();
        call.enqueue(new Callback<List<GoodsIssue>>() {
            @Override
            public void onResponse(Call<List<GoodsIssue>> call, Response<List<GoodsIssue>> response) {
                ArrayList<GoodsIssue> list = (ArrayList<GoodsIssue>) response.body();
                listGoodsIssue.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GoodsIssue>> call, Throwable t) {

            }
        });
    }

    public void SearchGoodsIssue(String name){
        listGoodsIssue.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsIssue>> call = dataClient.searchGoodsIssue(name);
        call.enqueue(new Callback<List<GoodsIssue>>() {
            @Override
            public void onResponse(Call<List<GoodsIssue>> call, Response<List<GoodsIssue>> response) {
                ArrayList<GoodsIssue> list = (ArrayList<GoodsIssue>) response.body();
                listGoodsIssue.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GoodsIssue>> call, Throwable t) {

            }
        });
    }

    public void DeleteGoodsIssue(final GoodsIssue goodsIssue) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Xóa phiếu xuất hàng");
        builder.setMessage("Bạn có chắc chắn muốn xóa phiếu xuất -" +
                goodsIssue.getProduct() + "- không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = goodsIssue.getID();
                Delete(id);
                iChangeIP.Success(2);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void Delete(String id){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.DeleteGoodsIssue(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void Edit(final GoodsIssue goodsIssue){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_goods_issue);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvNameProduct = (TextView) dialog.findViewById(R.id.tvNameProductOP);
        final EditText edtQuantityProduct = (EditText) dialog.findViewById(R.id.edtQuantityProductOP);
        Button btnSubmitEdit = (Button) dialog.findViewById(R.id.btnSubmitEditOP);
        Button btnCancelEdit = (Button) dialog.findViewById(R.id.btnCancelEditOP);

        tvNameProduct.setText("Tên sản phẩm: " + goodsIssue.getProduct());

        btnSubmitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtQuantityProduct.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    EditToDB(goodsIssue.getID(), edtQuantityProduct.getText().toString());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("key", 3);
                    startActivity(intent);
                    dialog.dismiss();
                }

            }
        });

        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void EditToDB(String id, String quantity){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.UpdateGoodsIssue(id, quantity);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
