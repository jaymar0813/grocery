package com.example.DCOD3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class prodlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref1,ref;
    private Adap2 adap2;
    private ArrayList<Itemodel> list2;
    private TextView tots;
    private Button fin;
    int total = 0;
    String mm;
    String addres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prodlist);
        recyclerView = findViewById(R.id.orderlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tots = (TextView)findViewById(R.id.totalam);
        fin = (Button)findViewById(R.id.complete);

        list2 = new ArrayList<>();
        adap2 = new Adap2(this,list2);
        addres = MainActivity.getMacAddress();
        ref1= database.getReference().child(addres);
        ref = ref1.child("cart");

        recyclerView.setAdapter(adap2);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String key = dataSnapshot.getKey();
                     int sss = Integer.parseInt(String.valueOf(snapshot.child(key).child("sum").getValue()));
                     total += sss;
                    Itemodel itmod = new Itemodel();
                    itmod.setItem2(String.valueOf(snapshot.child(key).child("item").getValue()));
                    itmod.setPric(String.valueOf(snapshot.child(key).child("price").getValue()));
                    itmod.setQnty(String.valueOf(snapshot.child(key).child("quantity").getValue()));
                    itmod.setSumn(String.valueOf(snapshot.child(key).child("sum").getValue()));

                    list2.add(itmod);
                   mm = "TOTAL: \n"+total;
                   System.out.println(mm);

                }
                adap2.notifyDataSetChanged();
                tots.setText(mm);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(prodlist.this,"Order Completed, Preparing for new Order",Toast.LENGTH_LONG).show();
                ref.removeValue();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });




    }
}