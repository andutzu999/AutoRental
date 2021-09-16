package com.upb.carrental.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upb.carrental.R;
import com.upb.carrental.Record;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final int TYPE_HEADER = 1;
    private final int TYPE_ITEM = 2;

    Context c;
    ArrayList<Record> records;

    public MyAdapter(Context c, ArrayList<Record> records) {
        this.c = c;
        this.records = records;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_ITEM) {
            v = LayoutInflater.from(c).inflate(R.layout.record, parent, false);
        } else {
            v = LayoutInflater.from(c).inflate(R.layout.table_header, parent, false);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyAdapter.MyViewHolder holder, int position) {
        Record r = records.get(position);

        holder.vehicle.setText(r.getVehicle());
        holder.duration.setText(r.getDuration());
        holder.price.setText(r.getPrice());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vehicle, duration, price;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            vehicle = itemView.findViewById(R.id.record_vehicle);
            duration = itemView.findViewById(R.id.record_duration);
            price = itemView.findViewById(R.id.record_price);
        }
    }
}
