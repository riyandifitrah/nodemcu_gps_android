package com.example.nodemcu_gps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends ArrayAdapter <RecyclerAdapter> {
    Context context;
    Button btnretrieve;
    List<RecyclerAdapter>arrayListAdapter;
    RequestQueue requestQueue;


    public MyAdapter( Context context, List<RecyclerAdapter> arrayListAdapter) {
        super(context, R.layout.list_data,arrayListAdapter);
        this.context =  context;
        this.arrayListAdapter = arrayListAdapter;


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,null,true);

        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-dd-MM");
        SimpleDateFormat sdf_time= new SimpleDateFormat("HH:MM:SS");
        String dateString = sdf_date.format(new Date());
        String timeString = sdf_time.format(new Date());
//        TextView tv_no = view.findViewById(R.id.txt_no);
        TextView tv_ldr = view.findViewById(R.id.txt_ldr);
        TextView tv_lat =  view.findViewById(R.id.txt_lat);
        TextView tv_acc= view.findViewById(R.id.txt_acc);
        TextView tv_date = view.findViewById(R.id.txt_date);
        TextView tv_time =  view.findViewById(R.id.txt_time);
        MaterialButton btn_user =  view.findViewById(R.id.btn_iduser);
        TextView tv_user =  view.findViewById(R.id.txtid_user2);

        tv_ldr.setText(arrayListAdapter.get(position).getLdr());
        tv_lat.setText(arrayListAdapter.get(position).getLat());
        tv_acc.setText(arrayListAdapter.get(position).getAcc());
//        tv_user.setText("Cek Id");
        tv_date.setText(dateString);
        tv_time.setText(timeString);
//        tv_time.setText(arrayListAdapter.get(position).getTime());
        MaterialButton button = (MaterialButton) view.findViewById(R.id.btn_retrievemaps);
        button.setOnClickListener(view1 -> {
//            tv_ldr.setText(arrayListAdapter.get(position).getLdr());
            String out_time = tv_time.getText().toString();
            String out_lon = tv_ldr.getText().toString();
            String out_lat = tv_lat.getText().toString();
            Intent intent = new Intent(view.getContext(), MapsActivity.class);
            intent.putExtra("time",out_time);
            intent.putExtra("latitude",out_lat);
            intent.putExtra("longitude",out_lon);
            context.startActivity(intent);
        });
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.btn_iduser){
                    String url = "https://mysqlmobile123.000webhostapp.com/auth.php";
                    final String originalText = tv_user.getText().toString();
                    tv_user.setText("Jangan Diberikan Ke Siapapun");
                    tv_user.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tv_user.setText(originalText);
                        }
                    }, 5000);

                    StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                            response -> tv_user.setText("ID User : "+ arrayListAdapter.get(position).getId_user()),
                            error -> Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show()){
                        //add parameter to the request

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
//                            params.put("id_user",tv_user.getText().toString());
                            params.put("id_user", "qweq");
                            return params;
                        }
                    };
                    requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

        return view;
    }


}
