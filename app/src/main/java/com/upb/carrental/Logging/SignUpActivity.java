package com.upb.carrental.Logging;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.upb.carrental.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    Button reg_registration;
    EditText reg_name;
    EditText reg_email;
    EditText reg_password;
    EditText reg_conf_pwd;
    TextView signin;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        reg_registration=findViewById(R.id.btn_register);
        reg_name=findViewById(R.id.reg_name);
        reg_email=findViewById(R.id.reg_email);
        reg_password=findViewById(R.id.reg_pwd);
        reg_conf_pwd=findViewById(R.id.reg_conpwd);
        signin=findViewById(R.id.siginup_view);
        firebaseFirestore=FirebaseFirestore.getInstance();

        ref = firebaseFirestore.collection("client").document();

        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        reg_registration.setOnClickListener(view -> {

            boolean valid = true;
            if (reg_name.getText().toString().equals("")) {
                Toast.makeText(SignUpActivity.this, "Please type a username", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            if (reg_email.getText().toString().equals("")) {
                Toast.makeText(SignUpActivity.this, "Please type an email id", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            if (reg_password.getText().toString().equals("")){
                Toast.makeText(SignUpActivity.this, "Please type a password", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            if (!reg_conf_pwd.getText().toString().equals(reg_password.getText().toString())){
                Toast.makeText(SignUpActivity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                valid = false;
            }
            if (valid) {

                ref.get().addOnSuccessListener(documentSnapshot -> {

                    if (documentSnapshot.exists())
                    {
                        Toast.makeText(SignUpActivity.this, "Sorry, this user exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, Object> reg_entry = new HashMap<>();
                        reg_entry.put("Name", reg_name.getText().toString());
                        reg_entry.put("Email", reg_email.getText().toString());
                        reg_entry.put("Password", reg_password.getText().toString());


                        firebaseFirestore.collection("client")
                                .add(reg_entry)
                                .addOnSuccessListener(documentReference -> Toast.makeText(SignUpActivity.this, "Successfully added", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Log.d("Error", e.getMessage()));
                    }


                    Intent mainpage = new Intent(SignUpActivity.this, LoadingPage.class);
                    editor.putString(getString(R.string.email), reg_email.getText().toString());
                    editor.putString(getString(R.string.password), reg_password.getText().toString());
                    editor.apply();
                    startActivity(mainpage);
                });
            }
        });
    }
}