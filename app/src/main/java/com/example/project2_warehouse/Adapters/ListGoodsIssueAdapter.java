package com.example.project2_warehouse.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_warehouse.Interfaces.IChangeIP;
import com.example.project2_warehouse.Model.GoodsIssue;
import com.example.project2_warehouse.Model.GoodsReceipt;
import com.example.project2_warehouse.R;

import java.util.List;

public class ListGoodsIssueAdapter extends RecyclerView.Adapter<ListGoodsIssueAdapter.ViewHolder> {

    List<GoodsIssue> goodsIssues;
    Context context;
    IChangeIP iChangeIP;

    public ListGoodsIssueAdapter(List<GoodsIssue> goodsIssues, Context context, IChangeIP iChangeIP) {
        this.goodsIssues = goodsIssues;
        this.context = context;
        this.iChangeIP = iChangeIP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_goodsissue, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvProductOP.setText(goodsIssues.get(position).getProduct());
        holder.tvQuantityOP.setText(goodsIssues.get(position).getQuantity());
        holder.tvDateOP.setText(goodsIssues.get(position).getDate());
        holder.tvCustomerIdOP.setText(goodsIssues.get(position).getCustomer());
        holder.imgDeleteO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeIP.DeleteGoodsIssue(goodsIssues.get(position));
            }
        });

        holder.imgEditO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeIP.EditGoodsIssue(goodsIssues.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsIssues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvProductOP;
        private TextView tvQuantityOP;
        private TextView tvDateOP;
        private TextView tvCustomerIdOP;
        private ImageView imgEditO;
        private ImageView imgDeleteO;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductOP = (TextView) itemView.findViewById(R.id.tvProductOP);
            tvQuantityOP = (TextView) itemView.findViewById(R.id.tvQuantityOP);
            tvDateOP = (TextView) itemView.findViewById(R.id.tvDateOP);
            tvCustomerIdOP = (TextView) itemView.findViewById(R.id.tvCustomerIdOP);
            imgEditO = (ImageView) itemView.findViewById(R.id.imgEditO);
            imgDeleteO = (ImageView) itemView.findViewById(R.id.imgDeleteO);
        }
    }
}
