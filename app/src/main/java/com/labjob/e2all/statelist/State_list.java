package com.labjob.e2all.statelist;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.labjob.e2all.R;
import com.labjob.e2all.Signup.Sign_up;
import com.labjob.e2all.allcountry.All_countires;
import com.labjob.e2all.allcountry.Country;
import com.labjob.e2all.allcountry.Country_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State_list extends AppCompatActivity {
    private static final String JSON_URL = "https://e2all.org/demo/api/getStateByCountry?country_id=1";
    ListView Country_name_list;
    ProgressDialog progressDialog ;
    List<State> stateList_ ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);
        Country_name_list = (ListView) findViewById(R.id.listView);
        stateList_ = new ArrayList<>();
        Country_name_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent state = new Intent(State_list.this , Sign_up.class) ;
                startActivity(state);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("All States");
        loadstateList();

    }
    private void loadstateList() {
        //getting the progressbar
        //  final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(State_list.this) ;
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching data");
        progressDialog.show();
        //making the progressbar visible
        //  progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        //     progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("success");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject country_object = heroArray.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                State country = new State(country_object.getString("name"), country_object.getString("id") ,country_object.getString("country_id"));
                                //adding the hero to herolist
                                stateList_.add(country);
                            }

                            //creating custom adapter object
                            State_adapter adapter = new State_adapter(stateList_, getApplicationContext());
                            //adding the adapter to listview
                            progressDialog.dismiss();
                            Country_name_list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("error",error.toString()) ;
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                final Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }
}


