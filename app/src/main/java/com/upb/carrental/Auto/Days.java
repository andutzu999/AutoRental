package com.upb.carrental.Auto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upb.carrental.Methods;
import com.upb.carrental.OnNoteListener;
import com.upb.carrental.R;

import java.util.ArrayList;


public class Days extends AppCompatActivity implements OnNoteListener {

    SharedPreferences settings;

    public static void addToList(ArrayList<Methods> methods, String day){
        Methods m = new Methods();
        m.setDay(day);
        methods.add(m);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all);

        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new MyAdapter(this, getData(), this));


    }


    public void onNoteClick(int position){
        String duration = getData().get(position).getDay();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("duration", duration);
        editor.apply();

        Intent intent = new Intent(this, Confirm.class);
        startActivity(intent);
    }

    private ArrayList<Methods> getData(){
        ArrayList<Methods> methods = new ArrayList<>();

        addToList(methods,"1 day");
        addToList(methods,"2 days");
        addToList(methods,"3 days");
        addToList(methods,"4 days");
        addToList(methods,"5 days");
        addToList(methods,"6 days");
        addToList(methods,"1 week");
        addToList(methods,"2 weeks");
        addToList(methods,"1 month");

        return methods;

    }



}
