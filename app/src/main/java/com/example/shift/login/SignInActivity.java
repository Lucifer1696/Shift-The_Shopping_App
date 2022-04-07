package com.example.shift.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shift.R;
import com.example.shift.activity.MainActivity;
import com.example.shift.seller.SellerHome;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SignInActivity extends AppCompatActivity  {

    private TextView txtdontHaveAccount;
    private TextView txtSkipSign;
    private TextView txtforgotPassword;
    private EditText email;
    private EditText password;
    private Button signinBtn;
    private FirebaseAuth firebaseAuth;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private ProgressBar progressBar;
    private ImageView fb;
    private ImageView google;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseFirestore database;
    private String []option = {"Customer","Seller"};
    private CallbackManager mCallbackManager;
    private String Module;
    private boolean b;
    SharedPreferences shrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        shrd=getSharedPreferences("Shift",MODE_PRIVATE);
        if(shrd.contains("CID")){
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(shrd.contains("SID")){
            Intent intent = new Intent(SignInActivity.this, SellerHome.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_sign_in);

        txtdontHaveAccount = (TextView)findViewById(R.id.dontHaveAccount);
        signinBtn = (Button)findViewById(R.id.LoginButton);
        txtforgotPassword = (TextView)findViewById(R.id.forgotPassword);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        txtSkipSign = (TextView) findViewById(R.id.skipSignIn);
        fb = (ImageView) findViewById(R.id.facebookicon);
        google = (ImageView) findViewById(R.id.googleicon);
        database = FirebaseFirestore.getInstance();


        Spinner spn = findViewById(R.id.Module);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Module, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Module =parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtdontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                intent.putExtra("Module",Module);
                startActivity(intent);
                finish();
            }
        });
        txtSkipSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        /// google authetication
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        /// facebook authetication
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Facebook Login button
            }
        });

        // email & password validation
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
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b=false;
                database.collection(Module).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot d: queryDocumentSnapshots.getDocuments()){
                            System.out.println("Email  : "+d.getString("Email")+email.getText().toString());
                            if(d.getString("Email")!=null)
                                if(d.getString("Email").equals(email.getText().toString()))
                                    if(d.getString("Password")!=null)
                                        if(d.getString("Password").equals(password.getText().toString()))
                                            b=true;
                                        else
                                            Toast.makeText(getApplicationContext(),"Password is Not Correct",Toast.LENGTH_LONG).show();
                        }
                        if(b==false)
                            Toast.makeText(getApplicationContext(),"Email is Not Correct",Toast.LENGTH_LONG).show();

                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(b==true) {
                            SharedPreferences.Editor editor =shrd.edit();
                            if(Module.equals("Customer")){
                                database.collection(Module).whereEqualTo("Email",email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
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
                                            editor.putString("CID",d.getString("CID"));
                                            editor.apply();
                                            editor.putString("CName",d.getString("Name"));
                                            editor.apply();
                                            editor.putString("CEmail",d.getString("Email"));
                                            editor.apply();
                                        }
                                    }
                                });
                                Intent it = new Intent(SignInActivity.this, MainActivity.class);
                                startActivity(it);
                            }
                            else {
                                database.collection(Module).whereEqualTo("Email",email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments()){
                                            editor.remove("SID");
                                            editor.apply();
                                            editor.remove("CID");
                                            editor.apply();
                                            editor.remove("SName");
                                            editor.apply();
                                            editor.remove("CName");
                                            editor.apply();
                                            editor.remove("CEmail");
                                            editor.apply();
                                            editor.remove("SEmail");
                                            editor.apply();
                                            editor.clear();
                                            editor.apply();
                                            editor.putString("SID",d.getString("SID"));
                                            editor.apply();
                                            editor.putString("SName",d.getString("Name"));
                                            editor.apply();
                                            editor.putString("SEmail",d.getString("Email"));
                                            editor.apply();

                                        }
                                    }
                                });
                                Intent it = new Intent(SignInActivity.this, SellerHome.class);
                                startActivity(it);
                            }
                            finish();
                        }
                    }
                });
            }
        });
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
                // firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void checkInput(){
        if(!TextUtils.isEmpty(email.getText())){
            if(!TextUtils.isEmpty(password.getText()) && password.length() >=4){
                signinBtn.setEnabled(true);
            }else{
                signinBtn.setEnabled(false);
            }
        }else {
            signinBtn.setEnabled(false);
        }
    }
}