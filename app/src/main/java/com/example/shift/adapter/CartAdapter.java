package com.example.shift.adapter;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shift.R;
import com.example.shift.model.CartitemModel;

import java.util.List;
public class CartAdapter extends RecyclerView.Adapter {

    private List<CartitemModel>cartitemModelList;

    public CartAdapter(List<CartitemModel> cartitemModelList) {
        this.cartitemModelList = cartitemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartitemModelList.get(position).getType()){
            case 0:
                return CartitemModel.Cart_Item;
            case 1:
                return CartitemModel.Total_Amount;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType) {

        switch (viewType){
            case CartitemModel.Cart_Item:
                View cartItemView = LayoutInflater.from(vg.getContext()).inflate(R.layout.cart_item_layout,vg,false);
                return new carItemViewholder(cartItemView);

            case CartitemModel.Total_Amount:
                View cartTotalView = LayoutInflater.from(vg.getContext()).inflate(R.layout.cart_total_amount_layout,vg,false);
                return new cartTotalAmountViewholder(cartTotalView);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartitemModelList.get(position).getType()){
            case CartitemModel.Cart_Item:
                int resource = cartitemModelList.get(position).getProductImage();
                String title = cartitemModelList.get(position).getProductTitle();
                String productPrice = cartitemModelList.get(position).getProductPrice();
                String cuttedPrice = cartitemModelList.get(position).getCuttedPrice();
                ((carItemViewholder)holder).setItemDetails(resource,title,productPrice,cuttedPrice);
                break;

            case CartitemModel.Total_Amount:
                String totalItems = cartitemModelList.get(position).getTotalItems();
                String totalItemsPrice = cartitemModelList.get(position).getTotalItemPrice();
                String deliveryPrice = cartitemModelList.get(position).getDeliveryPrice();
                String totalAmount = cartitemModelList.get(position).getTotalAmount();
                String savedAmount = cartitemModelList.get(position).getSavedAmount();
                ((cartTotalAmountViewholder)holder).setTotalAmount(totalItems,totalItemsPrice,deliveryPrice,totalAmount,savedAmount);
                break;

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return cartitemModelList.size();
    }

    class carItemViewholder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView productQuantity;

        public carItemViewholder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice= itemView.findViewById(R.id.dis_price);
           // cuttedPrice= itemView.findViewById(R.id.cutted_price);
//            offersApplied = itemView.findViewById(R.id.offer);
//            productQuantity =itemView.findViewById(R.id.product_quantity);
        }
        private void setItemDetails(int  resource,String title,String productPriceText ,String cuttedPriceText){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
            productQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog  quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quentity_dialog);
                    EditText quentity_no =  quantityDialog.findViewById(R.id.quentity_no);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancelBtn);
                    Button okBtn = quantityDialog.findViewById(R.id.okBtn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });

                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productQuantity.setText("Qty: " + quentity_no.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });
        }
    }

    class cartTotalAmountViewholder extends RecyclerView.ViewHolder{

        private TextView totalItems,totalItemPrice,deliverPrice,totalAmount,savedAmount;
        public cartTotalAmountViewholder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemPrice = itemView.findViewById(R.id.total_items_price);
            deliverPrice= itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_items);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(String totalItemstext,String totalItemPriceText,String deliverPriceText,String totalAmountText,String savedAmountText){
            totalItems.setText(totalItemstext);
            totalItemPrice.setText(totalItemPriceText);
            deliverPrice.setText(deliverPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);

        }
    }
}
