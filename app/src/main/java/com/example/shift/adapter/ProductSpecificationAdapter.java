package com.example.shift.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.model.ProductSpecificationModel;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {
    private List<ProductSpecificationModel> ProductSecificationList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSecificationList) {
        ProductSecificationList = productSecificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String featuretitle=ProductSecificationList.get(position).getFeaturename();
        String featueredetail=ProductSecificationList.get(position).getFeatureValue();
        holder.setFeature(featuretitle,featueredetail);
    }

    @Override
    public int getItemCount() {
        return ProductSecificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView featurename;
        private TextView featurevalue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            featurename=itemView.findViewById(R.id.featuer_name);
            featurevalue=itemView.findViewById(R.id.feature_Values);
        }
        private void  setFeature(String featuretitle,String featuredetail){
            featurename.setText(featuretitle);
            featurevalue.setText(featuredetail);
        }
    }
}
