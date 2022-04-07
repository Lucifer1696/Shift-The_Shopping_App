package com.example.shift.ui.fragmentsfornav;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.shift.R;
import com.example.shift.activity.AddProduct;

public class SellOnShift extends Fragment {

    Button sellbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_on_shift, container, false);

        sellbtn = view.findViewById(R.id.sellbtn);
        sellbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), AddProduct.class);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}