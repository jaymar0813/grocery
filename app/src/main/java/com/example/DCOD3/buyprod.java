package com.example.DCOD3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class buyprod extends AppCompatActivity {

    public static TextView textpname,textprice,bcode;
    Button buttonscan;
    Button buttonadd;
    Button buttonpreview;
    EditText qntity;
    private products products;
    private Order order;
    long maxid=0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref,ref2,ref1;
    String addres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyprod);
        textpname = findViewById(R.id.textViewpname);
        textprice = findViewById(R.id.textViewprice);
        buttonscan = findViewById(R.id.buttonscan);
        buttonadd = findViewById(R.id.buttonadd);
        buttonpreview = findViewById(R.id.buttonpreview);
        qntity = findViewById(R.id.editTextNumberqntity);
        bcode = findViewById(R.id.bcode);
        order = new Order();
        addres = MainActivity.getMacAddress();
        products = new products();
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("products");
        ref1=database.getReference().child(addres);
        ref2= ref1.child("cart");

        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    maxid = Integer.parseInt(childSnapshot.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        buttonscan.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                ScanCode.isuse = 0;
                startActivity(new Intent(getApplicationContext(),ScanCode.class));





            }



        });
        buttonadd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                if (textpname.getText().toString().equals("")||textprice.getText().toString().equals("")||qntity.getText().toString().equals("")) {
                    Toast.makeText(buyprod.this, "One or more Empty Field", Toast.LENGTH_LONG).show();
                } else{
                    int prc = Integer.parseInt(textprice.getText().toString());
                int qnt = Integer.parseInt(qntity.getText().toString());

                int sub = prc * qnt;
                String sum2 = Integer.toString(sub);

                order.setItem(textpname.getText().toString());
                order.setPrice(textprice.getText().toString());
                order.setQuantity(qntity.getText().toString());
                order.setSum(sum2);

                String itemh = textpname.getText().toString();
                Query checkD = ref2.orderByChild("item").equalTo(itemh);
                checkD.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        if (snapshot1.exists()) {
                            Toast.makeText(buyprod.this, "This has been already added", Toast.LENGTH_LONG).show();// kung ito ay kailangan ng staryActivity palagyan na lang para magredirect ulit sa register activity

                        } else {
                            ref2.child(String.valueOf(maxid + 1)).setValue(order);
                            Toast.makeText(buyprod.this, "Successfully added", Toast.LENGTH_LONG).show();

                        }

                        textpname.setText("");
                        textprice.setText("");
                        qntity.setText("");
                    }//onDataChange end curly brace

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            }


        });


        buttonpreview.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),prodlist.class));



            }


        });
    }
}