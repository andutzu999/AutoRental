package com.upb.carrental.Auto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.upb.carrental.Methods;
import com.upb.carrental.OnNoteListener;
import com.upb.carrental.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context c;
    ArrayList<Methods> methods;
    OnNoteListener monNoteListener;

    public MyAdapter(Context c, ArrayList<Methods> methods, OnNoteListener onNoteListener){
        this.c = c;
        this.methods = methods;
        this.monNoteListener = onNoteListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(c).inflate(R.layout.days, parent, false);
        return new MyViewHolder(v, monNoteListener);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {

        Methods m = methods.get(position);
        holder.dayTxt.setText(m.getDay());


    }

    public int getItemCount() {
        return methods.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTxt, fuelTxt, engineTxt, seatsTxt, yearTxt, priceTxt;
        ImageView img;
        TextView dayTxt;
        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
            dayTxt = (TextView) itemView.findViewById(R.id.dayTxt);
            fuelTxt = (TextView) itemView.findViewById(R.id.fuelTxt);
            engineTxt = (TextView) itemView.findViewById(R.id.engineTxt);
            seatsTxt = (TextView) itemView.findViewById(R.id.seatsTxt);
            yearTxt = (TextView) itemView.findViewById(R.id.yearTxt);
            priceTxt = (TextView) itemView.findViewById(R.id.priceTxt);
            img = (ImageView) itemView.findViewById(R.id.caravanImg);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }


}
