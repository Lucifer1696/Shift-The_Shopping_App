package com.example.shift.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    FirebaseFirestore db;
    ArrayList<Allitemmodel> allitemmodels;
    SharedPreferences shrd;
    String serchtexts[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView=findViewById(R.id.search_view);
        recyclerView=findViewById(R.id.search_recyclerview);

        LinearLayoutManager testinglayoutManager = new LinearLayoutManager(this);
        testinglayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(testinglayoutManager);

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        allitemmodels = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(allitemmodels);
        recyclerView.setAdapter(adapter);

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                serchtexts=query.split(" ");
                allitemmodels.clear();
                boolean o=true;
                if(o) {
                    db = FirebaseFirestore.getInstance();
                    if (query.length() > 1) {
                        db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                                for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                                    CollectionReference col = doc.get(i).getReference().collection("Items");
                                    col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                                if (addpro(d.getString("Name")))
                                                    allitemmodels.add(new Allitemmodel(1, shrd.getString("CID", "0000"), d.getString("Category"), d.getString("Name"), d.getString("Company"), d.getString("Price"), d.getString("Discount"), d.getString("Stock"), d.getString("Description"), d.getString("Photo1"), d.getString("Photo2"), d.getString("Photo3"), d.getString("Photo4"), d.getString("Warranty"), d.getString("Quantity")));
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }
                        });
                    }
                    adapter.notifyDataSetChanged();
                    o=false;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    Boolean addpro(String name){

        String arryname[] = name.split(" ");
        for (String text: serchtexts){
            for (String sname:arryname){
                if(text.trim().equalsIgnoreCase(sname.trim()))
                    return true;
            }
        }
        return false;
    }
}