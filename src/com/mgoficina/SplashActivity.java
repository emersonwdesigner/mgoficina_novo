package com.mgoficina;

import com.actionbarsherlock.app.SherlockActivity;
import com.example.mgoficina.R;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.content.Intent;

public class SplashActivity extends SherlockActivity implements Runnable {
	ProgressBar progressBar;
	private int tempo = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(this, tempo);
        
        
    }

    public void run(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
