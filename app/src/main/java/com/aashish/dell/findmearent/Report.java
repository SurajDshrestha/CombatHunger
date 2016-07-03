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
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Report extends AppCompatActivity{

    // LogCat tag
    private static final String TAG = AddPlace.class.getSimpleName();

    private ImageView imageView, imageView2, imageView3;

    private EditText editTextName;
    private Uri filePath;
    private Bitmap bitmap,bitmap1,bitmap2,bitmap3,bitmap11, bitmap22, bitmap33;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL = config.Localhost+"/report.php";


    private String KEY_email = "email";
    private String KEY_accountid = "accountid";
    private String KEY_justification = "justification";
    private String KEY_placeid = "placeid";

    SessionManager session;


    private LocationManager locationManager;


    private String value;
    @Bind(R.id.justification) EditText _justification;
    @Bind(R.id.report) Button _upload;

    String placeid1, email1, accountid1, description1, latitude1, longitude1, address1, status1, url0, url1, url2;
    Double area1, rate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        ButterKnife.bind(this);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        Intent intent = getIntent();
        placeid1 = intent.getExtras().getString("placeid");
      accountid1 = intent.getExtras().getString("accountid");
        email1 = intent.getExtras().getString("email");

        // _description.setText(description1);
        Log.i("HelloListView", "You clicked Item: image 2" + placeid1 + " area" + area1 +""+ address1);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        _upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        // Changing action bar background color
        // These two lines are not needed

    }
    public void upload() {

        if (!validate()) {
            validationFailed();
            return;
        }

        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Report.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Report.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String

                String placeid = placeid1;
                String accountid = accountid1;
                String email = email1;
               String justification = _justification.getText().toString();



                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put(KEY_placeid, placeid1);

                params.put(KEY_email, email1);
               params.put(KEY_accountid, accountid1);
                params.put(KEY_justification, justification);

                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);

    }
    public void validationFailed() {
        Toast.makeText(getBaseContext(), "Please fill the form correctly first.", Toast.LENGTH_LONG).show();

        _upload.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String justification = _justification.getText().toString();


        if (justification.isEmpty()){
            _justification.setError("Justification is required.");
            valid = false;
        }

        return valid;
    }

}




