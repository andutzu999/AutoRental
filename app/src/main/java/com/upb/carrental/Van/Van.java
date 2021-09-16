package com.upb.carrental.Van;

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


public class Van extends AppCompatActivity implements OnNoteListener {

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

        addToList(methods,"Ford Tranzit","DIESEL","2.0TDI-140CP","3 SEATS", "2017", "45E/day", R.drawable.fordtranzit2017);
        addToList(methods,"Mercedes Sprinter","DIESEL","2.2CDI-170CP","3 SEATS", "2016", "70E/day", R.drawable.mercedessprinter2016);
        addToList(methods,"Peugeot Partner 108","DIESEL","1.6HDI- 115CP","3 SEATS", "2013", "35E/day", R.drawable.peugeotpartner108);
        addToList(methods,"Renault Traffic","DIESEL","1.6cm3-110CP","3 SEATS", "2020", "60E/day", R.drawable.renaulttrafic2020);
        addToList(methods,"VW TRANSPORTER","DIESEL","2.0TDI-140CP","3 SEATS", "2013", "70E/day", R.drawable.vwtransporter2013);


        return methods;

    }

}
