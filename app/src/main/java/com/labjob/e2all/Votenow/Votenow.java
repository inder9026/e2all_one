package com.labjob.e2all.Votenow;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.labjob.e2all.Dashboard.Dashboard;
import com.labjob.e2all.R;
import com.labjob.e2all.UploadVideo.Upload_video;
import com.labjob.e2all.videofullview.Video_full_view;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Votenow extends AppCompatActivity {
    Button participate, vote;
    private static final long START_TIME_IN_MILLIS = 1000000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis;
    String timeLeftFormatted ;
    private long mEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votenow);
        vpn() ;
        participate = findViewById(R.id.Participate);
        vote = findViewById(R.id.vote);
        participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent part = new Intent(Votenow.this, Upload_video.class);
                startActivity(part);
               // resetTimer();
            }
        });
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                   // pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Vote Participant");
    }
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                showPopup();
//            }
//        }, 100);
//    public void showPopup(){
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup_window, null, false), 300, 400, true);
//        pw.showAtLocation(findViewById(R.id.votenow), Gravity.CENTER, 0, 0);
//    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent dashboard = new Intent(Votenow.this, Dashboard.class);
            startActivity(dashboard);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                vote.setBackgroundColor(Color.RED);
                vote.setText("Try again after " +timeLeftFormatted+ "\u0020" + "Minutes");
                updateCountDownText();

            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateButtons();
            }
        }.start();
        mTimerRunning = true;
        updateButtons();
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateButtons();
    }
    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        updateButtons();
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
         timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        Log.d("left",timeLeftFormatted) ;
     //   Toast.makeText(this, ""+timeLeftFormatted, Toast.LENGTH_SHORT).show();

    }
    private void updateButtons() {
        if (mTimerRunning) {
//            participate.setVisibility(View.INVISIBLE);
            vote.setBackgroundColor(Color.RED);
            vote.setText(timeLeftFormatted  +"/n"+"Time Left To Vote .");

        } else {
            //vote.setText("Tap To Vote !");
            if (mTimeLeftInMillis < 1000) {
               // vote.setVisibility(View.INVISIBLE);
                vote.setBackgroundColor(Color.parseColor("#0A9112"));
                vote.setText("Tap To Vote !!");
                mTimeLeftInMillis  = 1000000 ;

           }
            else {
                vote.setVisibility(View.VISIBLE);
                vote.setText("Indra");
            }
            if (mTimeLeftInMillis < START_TIME_IN_MILLIS) {
                //participate.setVisibility(View.VISIBLE);
            }
            else
                {
               // participate.setVisibility(View.INVISIBLE);
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("millisLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);
        editor.apply();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        mTimeLeftInMillis = prefs.getLong("millisLeft", START_TIME_IN_MILLIS);
        mTimerRunning = prefs.getBoolean("timerRunning", false);
        updateCountDownText();
        // updateButtons();
        if (mTimerRunning)
        {
            mEndTime = prefs.getLong("endTime", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;

//                updateCountDownText();
//                updateButtons();

            }
            else
            {

                startTimer();
            }
        }
    }
    public boolean vpn() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if ( iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")|| iface.contains("L2TP") || iface.contains("OpenVPN")) {
                    Toast.makeText(this, "Remove vpn connection to vote", Toast.LENGTH_SHORT).show();
                    vote.setClickable(false);
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }

}