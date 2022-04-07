package com.example.shift.ui.fragmentsfornav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.shift.R;
import com.example.shift.activity.CategoryActivity;

public class shopBycategory extends Fragment {

    CardView mobileCardView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shop_bycategory,container, false);
        mobileCardView = view.findViewById(R.id.mobile_category);
        mobileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), CategoryActivity.class);
                String name = "Mobiles";
                intent.putExtra("CategoryName", name);
                startActivity(intent);
            }
        });
        return view;
    }
}