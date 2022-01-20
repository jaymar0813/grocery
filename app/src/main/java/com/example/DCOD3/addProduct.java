package com.example.DCOD3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;

public class addProduct extends AppCompatActivity {

    public static TextView bcode,displaydate;
    Button scanbtn;
    private static final String Tag = "addProduct";
    private DatePickerDialog.OnDateSetListener mdatesetlistener;
    EditText pname,quan,oprice,pprice;
    private products products;
    Button btnsave;
    long maxid=0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        bcode = findViewById(R.id.bcode);
        displaydate =findViewById(R.id.expiration);
        pname = (EditText) findViewById(R.id.pname);
        quan = (EditText) findViewById(R.id.quantity);
        oprice = (EditText) findViewById(R.id.oprice);
        pprice = (EditText) findViewById(R.id.pprice);
        btnsave = (Button)findViewById(R.id.btnsave);

        scanbtn = findViewById(R.id.btnscan);

        products = new products();
        database=FirebaseDatabase.getInstance();
        ref=database.getReference().child("products");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    maxid = Integer.parseInt( childSnapshot.getKey());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.setCode(bcode.getText().toString());
                products.setProductname(pname.getText().toString());
                products.setQuan(quan.getText().toString());
                products.setExp(displaydate.getText().toString());
                products.setOrig(oprice.getText().toString());
                products.setProf(pprice.getText().toString());

                String bbcode = bcode.getText().toString().trim();
                Query checkD = ref.orderByChild("code").equalTo(bbcode); //checkmail query, yung sellers.Maill pakicheck kung eto yung naglalaman ng value pare
                checkD.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        if (snapshot1.exists()) {
                            Toast.makeText(addProduct.this, "This has been already scan", Toast.LENGTH_LONG).show();// kung ito ay kailangan ng staryActivity palagyan na lang para magredirect ulit sa register activity
                        } else {
                            String pdname = pname.getText().toString().trim();
                            Query checkD2 = ref.orderByChild("productname").equalTo(pdname);

                            checkD2.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    if (snapshot2.exists()) {
                                        Toast.makeText(addProduct.this, "This ProductName already exist", Toast.LENGTH_LONG).show();// kung ito ay kailangan ng staryActivity palagyan na lang para magredirect ulit sa register activity
                                    } else {

                                        ref.child(String.valueOf(maxid+1)).setValue(products);
                                        Toast.makeText(addProduct.this,"Save data!",Toast.LENGTH_LONG).show();

                                    }

                                }//onDataChange end curly brace

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }

                    }//onDataChange end curly brace

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });//addListner end curly brace






            }
        });


        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanCode.isuse = 1;
                startActivity(new Intent(getApplicationContext(),ScanCode.class));



            }
        });



        displaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(addProduct.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,mdatesetlistener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        mdatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(Tag,"onDateSet: date:"+day+"/"+month+"/"+year);

                String date =  month +"/"+ day + "/"+ year;
                displaydate.setText(date);
            }
        };




    }

}