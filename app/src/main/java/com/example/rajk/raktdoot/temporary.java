package com.example.rajk.raktdoot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RajK on 28-02-2017.
 */
public class temporary extends AppCompatActivity {
    private  static  String APPURL = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefernce", Context.MODE_PRIVATE);
        final String token = sharedPreferences.getString("FCMToken","");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, APPURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("fcm_token",token);
                return super.getParams();
            }
        };

        MySingleton.getmInstance(temporary.this).addToRequestqueue(stringRequest);


    }

}
