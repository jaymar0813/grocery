package com.example.DCOD3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class seller_act extends AppCompatActivity {

    Button inv, scn, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller2);

        inv = (Button) findViewById(R.id.inv);
        scn = (Button) findViewById(R.id.scn);
        add = (Button) findViewById(R.id.add);


        inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInv();
            }
        });

        scn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openScn();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAd();
            }
        });

    }

    public void openInv() {
        Intent intent = new Intent(this, productList.class);
        startActivity(intent);

    }

    public void openScn() {
        Intent intent = new Intent(this, buyprod.class);
        startActivity(intent);
    }
    public void openAd() {
        Intent intent = new Intent(this, addProduct.class);
        startActivity(intent);
    }
}