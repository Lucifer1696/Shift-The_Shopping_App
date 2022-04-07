package com.example.shift.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shift.R;

public class AddressesActivity extends AppCompatActivity {

    RecyclerView AddressesRecycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);
        TextView addAddress=findViewById(R.id.add_new_address);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(getApplicationContext(), AddressesActivity.class);
                startActivity(deliveryIntent);
            }
        });

        AddressesRecycleview=findViewById(R.id.addresses_recycleview);



    }
}