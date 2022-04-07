package com.example.shift.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shift.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextView forgotPasswordGoback;
    EditText RegisterEmail;
    Button ResetPasswordBtn;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordGoback = findViewById(R.id.forgotPasswordGoback);
        RegisterEmail = findViewById(R.id.RegisterEmail);
        ResetPasswordBtn = findViewById(R.id.ResetPasswordBtn);
        firebaseAuth = FirebaseAuth.getInstance();

        ResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordBtn.setEnabled(false);
                firebaseAuth.sendPasswordResetEmail(RegisterEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgotPasswordActivity.this, "Email Sent Successfully! ", Toast.LENGTH_SHORT).show();
                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(ForgotPasswordActivity.this, error , Toast.LENGTH_SHORT).show();
                                }
                                ResetPasswordBtn.setEnabled(true);
                            }
                        });
            }
        });

        forgotPasswordGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });

        RegisterEmail.addTextChangedListener(new TextWatcher() {
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
    }

    private void checkInput(){
        if(TextUtils.isEmpty(RegisterEmail.getText())){
            ResetPasswordBtn.setEnabled(false);
            ResetPasswordBtn.setTextColor(Color.argb(50,255,255,255));
        }else {
            ResetPasswordBtn.setEnabled(true);
        }
    }
}