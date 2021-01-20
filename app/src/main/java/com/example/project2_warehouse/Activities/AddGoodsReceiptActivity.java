package com.example.project2_warehouse.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2_warehouse.Adapters.ListChooseProductAdapter;
import com.example.project2_warehouse.Interfaces.IPassData;
import com.example.project2_warehouse.Model.Customer;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGoodsReceiptActivity extends AppCompatActivity implements IPassData {

    private TextView tvTitleIO;
    private EditText edtProductNameIO;
    private EditText edtQuantityIO;
    private EditText edtNoteIO;
    private TextView tvUnitIO;
    private Button btnSubmitAddIO;
    private Button btnCancelAddIO;
    List<Product> listProductIO;
    ListChooseProductAdapter adapter;
    Dialog dialog;
    String id;  //id of product selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_receipt);

        tvTitleIO = (TextView) findViewById(R.id.tvTitleIO);
        edtProductNameIO = (EditText) findViewById(R.id.edtProductNameIO);
        edtQuantityIO = (EditText) findViewById(R.id.edtQuantityIO);
        edtNoteIO = (EditText) findViewById(R.id.edtNoteIO);
        tvUnitIO = (TextView) findViewById(R.id.tvUnitIO);
        btnSubmitAddIO = (Button) findViewById(R.id.btnSubmitAddIO);
        btnCancelAddIO = (Button) findViewById(R.id.btnCancelAddIO);
        listProductIO = new ArrayList<>();
        GetData();

        btnSubmitAddIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGoodsReceipt();
                Intent intent = new Intent(AddGoodsReceiptActivity.this, MainActivity.class);
                intent.putExtra("key", 2);
                startActivity(intent);
            }
        });

        btnCancelAddIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoodsReceiptActivity.this, MainActivity.class);
                intent.putExtra("key", 2);
                startActivity(intent);
            }
        });
    }

    public void onClickShowProductIO (View view){   //show list product
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_choose_product);


        RecyclerView rvChoose = (RecyclerView) dialog.findViewById(R.id.rvChooseProduct);
        rvChoose.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChoose.setLayoutManager(layoutManager);
        adapter = new ListChooseProductAdapter(listProductIO, this, this);
        rvChoose.setAdapter(adapter);
        dialog.show();
    }

    public void GetData(){
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> callback = dataClient.getProduct();
        callback.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> listP = (ArrayList<Product>) response.body();
                for (int i = 0; i < listP.size() ; i++) {
                    Product product = listP.get(i);
                    listProductIO.add(product);

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void ChooseProduct(Product product) {
        edtProductNameIO.setText(product.getName());
        tvUnitIO.setText("Đơn vị tính: " + product.getUnit());
        id = product.getID();
        dialog.dismiss();
    }

    @Override
    public void ChooseCustomer(Customer customer) {

    }

    private void AddGoodsReceipt(){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.AddGoodsReceipt(id, edtQuantityIO.getText().toString(),
                edtNoteIO.getText().toString());
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
