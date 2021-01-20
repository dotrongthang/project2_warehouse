package com.example.project2_warehouse.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_warehouse.Fragments.CustomerFragment;
import com.example.project2_warehouse.Fragments.GoodsIssueFragment;
import com.example.project2_warehouse.Fragments.GoodsReceiptFragment;
import com.example.project2_warehouse.Fragments.ProductFragment;
import com.example.project2_warehouse.Interfaces.IChangeIP;
import com.example.project2_warehouse.Interfaces.IEdit;
import com.example.project2_warehouse.Model.Customer;
import com.example.project2_warehouse.Model.GoodsIssue;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IEdit, IChangeIP {

    Fragment fragment;
    private EditText edtSearch;
    private ImageButton imgAdd, imgSearch;
    private LinearLayout lnSortProduct;     //sort product, goodsreceipt, goodsissue
    private LinearLayout lnSortCustomer;    //sort customer
    ProductFragment productFragment;
    GoodsReceiptFragment goodsReceiptFragment;
    GoodsIssueFragment goodsIssueFragment;
    CustomerFragment customerFragment;
    int key = 0;

    public int tmp = 0;
    /*tmp = 1: product
        tmp = 2: goodsReceipt
        tmp = 3: goodsIssue
        tmp = 4: Customer
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        imgAdd = (ImageButton) findViewById(R.id.imgAdd);
        lnSortProduct = (LinearLayout) findViewById(R.id.lnSortProduct);
        lnSortCustomer = (LinearLayout) findViewById(R.id.lnSortCustomer);
        imgSearch = (ImageButton) findViewById(R.id.imgSearch);

        Intent intent = getIntent();
        key = intent.getIntExtra("key", -9999); // get key fragment

        switch (key){
            case 2: fragment = new GoodsReceiptFragment();
                loadFragment(fragment);
                tmp = 2;
                break;
            case 3:fragment = new GoodsIssueFragment();
                loadFragment(fragment);
                tmp = 3;
                break;
            case 4:fragment = new CustomerFragment();
                loadFragment(fragment);
                tmp = 4;
                break;
            default:fragment = new ProductFragment();
                loadFragment(fragment);
                tmp = 1;
                break;
        }



        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //add new
                switch (tmp){
                    case 1:  AddProduct();
                        break;
                    case 2: startActivity(new Intent(MainActivity.this, AddGoodsReceiptActivity.class));
                        break;
                    case 3: startActivity(new Intent(MainActivity.this, AddGoodsIssueActivity.class));
                        break;
                    case 4:  AddCustomer();
                        break;
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //search
                switch (tmp){
                    case 1: productFragment = (ProductFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                        productFragment.SearchProduct(edtSearch.getText().toString());
                        break;
                    case 2:goodsReceiptFragment = (GoodsReceiptFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                            goodsReceiptFragment.SearchGoodsReceipt(edtSearch.getText().toString());
                        break;
                    case 3:goodsIssueFragment = (GoodsIssueFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                            goodsIssueFragment.SearchGoodsIssue(edtSearch.getText().toString());
                        break;
                    case 4:customerFragment = (CustomerFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                        customerFragment.SearchCustomer(edtSearch.getText().toString());
                        break;
                }
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_product:
                    fragment = new ProductFragment();
                    loadFragment(fragment);
                    tmp = 1;
                    lnSortProduct.setVisibility(View.VISIBLE);
                    lnSortCustomer.setVisibility(View.INVISIBLE);
                    edtSearch.setText("");
                    edtSearch.setHint("Nhập tên sản phẩm");
                    return true;
                case R.id.navigation_input:
                    fragment = new GoodsReceiptFragment();
                    loadFragment(fragment);
                    tmp = 2;
                    lnSortProduct.setVisibility(View.VISIBLE);
                    lnSortCustomer.setVisibility(View.INVISIBLE);
                    edtSearch.setText("");
                    edtSearch.setHint("Nhập tên sản phẩm");
                    return true;
                case R.id.navigation_output:
                    fragment = new GoodsIssueFragment();
                    loadFragment(fragment);
                    tmp = 3;
                    lnSortProduct.setVisibility(View.VISIBLE);
                    lnSortCustomer.setVisibility(View.INVISIBLE);
                    edtSearch.setText("");
                    edtSearch.setHint("Nhập tên sản phẩm");
                    return true;
                case R.id.navigation_user:
                    fragment = new CustomerFragment();
                    loadFragment(fragment);
                    tmp = 4;
                    lnSortProduct.setVisibility(View.INVISIBLE);
                    lnSortCustomer.setVisibility(View.VISIBLE);
                    edtSearch.setText("");
                    edtSearch.setHint("Nhập tên khách hàng");
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void AddProduct(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_product);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtNameProduct = (EditText) dialog.findViewById(R.id.edtNameProduct);
        final EditText edtUnitProduct = (EditText) dialog.findViewById(R.id.edtUnitProduct);
        final EditText edtDescriptionProduct = (EditText) dialog.findViewById(R.id.edtDescriptionProduct);
        final Button btnSubmitAdd = (Button) dialog.findViewById(R.id.btnSubmitAdd);
        final Button btnCancelAdd = (Button) dialog.findViewById(R.id.btnCancelAdd);

        btnSubmitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNameProduct.getText().toString().isEmpty() || edtUnitProduct.getText().toString().isEmpty()
                       || edtDescriptionProduct.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    AddProductToDB(edtNameProduct.getText().toString(), edtUnitProduct.getText().toString(),
                            edtDescriptionProduct.getText().toString());
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    dialog.dismiss();
                }

            }
        });

        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void AddCustomer() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_customer);
        dialog.setCanceledOnTouchOutside(false);

        final EditText edtNameCustomer = (EditText) dialog.findViewById(R.id.edtNameCustomer);
        final EditText edtPhoneNumber = (EditText) dialog.findViewById(R.id.edtPhoneNumber);
        final EditText edtAddressCustomer = (EditText) dialog.findViewById(R.id.edtAddressCustomer);
        final Button btnSubmitAdd = (Button) dialog.findViewById(R.id.btnSubmitAddCustomer);
        final Button btnCancelAdd = (Button) dialog.findViewById(R.id.btnCancelAddCustomer);

        btnSubmitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNameCustomer.getText().toString().isEmpty() || edtPhoneNumber.getText().toString().isEmpty()
                        || edtAddressCustomer.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    AddCustomerToDB(edtNameCustomer.getText().toString(), edtPhoneNumber.getText().toString(),
                            edtAddressCustomer.getText().toString());
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("key", 4);
                    startActivity(intent);
                    dialog.dismiss();
                }

            }
        });

        btnCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void AddProductToDB(String name, String unit, String description){  //add to database
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.addProduct(name, unit, description);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void AddCustomerToDB(String name, String phoneNumber, String address){   //add to database
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.addCustomer(name, phoneNumber, address);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void onClickSortProduct(View view){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, lnSortProduct);
        popupMenu.getMenuInflater().inflate(R.menu.menu_sorter_product, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) { // call methods from child fragment
                switch (tmp){
                    case 1: productFragment = (ProductFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                        break;
                    case 2: goodsReceiptFragment = (GoodsReceiptFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                        break;
                    case 3: goodsIssueFragment = (GoodsIssueFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.frame_fragment);
                        break;
                }

                switch (item.getItemId()){
                    case R.id.menuSortName:
                        switch (tmp){
                            case 1: productFragment.SortProduct(1);
                                break;
                            case 2:goodsReceiptFragment.SortGoodsReceipt(1);
                                break;
                            case 3: goodsIssueFragment.SortGoodsIssue(1);
                                break;
                        }

                        break;
                    case R.id.menuSortQuantity:
                        switch (tmp){
                            case 1: productFragment.SortProduct(2);
                                break;
                            case 2: goodsReceiptFragment.SortGoodsReceipt(2);
                                break;
                            case 3: goodsIssueFragment.SortGoodsIssue(2);
                                break;
                        }

                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void onClickSortCustomer(View view){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, lnSortCustomer);
        popupMenu.getMenuInflater().inflate(R.menu.menu_sorter_customer, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuSortNameC:
                        customerFragment = (CustomerFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.frame_fragment);
                        customerFragment.SortCustomer(1);
                        break;
                    case R.id.menuSortAddress:
                        customerFragment = (CustomerFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.frame_fragment);
                        customerFragment.SortCustomer(2);
                        break;
                }
                        return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void EditProduct(final Product product) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_product);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvNameProductE = (TextView) dialog.findViewById(R.id.tvNameProductE);
        final EditText edtQuantityProductE = (EditText) dialog.findViewById(R.id.edtQuantityProductE);
        final EditText edtUnitProductE = (EditText) dialog.findViewById(R.id.edtUnitProductE);
        final EditText edtDescriptionProductE = (EditText) dialog.findViewById(R.id.edtDescriptionProductE);
        Button btnSubmitEdit = (Button) dialog.findViewById(R.id.btnSubmitEdit);
        Button btnCancelEdit = (Button) dialog.findViewById(R.id.btnCancelEdit);

        tvNameProductE.setText("Tên sản phẩm" + product.getName());

        btnSubmitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtQuantityProductE.getText().toString().isEmpty() || edtUnitProductE.getText().toString().isEmpty()
                        || edtDescriptionProductE.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    EditProductToDB(product.getID(), edtQuantityProductE.getText().toString(),
                            edtUnitProductE.getText().toString(), edtDescriptionProductE.getText().toString());
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
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

    @Override
    public void EditCustomer(Customer customer) {
        customerFragment = (CustomerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_fragment);
        customerFragment.Edit(customer);
    }

    public void EditProductToDB(String id, String quantity, String unit, String description){
        DataClient dataClient = APIUtils.getData();
        Call<String> call = dataClient.UpdateProduct(id, quantity, description, unit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void DeleteGoodsReceipt(GoodsReceipt goodsReceipt) {
        goodsReceiptFragment = (GoodsReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_fragment);
        goodsReceiptFragment.DeleteGoodsReceipt(goodsReceipt);
    }

    @Override
    public void EditGoodsReceipt(GoodsReceipt goodsReceipt) {
        goodsReceiptFragment = (GoodsReceiptFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_fragment);
        goodsReceiptFragment.Edit(goodsReceipt);
    }

    @Override
    public void Success(int key) {
        if (key == 1) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("key", 2);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("key", 3);
            startActivity(intent);
        }

    }

    @Override
    public void DeleteGoodsIssue(GoodsIssue goodsIssue) {
        goodsIssueFragment = (GoodsIssueFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_fragment);
        goodsIssueFragment.DeleteGoodsIssue(goodsIssue);
    }

    @Override
    public void EditGoodsIssue(GoodsIssue goodsIssue) {
        goodsIssueFragment = (GoodsIssueFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frame_fragment);
        goodsIssueFragment.Edit(goodsIssue);
    }
}
