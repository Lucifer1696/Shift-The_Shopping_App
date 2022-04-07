package com.example.shift.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.model.WishListModel;

import java.util.List;


public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder>{
    private  List<WishListModel>wishListModelList;


    public WishListAdapter(List<WishListModel> wishListModelList) {
        this.wishListModelList = wishListModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup , int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int resource = wishListModelList.get(position).getProductImage() ;
        String title = wishListModelList.get(position).getProductTitle() ;
        int freeCoupens = wishListModelList.get(position).getFreeCoupens() ;
        String rating = wishListModelList.get(position).getRating() ;
        int totalRatings = wishListModelList.get(position).getTotalRatings() ;
        String productPrice = wishListModelList.get(position).getProductPrice() ;
        String cuttedPrice = wishListModelList.get(position).getCuttedPrice() ;
        String paymentMethod = wishListModelList.get(position).getPaymentMethod();
        viewHolder.setData(resource,title,freeCoupens,rating,totalRatings,productPrice,cuttedPrice,paymentMethod);


    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMehtod;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupon);
            coupenIcon = itemView.findViewById(R.id.coupon_icon);
            rating = itemView.findViewById(R.id.rating1);
            totalRatings = itemView.findViewById(R.id.rating2);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.dis_price);
            cuttedPrice = itemView.findViewById(R.id.price_cut);
            paymentMehtod = itemView.findViewById(R.id.qauntity);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

        private void setData(int resource, String title, int freeCoupensNO, String avgrating, int totalRatingsNO, String ProductPrice, String cuttedPriceValue, String paymentMethod) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (freeCoupensNO != 0) {
                coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNO == 1) {
                    freeCoupens.setText("free " + freeCoupensNO + " coupen");
                } else {
                    freeCoupens.setText("free " + freeCoupensNO + " coupen");
                }
            }else{
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }
            rating.setText(avgrating);
            totalRatings.setText(totalRatingsNO+"(ratings)");
            productPrice.setText(ProductPrice);
            cuttedPrice.setText(cuttedPriceValue);
            paymentMehtod.setText(paymentMethod);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v){
              Toast.makeText(itemView.getContext () , "delete", Toast.LENGTH_SHORT).show() ;}
        });
    }
    }
}

