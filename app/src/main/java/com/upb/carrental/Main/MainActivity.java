package com.upb.carrental.Main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.upb.carrental.Logging.LoadingPage;
import com.upb.carrental.Logging.LoginActivity;
import com.upb.carrental.Logging.SignUpActivity;
import com.upb.carrental.R;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.ACCESS_FINE_LOCATION, 1);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 1);
            }
        }

        button1.setOnClickListener(view -> {
            if (settings.contains(getString(R.string.email)) && settings.contains(getString(R.string.password)) && settings.contains(getString(R.string.checkbox))) {
                if (settings.getBoolean(getString(R.string.checkbox), false)) {
                    Intent home = new Intent(view.getContext(), LoadingPage.class);
                    view.getContext().startActivity(home);
                    Toast.makeText(MainActivity.this, "Already logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    view.getContext().startActivity(intent);
                }
            } else {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SignUpActivity.class);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String @NotNull [] permissions,
            int @NotNull [] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> requestPermission(permission, permissionRequestCode));
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }
}