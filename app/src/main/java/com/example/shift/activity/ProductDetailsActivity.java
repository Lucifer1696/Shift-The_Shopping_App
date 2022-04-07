package com.example.shift.activity;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.shift.R;
import com.example.shift.adapter.ProductDetailsAdapter;
import com.example.shift.adapter.ProductImagesAdapter;
import com.example.shift.model.ProductSpecificationModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImageViewPager;
    private TabLayout viewpagerIndicator;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    private FloatingActionButton addToWishlistBtn;
    private TextView product_title,dis_price,cuted_price,Discount,company, quantity;
    private LinearLayout l1;

    private ViewPager productDetailViewpager;
    private TabLayout ProductDetailTablaout;
    public FirebaseFirestore db;
    DocumentReference Idoc;
    CollectionReference Ccol;

    // rate now
    private LinearLayout rateNowContainer;

    ArrayList<ProductSpecificationModel> specificationlist;

    public Button addcartbtn;
    public Map<String,String> cartitem;
    public SharedPreferences shrd;
    public SharedPreferences.Editor editor;
    public String CID,name;

    String category="kmghk";
    String Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name =getIntent().getStringExtra("Name");
        specificationlist=new ArrayList<>();

        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        editor=shrd.edit();
        CID=shrd.getString("CID","$$$$");

        cartitem= new HashMap<String,String>();

        productImageViewPager = findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistBtn = findViewById(R.id.floatingActionButton4);
        productDetailViewpager=findViewById(R.id.product_details_viewpager);
        ProductDetailTablaout=findViewById(R.id.product_details_tablayout);
        product_title=findViewById(R.id.product_titile);
        company=findViewById(R.id.company);
        quantity =findViewById(R.id.quantity);
        dis_price=findViewById(R.id.discnt_price);
        cuted_price=findViewById(R.id.cuted_price);
        Discount=findViewById(R.id.Discount);
        l1=findViewById(R.id.linearLayout7);
        addcartbtn=findViewById(R.id.AddToCartBtn);

        // ratings layout

        rateNowContainer =  findViewById(R.id.rate_now_container);
        for(int x =0;x< rateNowContainer.getChildCount();x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRatings(starPosition);
                }
            });
        }
        // ratings layout



        db= FirebaseFirestore.getInstance();


        db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                        for(int i=0;i<queryDocumentSnapshots.size();i++){
                            CollectionReference col = doc.get(i).getReference().collection("Items");
                            col.whereEqualTo("Name",name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                        final Boolean[] b = {false};
                                        cartitem.put("Category", d.getString("Category")); //Put elements in cartitem
                                        cartitem.put("Name", d.getString("Name"));
                                        cartitem.put("Company", d.getString("Company"));
                                        cartitem.put("Price", d.getString("Price"));
                                        cartitem.put("Discount", d.getString("Discount"));
                                        cartitem.put("Quantity", d.getString("Quantity"));
                                        cartitem.put("Photo1", d.getString("Photo1"));

                                        category=d.getString("Category");
                                        Description=d.getString("Description");

                                        if(category.equals("Mobiles"))
                                        {
                                            specificationlist.add(new ProductSpecificationModel("RAM",d.getString("RAM")));
                                            specificationlist.add(new ProductSpecificationModel("ROM",d.getString("ROM")));
                                            specificationlist.add(new ProductSpecificationModel("Processor",d.getString("Processor")));
                                            specificationlist.add(new ProductSpecificationModel("camera",d.getString("camera")));
                                            specificationlist.add(new ProductSpecificationModel("OS",d.getString("OS")));
                                            specificationlist.add(new ProductSpecificationModel("Bettary",d.getString("Bettary")));
                                            specificationlist.add(new ProductSpecificationModel("Inches",d.getString("Inches")));
                                            specificationlist.add(new ProductSpecificationModel("Wireless communication\n",d.getString("Wireless communication")));
                                            specificationlist.add(new ProductSpecificationModel("Battery Power Rating",d.getString("Battery Power Rating")));
                                            specificationlist.add(new ProductSpecificationModel("Weight",d.getString("Weight")));
                                            specificationlist.add(new ProductSpecificationModel("Colour",d.getString("Colour")));
                                            specificationlist.add(new ProductSpecificationModel("Audio Jack",d.getString("Audio Jack")));
                                        }

                                        db.collection("Customer").whereEqualTo("CID",CID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                                    DocumentReference doc =d.getReference();
                                                    Ccol=doc.collection("WishList");
                                                    doc.collection("WishList").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                            for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                                                Idoc =d.getReference();
                                                                if(d.contains("Name")) {
                                                                    if (d.getString("Name").equals(product_title.getText().toString())){
                                                                        addToWishlistBtn.setSupportBackgroundTintList(getResources().getColorStateList(R.color.red));
                                                                        ALREADY_ADDED_TO_WISHLIST = true;
                                                                    }
                                                                }
                                                                else {
                                                                    Idoc.delete();
                                                                }

                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }

                                }
                            }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size()>0) {
                                        if (category.equals("Mobiles")) {
                                            productDetailViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), ProductDetailTablaout.getTabCount(), Description, specificationlist));
                                            productDetailViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(ProductDetailTablaout));
                                            ProductDetailTablaout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                                @Override
                                                public void onTabSelected(TabLayout.Tab tab) {
                                                    productDetailViewpager.setCurrentItem(tab.getPosition());
                                                }

                                                @Override
                                                public void onTabUnselected(TabLayout.Tab tab) {

                                                }

                                                @Override
                                                public void onTabReselected(TabLayout.Tab tab) {

                                                }
                                            });
                                        } else {
                                            ProductDetailTablaout.removeTabAt(1);
                                            ProductDetailsAdapter productDetailsAdapter=new ProductDetailsAdapter(getSupportFragmentManager(), ProductDetailTablaout.getTabCount(), Description);
                                            productDetailViewpager.setAdapter(productDetailsAdapter);
                                            productDetailViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(ProductDetailTablaout));
                                            ProductDetailTablaout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                                @Override
                                                public void onTabSelected(TabLayout.Tab tab) {
                                                    productDetailViewpager.setCurrentItem(tab.getPosition());
                                                }

                                                @Override
                                                public void onTabUnselected(TabLayout.Tab tab) {

                                                }

                                                @Override
                                                public void onTabReselected(TabLayout.Tab tab) {

                                                }
                                            });
                                            productDetailsAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }
                            });
                        }
                    }
                });

              addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if(ALREADY_ADDED_TO_WISHLIST){
                          ALREADY_ADDED_TO_WISHLIST = false;
                          addToWishlistBtn.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C6C5B8")));
                          // Remove to Wishlist
                          Ccol.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                              @Override
                              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                  for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                      if(d.contains("Name")) {
                                          if (d.getString("Name").equals(product_title.getText().toString())){
                                              Idoc =d.getReference();
                                              Idoc.delete();
                                          }
                                      }
                                  }
                              }
                          });
                      }else{
                          ALREADY_ADDED_TO_WISHLIST = true;
                          addToWishlistBtn.setSupportBackgroundTintList(getResources().getColorStateList(R.color.red));
                          // Add to Wishlist
                          Ccol.add(cartitem);
                      }
                  }
              });

        List<String> productImages = new ArrayList<>();
        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImageViewPager.setAdapter(productImagesAdapter);

        db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                for(int i=0;i<queryDocumentSnapshots.size();i++){
                    CollectionReference col = doc.get(i).getReference().collection("Items");
                    col.whereEqualTo("Name",name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                String qnt=d.getString("Quantity"),dis=d.getString("Discount"),pri=d.getString("Price");
                                product_title.setText(d.getString("Name"));
                                company.setText(d.getString("Company"));
                                quantity.setText(qnt);
                                if(dis.equals("0") || pri.equals("")){
                                    l1.setVisibility(View.INVISIBLE);
                                    cuted_price.setVisibility(View.INVISIBLE);
                                    dis_price.setText(pri);
                                }
                                else
                                {
                                    double amount=Double.parseDouble(pri), dist=Double.parseDouble(dis);
                                    amount -= ((dist * amount) / 100);
                                    dis_price.setText(String.valueOf(amount));
                                    cuted_price.setText(pri);
                                    Discount.setText(dis);
                                }
                                productImages.add(d.getString("Photo1"));
                                productImages.add(d.getString("Photo2"));
                                productImages.add(d.getString("Photo3"));
                                productImages.add(d.getString("Photo4"));
                            }
                            productImagesAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        cartitem=new HashMap<String, String>();

        // Add Product into Cart
        addcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                        for(int i=0;i<queryDocumentSnapshots.size();i++){
                            CollectionReference col = doc.get(i).getReference().collection("Items");
                            col.whereEqualTo("Name",name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {
                                        final Boolean[] b = {false};
                                           cartitem.put("Category", d.getString("Category")); //Put elements in cartitem.
                                            cartitem.put("Name", d.getString("Name"));
                                            cartitem.put("Company", d.getString("Company"));
                                            cartitem.put("Price", d.getString("Price"));
                                            cartitem.put("Discount", d.getString("Discount"));
                                            cartitem.put("Warranty", d.getString("Warranty"));
                                            cartitem.put("Stock", d.getString("Stock"));
                                            cartitem.put("Description", d.getString("Stock"));
                                            cartitem.put("Quantity", d.getString("Quantity"));
                                            cartitem.put("Photo1", d.getString("Photo1"));
                                            cartitem.put("Photo2", d.getString("Photo2"));
                                            cartitem.put("Photo3", d.getString("Photo3"));
                                            cartitem.put("Photo4", d.getString("Photo4"));
                                            cartitem.put("NumberOfItem","1");
                                        db.collection("Customer").whereEqualTo("CID",CID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                                    DocumentReference doc =d.getReference();
                                                    CollectionReference Ccol=doc.collection("CartItems");
                                                    doc.collection("CartItems").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                            boolean b=true;
                                                            for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){

                                                                DocumentReference Idoc =d.getReference();
                                                                if(d.contains("Name")) {
                                                                    if (d.getString("Name").equals(product_title.getText().toString())){
                                                                        Idoc.update("NumberOfItem",String.valueOf(Integer.parseInt(d.getString("NumberOfItem"))+1));
                                                                        b=false;
                                                                    }
                                                                }
                                                                else {
                                                                    Idoc.delete();
                                                                    Ccol.add(cartitem);
                                                                    b=false;
                                                                }
                                                            }
                                                            if(b)
                                                                Ccol.add(cartitem);
                                                        }
                                                    });
                                                }
                                            }
                                        });

                                    }
                                   
                                }
                            });
                        }
                    }
                });

            }
        });



        productImagesAdapter.notifyDataSetChanged();


        viewpagerIndicator.setupWithViewPager(productImageViewPager,true);

        //Product Description




    }

    public void setRatings(int starPosition) {
        for(int x= 0;x< rateNowContainer.getChildCount();x++){
            ImageView starBtn  =  (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if(x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.layout.fragment_my_cart){
            //todo
            return true;
        }
        else if(id == R.id.main_search_icon) {
            //todo
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}