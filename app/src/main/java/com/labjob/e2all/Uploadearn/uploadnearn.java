package com.labjob.e2all.Uploadearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.labjob.e2all.Dashboard.Dashboard;
import com.labjob.e2all.R;
import com.labjob.e2all.UploadVideo.Upload_video;
import com.labjob.e2all.Votenow.Votenow;

public class uploadnearn extends AppCompatActivity {
Button upload_video ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadnearn);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Prize List");
        upload_video = findViewById(R.id.upload_video);
      upload_video.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent upload = new Intent(uploadnearn.this, Upload_video.class) ;
              startActivity(upload);
          }
      });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent dashboard = new Intent(uploadnearn.this, Dashboard.class);
            startActivity(dashboard);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}