package com.example.shift.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.example.shift.activity.ProductDetailsActivity;
import com.example.shift.model.HorizontalProductScrollModel;

import java.util.List;

public class gridProductLayoutAdapter extends BaseAdapter {

    public gridProductLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    List <HorizontalProductScrollModel> horizontalProductScrollModelList;


    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView ==null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(5);
            view.setBackgroundColor(Color.TRANSPARENT);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(parent.getContext(), ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("Name",horizontalProductScrollModelList.get(position).getProductTitle());
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });

            ImageView productImage = (ImageView) view.findViewById(R.id.h_s_product_img);
            TextView productTitle = (TextView) view.findViewById(R.id.h_s_product_titile);
            TextView productDescription = (TextView) view.findViewById(R.id.h_s_product_description);
            TextView productPrice = (TextView) view.findViewById(R.id.h_s_porduct_price);

            Glide.with(parent.getContext()).load(horizontalProductScrollModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(productImage);
            productTitle.setText(horizontalProductScrollModelList.get(position).getProductTitle());
            productDescription.setText(horizontalProductScrollModelList.get(position).getProductDescription());
            productPrice.setText("Rs."+ horizontalProductScrollModelList.get(position).getProductPrice()+"/-");
        }else {
            view = convertView;
        }
        return view;
    }
}
