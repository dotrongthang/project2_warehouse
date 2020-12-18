package com.example.project2_warehouse.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;

import java.util.List;

public class ListGoodsReceiptAdapter extends RecyclerView.Adapter<ListGoodsReceiptAdapter.ViewHolder> {

    List<GoodsReceipt> goodsReceipts;
    Context context;

    public ListGoodsReceiptAdapter(List<GoodsReceipt> goodsReceipts, Context context) {
        this.goodsReceipts = goodsReceipts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_goodsreceipt, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvProductIP.setText(goodsReceipts.get(position).getProduct());
        holder.tvQuantityIP.setText(goodsReceipts.get(position).getQuantity());
        holder.tvDateIP.setText(goodsReceipts.get(position).getDate());
        holder.tvNoteIP.setText(goodsReceipts.get(position).getNote());

    }

    @Override
    public int getItemCount() {
        return goodsReceipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvProductIP;
        private TextView tvQuantityIP;
        private TextView tvDateIP;
        private TextView tvNoteIP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductIP = (TextView) itemView.findViewById(R.id.tvProductIP);
            tvQuantityIP = (TextView) itemView.findViewById(R.id.tvQuantityIP);
            tvDateIP = (TextView) itemView.findViewById(R.id.tvDateIP);
            tvNoteIP = (TextView) itemView.findViewById(R.id.tvNoteIP);
        }
    }
}
