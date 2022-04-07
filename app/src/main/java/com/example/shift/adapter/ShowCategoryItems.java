package com.example.shift.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shift.R;
import com.example.shift.activity.AddProduct;
import com.example.shift.activity.ProductDetailsActivity;
import com.example.shift.model.Allitemmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowCategoryItems extends RecyclerView.Adapter<ShowCategoryItems.ViewHolder> {
    public ArrayList<Allitemmodel> allitemmodels;
    String type;
    Map cartitem;

    public ShowCategoryItems(ArrayList<Allitemmodel> allitemmodels) {
       this( allitemmodels,"regular");
    }

    public ShowCategoryItems(ArrayList<Allitemmodel> allitemmodels,String type) {
        this.allitemmodels = allitemmodels;
        this.type=type;
    }

    @NonNull
    @Override
    public ShowCategoryItems.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout,parent,false);
        if(type.equals("cart"))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
        if(type.equals("order"))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_orders_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowCategoryItems.ViewHolder holder, int position) {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
         CollectionReference[] Cartcol = new CollectionReference[5];
         DocumentSnapshot[] cartitemsnap = new DocumentSnapshot[5];
         DocumentReference[] cartitmedoc =new DocumentReference[5];

        if(type.equals("regular"))
            holder.qauntity.setText(allitemmodels.get(position).getQuantity());
        holder.product_title.setText(allitemmodels.get(position).getName());
        holder.company.setText(allitemmodels.get(position).getCompany());
        if(allitemmodels.get(position).getDiscount().equals("0") || allitemmodels.get(position).getDiscount().equals("")){
            holder.l1.setVisibility(View.INVISIBLE);
            holder.cuted_price.setVisibility(View.INVISIBLE);
            holder.dis_price.setText("Rs."+allitemmodels.get(position).getPrice()+"/-");
        }
        else
        {
            double amnt=Double.parseDouble(allitemmodels.get(position).getPrice());
            double dis=Double.parseDouble(allitemmodels.get(position).getDiscount());
            amnt=amnt-(dis*amnt/100);

            holder.dis_price.setText("Rs."+String.valueOf(amnt)+" /-");
            holder.cuted_price.setText("Rs."+allitemmodels.get(position).getPrice());
            holder.Discount.setText(allitemmodels.get(position).getDiscount());
        }

    if(type.equals("regular")) {
        db.collection("Customer").whereEqualTo("CID", allitemmodels.get(position).getCID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                    Cartcol[0] = d.getReference().collection("CartItems");
                    Cartcol[0].get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                                if (d.contains("Name")) {
                                    if (d.getString("Name").equals(allitemmodels.get(position).getName())) {
                                        holder.button2.setVisibility(View.INVISIBLE);
                                        holder.linearLayout12.setVisibility(View.VISIBLE);
                                        holder.numbwe_of_cartitem.setText(d.getString("NumberOfItem"));
                                    }
                                } else {
                                    DocumentReference delete = d.getReference();
                                    delete.delete();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
    else if(type.equals("cart")){
        db.collection("Customer").whereEqualTo("CID", allitemmodels.get(position).getCID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()) {
                    Cartcol[0] = d.getReference().collection("CartItems");
                }
            }
        });
    }
        cartitem=new HashMap<String,String>();

       if(type.equals("regular") && allitemmodels.get(position).getCID().equals("Seller")==false) {
            // Add Product into Cart
            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartitem.put("Category", allitemmodels.get(position).getCategory()); //Put elements in cartitem.
                    cartitem.put("Name", allitemmodels.get(position).getName());
                    cartitem.put("Company", allitemmodels.get(position).getCompany());
                    cartitem.put("Price", allitemmodels.get(position).getPrice());
                    cartitem.put("Discount",allitemmodels.get(position).getDiscount());
                    cartitem.put("Warranty", allitemmodels.get(position).getDiscount());
                    cartitem.put("Stock", allitemmodels.get(position).getStock());
                    cartitem.put("Description", allitemmodels.get(position).getDescription());
                    cartitem.put("Quantity", allitemmodels.get(position).getQuantity());
                    cartitem.put("Photo1", allitemmodels.get(position).getPhoto1());
                    cartitem.put("Photo2", allitemmodels.get(position).getPhoto2());
                    cartitem.put("Photo3", allitemmodels.get(position).getPhoto3());
                    cartitem.put("Photo4", allitemmodels.get(position).getPhoto4());
                    cartitem.put("NumberOfItem", "1");
                    Cartcol[0].add(cartitem).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()) {
                                cartitmedoc[0] = task.getResult();
                                holder.button2.setVisibility(View.INVISIBLE);
                                holder.linearLayout12.setVisibility(View.VISIBLE);
                                holder.numbwe_of_cartitem.setText("1");
                            }
                        }
                    });
                }
            });
        }
       if(allitemmodels.get(position).getCID().equals("Seller")){
           holder.button2.setText("Edit");
           holder.button2.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent categoryIntent = new Intent(v.getContext(), AddProduct.class);
                   categoryIntent.putExtra("Name", allitemmodels.get(position).getName());
                   holder.v.getContext().startActivity(categoryIntent);
               }
           });
       }
        // SET Image
        holder.setCategoryIcon(allitemmodels.get(position).getPhoto1());

        if(type.equals("cart")||type.equals("regular")){
            holder.numbwe_of_cartitem.setText(allitemmodels.get(position).getNumbweOfCartitem());

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cartcol[0].get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                            if(allitemmodels.get(position).getName().equals(d.getString("Name"))){
                                holder.numbwe_of_cartitem.setText(String.valueOf(Integer.parseInt(d.getString("NumberOfItem"))+1));
                                DocumentReference doc=d.getReference();
                                doc.update("NumberOfItem",String.valueOf(Integer.parseInt(d.getString("NumberOfItem"))+1));
                            }
                        }
                    });
                }
            });
            holder.sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cartcol[0].get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                                if(allitemmodels.get(position).getName().equals(d.getString("Name"))){
                                    String NumberOfItem= d.getString("NumberOfItem");
                                    if ((Integer.parseInt(NumberOfItem) - 1)>0) {
                                        holder.numbwe_of_cartitem.setText(String.valueOf(Integer.parseInt(NumberOfItem) - 1));
                                        d.getReference().update("NumberOfItem",String.valueOf(Integer.parseInt(NumberOfItem) - 1));
                                    }
                                    else {
                                        d.getReference().delete();
                                        if(type.equals("cart")) {
                                            allitemmodels.remove(position);
                                            holder.v.setVisibility(View.INVISIBLE);
                                        }
                                        if(type.equals("regular")) {
                                            holder.linearLayout12.setVisibility(View.INVISIBLE);
                                            holder.button2.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                        }
                    });
                }
            });
            if(type.equals("cart"))
                holder.removeitemtv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cartcol[0].get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                            if(allitemmodels.get(position).getName().equals(d.getString("Name"))){
                               DocumentReference doc=d.getReference();
                                doc.delete();
                                allitemmodels.remove(position);
                                holder.v.setVisibility(View.INVISIBLE);
                            }
                    }
                });
                }
            });
        }
        else if(type.equals("wish")){
            holder.button2.setVisibility(View.INVISIBLE);
            holder.deletwish.setVisibility(View.VISIBLE);
            holder.deletwish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.collection("Customer").whereEqualTo("CID",allitemmodels.get(position).getCID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                DocumentReference doc =d.getReference();
                                CollectionReference Ccol=doc.collection("WishList");
                                doc.collection("WishList").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        boolean b=true;
                                        for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                           if (d.getString("Name").equals(allitemmodels.get(position).getName())) {
                                               DocumentReference Cartdoc=d.getReference();
                                               Cartdoc.delete();
                                                allitemmodels.remove(position);
                                                holder.v.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allitemmodels.get(position).getCID().equals("Seller")) {
                    Intent categoryIntent = new Intent(v.getContext(), ProductDetailsActivity.class);
                    categoryIntent.putExtra("Name", allitemmodels.get(position).getName());
                    categoryIntent.putExtra("Category", allitemmodels.get(position).getCategory());
                    holder.v.getContext().startActivity(categoryIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return allitemmodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_title,dis_price,Discount,company,qauntity,numbwe_of_cartitem,removeitemtv,cuted_price;
        LinearLayout l1,linearLayout12;
        ImageView img1;
        Button button2;
        ImageButton add,sub,deletwish;
        View v;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_title=itemView.findViewById(R.id.product_title);
            dis_price=itemView.findViewById(R.id.dis_price);
            cuted_price=itemView.findViewById(R.id.cuted_price);
            Discount=itemView.findViewById(R.id.Discount);
            company=itemView.findViewById(R.id.company);
            qauntity=itemView.findViewById(R.id.qauntity);
            l1=itemView.findViewById(R.id.linearLayout7);
            img1=itemView.findViewById(R.id.product_image);
            v=itemView;
            linearLayout12=itemView.findViewById(R.id.linearLayout12);
            if(type.equals("wish"))
                deletwish=itemView.findViewById(R.id.delete_wishitem_btn);
            button2=itemView.findViewById(R.id.button2);


            if(type.equals("cart")||type.equals("regular")){
                add=itemView.findViewById(R.id.add);
                sub=itemView.findViewById(R.id.sub);
                numbwe_of_cartitem=itemView.findViewById(R.id.numbwe_of_cartitem);
                if (type.equals("cart"))
                    removeitemtv=itemView.findViewById(R.id.removeitembtn);
            }

        }

        public void setCategoryIcon(String iconUrl){
            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.ic_home2)).into(img1);
        }
    }
}
