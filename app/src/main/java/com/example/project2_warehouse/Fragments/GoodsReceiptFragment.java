package com.example.project2_warehouse.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_warehouse.Adapters.ListGoodsReceiptAdapter;
import com.example.project2_warehouse.Interfaces.IChangeIP;
import com.example.project2_warehouse.Activities.MainActivity;
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
    IChangeIP iChangeIP;
    Boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_receipt, container, false);
        rvListGoodsReceipt = (RecyclerView) view.findViewById(R.id.rvListGoodsReceipt);
        listGoodsReceipt = new ArrayList<>();
        iChangeIP = (IChangeIP) getActivity();

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
                adapter = new ListGoodsReceiptAdapter(listGoodsReceipt, getContext(), iChangeIP );

                rvListGoodsReceipt.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<GoodsReceipt>> call, Throwable t) {

            }
        });
    }

    public void SortGoodsReceipt(int key) {
        switch (key){
            case 1: GetDataSortName();
                break;
            case 2: GetDataSortQuantity();
                break;

        }
    }

    private void GetDataSortName() {    //sort by name
        listGoodsReceipt.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsReceipt>> call = dataClient.sortGoodsReceiptByName();
        call.enqueue(new Callback<List<GoodsReceipt>>() {
            @Override
            public void onResponse(Call<List<GoodsReceipt>> call, Response<List<GoodsReceipt>> response) {
                ArrayList<GoodsReceipt> list = (ArrayList<GoodsReceipt>) response.body();
                listGoodsReceipt.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GoodsReceipt>> call, Throwable t) {

            }
        });
    }

    private void GetDataSortQuantity() {    //sort by quantity
        listGoodsReceipt.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsReceipt>> call = dataClient.sortGoodsReceiptByQuantity();
        call.enqueue(new Callback<List<GoodsReceipt>>() {
            @Override
            public void onResponse(Call<List<GoodsReceipt>> call, Response<List<GoodsReceipt>> response) {
                ArrayList<GoodsReceipt> list = (ArrayList<GoodsReceipt>) response.body();
                listGoodsReceipt.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GoodsReceipt>> call, Throwable t) {

            }
        });
    }

    public void SearchGoodsReceipt(String name){
        listGoodsReceipt.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<GoodsReceipt>> call = dataClient.searchGoodsReceipt(name);
        call.enqueue(new Callback<List<GoodsReceipt>>() {
            @Override
            public void onResponse(Call<List<GoodsReceipt>> call, Response<List<GoodsReceipt>> response) {
                ArrayList<GoodsReceipt> list = (ArrayList<GoodsReceipt>) response.body();
                listGoodsReceipt.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GoodsReceipt>> call, Throwable t) {

            }
        });
    }

    public void Edit(final GoodsReceipt goodsReceipt){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_goods_receipt);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvNameProduct = (TextView) dialog.findViewById(R.id.tvNameProductIP);
        final EditText edtQuantityProduct = (EditText) dialog.findViewById(R.id.edtQuantityProductIP);
        final EditText edtNote = (EditText) dialog.findViewById(R.id.edtNoteProductIP);
        Button btnSubmitEdit = (Button) dialog.findViewById(R.id.btnSubmitEditIP);
        Button btnCancelEdit = (Button) dialog.findViewById(R.id.btnCancelEditIP);

        tvNameProduct.setText("Tên sản phẩm: " +goodsReceipt.getProduct());

        btnSubmitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtQuantityProduct.getText().toString().isEmpty() || edtNote.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    EditToDB(goodsReceipt.getID(), edtQuantityProduct.getText().toString(),
                            edtNote.getText().toString());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("key", 2);
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

    public void EditToDB(String id, String quantity, String note){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.UpdateGoodsReceipt(id, quantity, note);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void DeleteGoodsReceipt(final GoodsReceipt goodsReceipt) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Xóa phiếu nhập hàng");
        builder.setMessage("Bạn có chắc chắn muốn xóa phiếu nhập -" +
                goodsReceipt.getProduct() + "- không?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = goodsReceipt.getID();
                Delete(id);
                iChangeIP.Success(1);
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                check = false;
                dialog.cancel();
            }
        });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }

    public void Delete(String id){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.DeleteGoodsReceipt(id);
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
