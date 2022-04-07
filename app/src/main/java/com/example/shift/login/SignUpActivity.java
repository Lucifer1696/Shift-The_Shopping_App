package com.example.shift.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.example.shift.R;
import com.example.shift.activity.MainActivity;
import com.example.shift.model.Users;
import com.example.shift.seller.SellerHome;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    private boolean b;
    private TextView txt1;
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText mobileNumber;
    private EditText Address;
    private EditText dob;
    private Button signUpbtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseDatabase database;
    private FirebaseFirestore db;
    private ImageView fb;
    private ImageView google;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    public static boolean setSignUpFragment = false;
    private String Module;
    SharedPreferences shrd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txt1 = (TextView) findViewById(R.id.already_Create_An_account);
        name = (EditText) findViewById(R.id.product_name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mobileNumber = (EditText) findViewById(R.id.mobilenumber);
        Address = (EditText) findViewById(R.id.Address);
        dob = (EditText) findViewById(R.id.DOB);
        signUpbtn = (Button) findViewById(R.id.SignUpbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        fb = (ImageView)findViewById(R.id.facbookicon);
        google = (ImageView)findViewById(R.id.googleicon);
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your Account");
        FacebookSdk.sdkInitialize(getApplicationContext());
        shrd=getSharedPreferences("Shift",MODE_PRIVATE);

        Intent it =getIntent();
        Module=it.getStringExtra("Module");
        if(Module.equals("Seller")){
            dob.setHint("Shope Name");
            dob.setInputType(InputType.TYPE_CLASS_TEXT);
            Drawable Draw= AppCompatResources.getDrawable(this, R.drawable.ic_shop);
            dob.setCompoundDrawablesRelative(Draw,null,null,null);
        }
        System.out.println("Module   :   "+Module);
            signUpbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                       Chekemail(v);
                }
            });


        //        progressBar.setVisibility(View.GONE);
        txt1 = (TextView)findViewById(R.id.already_Create_An_account);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                signIn();
            }
        });

//        signUpbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//                checkEmailAndMobileNumber();
//            }
//        });
    }
    public  void  Chekemail(View v){
        db=FirebaseFirestore.getInstance();
        b=true;

        db.collection(Module).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()){
                    System.out.println("Email : "+d.getString("Email"));
                    if(email.getText().toString().equals(d.getString("Email"))) {
                        b=false;
                    }
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(b) {
                       Datainsert();
                }
                else
                    Toast.makeText(getApplicationContext(),"Email is Already Exist",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void Datainsert(){
        db=FirebaseFirestore.getInstance();

        final int[] cnt = new int[1];
        Map<String,String> map;
        db.collection(Module).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                cnt[0] =queryDocumentSnapshots.size()+1;
                Map<String,String> map=new HashMap<String, String>();//Creating HashMap.
                SharedPreferences.Editor editor =shrd.edit();
                editor.remove("SID");
                editor.apply();
                editor.remove("CID");
                editor.apply();
                editor.remove("CName");
                editor.apply();
                editor.remove("SName");
                editor.apply();
                editor.remove("CEmail");
                editor.apply();
                editor.remove("SEmail");
                editor.apply();
                editor.clear();
                editor.apply();

                if(Module.equals("Customer")){
                    if(cnt[0] <10) {
                        map.put("CID", "C00" + String.valueOf(cnt[0]));
                        editor.putString("CID","C00"+String.valueOf(cnt[0]));
                    }
                    else if(cnt[0] <100) {
                        map.put("CID", "C0" + String.valueOf(cnt[0]));
                        editor.putString("CID","C0"+String.valueOf(cnt[0]));
                    }
                    else if(cnt[0] >99) {
                        map.put("CID", "C" + String.valueOf(cnt[0]));
                        editor.putString("CID","C"+String.valueOf(cnt[0]));
                    }
                    map.put("DOB",dob.getText().toString());
                    editor.apply();
                    editor.putString("CName",name.getText().toString());
                    editor.apply();
                    editor.putString("CEmail",email.getText().toString());
                    editor.apply();
                }
                else
                {
                    if(Module.equals("Seller")){
                        if(cnt[0] <10) {
                            map.put("SID", "S00" + String.valueOf(cnt[0]));
                            editor.putString("SID", "S00" + String.valueOf(cnt[0]));
                        }
                        else if(cnt[0] <100) {
                            map.put("SID", "S0" + String.valueOf(cnt[0]));
                            editor.putString("SID", "S0" + String.valueOf(cnt[0]));
                        }
                        else if(cnt[0] >99) {
                            map.put("SID", "S" + String.valueOf(cnt[0]));
                            editor.putString("SID", "S" + String.valueOf(cnt[0]));
                        }
                        editor.apply();
                        editor.putString("CName",name.getText().toString());
                        editor.apply();
                        editor.putString("SEmail",email.getText().toString());
                        editor.apply();
                        map.put("ShopName",dob.getText().toString());
                    }
                }
                System.out.println("!!!!!!!!!!!!!!!!!!!!!"+shrd.contains("SID")+"$$$$$$$$$$$   "+shrd.getString("SID","0000"));
                map.put("Name",name.getText().toString());
                map.put("PhoneNo",mobileNumber.getText().toString());
                map.put("Address1",Address.getText().toString());
                map.put("Email",email.getText().toString());
                map.put("Password",password.getText().toString());
                map.put("ProfileImg","");

                db.collection(Module).add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Map<String,String> map =new HashMap<>();
                        if(Module.equals("Customer")){
                            task.getResult().collection("CartItems").add(map);
                            task.getResult().collection("WishList").add(map);
                            task.getResult().collection("MyOrders").add(map);
                        }
                        else
                        {
                            task.getResult().collection("Items").add(map);
                        }
                    }
                });

            }
        });

        if(Module.equals("Customer")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent = new Intent(this, SellerHome.class);
            startActivity(intent);
            finish();
        }

    }

    int RC_SIGN_IN = 65;
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Users users = new Users();
                            users.setUserid(user.getUid());
                            users.setEmail(user.getEmail());
                            users.setUsername(user.getDisplayName());
                            users.setPhoneNumber(user.getPhoneNumber());

                            String id =task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(users);

                            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    private void checkInput(){
        if(!TextUtils.isEmpty(name.getText())){
            if(!TextUtils.isEmpty(email.getText())){
                if(!TextUtils.isEmpty(password.getText()) && password.length() >=4){
                    if(!TextUtils.isEmpty(mobileNumber.getText()) && mobileNumber.length() >=10) {
                        signUpbtn.setEnabled(true);
                    }else{
                        signUpbtn.setEnabled(false);
                    }
                }else {
                    signUpbtn.setEnabled(false);
                }
            }else {
                signUpbtn.setEnabled(false);
            }
        }else{
            signUpbtn.setEnabled(false);
        }
    }

    private void checkEmailAndMobileNumber(){
        if(email.getText().toString().matches(emailpattern)){

            progressBar.setVisibility(View.VISIBLE);
            signUpbtn.setEnabled(false);
            MainActivity mainActivity = new MainActivity();

            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Users users = new Users(name.getText().toString(),password.getText().toString(),email.getText().toString(),mobileNumber.getText().toString());
                                String id =task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(users);
                                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                progressBar.setVisibility(View.INVISIBLE);
                                signUpbtn.setEnabled(true);
                                String error = task.getException().getMessage();

                            }
                        }
                    });
        } else {
            password.setError("Too weak password, Use Strong One");
        }
    }
}
