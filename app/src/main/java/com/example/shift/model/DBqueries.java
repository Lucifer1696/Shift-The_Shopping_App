package com.example.shift.model;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shift.adapter.CategoryAdapter;
import com.example.shift.adapter.HomePageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBqueries {

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModal> homePageModalList = new ArrayList<>();

    public static void loadcategories(final CategoryAdapter categoryAdapter,final Context context){

        db.collection("CATEGORIES").orderBy("Index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                try {
                                    categoryModelList.add(new CategoryModel(documentSnapshot.get("Icon").toString(), documentSnapshot.get("categoryName").toString()));
                                }catch (Exception e){
                                    System.out.println("The Errror is >>>>>>>>>>>>>>>>>>>"+e);
                                }
                            }
                            categoryAdapter.notifyDataSetChanged();
                        }else{
                            String error =  task.getException().toString();
                            Toast.makeText(context,error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(final HomePageAdapter homePageAdapter, final Context context){
        db.collection("CATEGORIES")
                .document("HOME")
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if((long)documentSnapshot.get("view_type") == 0){
                                    List<SliderModel>sliderModelList = new ArrayList<>();
                                    long no_of_banners = (long)documentSnapshot.get("no_of_banners");
                                    for(long x= 1; x < no_of_banners+1; x++ ){
                                        sliderModelList.add((new SliderModel(documentSnapshot.get("banner_"+x).toString(),
                                                documentSnapshot.get("banner_"+x+"_bgcolor").toString())));
                                    }
                                    homePageModalList.add(new HomePageModal(0,sliderModelList));
                                }else if((long)documentSnapshot.get("view_type") == 1){
                                    homePageModalList.add(new HomePageModal(1,documentSnapshot.get("strip_ad_banner").toString(),
                                            documentSnapshot.get("bgcolor").toString()));
                                }else if((long)documentSnapshot.get("view_type") == 2){
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for(long x= 1; x < no_of_products+1; x++ ){
//                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
//                                                ,documentSnapshot.get("product_image_"+x).toString()
//                                                ,documentSnapshot.get("product_title_"+x).toString()
//                                                ,documentSnapshot.get("product_subtitle_"+x).toString()
//                                                ,documentSnapshot.get("product_price_"+x).toString()));
                                    }
                                    homePageModalList.add(new HomePageModal(2,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList));
                                }else if((long)documentSnapshot.get("view_type") == 3){
                                    List<HorizontalProductScrollModel> gridlayoutModelList = new ArrayList<>();
                                    db.collection("Seller").whereEqualTo("SID","S011").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                                DocumentReference doc=d.getReference();
                                                doc.collection("Items").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                        for (DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
//                                                            gridlayoutModelList.add(new HorizontalProductScrollModel("$$$$$"
//                                                                    ,d.getString("Photo1")
//                                                                    ,d.getString("Name")
//                                                                    ,d.getString("Company")
//                                                                    ,d.getString("Price")));
                                                        }
                                                    }
                                                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        homePageModalList.add(new HomePageModal(3,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),gridlayoutModelList));
                                                    }
                                                });
                                            }

                                        }
                                    });
                                }
                            }
                            homePageAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
