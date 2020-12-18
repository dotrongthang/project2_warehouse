package com.example.project2_warehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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

import com.example.project2_warehouse.Fragments.GoodsIssueFragment;
import com.example.project2_warehouse.Fragments.GoodsReceiptFragment;
import com.example.project2_warehouse.Fragments.ProductFragment;
import com.example.project2_warehouse.Interfaces.ISort;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    private TextView tvTitle;
    private ImageButton imgAdd;
    private LinearLayout lnSort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        imgAdd = (ImageButton) findViewById(R.id.imgAdd);
        lnSort = (LinearLayout) findViewById(R.id.lnSort);

        fragment = new ProductFragment();
        loadFragment(fragment);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_product:
                    fragment = new ProductFragment();
                    tvTitle.setText("Sản phẩm");
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_input:
                    fragment = new GoodsReceiptFragment();
                    tvTitle.setText("Nhập sản phẩm");
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_output:
                    fragment = new GoodsIssueFragment();
                    tvTitle.setText("Xuất sản phẩm");
                    loadFragment(fragment);
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
                ProductFragment productFragment = (ProductFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.frame_fragment);
                switch (item.getItemId()){
                    case R.id.menuSortName:
                        productFragment.SortProduct(1);
                        break;
                    case R.id.menuSortQuantity:
                        productFragment.SortProduct(2);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
