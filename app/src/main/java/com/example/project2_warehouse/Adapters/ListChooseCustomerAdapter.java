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
import com.example.project2_warehouse.Model.Customer;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;

import java.util.List;

public class ListChooseCustomerAdapter extends RecyclerView.Adapter<ListChooseCustomerAdapter.ViewHolder> {

    List<Customer> customerList;
    Context context;
    IPassData iPassData;

    public ListChooseCustomerAdapter(List<Customer> customerList, Context context, IPassData iPassData) {
        this.customerList = customerList;
        this.context = context;
        this.iPassData = iPassData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_choose_customer, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvChooseCustomer.setText(customerList.get(position).getID()
                + " - " + customerList.get(position).getName());
        holder.rlChoosePCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPassData.ChooseCustomer(customerList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlChoosePCustomer;
        private TextView tvChooseCustomer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rlChoosePCustomer = (RelativeLayout) itemView.findViewById(R.id.rlChooseCustomer);
            tvChooseCustomer = (TextView) itemView.findViewById(R.id.tvChooseCustomer);
        }
    }
}
