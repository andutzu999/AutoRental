package com.upb.carrental.Cars;

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
import java.util.Random;



public class Cars extends AppCompatActivity implements OnNoteListener {

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

        addToList(methods,"Audi S3","DIESEL","2.0TDI-170CP","5 SEATS", "2016", "70E/day", R.drawable.audis32016);
        addToList(methods,"Ford Focus","DIESEL","1.6TDI-110CP","5 SEATS", "2016", "45E/day", R.drawable.fordfocus2016);
        addToList(methods,"Ford Mustang","GAS","5000cm3-500CP","4 SEATS", "2018", "100E/day", R.drawable.fordmustang2018);
        addToList(methods,"Tesla Model 3","ELECTRIC","491CP","5 SEATS", "2018", "200E/day", R.drawable.tesla2018model3);
        addToList(methods,"Mercedes E class","DIESEL","2.2TDI-163CP","5 SEATS", "2015", "80E/day", R.drawable.eclass2015);
        addToList(methods,"Skoda Octavia","GAS","1.6TSI-105CP","5 SEATS", "2014", "40E/day", R.drawable.octavia2014);
        addToList(methods,"Audi RS5","DIESEL","3.0TDI-260CP","4 SEATS", "2014", "90E/day", R.drawable.audirs52014);
        addToList(methods,"BMW M3","DIESEL","3.0TDI-300CP","5 SEATS", "2018", "120E/day", R.drawable.bmwm32018);
        addToList(methods,"Suzuki Swift","DIESEL","1.2TSI-85CP","5 SEATS", "2008", "30E/day", R.drawable.suzukiswift2008);


        // generate a random number between 0 and 3
        int t = 8;
        for(int i = 0; i <= new Random().nextInt(4); i++) {
            methods.remove(new Random().nextInt(t));
            t--;
        }

        return methods;

    }



}
