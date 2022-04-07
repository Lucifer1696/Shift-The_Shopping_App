package com.example.shift.ui.fragmentsfornav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shift.R;


public class ProductDescriptionFragment extends Fragment {

    String Description;

    public ProductDescriptionFragment(String description) {
        Description = description;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        TextView description=view.findViewById(R.id.product_Description_tv);
        description.setText(Description);
        return view;
    }
}