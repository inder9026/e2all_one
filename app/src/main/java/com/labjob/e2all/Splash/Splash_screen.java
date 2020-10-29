package com.labjob.e2all.Splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.labjob.e2all.R;
import com.labjob.e2all.login.login;

public class Splash_screen extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime =100;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        checkPermission(Manifest.permission.CAMERA,
                CAMERA_PERMISSION_CODE);
        start_splash();

    }

    private void checkPermission(String permission, int requestCode) {

        if (ContextCompat.checkSelfPermission(Splash_screen.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Splash_screen.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(Splash_screen.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Splash_screen.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(Splash_screen.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Splash_screen.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(Splash_screen.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    public void start_splash(){

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(500);
                        if (_active) {
                            waited += 500;
                        }
                    }
                } catch (Exception e) {

                } finally {
                    Intent signup = new Intent(Splash_screen.this, login.class) ;
                    startActivity(signup);

//                    session.checkLogin();
//                    String once = preferences.get(Constants.isUserSignedOnce);
//                    if (once.equals("")) {
//                        startActivity(new Intent(SplashScreen.this,
//                                Signup.class));
//                        finish();
//                    }
//                    if (session.isLoggedIn()) {
//
//                        intent = new Intent(SplashScreen.this, Main5Activity.class);
//                        HashMap<String, String> user = session.getUserDetails();
//
//                        // name
//                        String name = user.get(SessionManager.KEY_NAME);
//
//                        // email
//                        String email = user.get(SessionManager.KEY_EMAIL);
//                        intent.putExtra("names", name);
//                        intent.putExtra("passwordd", email);
//                        startActivity(intent);
//                        finish();
                    }
                }

        };
        splashTread.start();
    }
}