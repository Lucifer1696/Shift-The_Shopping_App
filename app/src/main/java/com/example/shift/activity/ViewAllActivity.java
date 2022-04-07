package com.example.shift.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.adapter.ShowCategoryItems;
import com.example.shift.adapter.gridProductLayoutAdapter;
import com.example.shift.model.Allitemmodel;
import com.example.shift.model.HorizontalProductScrollModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<Allitemmodel> itmlist;
    RecyclerView recyclerView;
    String sid,view;
    SharedPreferences shrd;
    private RecyclerView recyclerview;
    private GridView gridview;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("All Items");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        /////////
        sid=getIntent().getStringExtra("seller");
        view=getIntent().getStringExtra("view");
        db= FirebaseFirestore.getInstance();

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        itmlist = new ArrayList<>();

        System.out.println("Seller :  "+sid+"View : "+view);
        db= FirebaseFirestore.getInstance();
        db.collection("Seller").whereEqualTo("SID",sid).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("Items");

                    col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                if(view.equals("1"))
                                    itmlist.add(new Allitemmodel(1,shrd.getString("CID","0000"),d.getString("Category"), d.getString("Name"), d.getString("Company"), d.getString("Price"), d.getString("Discount"), d.getString("Stock"), d.getString("Description"), d.getString("Photo1"), d.getString("Photo2"), d.getString("Photo3"), d.getString("Photo4"), d.getString("Warranty"), d.getString("Quantity")));
                                else
                                    horizontalProductScrollModelList.add(new HorizontalProductScrollModel(d.getString("Photo1"),d.getString("Name"),d.getString("Company"),d.getString("Price")));
                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            gridview=findViewById(R.id.grid_View);
                            recyclerView = (RecyclerView) findViewById(R.id.recycler_View);

                            if(view.equals("1")) {
                                recyclerView.setVisibility(View.VISIBLE);
                                LinearLayoutManager testinglayoutManager = new LinearLayoutManager(getApplicationContext());
                                testinglayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(testinglayoutManager);
                                ShowCategoryItems adapter = new ShowCategoryItems(itmlist);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                System.out.println("Size : "+itmlist.size());

                            }
                            else {
                                gridview.setVisibility(View.VISIBLE);
                               gridProductLayoutAdapter gridproductlayoutadapter = new gridProductLayoutAdapter(horizontalProductScrollModelList);
                                gridview.setAdapter(gridproductlayoutadapter);
                            }
                        }
                    });
                }
            }
        });

        /////////
//
//        recyclerview=findViewById(R.id.recycler_View);
//
//        int layout_code = getIntent().getIntExtra("layout_code",-1);

//        if(layout_code==0) {
//
//            recyclerview.setVisibility(View.VISIBLE);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//            recyclerview.setLayoutManager(layoutManager);
//
//            List<WishListModel> wishListModelList = new ArrayList<>();
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "SAMSUNG GALAXY", 1, "3", 145, "Rs.49999/-", "Rs.59999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "IPHONE XR", 1, "3", 145, "Rs.199999/-", "Rs.299999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "ONEPLUS 5T", 1, "3", 145, "Rs.30999/-", "Rs.35999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "MI 9", 1, "3", 145, "Rs.15999/-", "Rs.20999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "REALME 1", 1, "3", 145, "Rs.10000/-", "Rs.11111/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "SAMSUNG GALAXY", 1, "3", 145, "Rs.49999/-", "Rs.59999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "IPHONE XR", 1, "3", 145, "Rs.199999/-", "Rs.299999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "ONEPLUS 5T", 1, "3", 145, "Rs.30999/-", "Rs.35999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "MI 9", 1, "3", 145, "Rs.15999/-", "Rs.20999/-", "Cash on delivery"));
//            wishListModelList.add(new WishListModel(R.drawable.mobile, "REALME 1", 1, "3", 145, "Rs.10000/-", "Rs.11111/-", "Cash on delivery"));
//
//            WishListAdapter adapter = new WishListAdapter(wishListModelList);
//            recyclerview.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        }
//        else if(layout_code==1) {
//            gridview.setVisibility(View.VISIBLE);
//            List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//
//            gridProductLayoutAdapter gridproductlayoutadapter = new gridProductLayoutAdapter(horizontalProductScrollModelList);
//            gridview.setAdapter(gridproductlayoutadapter);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
         finish();
         return true;
        }
        return super.onOptionsItemSelected(item);
    }
}