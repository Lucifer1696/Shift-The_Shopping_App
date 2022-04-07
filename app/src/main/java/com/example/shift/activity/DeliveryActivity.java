package com.example.shift.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.adapter.ShowCategoryItems;
import com.example.shift.model.Allitemmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DeliveryActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<Allitemmodel> allitemmodels;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;
    TextView TotalCartAmount,TotalItems,SavedAmmount,TotalPrice,TotalItemsPrice;
    double totalamount=0,totalitems=0,savedammount=0;
    Button delivery_done_Btn;

    Map orderitmes= new HashMap<String,String>();

    TextView CustmerName;
    EditText CustmerAddress;
    CollectionReference Cartcol,myorder;

    RecyclerView orderitmessRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        Button changeAddress=findViewById(R.id.change_or_add_address);
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(getApplicationContext(), AddressesActivity.class);
                startActivity(deliveryIntent);
            }
        });

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        editor=shrd.edit();
        TotalCartAmount=findViewById(R.id.total_cart_amount);
        SavedAmmount=findViewById(R.id.saved_amount);
        TotalItems=findViewById(R.id.total_items);
        TotalPrice=findViewById(R.id.total_price);
        TotalItemsPrice=findViewById(R.id.total_items_price);

        CustmerAddress=findViewById(R.id.customer_address);
        CustmerName=findViewById(R.id.Seller_name);

        delivery_done_Btn = findViewById(R.id.delivery_done_Btn);
        orderitmessRecyclerView = findViewById(R.id.cart_items_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderitmessRecyclerView.setLayoutManager(layoutManager);


        allitemmodels = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(allitemmodels,"cart");
        orderitmessRecyclerView.setAdapter(adapter);


        db= FirebaseFirestore.getInstance();
        db.collection("Customer").whereEqualTo("CID",shrd.getString("CID","####")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CustmerName.setText(doc.get(i).getString("Name"));
                    CustmerAddress.setText(doc.get(i).getString("Address"));

                    myorder=doc.get(i).getReference().collection("MyOrders");
                    Cartcol = doc.get(i).getReference().collection("CartItems");
                    Cartcol.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            totalitems=queryDocumentSnapshots.size();
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                String price=d.getString("Price"),discount= d.getString("Discount");
                                savedammount+=Double.parseDouble(price)*(Double.parseDouble(discount)/100);
                                totalamount+=Double.parseDouble(price);
                                allitemmodels.add(new Allitemmodel(shrd.getString("CID","###"),d.getString("Category"), d.getString("Name"), d.getString("Company"), price, discount, d.getString("Description"), d.getString("Photo1"), d.getString("Photo2"), d.getString("Photo3"), d.getString("Photo4"), d.getString("Warranty"), d.getString("Quantity"), d.getString("NumberOfItem")));
                                }
                            adapter.notifyDataSetChanged();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            TotalCartAmount.setText("Rs."+String.valueOf(totalamount));
                            TotalPrice.setText("Rs."+String.valueOf(totalamount));
                            TotalItemsPrice.setText("Rs."+String.valueOf(totalamount));
                            TotalItems.setText("Price ("+String.valueOf(totalitems)+" items)");
                            SavedAmmount.setText("You Saved Rs."+String.valueOf(savedammount)+"/- On This order");
                        }
                    });
                }
            }
        });
        adapter.notifyDataSetChanged();
        CustmerAddress.setText(getIntent().getStringExtra("Address"));

        delivery_done_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cartcol.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       for(DocumentSnapshot d :queryDocumentSnapshots.getDocuments()){
                            DocumentReference doc = d.getReference();

                            String Name=d.getString("Name")  ,  NumberOfItem=d.getString("NumberOfItem");

                           orderitmes.put("Name", d.getString("Name"));
                           orderitmes.put("Price", d.getString("Price"));
                           orderitmes.put("Discount", d.getString("Discount"));
                           orderitmes.put("Quantity", NumberOfItem);
                           orderitmes.put("Photo1", d.getString("Photo1"));
                           orderitmes.put("Address",CustmerAddress.getText().toString());
                           orderitmes.put("DeliveryCharged","10");
                           SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault());

                           orderitmes.put("OrderPlacedDate", sdf.format(new Date()));
                           orderitmes.put("PackedDate",sdf.format(new Date(System.currentTimeMillis() + 86400 * 1000 * 1)));
                           orderitmes.put("ShippedDate", sdf.format(new Date(System.currentTimeMillis() + 86400 * 1000 * 2)));
                           orderitmes.put("DeliveryDate", sdf.format(new Date(System.currentTimeMillis() + 86400 * 1000 * 2)));

                           myorder.add(orderitmes).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                               @Override
                               public void onComplete(@NonNull Task<DocumentReference> task) {
                                   orderitmes.clear();
                               }
                           });
                           db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                                        CollectionReference col = doc.get(i).getReference().collection("Items");
                                        col.whereEqualTo("Name",Name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                                    DocumentReference stockdoc=d.getReference();
                                                    String FinalStock=String.valueOf(Integer.parseInt(d.getString("Stock"))-Integer.parseInt(NumberOfItem));
                                                    stockdoc.update("Stock",FinalStock);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            doc.delete();
                        }
                    }
                });
                ConstraintLayout done =findViewById(R.id.order_done);
                done.setVisibility(View.VISIBLE);
                TextView oderid =findViewById(R.id.orderID);
                oderid.setText(String.valueOf((int)Math.random()*(10000000-1000000+1)+100000));
                ImageButton gotohome=findViewById(R.id.gotohome);
                gotohome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent deliveryIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(deliveryIntent);
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == android.R.id.home){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}