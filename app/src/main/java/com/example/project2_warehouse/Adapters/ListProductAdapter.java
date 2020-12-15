package com.example.project2_warehouse.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    List<Product> products;
    Context context;

    public ListProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_product, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvListName.setText(products.get(position).getName());
        holder.tvListDescription.setText(products.get(position).getDescription());
        holder.tvListUnit.setText(products.get(position).getUnit());
        holder.tvListQuantity.setText(products.get(position).getQuantity());
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changeInfo.changeInfoSender(senders.get(position));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changeInfo.DeleteSender(senders.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvListName;
        private TextView tvListDescription;
        private TextView tvListUnit;
        private TextView tvListQuantity;
        ImageView imgEdit, imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListName = (TextView) itemView.findViewById(R.id.tvListName);
            tvListDescription = (TextView) itemView.findViewById(R.id.tvListDescription);
            tvListUnit = (TextView) itemView.findViewById(R.id.tvListUnit);
            tvListQuantity = (TextView) itemView.findViewById(R.id.tvListQuantity);
            imgEdit = (ImageView) itemView.findViewById(R.id.ListEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.ListDelete);
        }
    }
}
