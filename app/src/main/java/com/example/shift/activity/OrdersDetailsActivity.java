package com.example.shift.activity;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersDetailsActivity extends AppCompatActivity {

    // rate now
    private LinearLayout rateNowContainer;
    ImageView ItemImg,orderIndicator;
    SharedPreferences shrd;
    FirebaseFirestore db;
    TextView orderDate,packDate,shipDate,deliveryDate;
    Double totalamount,savedammount;
    CollectionReference myorder;
    TextView CustmerName,CustmerAddress,savedAmount,totalPrice,deliveryCharge,totalItem,totalItemsPrice,deliveryPrice,productPrice,productQuentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_deatails_);

        // ratings layout

        rateNowContainer =  findViewById(R.id.orders_rate_now_container);
        for(int x =0;x< rateNowContainer.getChildCount();x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRatings(starPosition);
                }
            });
        }
        // ratings layout

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        String name=getIntent().getStringExtra("Name");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout c=findViewById(R.id.delivery_linearlayout);
        View v1=c.getChildAt(0);
        View v2=c.getChildAt(1);
        View v3=c.getChildAt(2);

        orderDate=v1.findViewById(R.id.ordered_date);
        packDate=v1.findViewById(R.id.pack_date);
        shipDate=v1.findViewById(R.id.shipp_date);
        deliveryDate=v1.findViewById(R.id.delivery_date);
        ItemImg=v1.findViewById(R.id.productImage);
        productPrice=v1.findViewById(R.id.productPrice);
        productQuentity=v1.findViewById(R.id.productQuentity);
        orderIndicator=v1.findViewById(R.id.ordered_indicator);

        CustmerName =v2.findViewById(R.id.Seller_name);
        CustmerAddress=v2.findViewById(R.id.customer_address);
        deliveryCharge=v2.findViewById(R.id.delivery_charge);

        totalItem=v3.findViewById(R.id.total_items);
        totalItemsPrice=v3.findViewById(R.id.total_items_price);
        deliveryPrice=v3.findViewById(R.id.delivery_price);
        totalPrice=v3.findViewById(R.id.total_price);
        savedAmount=v3.findViewById(R.id.saved_amount);
        savedAmount.setVisibility(View.INVISIBLE);

        db=FirebaseFirestore.getInstance();
        db= FirebaseFirestore.getInstance();
        db.collection("Customer").whereEqualTo("CID",shrd.getString("CID","####")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CustmerName.setText(doc.get(i).getString("Name"));
                    myorder=doc.get(i).getReference().collection("MyOrders");
                    myorder.whereEqualTo("Name",name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                String price=d.getString("Price");
                                productPrice.setText("RS "+price+" /-");
                                totalItemsPrice.setText("RS "+price+" /-");
                                productQuentity.setText("Quantity : "+d.getString("Quantity"));
                                totalItem.setText("( "+d.getString("Quantity")+" items)");
                                if(d.getString("DeliveryCharged").equals("Free")){
                                    deliveryCharge.setText(d.getString("DeliveryCharged"));
                                    deliveryPrice.setText(d.getString("DeliveryCharged"));
                                    totalPrice.setText("RS "+price+" /-");
                                }
                                else {
                                    Double DeliveryCharge=Double.parseDouble(price)*0.1;
                                    deliveryCharge.setText(d.getString("DeliveryCharged"));
                                    deliveryPrice.setText(d.getString("DeliveryCharged"));
                                    totalamount = Double.parseDouble(price) + DeliveryCharge;
                                    totalPrice.setText("RS "+String.valueOf(totalamount)+" /-");
                                }
                                CustmerAddress.setText(d.getString("Address"));
                                orderDate.setText(d.getString("OrderPlacedDate"));
                                packDate.setText(d.getString("PackedDate"));
                                shipDate.setText(d.getString("ShippedDate"));
                                deliveryDate.setText(d.getString("DeliveryDate"));

                                Glide.with(v1.getContext()).load(d.getString("Photo1")).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(ItemImg);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy h:mm a");
                                Date date = null;
                                try {
                                    date = dateFormat.parse(d.getString("OrderPlacedDate"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Date current=new Date();
                                if (current.after(date))  {
                                    orderIndicator.setImageTintList(ColorStateList.valueOf(v1.getContext().getResources().getColor(R.color.green)));
                                }else {
                                    orderIndicator.setImageTintList(ColorStateList.valueOf(v1.getContext().getResources().getColor(R.color.red)));
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_cart){
            //todo
            return true;
        }
        else if(id == R.id.action_notifiaction) {
            //todo
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setRatings(int starPosition) {
        for(int x= 0;x< rateNowContainer.getChildCount();x++){
            ImageView starBtn  =  (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }
}