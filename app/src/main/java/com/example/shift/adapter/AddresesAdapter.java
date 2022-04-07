package com.example.shift.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.model.AddressesModel;

import java.util.List;


public class AddresesAdapter extends RecyclerView.Adapter<AddresesAdapter.Viewholder> {

    private List<AddressesModel> addressesModelList;
    public AddresesAdapter (List<AddressesModel> addressesModelList){
        this . addressesModelList = addressesModelList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layput,parent,false);
        return  new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddresesAdapter.Viewholder holder, int position) {
        String address =addressesModelList. get(position).getAddress();
         holder.setData(address);
    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public  class Viewholder extends RecyclerView. ViewHolder{

        private TextView fullname;
        private TextView address;
        private TextView pincode;

        public Viewholder (@NonNull View itemView) {
            super (itemView);
            fullname = itemView. findViewById (R.id.product_name) ;
            address = itemView. findViewById (R.id.customer_address) ;
            pincode = itemView . findViewById (R. id.pincode);
        }
            private void setData(String username){
                    fullname.setText(username);
            }

    }

}