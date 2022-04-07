package com.example.shift.ui.fragmentsfornav;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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

public class WishList extends Fragment {
    private RecyclerView wishlistRecyclerview;
    FirebaseFirestore db;
    ArrayList<Allitemmodel> allitemmodels;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list ,container,false);
        shrd = this.getActivity().getSharedPreferences("Shift", Context.MODE_PRIVATE);
        editor=shrd.edit();

        wishlistRecyclerview = view.findViewById(R.id.my_wishlist_recyclerview) ;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerview.setLayoutManager(layoutManager);
        allitemmodels = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(allitemmodels,"wish");
        wishlistRecyclerview.setAdapter(adapter);


        db= FirebaseFirestore.getInstance();
        db.collection("Customer").whereEqualTo("CID",shrd.getString("CID","####")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("WishList");
                    col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                String price=d.getString("Price"),discount= d.getString("Discount");
                                allitemmodels.add(new Allitemmodel(d.getString("Category"), d.getString("Name"), d.getString("Company"), price, discount, d.getString("Photo1"), d.getString("Quantity"),shrd.getString("CID","###")));
                                }
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        adapter.notifyDataSetChanged();
        return view;
    }
}