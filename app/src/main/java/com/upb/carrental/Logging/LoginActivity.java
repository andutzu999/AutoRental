package com.upb.carrental.Logging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upb.carrental.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText pwd;
    TextView email;
    CheckBox checkBox;

    FirebaseFirestore db;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.txt_email);
        pwd = findViewById(R.id.txt_pwd);
        login = findViewById(R.id.btn_login);
        checkBox = findViewById(R.id.checkbox);

        db = FirebaseFirestore.getInstance();

        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        login.setOnClickListener(view -> {
            boolean valid = true;
            if (email.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            if (pwd.getText().toString().equals("")) {
                Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            if (valid) {
                db.collection("client")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                boolean match_found = false;
                                for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                                    String a = doc.getString("Email");
                                    String b = doc.getString("Password");
                                    String a1 = email.getText().toString().trim();
                                    String b1 = pwd.getText().toString().trim();
                                    assert a != null;
                                    assert b != null;
                                    if (a.equalsIgnoreCase(a1) && b.equalsIgnoreCase(b1)) {

                                        // open loading intent
                                        Intent home = new Intent(view.getContext(), LoadingPage.class);
                                        editor.putString(getString(R.string.email), a1);
                                        editor.putString(getString(R.string.password), b1);
                                        if (checkBox.isChecked()) {
                                            editor.putBoolean(getString(R.string.checkbox), true);
                                        } else {
                                            editor.putBoolean(getString(R.string.checkbox), false);
                                        }
                                        editor.apply();
                                        view.getContext().startActivity(home);
                                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                        match_found = true;
                                        break;
                                    }
                                }
                                if (!match_found) {
                                    Toast.makeText(LoginActivity.this, "Cannot login, incorrect email or password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

