package com.example.shift.ui.fragmentsfornav;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.activity.DeliveryActivity;
import com.example.shift.adapter.ShowCategoryItems;
import com.example.shift.model.Allitemmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    Button cart_continue_btn;
    private RecyclerView cartItemsRecyclerView;
    FirebaseFirestore db;
    ArrayList<Allitemmodel> allitemmodels;
    SharedPreferences shrd;
    SharedPreferences.Editor editor;
    TextView TotalCartAmount,TotalItems,SavedAmmount,TotalPrice,TotalItemsPrice;
    double totalamount=0,totalitems=0,savedammount=0;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        shrd = this.getActivity().getSharedPreferences("Shift", Context.MODE_PRIVATE);
        editor=shrd.edit();
        TotalCartAmount=view.findViewById(R.id.total_cart_amount);
        SavedAmmount=view.findViewById(R.id.saved_amount);
        TotalItems=view.findViewById(R.id.total_items);
        TotalPrice=view.findViewById(R.id.total_price);
        TotalItemsPrice=view.findViewById(R.id.total_items_price);

        cart_continue_btn = view.findViewById(R.id.delivery_done_Btn);
        cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);

        cart_continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(view.getContext(), DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });

        allitemmodels = new ArrayList<>();
        ShowCategoryItems adapter = new ShowCategoryItems(allitemmodels,"cart");
        cartItemsRecyclerView.setAdapter(adapter);


        db= FirebaseFirestore.getInstance();
        db.collection("Customer").whereEqualTo("CID",shrd.getString("CID","####")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("CartItems");
                    col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        return view;
    }
}