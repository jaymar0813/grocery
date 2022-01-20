package com.example.DCOD3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adap2 extends RecyclerView.Adapter<Adap2.ViewHolderko2>{

    ArrayList<Itemodel> itList;
    Context context2;
    RecyclerView mrecycle;

    public Adap2(Context context2, ArrayList itList){
        this.itList = itList;
        this.context2 = context2;
    }
    @NonNull
    @Override
    public Adap2.ViewHolderko2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context2).inflate(R.layout.item2, parent, false);
        return new Adap2.ViewHolderko2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adap2.ViewHolderko2 holder, int position) {
        Itemodel model = itList.get(position);
        holder.item2.setText(model.getItem2());
        holder.price2.setText(model.getPric());
        holder.quantity2.setText(model.getQnty());
        holder.sum3.setText(model.getSumn());

    }
    @Override
    public int getItemCount() {
        return itList.size();
    }
    public class ViewHolderko2 extends RecyclerView.ViewHolder{

        TextView item2, price2, quantity2, sum3;

        public ViewHolderko2(@NonNull View itemView){

            super(itemView);

            item2 = itemView.findViewById(R.id.itm1);
            price2 = itemView.findViewById(R.id.price2);
            quantity2 = itemView.findViewById(R.id.qnty2);
            sum3 = itemView.findViewById(R.id.subtot);


            itemView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                        System.out.println(item2.getText());
                    Select_item.temval = item2.getText().toString();
                    Select_item.prval = price2.getText().toString();
                    Select_item.suval = sum3.getText().toString();
                    Intent intent = new Intent(context2,Select_item.class);
                    context2.startActivity(intent);
                }
            });

        }
    }
}
