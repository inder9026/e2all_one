package com.labjob.e2all.Videos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.labjob.e2all.AllVideos.All_Videos;
import com.labjob.e2all.R;
import com.labjob.e2all.Uploadearn.uploadnearn;
import com.labjob.e2all.Votenow.Votenow;

public class Videos extends AppCompatActivity {
    Button participate , opengallery ,view_videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        participate = findViewById(R.id.participate) ;
        view_videos = findViewById(R.id.view_videos) ;
        opengallery = findViewById(R.id.opengallery) ;
        participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Videos.this, uploadnearn.class) ;
                startActivity(part);
            }
        });
        opengallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Videos.this, Votenow.class) ;
                startActivity(part);
            }
        });

    view_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Videos.this, All_Videos.class) ;
                startActivity(part);
            }
        });

    }
}