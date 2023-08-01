package com.example.nodemcu_gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailLokasi extends AppCompatActivity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lokasi);

        TextView txt_1 = (TextView) findViewById(R.id.txt1);
        TextView txt_2 = (TextView) findViewById(R.id.txt2);
        TextView txt_3 = (TextView) findViewById(R.id.txt3);

        Intent intent = getIntent();
        position=intent.getExtras().getInt("position");
        txt_1.setText("No"+MainActivity.recyclerAdapters.get(position).getNo());
        txt_2.setText("Lat"+MainActivity.recyclerAdapters.get(position).getLat());
        txt_3.setText("Lot"+MainActivity.recyclerAdapters.get(position).getLdr());
    }
}