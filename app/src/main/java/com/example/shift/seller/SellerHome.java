package com.example.shift.seller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.activity.AddProduct;
import com.example.shift.adapter.ShowCategoryItems;
import com.example.shift.login.SignInActivity;
import com.example.shift.model.Allitemmodel;
import com.example.shift.ui.fragmentsfornav.Account;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SellerHome extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<Allitemmodel> itmlist;
    RecyclerView recyclerView;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;
    TextView SellerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        SellerName=findViewById(R.id.Seller_name);

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        editor=shrd.edit();

        String SID =shrd.getString("SID","Name");
        SellerName.setText("Welcome , "+shrd.getString("SName","Name"));
        getSupportActionBar().setTitle("Seller Page");

        recyclerView = (RecyclerView) findViewById(R.id.Items_recyclerView);
        LinearLayoutManager testinglayoutManager = new LinearLayoutManager(this);
        testinglayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(testinglayoutManager);

        itmlist = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(itmlist);
        recyclerView.setAdapter(adapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Seller").whereEqualTo("SID",SID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("Items");

                    col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            System.out.println("Size : "+queryDocumentSnapshots.size());
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                System.out.println("Size : ");
                                if(d.contains("Category"))
                                    itmlist.add(new Allitemmodel(3,"Seller",d.getString("Category"), d.getString("Name"), d.getString("Company"), d.getString("Price"), d.getString("Discount"), d.getString("Stock"), d.getString("Description"), d.getString("Photo1"), d.getString("Photo2"), d.getString("Photo3"), d.getString("Photo4"), d.getString("Warranty"), d.getString("Quantity")));
                                else {
                                    DocumentReference doc54 = d.getReference();
                                    doc54.delete();
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        adapter.notifyDataSetChanged();

    }
    public void addpro(View v){
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
    }

    public void  stockmanage(View v){

    }
    public void myaccount(View v){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new Account("Seller","SID")).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(SellerHome.this);
        builder.setMessage("Do you want to exit ?");
        builder.setTitle("Alert !");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.ic_logout:
//                Toast.makeText(getApplicationContext(),"Seller Logout",Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(SellerHome.this);
                builder.setMessage("Are you sure you want to Log out ?");
                builder.setTitle("Log out");
                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SellerHome.this, SignInActivity.class);
                        editor.clear();
                        editor.apply();
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}