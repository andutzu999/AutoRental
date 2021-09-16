package com.upb.carrental.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upb.carrental.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ProfilePaging extends AppCompatActivity {

    TextView receiver_msg;
    TextView change_username;
    TextView change_email;
    TextView change_password;
    TextView confirm_change_password;
    SharedPreferences settings;
    FirebaseFirestore db;
    Button modify;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        receiver_msg = findViewById(R.id.received_value_id);
        change_username = findViewById(R.id.change_username);
        change_email = findViewById(R.id.change_email);
        change_password = findViewById(R.id.change_pwd);
        confirm_change_password = findViewById(R.id.confirm_change_pwd);
        modify = findViewById(R.id.btn_modify);
        logout = findViewById(R.id.btn_logout);

        db = FirebaseFirestore.getInstance();

        // email logged in app
        String str = settings.getString(getString(R.string.email), "empty");
        SharedPreferences.Editor editor = settings.edit();

        // display the string into textView
        receiver_msg.setText(str);

        logout.setOnClickListener(view -> {
            if (settings.contains(getString(R.string.email))) {
                editor.remove(getString(R.string.email));
            }
            if (settings.contains(getString(R.string.password))) {
                editor.remove(getString(R.string.password));
            }
            editor.apply();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        });

        AtomicReference<String> id = new AtomicReference<>("");
        modify.setOnClickListener(view -> {
            db.collection("client")
                    .whereEqualTo("Email", str)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                id.set(doc.getId());
                                break;
                            }

                            if (!id.get().equals("")) {
                                Map<String, Object> updates = new HashMap<>();

                                if (!change_username.getText().toString().trim().equals(""))
                                    updates.put("Name", change_username.getText().toString().trim());
                                if (!change_email.getText().toString().trim().equals("")) {
                                    updates.put("Email", change_email.getText().toString().trim());
                                    editor.putString(getString(R.string.email), change_email.getText().toString().trim());
                                    editor.apply();
                                }
                                if (!change_password.getText().toString().trim().equals("") &&
                                        change_password.getText().toString().equals(confirm_change_password.getText().toString())) {
                                    updates.put("Password", change_password.getText().toString().trim());
                                    editor.putString(getString(R.string.password), change_password.getText().toString().trim());
                                    editor.apply();
                                }

                                if (!updates.isEmpty()) {
                                    db.collection("client")
                                            .document(id.get())
                                            .update(updates)
                                            .addOnSuccessListener(aVoid -> Toast.makeText(ProfilePaging.this, "Successfully updated user details", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(e -> Toast.makeText(ProfilePaging.this, "Failed to update user details", Toast.LENGTH_SHORT).show());

                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                }
                            } else {
                                Toast.makeText(ProfilePaging.this, "Failed to get user details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            if (!change_email.getText().toString().equals("")) {
                db.collection("transactions")
                        .whereEqualTo("Email", str)
                        .get()
                        .addOnCompleteListener(task -> {
                           if (task.isSuccessful()) {
                               for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                   id.set(doc.getId());
                                   db.collection("transactions")
                                           .document(id.get())
                                           .update("Email", change_email.getText().toString().trim())
                                           .addOnSuccessListener(aVoid -> Toast.makeText(ProfilePaging.this, "Successfully updated transaction details", Toast.LENGTH_SHORT).show())
                                           .addOnFailureListener(e -> Toast.makeText(ProfilePaging.this, "Failed to update transaction details", Toast.LENGTH_SHORT).show());
                               }
                           }
                        });
            }
        });
    }
}