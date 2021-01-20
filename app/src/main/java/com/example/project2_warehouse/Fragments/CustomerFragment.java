package com.example.project2_warehouse.Fragments;

import android.app.Dialog;
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

import com.example.project2_warehouse.Adapters.ListCustomerAdapter;
import com.example.project2_warehouse.Interfaces.IEdit;
import com.example.project2_warehouse.Activities.MainActivity;
import com.example.project2_warehouse.Model.Customer;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerFragment extends Fragment {

    RecyclerView rvCustomer;
    List<Customer> customerList;
    ListCustomerAdapter adapter;
    IEdit iEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        rvCustomer = (RecyclerView) view.findViewById(R.id.rvListCustomer);
        customerList = new ArrayList<>();
        iEdit = (IEdit) getActivity();
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
                adapter = new ListCustomerAdapter(customerList, getContext(), iEdit);
                rvCustomer.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public void SearchCustomer(String name){
        customerList.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<Customer>> call = dataClient.searchCustomer(name);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                ArrayList<Customer> list = (ArrayList<Customer>) response.body();
                customerList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public void SortCustomer(int key) {
        switch (key){
            case 1: GetDataSortName();
                break;
            case 2: GetDataSortAddress();
                break;
        }
    }

    private void GetDataSortAddress() {     //sort by address
        customerList.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<Customer>> call = dataClient.sortCustomerByAddress();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                ArrayList<Customer> list = (ArrayList<Customer>) response.body();
                customerList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    private void GetDataSortName() {    //sort by name
        customerList.clear();
        DataClient dataClient = APIUtils.getData();
        Call<List<Customer>> call = dataClient.sortCustomerByName();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                ArrayList<Customer> list = (ArrayList<Customer>) response.body();
                customerList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }

    public void Edit(final Customer customer){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_customer);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvNameCustomer = (TextView) dialog.findViewById(R.id.tvNameCustomer);
        final EditText edtPhoneNumber = (EditText) dialog.findViewById(R.id.edtPhoneNumberE);
        final EditText edtAddress = (EditText) dialog.findViewById(R.id.edtAddressE);
        Button btnSubmitEdit = (Button) dialog.findViewById(R.id.btnSubmitCustomerE);
        Button btnCancelEdit = (Button) dialog.findViewById(R.id.btnCancelCustomerE);

        tvNameCustomer.setText("Tên khách hàng: " + customer.getName());

        btnSubmitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddress.getText().toString().isEmpty() || edtPhoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    EditToDB(customer.getID(), edtPhoneNumber.getText().toString(),
                            edtAddress.getText().toString());
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("key", 4);
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

    public void EditToDB(String id, String phoneNumber, String address){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.UpdateCustomer(id, phoneNumber, address);
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
