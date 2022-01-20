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

public class Select_item extends AppCompatActivity {
    static String temval,prval,suval;
    public static TextView iteem, priic, subbb;
    Button sv, del;
    EditText qqw;
    private Order order;
    long id = 0;
    String addres;


    DatabaseReference ref2,ref3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);
        iteem =(TextView) findViewById(R.id.Ittem);
        priic = (TextView) findViewById(R.id.prrc);
        subbb  =(TextView) findViewById(R.id.subbb);
        sv = (Button)findViewById(R.id.saave);
        del = (Button)findViewById(R.id.delet);
        qqw = (EditText)findViewById(R.id.qnttty);
        iteem.setText(temval);
        priic.setText(prval);
        subbb.setText(suval);
        order = new Order();
        addres = MainActivity.getMacAddress();
        System.out.print(addres);
        ref2 = FirebaseDatabase.getInstance().getReference(addres);


        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int prc = Integer.parseInt(priic.getText().toString());
                int qnt = Integer.parseInt(qqw.getText().toString());

                int sub = prc*qnt;
                String sum2 = Integer.toString(sub);

                order.setItem(iteem.getText().toString());
                order.setPrice(priic.getText().toString());
                order.setQuantity(qqw.getText().toString());
                order.setSum(sum2);


                String itemh = iteem.getText().toString();

                ref3 = ref2.child("cart");
                Query checkD = ref3.orderByChild("item").equalTo(itemh);
                checkD.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        if (snapshot1.exists()) {
                            for (DataSnapshot childSnapshot: snapshot1.getChildren()) {
                                id = Integer.parseInt(childSnapshot.getKey());

                            }
                            ref3.child(String.valueOf(id)).setValue(order);
                            Toast.makeText(Select_item.this,"updated",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),buyprod.class));
                                                   }

                    }//onDataChange end curly brace

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemh = iteem.getText().toString();

                ref3 =ref2.child("cart");
                Query checkD = ref3.orderByChild("item").equalTo(itemh);
                checkD.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        if (snapshot1.exists()) {
                            for (DataSnapshot childSnapshot: snapshot1.getChildren()) {
                                id = Integer.parseInt(childSnapshot.getKey());
                            }

                            ref3.child(String.valueOf(id)).setValue(order);
                            Toast.makeText(Select_item.this,"deleted!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),buyprod.class));
                        }

                    }//onDataChange end curly brace

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


}