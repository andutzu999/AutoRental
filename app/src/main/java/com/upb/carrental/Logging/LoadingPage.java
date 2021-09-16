package com.upb.carrental.Logging;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;

import com.upb.carrental.R;
import com.upb.carrental.Service.MapsActivity;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class LoadingPage extends AppCompatActivity {

    RingProgressBar progressBar1;

    Handler myHandler;
    int progress = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent menu = new Intent(LoadingPage.this, MapsActivity.class);
            startActivity(menu);
            finish();
        }, 4000);
    }


}

