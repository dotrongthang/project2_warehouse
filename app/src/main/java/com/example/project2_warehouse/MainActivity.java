package com.example.project2_warehouse;

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
import android.widget.Toast;

import com.example.project2_warehouse.Activities.AddGoodsIssueActivity;
import com.example.project2_warehouse.Activities.AddGoodsReceiptActivity;
import com.example.project2_warehouse.Fragments.CustomerFragment;
import com.example.project2_warehouse.Fragments.GoodsIssueFragment;
import com.example.project2_warehouse.Fragments.GoodsReceiptFragment;
import com.example.project2_warehouse.Fragments.ProductFragment;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    private EditText edtSearch;
    private ImageButton imgAdd, imgSearch;
    private LinearLayout lnSort;
    ProductFragment productFragment;
    GoodsReceiptFragment goodsReceiptFragment;
    GoodsIssueFragment goodsIssueFragment;
    public int tmp = 0;
    /*tmp = 1: product
        tmp = 2: goodsReceipt
        tmp = 3: goodsIssue
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        imgAdd = (ImageButton) findViewById(R.id.imgAdd);
        lnSort = (LinearLayout) findViewById(R.id.lnSort);
        imgSearch = (ImageButton) findViewById(R.id.imgSearch);

        fragment = new ProductFragment();
        loadFragment(fragment);
        tmp = 1;
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tmp){
                    case 1:  AddProduct();
                        break;
                    case 2: startActivity(new Intent(MainActivity.this, AddGoodsReceiptActivity.class));
                        break;
                    case 3: startActivity(new Intent(MainActivity.this, AddGoodsIssueActivity.class));
                        break;
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    edtSearch.setText("");
                    return true;
                case R.id.navigation_input:
                    fragment = new GoodsReceiptFragment();
                    loadFragment(fragment);
                    tmp = 2;
                    edtSearch.setText("");
                    return true;
                case R.id.navigation_output:
                    fragment = new GoodsIssueFragment();
                    loadFragment(fragment);
                    tmp = 3;
                    edtSearch.setText("");
                    return true;
                case R.id.navigation_user:
                    fragment = new CustomerFragment();
                    loadFragment(fragment);
                    tmp = 4;
//                    lnSort.setVisibility(View.INVISIBLE);
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

    private void AddProductToDB(String name, String unit, String description){
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

    public void onClickSort(View view){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, lnSort);
        popupMenu.getMenuInflater().inflate(R.menu.menu_choose_sorter, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
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

}
