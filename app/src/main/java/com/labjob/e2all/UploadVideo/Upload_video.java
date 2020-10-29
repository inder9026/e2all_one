package com.labjob.e2all.UploadVideo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.labjob.e2all.Dashboard.Dashboard;
import com.labjob.e2all.R;
import com.labjob.e2all.Signup.Sign_up;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class Upload_video extends AppCompatActivity {
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 1;

    boolean check = true;

    EditText v_desc;
    private BottomSheetBehavior<View> mBottomSheetBehavior2;
    int i = 0;
    ProgressDialog progressDialog;
    public static String timeStamp1;
    EditText u_email;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView profile, camera, video;
    String encoded;
    // API urls

    Intent intent;

    public static final int RequestPermissionCode = 1;
    TextView post;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 1000;
    private Uri fileUri; // file url to store image/video
    public static final int REQUEST_PERMISSION_LOCATION = 10;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    EditText post_text_status;
    String status;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private static final String URL = "http://cdz.co.in/mobileBazar/Android/All_services.php";
    String miUrlOk = "";
    String miUrlVideo = "";
    private Uri mImageUri;
    String username = "image";
    String img_str;
    private ImageView imageView;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    VideoView videoView;
    CircleImageView im;
    Uri videoUri = null;
    TextView username_;
    String pname;
    String userprofiles;
    String date_;
    Button upload;
    String time_;
    String gml;
    EditText number, title;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    String format;
    TextView loctaion ;
    String base64 = "";
    private Uri videouri;
    EditText uname, uemail, umobile, vtitle, vdesc;
    VideoView video_url;
    Button uploadtoserver_btn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        checkpermi() ;
        ScrollView v = (ScrollView) findViewById(R.id.full);
        uname = findViewById(R.id.name);
        uemail = findViewById(R.id.email);
        umobile = findViewById(R.id.mobile);
        vtitle = findViewById(R.id.video_title);
        vdesc = findViewById(R.id.words);
        videoView = findViewById(R.id.video_view);
        uploadtoserver_btn = findViewById(R.id.submit_video);
        uploadtoserver_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uploadvideo_to_server() ;
            }
        });
        //  v.requestFocus();


        upload = findViewById(R.id.upload_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Upload your Video");
        timeStamp1 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Timestamp(System.currentTimeMillis()));
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timestam = new SimpleDateFormat(" dyyyyHHmmss");
        SimpleDateFormat date = new SimpleDateFormat(" dd-MMM-yyyy");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat time = new SimpleDateFormat(" HH:mm:ss");
        format = timestam.format(calendar.getInstance().getTime());
        Log.d("hllll", format);
        date_ = date.format(calendar.getTime());
        time_ = time.format(calendar.getTime());
       // camerapermit();
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videouri);
//        makeCameraPermissionRequest();
//        checkPermssions();
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dispatchTakeVideoIntent();
                //  PopUp popUpClass = new PopUp();
                // popUpClass.showPopupWindow(v);
                showVideoChooserDialog();
            }
        });
    }

    private void checkPermission(String writeExternalStorage, int storagePermissionCode) {
        if (ContextCompat.checkSelfPermission(Upload_video.this, writeExternalStorage)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Upload_video.this,
                    new String[] { writeExternalStorage },
                    storagePermissionCode);
        }
        else {
            Toast.makeText(Upload_video.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }



    }


    private void captureImage() {
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }


    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


