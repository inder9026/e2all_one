package com.labjob.e2all.forget;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.labjob.e2all.JSONfunctions;
import com.labjob.e2all.R;
import com.labjob.e2all.Signup.Sign_up;
import com.labjob.e2all.login.login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Forget extends AppCompatActivity {
ProgressDialog mProgressDialog ;
JSONObject jsonobject ;
Button submit ;
    ProgressDialog progressDialog;

EditText mail ;
String email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        submit = findViewById(R.id.submit) ;
        mail = findViewById(R.id.useremail) ;

        getSupportActionBar().setTitle("Forgot Password");
    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Forgot_password();
        }
    });
    }
    public  void  Forgot_password()
    {
        progressDialog = new ProgressDialog(Forget.this) ;
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sending link to reset password.Kindly Check your email");
        progressDialog.show();
        String url = "https://e2all.org/demo/api/forgotPasswordUser";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", mail.getText().toString());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
//            try {
//                jsonObject.put("password", "I9026597501j");
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(login.this, ""+response, Toast.LENGTH_SHORT).show();
                Log.d("@@",response.toString()) ;
//                JSONObject iOSObject = jObject.getJSONObject("email");..
                String resp = response.toString() ;
                try {
                    JSONObject jObject = new JSONObject(resp);
                    //JSONObject iOSObject = jObject.getJSONObject("success");
                    String reset_password = jObject.getString("msg");
progressDialog.dismiss();
                    Toast.makeText(Forget.this, ""+reset_password, Toast.LENGTH_SHORT).show();
                    Log.d("@@",reset_password) ;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something...
                Log.d("@@", error.toString());
                NetworkResponse response = error.networkResponse;
                if (response != null && response.statusCode == 401) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);
                        Log.d("@date",res) ;
                        String er =  obj.getString("error") ;
                        progressDialog.dismiss();
                        Toast.makeText(Forget.this, ""+er, Toast.LENGTH_SHORT).show();
                        Log.d("@date",er) ;
                        //use this json as you want
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                        Log.d("@date",e1.toString()) ;
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                        Log.d("@date",e2.toString()) ;
                    }
                }            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                //headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent login = new Intent(Forget.this,login.class);
            startActivity(login);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}