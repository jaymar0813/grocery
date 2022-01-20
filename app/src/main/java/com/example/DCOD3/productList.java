package com.example.DCOD3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class productList extends AppCompatActivity {

  private RecyclerView recyclerView;
  private FirebaseDatabase database = FirebaseDatabase.getInstance();
  private  DatabaseReference ref = database.getReference().child("products");
  private Adapterko adap;
  private ArrayList<Prodmodel> list;
  private Button btnadd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        btnadd = (Button)findViewById(R.id.buttonAddnew);
        recyclerView = findViewById(R.id.productlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adap = new Adapterko(this,list);

        recyclerView.setAdapter(adap);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String key = dataSnapshot.getKey();
                    Prodmodel pmodel = new Prodmodel();
                    pmodel.setCode(String.valueOf(snapshot.child(key).child("code").getValue()));
                    pmodel.setExp(String.valueOf(snapshot.child(key).child("exp").getValue()));
                    pmodel.setOrprice(String.valueOf(snapshot.child(key).child("orig").getValue()));
                    pmodel.setPdname(String.valueOf(snapshot.child(key).child("productname").getValue()));
                    pmodel.setProprice(String.valueOf(snapshot.child(key).child("prof").getValue()));
                    pmodel.setQuantity(String.valueOf(snapshot.child(key).child("quan").getValue()));
                    list.add(pmodel);
                }
                adap.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),addProduct.class));

            }
        });



    }
}