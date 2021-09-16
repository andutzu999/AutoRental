package com.upb.carrental.Motorcycles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upb.carrental.Auto.Days;
import com.upb.carrental.Methods;
import com.upb.carrental.OnNoteListener;
import com.upb.carrental.R;

import java.util.ArrayList;


public class Motorcycles extends AppCompatActivity implements OnNoteListener {

    SharedPreferences settings;

    public static void addToList(ArrayList<Methods> methods, String name, String fuel, String engine,String seats, String year,String price, int image){
        Methods m = new Methods();
        m.setName(name);
        m.setFuel(fuel);
        m.setEngine(engine);
        m.setSeats(seats);
        m.setYear(year);
        m.setPrice(price);
        m.setImage(image);
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
        Methods m = getData().get(position);
        String vehicle = m.getName();
        String price = m.getPrice().split("E/")[0];
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("vehicle", vehicle);
        editor.putString("price", price);
        editor.apply();

        Intent intent = new Intent(this, Days.class);
        startActivity(intent);
    }

    private ArrayList<Methods> getData(){
        ArrayList<Methods> methods = new ArrayList<>();

        addToList(methods,"Suzuki GSX-R","GAS","0.8cm3","2 SEATS", "2014", "25E/day", R.drawable.suzukigsxr1000);
        addToList(methods,"Ducati Panigale","GAS","1.2cm3","2 SEATS", "2018", "30E/day", R.drawable.ducatipanigale);
        addToList(methods,"Honda Cbr","GAS","1.1cm3","2 SEATS", "2009", "25E/day", R.drawable.hondacbr09);
        addToList(methods,"Kawasa Kininja","GAS","1.1cm3","2 SEATS", "2014", "35E/day", R.drawable.kawasakininja);
        addToList(methods,"Suzuki Gixxer","GAS","0.9cm3","2 SEATS", "2016", "45E/day", R.drawable.suzukigixxer);

        return methods;

    }

}