//    private void checkPermssions() {
//        int permission = ContextCompat.checkSelfPermission(Upload_video.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(("allow"))
//                        .setTitle(("yes allow"));
//                builder.setCancelable(false);
//                builder.setPositiveButton(("ok"), new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int id) {
//                        makeRequest();
//                        camerapermit();
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            } else {
//               // camerapermit();
//                makeRequest();
//            }
//        }
//    }
//
//    public void camerapermit() {
//        int permission = ContextCompat.checkSelfPermission(Upload_video.this,
//                Manifest.permission.CAMERA);
//
//        if (permission != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.CAMERA)) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage(("title"))
//                        .setTitle(("Permission"));
//                builder.setCancelable(false);
//
//                builder.setPositiveButton(("ok"), new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        makeRequest();
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            } else {
//
//                makeCameraPermissionRequest();
//            }
//        }
//    }


//    protected void makeCameraPermissionRequest() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.CAMERA},
//                500);
//    }
//
//    protected void makeRequest() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                500);
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull
                                                   String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Upload_video.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(Upload_video.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Upload_video.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(Upload_video.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Upload_video.this);
        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    .Context()
//                            .setAspectRatio(5, 7)
//                            .start(PostStatusActivity.this);
//                }
//                else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
        builder.show();
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        encoded = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                encoded,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        encoded = image.getAbsolutePath();

        Log.d("mila", encoded);
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("LongLogTag")
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK && data != null) {
            videouri = data.getData();
            videoView.setVideoURI(videouri);
            videoView.setVisibility(View.VISIBLE);
            videoView.seekTo(1);
            try {/*from   w w w .  ja  va  2s  .  c om*/
                File file = new File(String.valueOf(videouri));
                byte[] buffer = new byte[(int) file.length() + 100];
                @SuppressWarnings("resource")
                int length = new FileInputStream(file).read(buffer);
                base64 = Base64.encodeToString(buffer, 0, length,
                        Base64.DEFAULT);
                Log.d("aata1",base64) ;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("aata1",e.toString()) ;

            }
        } else if (requestCode == 2) {

            videouri = data.getData();
            if (data.getData().equals(""))
            {
                Toast.makeText(this, "Nothng Selected", Toast.LENGTH_SHORT).show();
            }
            String[] filePath = {MediaStore.Video.Media.DATA};
            Cursor c = getContentResolver().query(videouri, filePath,
                    null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String videoPath = c.getString(columnIndex);
            c.close();
            videoView.setVideoURI(videouri);
            Log.d("@@",videouri.toString()) ;
            // mVideoView.setVideoURI( yourVideoPath );
            videoView.seekTo(1);
            videoView.setVisibility(View.VISIBLE);
            try {/*from   w w w .  ja  va  2s  .  c om*/
                File file = new File(videoPath);
                byte[] buffer = new byte[(int) file.length() + 100];
                @SuppressWarnings("resource")
                int length = new FileInputStream(file).read(buffer);
                base64 = Base64.encodeToString(buffer, 0, length,
                        Base64.DEFAULT);
                Log.d("data",base64) ;
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.d("data",e.toString()) ;

            }

        }
        else
        {
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show();

        }
    }

    public void showVideoChooserDialog() {

        final CharSequence[] options = {"From Camera", "From Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("From Camera")) {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
                    }
                }
                else if (options[item].equals("From Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
                    startActivityForResult(intent.createChooser(intent, "select file"), 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void checkpermi()
    {
        Log.d("a","a") ;
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        Log.d("a","b") ;

    }

    public class ImageProcessClass {
        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;
                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));
                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();
                RC = httpURLConnectionObject.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReaderObject.readLine()) != null) {
                        stringBuilder.append(RC2);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            Intent dashboard = new Intent(Upload_video.this, Dashboard.class);
            startActivity(dashboard);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Uploadvideo_to_server() {
        progressDialog = new ProgressDialog(Upload_video.this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading your video");
        progressDialog.show();
        String url = "https://e2all.org/demo/api/upload_video_to_contenst";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();

        try {
            try {
                jsonObject.put("user_id", "128");
                jsonObject.put("vid_title", "Android test");
                jsonObject.put("vid_description", "In Progress");
                jsonObject.put("cntst_id", "1");
                jsonObject.put("video_url", "AAAAIGZ0eXBpc29tAAACAGlzb21pc28yYXZjMW1wNDEAAAAYYmVhbQEAAAABAAAAAAAAAAUAAAAA\\nAAAIZnJlZQABLX9tb292AAAAbG12aGQAAAAAAAAAAAAAAAAAAAPoAAEelwABAAABAAAAAAAAAAAA\\nAAAAAQAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\\nAAAAAAAAAAADAAB9I3RyYWsAAABcdGtoZAAAAAMAAAAAAAAAAAAAAAEAAAAAAAEelwAAAAAAAAAA\\nAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAEAAAAAAAAAAAAAAAAAAEAAAAADVgAAAeAAAAAAACRm\\ncmVlAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAfJttZGlhAAAAIG1kaGQAAAAAAAAAAAAA\\nAAAAADwAABEyAFXEAAAAAAAtaGRscgAAAAAAAAAAdmlkZQAAAAAAAAAAAAAAAFZpZGVvSGFuZGxl\\ncgAAAHxGbWluZgAAABR2bWhkAAAAAQAAAAAAAAAAAAAAJGRpbmYAAAAcZHJlZgAAAAAAAAABAAAA\\nDHVybCAAAAABAAB8BnN0YmwAAACac3RzZAAAAAAAAAABAAAAimF2YzEAAAAAAAAAAQAAAAAAAAAA\\nAAAAAAAAAAADVgHgAEgAAABIAAAAAAAAAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\\nAAAY\\/\\/8AAAA0YXZjQwFNQB\\/\\/4QAdZ01AH+iAbB7zeAtQUBBkAAADAAQAAAMA8DxgxEgBAARo6+8g\\nAAAAGHN0dHMAAAAAAAAAAQAACJkAAAIAAAAATHN0c3MAAAAAAAAADwAAAAEAAACXAAABNQAAAboA\\nAAJQAAAC5gAAA4UAAAQbAAAEsQAABUAAAAXXAAAGXwAABvUAAAd6AAAICQAANfBjdHRzAAAAAAAA\\nBrwAAAACAAACAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABAAAAAABAAAA\\nAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAYA\\nAAAAAgAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAA\\nAAABAAAEAAAAAAEAAAAAAAAAAQAAAgAAAAABAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAA\\nAAEAAAQAAAAAAQAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAA\\nAQAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAwAAAgAAAAABAAAEAAAAAAEAAAAAAAAAAQAABAAAAAAB\\nAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEA\\nAAQAAAAAAQAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEAAAQAAAAAAQAA\\nAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAC\\nAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEAAAQA\\nAAAAAQAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAA\\nAAABAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAA\\nAAIAAAAAAAAAAgAAAgAAAAABAAAGAAAAAAIAAAAAAAAAAQAAAgAAAAABAAAGAAAAAAIAAAAAAAAA\\nAQAABAAAAAABAAAAAAAAAAEAAAIAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAAB\\nAAACAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEA\\nAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAA\\nAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAC\\nAAAAAAEAAAYAAAAAAgAAAAAAAAABAAACAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAEAAAAAAEAAAAA\\nAAAAAQAABAAAAAABAAAAAAAAAAEAAAIAAAAAAQAABgAAAAACAAAAAAAAAAEAAAIAAAAAAQAABgAA\\nAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAACAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAEAAAA\\nAAEAAAAAAAAAAQAAAgAAAAABAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAYAAAAA\\nAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAAB\\nAAAGAAAAAAIAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAGAAAAAAIA\\nAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAACAAAAAAEAAAYAAAAAAgAA\\nAAAAAAABAAAEAAAAAAEAAAAAAAAAAQAAAgAAAAABAAAGAAAAAAIAAAAAAAAAAQAABAAAAAABAAAA\\nAAAAAAEAAAIAAAAAAQAABAAAAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAA\\nAAAAAQAABgAAAAACAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABAAA\\nAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABgAAAAACAAAAAAAA\\nAAEAAAQAAAAAAQAAAAAAAAABAAACAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAEAAAAAAEAAAAAAAAA\\nAQAABAAAAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAAAgAAAAAB\\nAAAEAAAAAAEAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAEAAAAAAEA\\nAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAA\\nBAAAAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAACAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAG\\nAAAAAAIAAAAAAAAAAQAABAAAAAABAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAEAAAAAAEAAAAA\\nAAAAAQAABAAAAAABAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAACAAAAAAEAAAQAAAAAAQAAAAAA\\nAAABAAAGAAAAAAIAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAGAAAA\\nAAIAAAAAAAAAAQAABgAAAAACAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAEAAAAAAEAAAAAAAAA\\nAQAABAAAAAABAAAAAAAAAAEAAAQAAAAAAQAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAABAAAAAAB\\nAAAAAAAAAAEAAAYAAAAAAgAAAAAAAAABAAAGAAAAAAIAAAAAAAAAAQAAAgAAAAABAA");


                Log.d("obj",jsonObject.toString()) ;


            } catch (JSONException ex) {
                ex.printStackTrace();
            }

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
                        JSONObject failed = jObject.getJSONObject("status");
                        //String theCounter = iOSObject.getString("email");
                        progressDialog.dismiss();
                        Toast.makeText(Upload_video.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                        Intent dash = new Intent(Sign_up.this, Dashboard.class) ;
//                        startActivity(dash);

                        Log.d("@@", iOSObject.toString());
                        Log.d("failed", failed.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("error", error.toString());
                    Log.d("@@", error.toString());
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.statusCode == 500) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            Log.d("@date1", res);
                            progressDialog.dismiss();
                            //use this json as you want
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            progressDialog.dismiss();
                            Log.d("@date2", e1.toString());
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                            progressDialog.dismiss();
                            Log.d("@date3", e2.toString());
                        }
                    }
                    // do something...
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "dGdXSVVFdHdsN0tSalRyWlJ5Z2xUcHh2TXVxUW5POGJCRmd2SndJbVVFTEtDMVRVS0hib1RzOEpZNldL5ed3f6d4c0950");
                    //    headers.put("Authorization", "Basic YWRtaW46YWRtaW4=");
                    headers.put("Accept","application/json");
                    headers.put("Content-Type","application/json");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
        }
    }

}