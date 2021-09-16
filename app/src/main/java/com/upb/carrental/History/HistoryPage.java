package com.upb.carrental.History;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.upb.carrental.R;
import com.upb.carrental.Record;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryPage extends AppCompatActivity {

    FirebaseFirestore db;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_page);

        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();

        createData();
    }

    private void createData(){
        ArrayList<Record> data = new ArrayList<>();
        data.add(new Record("Email", "Vehicle", "Duration", "Price"));
        String email = settings.getString(getString(R.string.email), "empty");
        RecyclerView rv = findViewById(R.id.recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db.collection("transactions")
                .whereEqualTo("Email", email)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                            Record r = new Record();
                            r.setEmail(doc.getString("Email"));
                            r.setVehicle(doc.getString("Vehicle"));
                            r.setDuration(doc.getString("Duration"));
                            r.setPrice(doc.getString("Price"));
                            data.add(r);
                            Log.d("added", "record");
                        }
                        rv.setAdapter(new MyAdapter(this, data));
                    }
                });
    }
}
