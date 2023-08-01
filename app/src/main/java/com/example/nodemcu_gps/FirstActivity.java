package com.example.nodemcu_gps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;

public class FirstActivity extends AppCompatActivity {
    private Animation top_view, bottom_view;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ImageView imageView = (ImageView) findViewById(R.id.maps_animation);
        MaterialButton mb_mulai = (MaterialButton) findViewById(R.id.btn_mulai);

        top_view= AnimationUtils.loadAnimation(this,R.anim.top);
        bottom_view=AnimationUtils.loadAnimation(this,R.anim.bottom);

        imageView.setAnimation(top_view);
        mb_mulai.setAnimation(bottom_view);
        mb_mulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = new ProgressDialog(FirstActivity.this);
                progress.setMessage("Tunggu ");
                progress.setCancelable(true);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        startActivity(new Intent(FirstActivity.this,MainActivity.class));
                        finish();
                    }
                }, 5000);

            }
        });
    }
}