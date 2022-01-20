package com.example.DCOD3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterko extends RecyclerView.Adapter<Adapterko.ViewHolderko>{

    ArrayList<Prodmodel> modelList;
    Context context;

    public Adapterko(Context context, ArrayList modelList){
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderko onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.item1, parent, false);
       return new ViewHolderko(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderko holder, int position) {
        Prodmodel model = modelList.get(position);
        holder.code.setText(model.getCode());
        holder.exp.setText(model.getExp());
        holder.orig.setText(String.valueOf(model.getOrprice()));
        holder.pname.setText(model.getPdname());
        holder.prof.setText(String.valueOf(model.getProprice()));
        holder.qnty.setText(String.valueOf(model.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolderko extends RecyclerView.ViewHolder{

        TextView code, exp, orig, pname, prof, qnty;

        public ViewHolderko(@NonNull View itemView){

            super(itemView);
            code = itemView.findViewById(R.id.code1);
            exp = itemView.findViewById(R.id.expiration1);
            orig = itemView.findViewById(R.id.origprice1);
            pname = itemView.findViewById(R.id.pdrname1);
            prof = itemView.findViewById(R.id.proprice1);
            qnty = itemView.findViewById(R.id.quantity1);

        }
    }

}
