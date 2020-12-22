package com.example.project2_warehouse.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_warehouse.Interfaces.IPassData;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;

import java.util.List;

public class ListChooseProductAdapter extends RecyclerView.Adapter<ListChooseProductAdapter.ViewHolder> {

    List<Product> productList;
    Context context;
    IPassData iPassData;

    public ListChooseProductAdapter(List<Product> productList, Context context, IPassData iPassData) {
        this.productList = productList;
        this.context = context;
        this.iPassData = iPassData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_choose_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvChooseProduct.setText(productList.get(position).getID()
                + " - " + productList.get(position).getName());
        holder.rlChooseProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPassData.ChooseProduct(productList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlChooseProduct;
        private TextView tvChooseProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlChooseProduct = (RelativeLayout) itemView.findViewById(R.id.rlChooseProduct);
            tvChooseProduct = (TextView) itemView.findViewById(R.id.tvChooseProduct);
        }
    }
}
