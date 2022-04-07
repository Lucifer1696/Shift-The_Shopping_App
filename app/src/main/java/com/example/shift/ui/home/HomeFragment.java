package com.example.shift.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.example.shift.activity.ProductDetailsActivity;
import com.example.shift.activity.ViewAllActivity;
import com.example.shift.adapter.CategoryAdapter;
import com.example.shift.adapter.HomePageAdapter;
import com.example.shift.adapter.HorizontalProductScrollAdapter;
import com.example.shift.adapter.SliderAdapter;
import com.example.shift.model.HorizontalProductScrollModel;
import com.example.shift.model.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.shift.model.DBqueries.categoryModelList;
import static com.example.shift.model.DBqueries.loadcategories;
import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private HomePageAdapter homePageAdapter;
    private ImageView noInternetConnection;
    FirebaseFirestore db;

    ViewPager bannersliderViewpager;
    int currentpage = 2;
    Timer timer;
    final long DELAY_TIME = 2000;
    final long PERIOD_TIME = 2000;
    List<SliderModel> arrangedList;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home,container, false);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() == true) {
            noInternetConnection.setVisibility(View.GONE);

            /// categorires
            categoryRecyclerView =  view.findViewById(R.id.category_recyclerview);
            LinearLayoutManager linearLayoutManagerbanner = new LinearLayoutManager(getActivity());
            linearLayoutManagerbanner.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(linearLayoutManagerbanner);
            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            if(categoryModelList.size() == 0) {
                loadcategories(categoryAdapter,getContext());
            }else{
                categoryAdapter.notifyDataSetChanged();
            }
            /// homepagelayout
            db=FirebaseFirestore.getInstance();
            LinearLayout linearLayout=view.findViewById(R.id.home_linerlayout);
            View v;

            //horizontal items
            v=linearLayout.getChildAt(2);
            RecyclerView horizontalRecyclerview;
            horizontalRecyclerview=v.findViewById(R.id.horizontal_scroll_lt_recyclerview);

            List<HorizontalProductScrollModel> horizontalitmlist=new ArrayList<>();

            LinearLayoutManager testinglayoutManager = new LinearLayoutManager(v.getContext());
            testinglayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerview.setLayoutManager(testinglayoutManager);

            HorizontalProductScrollAdapter Horizontaladapter=new HorizontalProductScrollAdapter(horizontalitmlist);
            horizontalRecyclerview.setAdapter(Horizontaladapter);

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
                                int i=-1;
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                    if(i==5)
                                        break;
                                    else
                                        i++;
                                    horizontalitmlist.add(new HorizontalProductScrollModel(d.getString("Photo1"),d.getString("Name"),d.getString("Company"),d.getString("Price")));
                                }
                                Horizontaladapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
            Horizontaladapter.notifyDataSetChanged();
            Button ViewAll=v.findViewById(R.id.horizontal_scroll_lt_viewAllbtn);
            ViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewAll("S003","1");
                }
            });


            v=linearLayout.getChildAt(4);
            RecyclerView horizontalRecyclerview4;
            horizontalRecyclerview4=v.findViewById(R.id.horizontal_scroll_lt_recyclerview);

            List<HorizontalProductScrollModel> horizontalitmlist4=new ArrayList<>();

            LinearLayoutManager testinglayoutManager4 = new LinearLayoutManager(v.getContext());
            testinglayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerview4.setLayoutManager(testinglayoutManager4);

            HorizontalProductScrollAdapter Horizontaladapter4=new HorizontalProductScrollAdapter(horizontalitmlist4);
            horizontalRecyclerview4.setAdapter(Horizontaladapter4);

            db= FirebaseFirestore.getInstance();
            db.collection("Seller").whereEqualTo("SID","S002").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                        CollectionReference col = doc.get(i).getReference().collection("Items");
                        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int i=-1;
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                    if(i==5)
                                        break;
                                    else
                                        i++;
                                    horizontalitmlist4.add(new HorizontalProductScrollModel(d.getString("Photo1"),d.getString("Name"),d.getString("Company"),d.getString("Price")));

                                }
                                Horizontaladapter4.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
            Horizontaladapter4.notifyDataSetChanged();
            Button ViewAll4=v.findViewById(R.id.horizontal_scroll_lt_viewAllbtn);
            ViewAll4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewAll("S002","1");
                }
            });


            v=linearLayout.getChildAt(6);
            RecyclerView horizontalRecyclerview6;
            horizontalRecyclerview6=v.findViewById(R.id.horizontal_scroll_lt_recyclerview);

            List<HorizontalProductScrollModel> horizontalitmlist6=new ArrayList<>();

            LinearLayoutManager testinglayoutManager6 = new LinearLayoutManager(v.getContext());
            testinglayoutManager6.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerview6.setLayoutManager(testinglayoutManager6);

            HorizontalProductScrollAdapter Horizontaladapter6=new HorizontalProductScrollAdapter(horizontalitmlist6);
            horizontalRecyclerview6.setAdapter(Horizontaladapter6);

            db= FirebaseFirestore.getInstance();
            db.collection("Seller").whereEqualTo("SID","S001").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                        CollectionReference col = doc.get(i).getReference().collection("Items");
                        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int i=-1;
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                    if(i==5)
                                        break;
                                    else
                                        i++;
                                    horizontalitmlist6.add(new HorizontalProductScrollModel(d.getString("Photo1"),d.getString("Name"),d.getString("Company"),d.getString("Price")));
                                }
                                Horizontaladapter6.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
            Horizontaladapter6.notifyDataSetChanged();
            Button ViewAll6=v.findViewById(R.id.horizontal_scroll_lt_viewAllbtn);
            ViewAll6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewAll("S001","1");
                }
            });


            // GridLayouts

            v=linearLayout.getChildAt(3);
            TextView gridLayoutTitle = v.findViewById(R.id.grid_product_layout_titile);
            Button gridLayoutViewAllbtn = v.findViewById(R.id.grid_product_layout_viewAlbtn);
            GridLayout gridProductLayout = v.findViewById(R.id.grid_layout);
            ConstraintLayout constraintLayout = v.findViewById(R.id.Container);

            db.collection("Seller").whereEqualTo("SID","S010").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                        CollectionReference col = doc.get(i).getReference().collection("Items");
                        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                 
                                int i=-1;
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                    if(i==3)
                                        break;
                                    else
                                        i++;
                                    View vgrid=gridProductLayout.getChildAt(i);
                                    vgrid.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent productdetailsintet = new Intent(vgrid.getContext(), ProductDetailsActivity.class);
                                            productdetailsintet.putExtra("Name",d.getString("Name"));
                                            vgrid.getContext().startActivity(productdetailsintet);
                                        }
                                    });
                                    ImageView productImage = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_img);
                                    TextView productTitle = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_titile);
                                    TextView productDiscription = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_description);
                                    TextView productPrice = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_porduct_price);

                                    Glide.with(gridProductLayout.getContext()).load(d.getString("Photo1")).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(productImage);
                                    productTitle.setText(d.getString("Name"));
                                    productDiscription.setText(d.getString("Quantity"));
                                    productPrice.setText("Rs."+d.getString("Price")+"/-");
                                    gridProductLayout.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);

                                }
                            }
                        });
                    }
                }
            });

            gridLayoutViewAllbtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    viewAll("S015","2");
                }
            });



            v=linearLayout.getChildAt(7);
            TextView gridLayoutTitle8 = v.findViewById(R.id.grid_product_layout_titile);
            Button gridLayoutViewAllbtn8 = v.findViewById(R.id.grid_product_layout_viewAlbtn);
            GridLayout gridProductLayout8 = v.findViewById(R.id.grid_layout);
            ConstraintLayout constraintLayout8 = v.findViewById(R.id.Container);


            db.collection("Seller").whereEqualTo("SID","S015").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                        CollectionReference col = doc.get(i).getReference().collection("Items");
                        col.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                 
                                int i=-1;
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                    if(i==3)
                                        break;
                                    else
                                        i++;
                                    ImageView productImage = gridProductLayout8.getChildAt(i).findViewById(R.id.h_s_product_img);
                                    TextView productTitle = gridProductLayout8.getChildAt(i).findViewById(R.id.h_s_product_titile);
                                    TextView productDiscription = gridProductLayout8.getChildAt(i).findViewById(R.id.h_s_product_description);
                                    TextView productPrice = gridProductLayout8.getChildAt(i).findViewById(R.id.h_s_porduct_price);

                                    Glide.with(gridProductLayout8.getContext()).load(d.getString("Photo1")).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(productImage);
                                    productTitle.setText(d.getString("Name"));
                                    productDiscription.setText(d.getString("Quantity"));
                                    productPrice.setText("Rs."+d.getString("Price")+"/-");
                                    gridProductLayout8.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

                                    View vgrid3=gridProductLayout.getChildAt(i);
                                    vgrid3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent productdetailsintet = new Intent(vgrid3.getContext(), ProductDetailsActivity.class);
                                            productdetailsintet.putExtra("Name",d.getString("Name"));
                                            vgrid3.getContext().startActivity(productdetailsintet);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            });


            gridLayoutViewAllbtn8.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    viewAll("S010","2");
                }
            });

            //Slider
            v=linearLayout.getChildAt(0);
            bannersliderViewpager = v.findViewById(R.id.banner_slider_viewpager);
            currentpage = 2;
            List<SliderModel> sliderModelList = new ArrayList<>();
            db.collection("CATEGORIES")
                    .document("HOME")
                    .collection("TOP_DEALS").orderBy("index").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    if((long)documentSnapshot.get("view_type") == 0){
                                        long no_of_banners = (long) documentSnapshot.get("no_of_banners");
                                        for (long x = 1; x < no_of_banners + 1; x++) {
                                            sliderModelList.add((new SliderModel(documentSnapshot.get("banner_" + x).toString(),
                                                    documentSnapshot.get("banner_" + x + "_bgcolor").toString())));
                                        }
                                    }
                                }
                            }
                        }
                    }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(timer != null){
                        timer.cancel();
                    }
                    arrangedList = new ArrayList<>();
                    for(int x=0; x<sliderModelList.size();x++){
                        arrangedList.add(x,sliderModelList.get(x));
                    }
                    arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
                    arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
                    arrangedList.add(sliderModelList.get(0));
                    arrangedList.add(sliderModelList.get(1));

                    SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
                    bannersliderViewpager.setAdapter(sliderAdapter);
                    bannersliderViewpager.setClipToPadding(false);
                    bannersliderViewpager.setPageMargin(20);
                    bannersliderViewpager.setCurrentItem(currentpage);

                    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {
                            currentpage = position;
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                            if(state == ViewPager.SCROLL_STATE_IDLE){
                                PageLooper(arrangedList);
                            }
                        }
                    };
                    bannersliderViewpager.addOnPageChangeListener(onPageChangeListener);
                    startBannerSlideshow(arrangedList);

                    bannersliderViewpager.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            PageLooper(arrangedList);
                            stopBannerSlideshow();
                            if(event.getAction()==MotionEvent.ACTION_UP){
                                startBannerSlideshow(arrangedList);
                            }
                            return false;
                        }
                    });
                }
            });


            //strip AD
            v=linearLayout.getChildAt(1);
            ImageView stripAdImage;
            ConstraintLayout stripAdContainer;

            stripAdImage = v.findViewById(R.id.strip_ad_image);
            View finalV = v;
            db.collection("CATEGORIES").whereEqualTo("Name","Strip1").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot docspan:queryDocumentSnapshots.getDocuments())
                        Glide.with(finalV.getContext()).load(docspan.getString("Photo")).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(stripAdImage);
                }
            });

            stripAdImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("****************");
                    Intent categoryIntent = new Intent(v.getContext(), ProductDetailsActivity.class);
                    categoryIntent.putExtra("Name","LG 6.0 Kg 5 Star Inverter Washing Machine");
                    categoryIntent.putExtra("Category", "Electronics");
                    startActivity(categoryIntent);
                }
            });

            v=linearLayout.getChildAt(5);
            ImageView stripAdImage5;
            ConstraintLayout stripAdContainer5;

            stripAdImage5 = v.findViewById(R.id.strip_ad_image);
            View finalV5 = v;
            db.collection("CATEGORIES").whereEqualTo("Name","Strip2").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (DocumentSnapshot docspan:queryDocumentSnapshots.getDocuments())
                        Glide.with(finalV5.getContext()).load(docspan.getString("Photo")).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(stripAdImage5);
                }
            });

        }else{
            Glide.with(this).load(R.drawable.nointernet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);

        }
        return view;
    }

    void viewAll(String seller,String view) {
        Intent i = new Intent(getApplicationContext(), ViewAllActivity.class);
        i.putExtra("seller", seller);
        i.putExtra("view", view);
        startActivity(i);
    }

    private void PageLooper(@NotNull List<SliderModel> sliderModelList) {
        if (currentpage == sliderModelList.size() - 2) {
            currentpage = 2;
            bannersliderViewpager.setCurrentItem(currentpage, false);
        }

        if (currentpage == 1) {
            currentpage = sliderModelList.size() - 3;
            bannersliderViewpager.setCurrentItem(currentpage, false);
        }
    }
    private void startBannerSlideshow(List<SliderModel> sliderModelList) {
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentpage >= sliderModelList.size()) {
                    currentpage = 1;
                }
                bannersliderViewpager.setCurrentItem(currentpage++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }
    private void stopBannerSlideshow() {
        timer.cancel();
    }
}