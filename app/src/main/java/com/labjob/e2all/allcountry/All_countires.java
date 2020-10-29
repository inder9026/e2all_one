package com.labjob.e2all.allcountry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.labjob.e2all.forget.Forget;
import com.labjob.e2all.login.login;
import com.labjob.e2all.statelist.State;
import com.labjob.e2all.statelist.State_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class All_countires extends AppCompatActivity {
    private static final String JSON_URL = "https://e2all.org/demo/api/getCountry";
    ListView Country_name_list;
    ProgressDialog progressDialog ;
    List<Country> country_list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_countires);
        Country_name_list = (ListView) findViewById(R.id.listView);
        country_list = new ArrayList<>();
        loadCountryList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("All Countries");
        Country_name_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent state = new Intent(All_countires.this , State_list.class) ;
                startActivity(state);
            }
        });
    }
    private void loadCountryList() {
        //getting the progressbar
      //  final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
progressDialog = new ProgressDialog(All_countires.this) ;
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
                                Country country = new Country(country_object.getString("name"), country_object.getString("id") ,country_object.getString("sortname"),country_object.getString("phonecode"));
                                //adding the hero to herolist
                                country_list.add(country);

                            }

                            //creating custom adapter object
                            Country_Adapter adapter = new Country_Adapter(country_list, getApplicationContext());

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent login = new Intent(All_countires.this, Sign_up.class);
            startActivity(login);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


