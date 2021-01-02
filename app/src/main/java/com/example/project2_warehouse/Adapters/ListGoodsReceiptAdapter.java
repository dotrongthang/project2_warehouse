package com.example.project2_warehouse.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_warehouse.Interfaces.IChangeIP;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;
import com.example.project2_warehouse.Retrofit.APIUtils;
import com.example.project2_warehouse.Retrofit.DataClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListGoodsReceiptAdapter extends RecyclerView.Adapter<ListGoodsReceiptAdapter.ViewHolder> {

    List<GoodsReceipt> goodsReceipts;
    Context context;
    IChangeIP iChangeIP;

    public ListGoodsReceiptAdapter(List<GoodsReceipt> goodsReceipts, Context context, IChangeIP iChangeIP) {
        this.goodsReceipts = goodsReceipts;
        this.context = context;
        this.iChangeIP = iChangeIP;
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
        holder.imgDeleteI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeIP.DeleteGoodsReceipt(goodsReceipts.get(position));
            }
        });
        holder.imgEditI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeIP.EditGoodsReceipt(goodsReceipts.get(position));
            }
        });

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
        private ImageView imgDeleteI, imgEditI;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductIP = (TextView) itemView.findViewById(R.id.tvProductIP);
            tvQuantityIP = (TextView) itemView.findViewById(R.id.tvQuantityIP);
            tvDateIP = (TextView) itemView.findViewById(R.id.tvDateIP);
            tvNoteIP = (TextView) itemView.findViewById(R.id.tvNoteIP);
            imgDeleteI = (ImageView) itemView.findViewById(R.id.imgDeleteI);
            imgEditI = (ImageView) itemView.findViewById(R.id.imgEditI);
        }
    }


}
