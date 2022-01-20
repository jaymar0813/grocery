package com.example.DCOD3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    Button res,cus;
    String mobile_mac_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res=(Button)findViewById(R.id.button_res);
        cus=(Button)findViewById(R.id.button_cus);
        res.setOnClickListener(this);
        cus.setOnClickListener(this);


        cus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view){
                mobile_mac_address = getMacAddress();
                System.out.println(mobile_mac_address);
                openbuyprod();

            }

        });



    }
    public void openbuyprod(){
        Intent intent =  new Intent(this, buyprod.class);
        startActivity(intent);
    }

    @Override

    public void onClick(View v) {
            switch (v.getId()){

                case R.id.button_res:
                    startActivity(new Intent(this,resellerlogin.class));
                    break;

            }

    }
    public static String getMacAddress(){
        try{
            List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
            String stringMac = "";
            for(NetworkInterface networkInterface : networkInterfaceList)
            {
                if(networkInterface.getName().equalsIgnoreCase("wlon0"));
                {
                    for(int i = 0 ;i <networkInterface.getHardwareAddress().length; i++){
                        String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i]& 0xFF);
                        if(stringMacByte.length() == 1)
                        {
                            stringMacByte = "0" +stringMacByte;
                        }
                        stringMac = stringMac + stringMacByte.toUpperCase() + ":";
                    }
                    break;
                }
            }
            return stringMac;
        }catch (SocketException e)
        {
            e.printStackTrace();
        }
        return  "0";
    }

}