package com.example.shift.ui.fragmentsfornav;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.adapter.MyOrdersAdapter;
import com.example.shift.model.MyOrdersItemsModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {

    FirebaseFirestore db;
    SharedPreferences shrd;
    private RecyclerView myOrderRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders,container,false);

        shrd=getActivity().getSharedPreferences("Shift", Context.MODE_PRIVATE);
        myOrderRecyclerView = view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(linearLayoutManager);

        List<MyOrdersItemsModel> myOrdersItemsModelList = new ArrayList<>();

        MyOrdersAdapter myOrdersAdapter = new MyOrdersAdapter(myOrdersItemsModelList);
        myOrderRecyclerView.setAdapter(myOrdersAdapter);

        db= FirebaseFirestore.getInstance();
        db.collection("Customer").whereEqualTo("CID",shrd.getString("CID","####")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("MyOrders");
                    col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                myOrdersItemsModelList.add(new MyOrdersItemsModel(d.getString("Photo1") , d.getString("Name"), d.getString("DeliveryDate")));
                            }
                            myOrdersAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        myOrdersAdapter.notifyDataSetChanged();

        return view;
    }
}