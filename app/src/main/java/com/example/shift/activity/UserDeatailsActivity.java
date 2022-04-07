package com.example.shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shift.R;
import com.example.shift.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDeatailsActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("Customer/C003");
    Button RegisterBtn;
    ImageView profileImage;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAddress;
    EditText editTextPhone;
    EditText editTextPassword;
    EditText editTextDob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatails);

        profileImage = findViewById(R.id.UserProfileImage);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextDob = findViewById(R.id.editTextDOB);
        RegisterBtn = findViewById(R.id.RegisterBtn);

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String address  = editTextAddress.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String dob = editTextDob.getText().toString().trim();

                if (!validateInputs(name,email,address,phone,password,dob)){
                    Users user = new Users( name, email, address, phone, password, dob);

                    db.collection("Customer")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(UserDeatailsActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent =  new Intent(UserDeatailsActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserDeatailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    DocumentReference dbData =  db.collection("Customer").document("C003");
                    dbData.set(user);
                }
            }
        });
    }

    private boolean validateInputs(String name,String email,String address,String phone,String password,String dob){

        if(name.isEmpty()){
            editTextName.setError("Name Required");
            editTextName.requestFocus();
            return true;
        }else if(email.isEmpty()){
            editTextEmail.setError("Email Required");
            editTextEmail.requestFocus();
            return true;
        }else if(address.isEmpty()){
            editTextAddress.setError("Address Required");
            editTextAddress.requestFocus();
            return true;
        }else if(phone.isEmpty()){
            editTextPhone.setError("Phone Required");
            editTextPhone.requestFocus();
            return true;
        }else if(password.isEmpty()){
            editTextPassword.setError("Password Required");
            editTextPassword.requestFocus();
            return true;
        }else if(dob.isEmpty()){
            editTextDob.setError("DOB Required");
            editTextDob.requestFocus();
            return true;
        }
            return false;
    }

}