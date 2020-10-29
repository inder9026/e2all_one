package com.labjob.e2all.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.labjob.e2all.Dashboard.Dashboard;
import com.labjob.e2all.R;
import com.labjob.e2all.allcountry.All_countires;
import com.labjob.e2all.login.login;
import com.labjob.e2all.sendotp.send_otp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Sign_up extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button signup ;
ProgressDialog progressDialog ;
TextView login  ;
Button cont_list ;
String[] user_type = { "Teacher", "Student", "Dealer", "Parent"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        login = findViewById(R.id.signup) ;
        cont_list = findViewById(R.id.login_btn) ;
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,user_type);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        cont_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list = new Intent(Sign_up.this, login.class) ;
                startActivity(list);
            }
        }); login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent list = new Intent(Sign_up.this, login.class) ;
                startActivity(list);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Sign Up");
        signup = findViewById(R.id.register) ;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup() ;
//                Intent login = new Intent(Sign_up.this, com.labjob.e2all.login.login.class) ;
//                startActivity(login);
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),user_type[i] , Toast.LENGTH_LONG).show();
        if (i==1)
        {
            Intent board = new Intent(Sign_up.this,All_countires.class) ;
            startActivity(board);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public  void  Signup()
    {
        progressDialog = new ProgressDialog(Sign_up.this) ;
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Signing Up");
        progressDialog.show();
        String url = "https://e2all.org/demo/api/registers";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try

        {
            try {
                jsonObject.put("email", "indrajeet.chaturvedi130@gmail.com");
                jsonObject.put("password", "I9026597501j");
                jsonObject.put("confirm_password", "I9026597501j");
                jsonObject.put("country", "2");
                jsonObject.put("state", "1");
                jsonObject.put("fname", "i");
                jsonObject.put("lname", "c");
                jsonObject.put("number", "8004939476");
                jsonObject.put("user_type", "3");

            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(login.this, ""+response, Toast.LENGTH_SHORT).show();
                    Log.d("@@",response.toString()) ;
//                JSONObject iOSObject = jObject.getJSONObject("email");
                    String resp = response.toString() ;
                    try {
                        JSONObject jObject = new JSONObject(resp);
                        JSONObject iOSObject = jObject.getJSONObject("success");
                        JSONObject failed = jObject.getJSONObject("status");
                        //String theCounter = iOSObject.getString("email");
                        progressDialog.dismiss();
                        Toast.makeText(Sign_up.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                        Intent dash = new Intent(Sign_up.this, Dashboard.class) ;
//                        startActivity(dash);

                        Log.d("@@",iOSObject.toString()) ;
                        Log.d("failed",failed.toString()) ;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error",error.toString());
                    Log.d("@@", error.toString());
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 401) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            Log.d("@date1",res) ;
                            progressDialog.dismiss();
                            //use this json as you want
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            progressDialog.dismiss();
                            Log.d("@date2",e1.toString()) ;
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                            progressDialog.dismiss();
                            Log.d("@date3",e2.toString()) ;
                        }
                    }
                    // do something...
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                   headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                //    headers.put("Authorization", "Basic YWRtaW46YWRtaW4=");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e)
        {
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent login = new Intent(Sign_up.this,login.class);
            startActivity(login);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
