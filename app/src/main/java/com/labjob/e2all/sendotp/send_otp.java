package com.labjob.e2all.sendotp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.labjob.e2all.R;
import com.labjob.e2all.allcountry.All_countires;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class send_otp extends AppCompatActivity {
TextView resend ;
ProgressDialog progressDialog ;
Button verify_ ;
EditText email ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        resend = findViewById(R.id.resend) ;
        verify_  = findViewById(R.id.verify) ;
        email = findViewById(R.id.useremail) ;
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals(""))
                {
                    email.setHint("Enter your email");
                }
                else
                {
                    sendingOtp() ;
                }
            }
        });

    }
    public  void  sendingOtp()
    {
        progressDialog = new ProgressDialog(send_otp.this) ;
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("fetching data");
        progressDialog.show();
        String url = "https://e2all.org/demo/api/reSendOtp";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try

        {
            try {
                jsonObject.put("email", email.getText().toString());
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
//            try {
//                jsonObject.put("password", "I9026597501j");
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(login.this, ""+response, Toast.LENGTH_SHORT).show();
                    Log.d("@@",response.toString()) ;
//                JSONObject iOSObject = jObject.getJSONObject("email");
                    String resp = response.toString() ;
                    try {
                        JSONObject jObject = new JSONObject(resp);
                        //JSONObject iOSObject = jObject.getJSONObject("success");
                        progressDialog.dismiss();
                        String reset_password = jObject.getString("msg");
                        Toast.makeText(send_otp.this, ""+reset_password, Toast.LENGTH_SHORT).show();
                        Log.d("@@",reset_password) ;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // do something...
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);

        }
        catch (Exception e)
        {
        }
    }
}