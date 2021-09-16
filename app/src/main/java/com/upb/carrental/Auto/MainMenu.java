package com.upb.carrental.Auto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.upb.carrental.Caravan.Caravan;
import com.upb.carrental.Cars.Cars;
import com.upb.carrental.History.HistoryPage;
import com.upb.carrental.Main.ProfilePaging;
import com.upb.carrental.Motorcycles.Motorcycles;
import com.upb.carrental.R;
import com.upb.carrental.Service.MapsActivity;
import com.upb.carrental.Van.Van;



public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        ImageView motorcycle = (ImageView) findViewById(R.id.motorcycle);
        ImageView caravan = (ImageView) findViewById(R.id.caravan);
        ImageView car = (ImageView) findViewById(R.id.car);
        ImageView van = (ImageView) findViewById(R.id.van);
        Button profile = findViewById(R.id.profile);
        Button b = findViewById(R.id.button1);
        Button history = findViewById(R.id.history);

        motorcycle.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Motorcycles.class);
            view.getContext().startActivity(intent);
        });

        car.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Cars.class);
            view.getContext().startActivity(intent);
        });

        van.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Van.class);
            view.getContext().startActivity(intent);
        });

        caravan.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), Caravan.class);
            view.getContext().startActivity(intent);
        });

        b.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MapsActivity.class);
            view.getContext().startActivity(intent);
        });

        profile.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProfilePaging.class);
            view.getContext().startActivity(intent);
        });

        history.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), HistoryPage.class);
            view.getContext().startActivity(intent);
        });


    }


}