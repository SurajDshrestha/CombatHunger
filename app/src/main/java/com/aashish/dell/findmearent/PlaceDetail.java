package com.aashish.dell.findmearent;

/**
 * Created by Dell on 13-Mar-16.
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import android.graphics.BitmapFactory;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.aashish.dell.findmearent.model.VacantPlaces;
import com.aashish.dell.findmearent.model.config;
import com.aashish.dell.findmearent.volleycustomlistview.AppController;
//import com.aashish.dell.findmearent.volleycustomlistview.VolleyImagerRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlaceDetail extends AppCompatActivity{

    // LogCat tag
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

private String image0, image1, image2, location;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri; // file url to store image/video

    private ImageButton btnCapturePicture1, btnCapturePicture2, btnCapturePicture3;

    private Button buttonmap, buttoncontact, buttonReport;
    private Button buttonUpload;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private ImageView imageView, imageView2, imageView3;
private int area;

    private Uri filePath;
    private Bitmap bitmap,bitmap1,bitmap2,bitmap3,bitmap11, bitmap22, bitmap33;

    private int PICK_IMAGE_REQUEST = 1;

    String placeid;

    SessionManager session;
    private ProgressDialog pDialog;

    private LocationManager locationManager;

    private static final String TAG = OneFragment.class.getSimpleName();

    private static final String url = config.Localhost+"/contact.php?query=";
    String placeid1, type1, description1, address1, url0, url1, url2, accountid1;
    Double area1, latitude1, longitude1;
    String rate1;

    private String email1, phoneno;

    @Bind(R.id.area1) EditText _area;
    @Bind(R.id.rate1) TextView _rate;
   @Bind(R.id.description1) EditText _description;
   @Bind(R.id.latitude1) EditText _latitude;
    @Bind(R.id.longitude1) EditText _longitude;
    @Bind(R.id.address1) EditText _address;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placedetails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse);

        Intent intent = getIntent();
        placeid1 = intent.getExtras().getString("placeid");
        accountid1 = intent.getExtras().getString("accountid");

        type1 = intent.getExtras().getString("type");
        area1 = intent.getExtras().getDouble("area");
        rate1 = intent.getExtras().getString("rate");
        description1 = intent.getExtras().getString("description");
        latitude1 = intent.getExtras().getDouble("latitude");
        longitude1 = intent.getExtras().getDouble("longitude");
        address1 = intent.getExtras().getString("address");
        url0 = intent.getExtras().getString("url0");
        url1 = intent.getExtras().getString("url1");
        url2 = intent.getExtras().getString("url2");
        _area.setText(area1.toString());
        _rate.setText(rate1.toString());
        _description.setText(description1);
        _latitude.setText(latitude1.toString());
        _longitude.setText(longitude1.toString());
        _address.setText(address1);
        //area1 = (EditText) findViewById(R.id.area1);
        Log.i(TAG, "onCreate: " + url0 + url1 + url2);
        //disable edit
        _area.setEnabled(false);
        _rate.setEnabled(false);
        _description.setEnabled(false);
        _latitude.setEnabled(false);
        _longitude.setEnabled(false);
        _address.setEnabled(false);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        /**
         * Capture image button click event
         */btnCapturePicture1 = (ImageButton) findViewById(R.id.imageView11);
        btnCapturePicture2 = (ImageButton) findViewById(R.id.imageView21);
        btnCapturePicture3 = (ImageButton) findViewById(R.id.imageView31);
        buttonmap = (Button) findViewById(R.id.showonmap);


        buttoncontact = (Button) findViewById(R.id.Contact);

        buttonReport = (Button) findViewById(R.id.Report);

        Picasso.with(this)
                .load(url0)
                .placeholder(R.drawable.ic_menu_camera)   // optional
                .error(R.drawable.ic_menu_camera)      // optional
                .resize(400, 400).centerInside()                  // optional
                .into(btnCapturePicture1);
        Picasso.with(this)
                .load(url1)
                        //.placeholder(R.drawable.ic_menu_camera)   // optional
                .error(R.drawable.ic_menu_camera)      // optional
                .resize(400, 400).centerInside()
                .into(btnCapturePicture2);

        Picasso.with(this)
                .load(url2)
                        //.placeholder(R.drawable.ic_menu_camera)   // optional
                .error(R.drawable.ic_menu_camera)      // optional
                .resize(400, 400).centerInside()                        // optional
                .into(btnCapturePicture3);

        /**
         * Capture image button click event
         */
//
        btnCapturePicture1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                //choose();
                Intent intent = new Intent();

                intent.setClass(getBaseContext(), FullscreenActivity.class);
                intent.putExtra("url", url0);

                startActivity(intent);
            }
        });
        btnCapturePicture2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                //choose();
                Intent intent = new Intent();

                intent.setClass(getBaseContext(), FullscreenActivity.class);
                intent.putExtra("url", url1);
                startActivity(intent);


            }
        });

        btnCapturePicture3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                Intent intent = new Intent();

                intent.setClass(getBaseContext(), FullscreenActivity.class);
                intent.putExtra("url", url2);
                startActivity(intent);
            }
        });
        buttonmap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                //choose();
                Intent intent = new Intent();

                intent.setClass(getBaseContext(), MapsActivity.class);
                intent.putExtra("latitude", latitude1);
                intent.putExtra("longitude", longitude1);
                intent.putExtra("description", description1);

                startActivity(intent);
            }
        });
        buttonReport.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                //choose();
                HashMap<String, String> user = session.getUserDetails();

                // name


                // email
               final String email = user.get(SessionManager.KEY_EMAIL);

                Intent intent = new Intent();

                intent.setClass(getBaseContext(), Report.class);

                intent.putExtra("placeid", placeid1);
                intent.putExtra("email", email);
                intent.putExtra("accountid", accountid1);

                startActivity(intent);
            }
        });

//    private void loadImage(NetworkImageView iv, String url){
//
//        imageLoader = VolleyImagerRequest.getInstance(this.getApplicationContext())
//                .getImageLoader();
//        imageLoader.get(url, ImageLoader.getImageListener(imageView,
//                R.drawable.ic_menu_camera, android.R.drawable
//                        .ic_dialog_alert));
//
//       //iv.setImageurl(url, imageLoader);
//    }

        buttoncontact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
phoneno = "";
                email1 = "";

                String url1 = url + placeid1;
                Log.i(TAG, "onClick: " +url1+"     "+area1);
                // Creating volley request obj
                JsonArrayRequest myplaces = new JsonArrayRequest(url1,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());
                                for (int i = 0; i < response.length(); i++) {
                                    try {

                                        JSONObject obj = response.getJSONObject(i);
                                       // VacantPlaces myplaces = new VacantPlaces();
                               phoneno = (obj.getString("contactno"));
                                        email1 = (obj.getString("email"));

                                        Log.i(TAG, "onResponse: "+obj.getString("contactno"));

                                        contact();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }


                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                    }
                });
                AppController.getInstance().addToRequestQueue(myplaces);

                contact();       //choose();
            }

        });}
            private void contact() {
                Log.i(TAG, "contact: "+phoneno+""+email1);
                CharSequence contact[] = new CharSequence[]{"Make a Call.", "Send Email"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Contact Owner");
                builder.setItems(contact, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:"+phoneno));
                            startActivity(intent);
                        } else {
                            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                    "mailto", email1, null));
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                            startActivity(Intent.createChooser(emailIntent, "Send email..."));
                        }
                        // the user clicked on colors[which]
                    }
                });
                builder.show();

            }


    }

