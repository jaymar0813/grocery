package com.example.DCOD3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode extends AppCompatActivity implements ZXingScannerView.ResultHandler  {

    int MY_PERMISSION_REQUEST_CAMERA=0;
    ZXingScannerView scannerview;
   static int isuse = 1;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerview = new ZXingScannerView(this);
        setContentView(scannerview);
    }

    @Override
    public void handleResult(Result result) {

        if (isuse ==1){
        addProduct.bcode.setText(result.getText());

        }
        else if(isuse == 0){
            database=FirebaseDatabase.getInstance();
            ref=database.getReference().child("products");
            String bbcode = result.getText();
            Query checkD = ref.orderByChild("code").equalTo(bbcode); //checkmail query, yung sellers.Maill pakicheck kung eto yung naglalaman ng value pare
            checkD.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                    if (snapshot1.exists()) {


                        for(DataSnapshot ds : snapshot1.getChildren()) {
                            String key = ds.getKey();
                            String pn =String.valueOf(snapshot1.child(key).child("productname").getValue());
                            buyprod.textpname.setText(pn);
                            buyprod.textprice.setText(String.valueOf(snapshot1.child(key).child("orig").getValue()));


                        }
                    } else {
                        Toast.makeText(ScanCode.this,"This Product is not on the list",Toast.LENGTH_LONG).show();


                    }

                }//onDataChange end curly brace

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });//addListner end curly brace
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        scannerview.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!=
        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSION_REQUEST_CAMERA);
        }

        scannerview.setResultHandler(this);
        scannerview.startCamera();
    }
}