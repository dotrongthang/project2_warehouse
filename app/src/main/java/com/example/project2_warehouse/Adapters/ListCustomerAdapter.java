package com.example.project2_warehouse.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_warehouse.Interfaces.IEdit;
import com.example.project2_warehouse.Model.Customer;
import com.example.project2_warehouse.Model.Product;
import com.example.project2_warehouse.R;

import java.util.List;

public class ListCustomerAdapter extends RecyclerView.Adapter<ListCustomerAdapter.ViewHolder> {

    List<Customer> customers;
    Context context;
    IEdit iEdit;

    public ListCustomerAdapter(List<Customer> customers, Context context, IEdit iEdit) {
        this.customers = customers;
        this.context = context;
        this.iEdit = iEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_customer, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvListNameCustomer.setText(customers.get(position).getName());
        holder.tvListPhoneCustomer.setText(customers.get(position).getPhoneNumber());
        holder.tvListAddressCustomer.setText(customers.get(position).getAddress());
        holder.ListEditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iEdit.EditCustomer(customers.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvListNameCustomer;
        private ImageView ListEditCustomer;
        private TextView tvListPhoneCustomer;
        private TextView tvListAddressCustomer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListNameCustomer = (TextView) itemView.findViewById(R.id.tvListNameCustomer);
            ListEditCustomer = (ImageView) itemView.findViewById(R.id.ListEditCustomer);
            tvListPhoneCustomer = (TextView) itemView.findViewById(R.id.tvListPhoneCustomer);
            tvListAddressCustomer = (TextView) itemView.findViewById(R.id.tvListAddressCustomer);
        }
    }
}
