package com.example.shift.ui.fragmentsfornav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shift.R;
import com.example.shift.seller.SellerHome;
import com.example.shift.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Account extends Fragment {

    ImageView EditButton;
    Button signoutbtn;
    TextView TitleTxt;
    TextView txtName;
    TextView txtAddress;
    TextView txtPhone;
    TextView txtDob,txtEmail,custName,backTextview,change;
    SharedPreferences shrd;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String module,id;

    public Account(String module, String id) {
        this.module = module;
        this.id = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        shrd = this.getActivity().getSharedPreferences("Shift", Context.MODE_PRIVATE);
        custName=view.findViewById(R.id.Seller_name);
        txtName =view.findViewById(R.id.nameTextView);
        txtEmail=view.findViewById(R.id.emailTextView);
        txtAddress =view.findViewById(R.id.addressTextView);
        txtPhone =view.findViewById(R.id.mobileTextView);
        txtDob =view.findViewById(R.id.dobTextView);
        backTextview=view.findViewById(R.id.back_textview);
        change=view.findViewById(R.id.change);

        backTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(module.equals("Seller")){
                    Intent i = new Intent(getActivity(), SellerHome.class);
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0, 0);
                }
                else {
                    FragmentTransaction fr = getFragmentManager().beginTransaction();
                    fr.replace(R.id.container,new HomeFragment());
                    fr.commit();
                }
            }
        });

        fetchData();

        return view;
    }
    public void fetchData(){
        db=FirebaseFirestore.getInstance();
        db= FirebaseFirestore.getInstance();
        db.collection(module).whereEqualTo(id,shrd.getString(id,"####")).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    txtName.setText(doc.get(i).getString("Name"));
                    custName.setText(doc.get(i).getString("Name"));
                    txtAddress.setText(doc.get(i).getString("Address"));
                    txtPhone.setText(doc.get(i).getString("PhoneNo"));
                    txtEmail.setText(doc.get(i).getString("Email"));
                    if(module.equals("Customer"))
                        txtDob.setText(doc.get(i).getString("DOB"));
                    else {
                        txtDob.setText(doc.get(i).getString("ShopName"));
                        change.setText("Shop Name :");
                    }
                }
            }
        });
    }
    void backtohome(View v){
        
    }
}