package com.example.shift.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.example.shift.activity.OrdersDetailsActivity;
import com.example.shift.model.MyOrdersItemsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {

    private List<MyOrdersItemsModel> myOrdersItemsModelList;

    public MyOrdersAdapter(List<MyOrdersItemsModel> myOrdersItemsModelList) {
        this.myOrdersItemsModelList = myOrdersItemsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource = myOrdersItemsModelList.get(position).getProductImage();
        String title = myOrdersItemsModelList.get(position).getProductTilte();
        String deliveredDate = myOrdersItemsModelList.get(position).getDeliveryStatus();
        try {
            holder.setData(resource,title,deliveredDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return  myOrdersItemsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNowContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            productTitle = itemView.findViewById(R.id.product_title);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
            rateNowContainer= itemView.findViewById(R.id.orders_rate_now_container);


        }
        private void setData(String resource,String title,String deliveryDate) throws ParseException {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(productImage);
            productTitle.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent OrderDetailsIntent = new Intent(itemView.getContext(), OrdersDetailsActivity.class);
                    OrderDetailsIntent.putExtra("Name",title);
                    itemView.getContext().startActivity(OrderDetailsIntent);
                }
            });
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h:mm a");
            Date d = dateFormat.parse(deliveryDate);
            Date current=new Date();

            if (current.after(d))  {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.green)));
            }else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));
            }
            deliveryStatus.setText(deliveryDate);
        }
    }
}
