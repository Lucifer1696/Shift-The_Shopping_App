package com.example.shift.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shift.model.ProductSpecificationModel;
import com.example.shift.ui.fragmentsfornav.ProductDescriptionFragment;
import com.example.shift.ui.fragmentsfornav.ProductSpecificationFragment;

import java.util.ArrayList;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    public int totalTab=1212;
    public String Description;
    ArrayList<ProductSpecificationModel> Specificationlist;

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTab,String description) {
        super(fm);
        this.totalTab=totalTab;
        Description=description;
    }

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTab, String description, ArrayList<ProductSpecificationModel> specificationlist) {
        super(fm);
        this.totalTab = totalTab;
        Description = description;
        Specificationlist = specificationlist;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case  0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment(Description);
                return  productDescriptionFragment;
            case  1:
                ProductSpecificationFragment productSpecificationFragment =new ProductSpecificationFragment(Specificationlist);
                return productSpecificationFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTab;
    }
}
