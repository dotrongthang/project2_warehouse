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

import com.example.project2_warehouse.Adapters.ListChooseCustomerAdapter;
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

public class AddGoodsIssueActivity extends AppCompatActivity implements IPassData {
    private EditText edtProductNameOP;
    private EditText edtCustomerNameOP;
    private EditText edtQuantityOP;
    private TextView tvUnitOP;
    private Button btnSubmitAddOP;
    private Button btnCancelAddOP;
    Dialog dialogP, dialogC;
    ListChooseProductAdapter adapterP;
    ListChooseCustomerAdapter adapterC;
    List<Product> listProductOP;
    List<Customer> customerList;
    String productId, customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_issue);

        edtProductNameOP = (EditText) findViewById(R.id.edtProductNameOP);
        edtCustomerNameOP = (EditText) findViewById(R.id.edtCustomerNameOP);
        edtQuantityOP = (EditText) findViewById(R.id.edtQuantityOP);
        tvUnitOP = (TextView) findViewById(R.id.tvUnitOP);
        btnSubmitAddOP = (Button) findViewById(R.id.btnSubmitAddOP);
        btnCancelAddOP = (Button) findViewById(R.id.btnCancelAddOP);
        listProductOP = new ArrayList<>();
        customerList = new ArrayList<>();

        GetDataProduct();
        GetDataCustomer();

        btnSubmitAddOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertGoodsIssue();
                Intent intent = new Intent(AddGoodsIssueActivity.this, MainActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });

        btnCancelAddOP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddGoodsIssueActivity.this, MainActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });
    }

    @Override
    public void ChooseProduct(Product product) {
        edtProductNameOP.setText(product.getName());
        tvUnitOP.setText("Đơn vị tính: " + product.getUnit());
        productId = product.getID();
        dialogP.dismiss();
    }

    @Override
    public void ChooseCustomer(Customer customer) {
        edtCustomerNameOP.setText(customer.getName());
        customerId = customer.getID();
        dialogC.dismiss();
    }

    public void onClickShowProductOP (View view){   //show list product
        dialogP = new Dialog(this);
        dialogP.setContentView(R.layout.dialog_choose_product);

        RecyclerView rvChoose = (RecyclerView) dialogP.findViewById(R.id.rvChooseProduct);
        rvChoose.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChoose.setLayoutManager(layoutManager);
        adapterP = new ListChooseProductAdapter(listProductOP, this, this);
        rvChoose.setAdapter(adapterP);
        dialogP.show();
    }

    private void GetDataProduct(){
        DataClient dataClient = APIUtils.getData();
        Call<List<Product>> callback = dataClient.getProduct();
        callback.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                ArrayList<Product> listP = (ArrayList<Product>) response.body();
                for (int i = 0; i < listP.size() ; i++) {
                    Product product = listP.get(i);
                    listProductOP.add(product);

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    public void onClickShowCustomerOP (View view){  //show list customer
        dialogC = new Dialog(this);
        dialogC.setContentView(R.layout.dialog_choose_customer);

        RecyclerView rvChooseCustomer = (RecyclerView) dialogC.findViewById(R.id.rvChooseCustomer);
        rvChooseCustomer.hasFixedSize();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvChooseCustomer.setLayoutManager(layoutManager);
        adapterC = new ListChooseCustomerAdapter(customerList, this, this);
        rvChooseCustomer.setAdapter(adapterC);
        dialogC.show();
    }

    private void GetDataCustomer(){
        DataClient dataClient = APIUtils.getData();
        Call<List<Customer>> call = dataClient.getCustomer();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                ArrayList<Customer> listC = (ArrayList<Customer>) response.body();
                customerList.addAll(listC);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    private void InsertGoodsIssue(){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.AddGoodsIssue(productId, customerId,
                edtQuantityOP.getText().toString());
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
