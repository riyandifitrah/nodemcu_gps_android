package com.example.nodemcu_gps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    MyAdapter myAdapter;
    public static ArrayList<RecyclerAdapter> recyclerAdapters =  new ArrayList<>();
    Button btnRetrieveMaps;
    RecyclerAdapter recyclerAdapter123;
    String url = "https://mysqlmobile123.000webhostapp.com/api_read.php";
    List<RecyclerAdapter> arrayListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btnRetrieveMaps= findViewById(R.id.btn_retrievemaps);
        listView=findViewById(R.id.myListView);
        myAdapter = new MyAdapter(this, recyclerAdapters);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"Hapus Data","aasda","acadasd"};
                builder.setTitle(recyclerAdapters.get(position).getNo());
//                builder.setTitle("Pengaturan");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                startActivity(new Intent(getApplicationContext(), DetailLokasi.class)
                                        .putExtra("position",position));
                                break;
                            case 1:
//                                deleteData(position);
                                break;
                            case 2:
                                break;
                        }
                    }


                });
                builder.show();

            }
        });

        listView.setAdapter(myAdapter);
        read_data();
    }

    private void deleteData(int position) {
        StringRequest request =  new StringRequest(Request.Method.POST, "https://mysqlmobile123.000webhostapp.com/hapus.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Menghapus Data")){
                            Toast.makeText(MainActivity.this, "Gagal Hapus", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("no", String.valueOf(position));
//                params.put("ldr",ldr);
//                params.put("lat",lat);
//                params.put("acc",acc);
//                params.put("date",date);
//                params.put("time",time);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
    private void read_data() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                recyclerAdapters.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String sucess = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("nodemcu_ldr_table");
                    if (sucess.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String no = object.getString("no");
                            String ldr = object.getString("ldr");
                            String lat = object.getString("lat");
                            String acc = object.getString("acc");
                            String date = object.getString("date");
                            String time = object.getString("time");
                            String id_user = object.getString("id_user");

                            recyclerAdapter123 = new RecyclerAdapter(no, ldr, lat, acc, date, time, id_user);
                            recyclerAdapters.add(recyclerAdapter123);
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
            }
         });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

//            request.setRetryPolicy(new RetryPolicy() {
//                @Override
//                public int getCurrentTimeout() {
//                    return 5000;
//                }
//
//                @Override
//                public int getCurrentRetryCount() {
//                    return 5000;
//                }
//
//                @Override
//                public void retry(VolleyError error) throws VolleyError {
//
//                }
//            });

            requestQueue.add(request);
    }
//    public View getView(int position) {
//        btnRetrieveMaps.setOnClickListener(view -> {
//            LayoutInflater inflater = getLayoutInflater();
//            View myView = inflater.inflate(R.layout.list_data, null, true);
//            TextView tv_ldr = myView.findViewById(R.id.txt_ldr);
////            tv_ldr.setText(arrayListAdapter.get(position).getLdr());
//            String out_ldr = tv_ldr.getText().toString();
//            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//            intent.putExtra("latitude", out_ldr);
//            startActivity(intent);
//        });
//
}
