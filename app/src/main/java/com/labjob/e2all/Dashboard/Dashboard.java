package com.labjob.e2all.Dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.labjob.e2all.AllVideos.All_Videos;
import com.labjob.e2all.R;
import com.labjob.e2all.UploadVideo.Upload_video;
import com.labjob.e2all.Uploadearn.uploadnearn;
import com.labjob.e2all.Videos.Videos;
import com.labjob.e2all.Votenow.Votenow;
import com.labjob.e2all.statelist.State;
import com.labjob.e2all.statelist.State_adapter;
import com.labjob.e2all.statelist.State_list;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity  {
    Button participate, opengallery, view_videos;
    TextView more;
    NavController navController ;
    DrawerLayout drawer ;
    private static final String JSON_URL = "https://e2all.org/demo/api/get_active_contest";
    ListView get_contest_list;
    ProgressDialog progressDialog ;
    List<Dashboard_items> contest_list ;
    NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        get_contest_list = (ListView) findViewById(R.id.get_contest);
        get_contest_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                        // Disallow the touch request for parent scroll on touch of child view
                view.getParent().requestDisallowInterceptTouchEvent(true);
                        return false;


            }
        });
        contest_list = new ArrayList<>();
      //  participate = findViewById(R.id.participate);
        more = findViewById(R.id.more);
        LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        get_contest_list.setLayoutParams(mParam);
        view_videos = findViewById(R.id.view_videos);
        opengallery = findViewById(R.id.opengallery);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Dashboard");
        loadstateList() ;
//        participate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent part = new Intent(Dashboard.this, uploadnearn.class);
//                startActivity(part);
//            }
//        });
        opengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Dashboard.this, Votenow.class);
                startActivity(part);
            }
        });

        view_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Dashboard.this, All_Videos.class);
                startActivity(part);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Dashboard.this, Upload_video.class);
                startActivity(part);
            }
        });

         drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()

                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem,navController);
                drawer.bringToFront();
                drawer.requestLayout();
                drawer.closeDrawer(GravityCompat.START);
                if (id==R.id.nav_home)
                {
                    Log.d("clicked","clicked") ;
                    Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                }
                //This is for closing the drawer after acting on it
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void loadstateList() {
        //getting the progressbar
        //  final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressDialog = new ProgressDialog(Dashboard.this) ;
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
                            JSONArray heroArray = obj.getJSONArray("active_contest");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < heroArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject contest_object = heroArray.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                Dashboard_items country = new Dashboard_items(contest_object.getString("name"), contest_object.getString("startdate") ,contest_object.getString("enddate"),contest_object.getString("result_date"),contest_object.getString("catname"));
                                //adding the hero to herolist
                                contest_list.add(country);
                            }

                            //creating custom adapter object
                            Dashboard_adapter adapter = new Dashboard_adapter(contest_list, getApplicationContext());
                            //adding the adapter to listview
                            progressDialog.dismiss();
                            get_contest_list.setAdapter(adapter);
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