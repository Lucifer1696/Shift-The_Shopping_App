package com.example.shift.seller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;

import java.util.ArrayList;

public class ItemsAdapeter extends RecyclerView.Adapter<ItemsAdapeter.ViewHolder> {

    ArrayList<ItemsModel> itmList;

    public ItemsAdapeter(ArrayList<ItemsModel> itmList) {
        this.itmList = itmList;
    }

    @NonNull
    @Override
    public ItemsAdapeter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.t1.setText(itmList.get(position).name);
        holder.t2.setText(itmList.get(position).price);
        holder.t3.setText(itmList.get(position).ram);
        //holder.t4.setText(itmList.get(position).rom);
        holder.t5.setText(itmList.get(position).os);
    }


    @Override
    public int getItemCount() {
        return itmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3,t5;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.product_name);
            t2=itemView.findViewById(R.id.product_price);
            t3=itemView.findViewById(R.id.ram);
        }
    }
}
