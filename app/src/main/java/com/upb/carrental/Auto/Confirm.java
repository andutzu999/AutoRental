package com.upb.carrental.Auto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.upb.carrental.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Confirm extends AppCompatActivity {

    FirebaseFirestore db;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);

        db = FirebaseFirestore.getInstance();

        button1.setOnClickListener(view -> {
            db.collection("transactions")
                    .document()
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Map<String, Object> record_entry = new HashMap<>();
                        record_entry.put("Email", settings.getString(getString(R.string.email), "empty"));
                        record_entry.put("Vehicle", settings.getString("vehicle", "empty"));
                        int unit_price = Integer.parseInt(settings.getString("price", "0"));
                        int duration = Integer.parseInt(settings.getString("duration", "0").split(" ")[0]);
                        String time_interval = settings.getString("duration", "0 0").split(" ")[1];
                        if (time_interval.contains("week")) {
                            duration *= 7;
                        } else if (time_interval.contains("month")) {
                            duration *= 30;
                        }
                        int total_price = unit_price * duration;
                        record_entry.put("Duration", settings.getString("duration", "0"));
                        record_entry.put("Price", String.valueOf(total_price));

                        db.collection("transactions")
                                .add(record_entry)
                                .addOnSuccessListener(documentReference -> Toast.makeText(Confirm.this, "Successfully added", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Log.d("Error", e.getMessage()));

                    });

            Intent intent = new Intent(view.getContext(), FinalPage.class);
            view.getContext().startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), MainMenu.class);
            view.getContext().startActivity(intent);
        });

    }
}