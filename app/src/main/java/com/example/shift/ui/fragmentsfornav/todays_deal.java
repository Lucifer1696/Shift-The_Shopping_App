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
import com.example.shift.activity.MainActivity;
import com.example.shift.adapter.ShowCategoryItems;
import com.example.shift.model.Allitemmodel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class todays_deal extends Fragment {

    FirebaseFirestore db;
    ArrayList<Allitemmodel> itmlist;
    RecyclerView recyclerView;
    SharedPreferences shrd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_todays_deal, container, false);

        shrd = this.getActivity().getSharedPreferences("Shift", Context.MODE_PRIVATE);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Todays Deal");
        recyclerView = (RecyclerView)view.findViewById(R.id.Items_recyclerView);
        LinearLayoutManager testinglayoutManager = new LinearLayoutManager(getActivity());
        testinglayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(testinglayoutManager);

        itmlist = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(itmlist);
        recyclerView.setAdapter(adapter);


        db= FirebaseFirestore.getInstance();
        db.collection("Seller").whereEqualTo("SID","S003").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                                    itmlist.add(new Allitemmodel(3,shrd.getString("CID","####"),d.getString("Category"), d.getString("Name"), d.getString("Company"), d.getString("Price"), d.getString("Discount"), d.getString("Stock"), d.getString("Description"), d.getString("Photo1"), d.getString("Photo2"), d.getString("Photo3"), d.getString("Photo4"), d.getString("Warranty"), d.getString("Quantity")));
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

        return view;
    }
}