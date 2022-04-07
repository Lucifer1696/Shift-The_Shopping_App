package com.example.shift.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.example.shift.activity.ProductDetailsActivity;
import com.example.shift.model.HorizontalProductScrollModel;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder>{

    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    private ImageView productImage;
    private TextView productTitle;
    private TextView productDescription;
    private TextView productPrice;
    private View v;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getProductImage();
        String title = horizontalProductScrollModelList.get(position).getProductTitle();
        String description = horizontalProductScrollModelList.get(position).getProductDescription();
        String price = horizontalProductScrollModelList.get(position).getProductPrice();

        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductDescription(description);
        holder.setProductPrice(price);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailsintet = new Intent(v.getContext(), ProductDetailsActivity.class);
                productdetailsintet.putExtra("Name",horizontalProductScrollModelList.get(position).getProductTitle());
                v.getContext().startActivity(productdetailsintet);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(horizontalProductScrollModelList.size() >8 ){
            return 8;
        }else
             return horizontalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.h_s_product_img);
            productTitle =(TextView) itemView.findViewById(R.id.h_s_product_titile);
            productDescription =(TextView) itemView.findViewById(R.id.h_s_product_description);
            productPrice = (TextView) itemView.findViewById(R.id.h_s_porduct_price);
            v=itemView;
        }
        private void setProductImage(String resource){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(productImage);
        }

        private void setProductTitle(String title){
            productTitle.setText(title);
        }

        private void setProductDescription(String description){
            productDescription.setText(description);
        }

        private void setProductPrice(String price){
            productPrice.setText("Rs."+price+"/-");
        }
    }
}