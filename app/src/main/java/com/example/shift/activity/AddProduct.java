package com.example.shift.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shift.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

   int cnt=1;
    private int code=8533;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseStorage st=FirebaseStorage.getInstance();
    SharedPreferences shrd;
    DocumentReference deletedoc;

    ConstraintLayout m;
    String EditProductName;
    String category;
    String SID;
    String Photos[]=new String[5];
    EditText name,company,price,discount,quantity,stock,description,warranty;
    EditText RAM;
    EditText ROM;
    EditText Processor;
    EditText camera;
    EditText OS;
    EditText Bettary;
    EditText Inches;
    EditText Wireless_communication;
    EditText Battery_Power_Rating;
    EditText Weight;
    EditText Colour;
    EditText Audio_Jack;
    ImageView img1,img2,img3,img4;
    Button upldimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        st=FirebaseStorage.getInstance();
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img1=findViewById(R.id.imageView1);
        img2=findViewById(R.id.imageView2);
        img3=findViewById(R.id.imageView3);
        img4=findViewById(R.id.imageView4);
        upldimg=findViewById(R.id.button3);
        name=findViewById(R.id.name);
        company=findViewById(R.id.company);
        price=findViewById(R.id.price);
        discount=findViewById(R.id.discount);
        quantity=findViewById(R.id.product_quantty);
        warranty=findViewById(R.id.warranty);
        stock=findViewById(R.id.stock);
        description=findViewById(R.id.descrption);
        RAM=findViewById(R.id.RAM);
        ROM=findViewById(R.id.ROM);
        Weight=findViewById(R.id.Weight);
        Bettary=findViewById(R.id.Bettary);
        Battery_Power_Rating=findViewById(R.id.Power_Rating);
        Processor=findViewById(R.id.Processor);
        camera=findViewById(R.id.camera);
        OS=findViewById(R.id.OS);
        Inches=findViewById(R.id.Inches);
        Audio_Jack=findViewById(R.id.Audio_Jack);
        Wireless_communication=findViewById(R.id.Wireless_communication);
        Colour=findViewById(R.id.Colour);
        Spinner spinner = findViewById(R.id.spinner1);


        shrd=getSharedPreferences("Shift",MODE_PRIVATE);

        SID =shrd.getString("SID","0000");

        EditProductName=getIntent().getStringExtra("Name");
        if(EditProductName!=null){
            db.collection("Seller").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                    for(int i=0;i<queryDocumentSnapshots.size();i++){
                        CollectionReference col = doc.get(i).getReference().collection("Items");
                        col.whereEqualTo("Name",EditProductName).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()) {

                                    final Boolean[] b = {false};
                                    deletedoc=d.getReference();
                                    spinner.setSelection(0);
                                   name.setText(d.getString("Name"));
                                    company.setText(d.getString("Company"));
                                    price.setText(d.getString("Price"));
                                    discount.setText(d.getString("Discount"));
                                    quantity.setText( d.getString("Quantity"));
                                    stock.setText( d.getString("Stock"));
                                    warranty.setText( d.getString("Warranty"));

                                    category=d.getString("Category");
                                    description.setText(d.getString("Description"));

                                    if(category.equals("Mobiles"))
                                    {
                                        m =findViewById(R.id.mobile_constraintLayout);
                                        m.setVisibility(View.VISIBLE);
                                        RAM.setText(d.getString("RAM"));
                                        ROM.setText(d.getString("ROM"));
                                        name.setText(d.getString("Processor"));
                                        name.setText(d.getString("camera"));
                                        name.setText(d.getString("OS"));
                                        name.setText(d.getString("Bettary"));
                                        name.setText(d.getString("Inches"));
                                        name.setText(d.getString("Wireless communication"));
                                        name.setText(d.getString("Battery Power Rating"));
                                        name.setText(d.getString("Weight"));
                                        name.setText(d.getString("Colour"));
                                        name.setText(d.getString("Audio Jack"));
                                    }

                                }

                            }
                        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().size()>0) {
                                    deletedoc.delete();
                                }
                            }
                        });
                    }
                }
            });

        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category=parent.getItemAtPosition(position).toString();
                if(category.equals("Mobiles")){
                    m =findViewById(R.id.mobile_constraintLayout);
                    m.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_cart){
            //todo
            return true;
        }
        else if(id == R.id.action_notifiaction) {
            //todo
            return true;
        }else if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void uploadimg(View v){
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it,code);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == code  && resultCode  == RESULT_OK) {
                Uri imgUri= data.getData();
                if(cnt==1){
                    img1.setImageURI(imgUri);
                    upldimg.setText("Upload Photos 2");
                }
                else if(cnt==2){
                    img2.setImageURI(imgUri);
                    upldimg.setText("Upload Photos 3");
                }
                else if(cnt==3){
                    img3.setImageURI(imgUri);
                    upldimg.setText("Upload Photos 4");
                }
                else if(cnt==4){
                    img4.setImageURI(imgUri);
                    upldimg.setText("Photos Uploaded");
                    upldimg.setEnabled(false);
                }
                String url ="/Categories/"+category+"/";
                StorageReference sr = st.getReference(url);
                EditText name =findViewById(R.id.name);
                sr=sr.child(name.getText().toString()+String.valueOf(cnt));
                sr.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_SHORT).show();
                        cnt++;

                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (cnt==5)
                            cnt=1;
                    }
                });

            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }

    }


    public void addProduct(View v){





        if(name.getText().equals("") ||company.getText().equals("") ||price.getText().equals("") ||discount.getText().equals("") ||quantity.getText().equals("") ||stock.getText().equals("") ||description.getText().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Any Field is not set";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else {
            db=FirebaseFirestore.getInstance();
            st=FirebaseStorage.getInstance();
//            ArrayList<List<FileDownloadTask>> Photos=new ArrayList<List<FileDownloadTask>>();
            String Photos[] = new String[4];

            db.collection("Seller").whereEqualTo("SID",SID).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                        DocumentReference doc=d.getReference();
                        Map<String,String> map=new HashMap<String, String>();//Creating HashMap.
                        doc.collection("Items").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int n=queryDocumentSnapshots.size()+1;
                                map.put("Category",category); //Put elements in Map.
                                map.put("Name",name.getText().toString());
                                map.put("Company",company.getText().toString());
                                map.put("Price",price.getText().toString());
                                map.put("Discount",discount.getText().toString());
                                map.put("Warranty",warranty.getText().toString());
                                map.put("Stock",stock.getText().toString());
                                map.put("Description",description.getText().toString());
                                map.put("Quantity",quantity.getText().toString());
                                map.put("ItmID",SID+"Itm00"+String.valueOf(n));
                                map.put("Photo1","");
                                map.put("Photo2","");
                                map.put("Photo3","");
                                map.put("Photo4","");
                                if(category.equals("Mobiles")){
                                    map.put("RAM",RAM.getText().toString()); //Put elements in Map.
                                    map.put("ROM",ROM.getText().toString());
                                    map.put("Processor",Processor.getText().toString());
                                    map.put("camera",camera.getText().toString());
                                    map.put("OS",OS.getText().toString());
                                    map.put("Bettary",Bettary.getText().toString());
                                    map.put("Inches",Inches.getText().toString());
                                    map.put("Wireless communication",Wireless_communication.getText().toString());
                                    map.put("Battery Power Rating",Battery_Power_Rating.getText().toString());
                                    map.put("Weight",Weight.getText().toString());
                                    map.put("Colour",Colour.getText().toString());
                                    map.put("Audio Jack",Audio_Jack.getText().toString());
                                }
                            }
                        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                               doc.collection("Items").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getApplicationContext(),"Data Upload ",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Data is not Upload ",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                   @Override
                                   public void onComplete(@NonNull Task<DocumentReference> task) {
                                       for(int i=0;i<4;i++) {
                                           String url = "/Categories/" + category + "/" + name.getText().toString() + String.valueOf(i + 1);
                                           StorageReference sr = st.getReference(url);
                                           int finalI = i;
                                           sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                               @Override
                                               public void onSuccess(Uri uri) {
                                                    doc.collection("Items").whereEqualTo("Name",name.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                            for (DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                                                                String pt="Photo"+String.valueOf(finalI+1);
                                                                DocumentReference doc=d.getReference();
                                                                doc.update(pt,uri.toString());

                                                            }
                                                        }
                                                    });
                                               }
                                           }).addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                                   System.out.println("PhotoPhotoPhotoPhotoPhoto :  " + e+"     "+url);
                                               }
                                           });
                                       }
                                   }
                               });
                            }
                        });
                    }
                }
            });


        }
    }
    public void clearData(){

        name.setText("");
        discount.setText("");
        price.setText("");
        company.setText("");
        warranty.setText("");
        stock.setText("");
        quantity.setText("");
        description.setText("");
    }
}
