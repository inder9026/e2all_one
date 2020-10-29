package com.labjob.e2all.AllVideos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.labjob.e2all.Dashboard.Dashboard;
import com.labjob.e2all.R;
import com.labjob.e2all.Votenow.Votenow;
import com.labjob.e2all.videofullview.Video_full_view;

public class All_Videos extends AppCompatActivity {
Button watch ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__videos);
        watch = findViewById(R.id.watch);
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent full_view = new Intent(All_Videos.this, Video_full_view.class);
                startActivity(full_view);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Paticipants Videos");
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent dashboard = new Intent(All_Videos.this, Dashboard.class);
            startActivity(dashboard);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}