package com.example.shift.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.adapter.ShowCategoryItems;
import com.example.shift.model.Allitemmodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView CategoryRecyclerView;
    FirebaseFirestore db;
    ArrayList<Allitemmodel> allitemmodels;
    SharedPreferences shrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        String title =getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);


        CategoryRecyclerView = (RecyclerView) findViewById(R.id.categorymain_recyclerview);
        LinearLayoutManager testinglayoutManager = new LinearLayoutManager(this);
        testinglayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        CategoryRecyclerView.setLayoutManager(testinglayoutManager);

        allitemmodels = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(allitemmodels);
        CategoryRecyclerView.setAdapter(adapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("Items");
                    int finalI = i;
                    col.whereEqualTo("Category",title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            System.out.println("@@@@@!!!!!!"+ finalI);
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                allitemmodels.add(new Allitemmodel(1,shrd.getString("CID","####"),d.getString("Category"), d.getString("Name"), d.getString("Company"), d.getString("Price"), d.getString("Discount"), d.getString("Stock"), d.getString("Description"), d.getString("Photo1"), d.getString("Photo2"), d.getString("Photo3"), d.getString("Photo4"), d.getString("Warranty"), d.getString("Quantity")));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
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
            Intent signInintent = new Intent(CategoryActivity.this, SearchActivity.class);
            startActivity(signInintent);
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
