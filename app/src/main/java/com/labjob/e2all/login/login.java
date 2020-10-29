package com.labjob.e2all.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.labjob.e2all.AllVideos.All_Videos;
import com.labjob.e2all.Dashboard.Dashboard;
import com.labjob.e2all.JSONfunctions;
import com.labjob.e2all.R;
import com.labjob.e2all.Signup.Sign_up;
import com.labjob.e2all.allcountry.All_countires;
import com.labjob.e2all.forget.Forget;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class login extends AppCompatActivity {
    TextView forget, signup;
    Button login;
    ProgressDialog mProgressDialog ;
    EditText user_id, pass;
    JSONObject jsonobject ;
    ProgressDialog progressDialog;
    String memail;
    String mpassword;
    boolean check = true;
    private long mRequestStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forget = findViewById(R.id.forgot);
        signup = findViewById(R.id.signup);
        login = findViewById(R.id.login);
        user_id = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        user_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (pass.getLeft() - pass.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        // your action here
                        pass.setTransformationMethod(null);
                        return true;
                    }
                    else
                    {
                        //user_id.setTransformationMethod(new HideReturnsTransformationMethod());
                    }

                }


                return false;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Login");
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget = new Intent(login.this, Forget.class);
                startActivity(forget);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_id.getText().toString().equals("")) {

                    user_id.setError("enter user name");
                } else if (pass.getText().toString().equals("")) {
                    pass.setError("enter user password");
                } else {
                    login() ;
                }


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(login.this, Sign_up.class);
                startActivity(signup);

            }
        });
    }

public  void  login()
{
    progressDialog = new ProgressDialog(login.this) ;
    progressDialog.setTitle("Loading");
    progressDialog.setCancelable(false);
    progressDialog.setMessage("Logging in");
    progressDialog.show();
    String url = "https://e2all.org/demo/api/login";
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("email", user_id.getText().toString());
          //  jsonObject.put("password", "I9026597501j");
            jsonObject.put("password", pass.getText().toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(login.this, ""+response, Toast.LENGTH_SHORT).show();
                    Log.d("@@", response.toString());
//                JSONObject iOSObject = jObject.getJSONObject("email");
                    String resp = response.toString();
                    try {
                        JSONObject jObject = new JSONObject(resp);
                        JSONObject iOSObject = jObject.getJSONObject("success");
                        String theCounter = iOSObject.getString("email");
                        progressDialog.dismiss();
                        Toast.makeText(login.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                        Intent dash = new Intent(login.this, Dashboard.class);
                        startActivity(dash);

                        Log.d("@@", theCounter);

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
                            String er =  obj.getString("error") ;
                            progressDialog.dismiss();
                            Toast.makeText(login.this, ""+er, Toast.LENGTH_SHORT).show();
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
                    }

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    //headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                    headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



//    private class startLogin extends AsyncTask<Void, Void, Void> {
//        String i = "0";
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mProgressDialog = new ProgressDialog(login.this);
//            mProgressDialog.setTitle("Login");
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setCancelable(false);
//            mProgressDialog.show();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            HttpPost post = new HttpPost("https://e2all.org/demo/api/login");
//
//            post.addHeader("Authentication", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
//
//            //  memail = user_id.getText().toString();
//            memail = "indrajeet.chaturvedi46@gmail.com";
//            // mpassword = pass.getText().toString();
//            mpassword = "I9026597501j";
//            String token = "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950";
//            jsonobject = JSONfunctions.getJSONfromURL("https://e2all.org/demo/api/login?email=" + memail + "&password=" + mpassword);
//            Log.v("z", ("https://e2all.org/demo/api/login?email=" + memail + "&password=" + mpassword));
//            Log.v("z", jsonobject.toString());
//            String data = jsonobject.toString();
//            try {
//                //Locate the array name in JSON
//                JSONObject jObject = new JSONObject(data);
//                JSONObject iOSObject = jObject.getJSONObject("email");
//                Log.v("z", iOSObject.toString());
//                // session.createLoginSession(Senderidvalue, pass);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void args) {
//            mProgressDialog.dismiss();
////            if(!i.equals("0")){
////                myvalue();
////            }
////            else{
////                Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
////            }
//
//
//        }
//    }
}




