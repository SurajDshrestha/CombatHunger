package com.aashish.dell.findmearent;

/**
 * Created by Dell on 13-Mar-16.
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aashish.dell.findmearent.model.config;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Setting extends AppCompatActivity {

    // LogCat tag
    private static final String TAG = AddPlace.class.getSimpleName();
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";


    private static final String url1 = config.Localhost+"/changephoneno.php";
    private static final String url2 = config.Localhost+"/delete.php";

    public static final String  KEY_CONTACT = "phoneno";
    public static final String  KEY_EMAIL = "email";

    private Uri fileUri; // file url to store image/video

    private ImageButton btnCapturePicture1, btnCapturePicture2, btnCapturePicture3;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView, imageView2, imageView3;

    private EditText editTextName;
    private Uri filePath;
    private Bitmap bitmap,bitmap1,bitmap2,bitmap3,bitmap11, bitmap22, bitmap33;

    private int PICK_IMAGE_REQUEST = 1;


    SessionManager session;


    private LocationManager locationManager;


    private String value;

    @Bind(R.id.phoneno) EditText _phoneno;
    @Bind(R.id.change) Button _change;
    @Bind(R.id.deactivateAccount) Button _deactivate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ButterKnife.bind(this);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        _deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deactivate();
            }
        });
        // Changing action bar background color
        // These two lines are not needed
        _change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
    }
    public void change(){
        if (!validate()) {
            return;
        }
        final String phoneno = _phoneno.getText().toString().trim();
        HashMap<String, String> user = session.getUserDetails();

        // name

final String email =  user.get(SessionManager.KEY_EMAIL);
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            Toast.makeText(Setting.this, response, Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(Setting.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Setting.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_CONTACT, phoneno);
                map.put(KEY_EMAIL, email);
                Log.i(TAG, "getParams: "+ email+""+phoneno);

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest2);

    }
public void deactivate(){


    HashMap<String, String> user = session.getUserDetails();

    // name

    final String email =  user.get(SessionManager.KEY_EMAIL);
    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Success")) {
                        Toast.makeText(Setting.this, response, Toast.LENGTH_LONG).show();
session.logoutUser();
                    }
                    else {
                        Toast.makeText(Setting.this, response, Toast.LENGTH_LONG).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Setting.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<String, String>();
            map.put(KEY_EMAIL, email);
            Log.i(TAG, "getParams: "+ email+"");

            return map;
        }
    };
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest2);


}

    public boolean validate() {
        boolean valid = true;

        String phoneno = _phoneno.getText().toString();


        if (phoneno.isEmpty() || phoneno.length()< 10 || phoneno.length() > 10){
           _phoneno.setError("Phone no should be  10 digits.");
            valid = false;
        }
        return valid;
    }

}