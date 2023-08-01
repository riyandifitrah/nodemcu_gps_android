package com.example.nodemcu_gps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Auth extends AppCompatActivity implements View.OnClickListener {
    TextView tv_iduser, tv_2;
    Button btn_iduser;
   RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        tv_iduser=findViewById(R.id.auth);
        tv_2=findViewById(R.id.auth2);
        btn_iduser= (Button) findViewById(R.id.btn_auth);
        btn_iduser.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if (view.getId()==R.id.btn_auth){
            String url = "http://192.168.100.228:80/apimobile3/auth.php";
            StringRequest stringRequest=new StringRequest(Request.Method.POST,url,
                    response -> tv_iduser.setText(response),
                    error -> Toast.makeText(Auth.this, "Error", Toast.LENGTH_SHORT).show()){
                //add parameter to the request
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String, String> params = new HashMap<>();
//                    params.put("id_user",tv_2.getText().toString());
                    params.put("iduser", "asd");
//                    params.put("id_user", "asd");
                    return params;
                }
            };
            requestQueue= Volley.newRequestQueue(Auth.this);
            requestQueue.add(stringRequest);
        }
    }
}