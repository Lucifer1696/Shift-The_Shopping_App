package com.example.shift.ui.fragmentsfornav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;
import com.example.shift.adapter.ProductSpecificationAdapter;
import com.example.shift.model.ProductSpecificationModel;

import java.util.ArrayList;
import java.util.List;


public class ProductSpecificationFragment extends Fragment {

    private RecyclerView productSpecificationRecyclerview;

    ArrayList<ProductSpecificationModel> specificationlist;

    public ProductSpecificationFragment(ArrayList<ProductSpecificationModel> specificationlist) {
        this.specificationlist = specificationlist;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecyclerview = view.findViewById(R.id.product_specification_recyclerview);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        productSpecificationRecyclerview.setLayoutManager(linearLayoutManager);

        List<ProductSpecificationModel> productSpecificationModelsList=specificationlist;
        ProductSpecificationAdapter productSpecificationAdapter= new ProductSpecificationAdapter(productSpecificationModelsList);
        productSpecificationRecyclerview.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }
}