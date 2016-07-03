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

public class Updateplace extends AppCompatActivity implements LocationListener {

    // LogCat tag
    private static final String TAG = AddPlace.class.getSimpleName();
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private Uri fileUri; // file url to store image/video

    private ImageButton btnCapturePicture1, btnCapturePicture2, btnCapturePicture3;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView, imageView2, imageView3;

    private EditText editTextName;
    private Uri filePath;
    private Bitmap bitmap,bitmap1,bitmap2,bitmap3,bitmap11, bitmap22, bitmap33;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL = config.Localhost+"/updateplace.php";


    private String KEY_area = "area";
    private String KEY_rate = "rate";
    private String KEY_description = "description";
    private String KEY_latitude = "latitude";
    private String KEY_longitude = "longitude";
    private String KEY_address = "address";
    private String KEY_type = "type";
    private String KEY_status = "status";

    private String KEY_IMAGE1 = "image1";
    private String KEY_IMAGE2 = "image2";
    private String KEY_IMAGE3 = "image3";
    private String KEY_EMAIL = "email";
    private String KEY_placeid = "placeid";

    SessionManager session;


    private LocationManager locationManager;


    private String value;
    @Bind(R.id.type)
    RadioGroup _type;
    @Bind(R.id.status) RadioGroup _status;
    @Bind(R.id.area) EditText _area;
    @Bind(R.id.rate) EditText _rate;
    @Bind(R.id.description) EditText _description;
    @Bind(R.id.latitude) EditText _latitude;
    @Bind(R.id.longitude) EditText _longitude;
    @Bind(R.id.address) EditText _address;
    @Bind(R.id.generate) Button _generate;
    @Bind(R.id.UpdatePlace) Button _upload;

    String placeid1, type1, description1, latitude1, longitude1, address1, status1, url0, url1, url2;
Double area1, rate1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateplace);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        placeid1 = intent.getExtras().getString("placeid");
        type1 = intent.getExtras().getString("type");
        area1 = intent.getExtras().getDouble("area");
        rate1 = intent.getExtras().getDouble("rate");
        description1 = intent.getExtras().getString("description");
        latitude1 = intent.getExtras().getString("latitude");
        longitude1 = intent.getExtras().getString("longitude");
        address1 = intent.getExtras().getString("address");
        status1 = intent.getExtras().getString("status");
        url0 = intent.getExtras().getString("url0");
        url1 = intent.getExtras().getString("url1");
        url2 = intent.getExtras().getString("url2");
        _area.setText(area1.toString());
        _rate.setText(rate1.toString());
        _description.setText(description1);
        _latitude.setText(latitude1);
        _longitude.setText(longitude1);
        _address.setText(address1);
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
        _generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        btnCapturePicture1 = (ImageButton) findViewById(R.id.imageView41);
        btnCapturePicture2 = (ImageButton) findViewById(R.id.imageView42);
        btnCapturePicture3 = (ImageButton) findViewById(R.id.imageView43);
        Picasso.with(this)
                .load(url0)
                .placeholder(R.drawable.ic_menu_camera)   // optional
                .error(R.drawable.ic_menu_camera)      // optional
                .resize(350, 350) .centerInside()                  // optional
                .into(btnCapturePicture1);
        Picasso.with(this)
                .load(url1)
                .placeholder(R.drawable.ic_menu_camera)   // optional
                .error(R.drawable.ic_menu_camera)      // optional
                .resize(350, 350) .centerInside()        // optional
                .into(btnCapturePicture2);

        Picasso.with(this)
                .load(url2)
                .placeholder(R.drawable.ic_menu_camera)   // optional
                .error(R.drawable.ic_menu_camera)      // optional
                .resize(350, 350) .centerInside()        // optional
                .into(btnCapturePicture3);

        /**
         * Capture image button click event
         */

        btnCapturePicture1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                choose();
                value = "1";
                imageView = (ImageView) findViewById(R.id.imageView41);
            }
        });
        btnCapturePicture2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                choose();
                value = "2";
                imageView = (ImageView) findViewById(R.id.imageView42);
            }
        });

        btnCapturePicture3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // capture picture
                choose();
                value = "3";
                imageView = (ImageView) findViewById(R.id.imageView43);
            }
        });


        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }
    }
    public void choose()
    {
        CharSequence colors[] = new CharSequence[] {"Take a Photo.", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Photo");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    captureImage();
                }
                else{
                    showFileChooser();
                }
                // the user clicked on colors[which]
            }
        });
        builder.show();
    }
    public String getStringImage(Bitmap bmp){
        if (bmp != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
        else{
            String image = "";
            return image;
        }

    }
    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE || resultCode == RESULT_OK) {
            this.filePath = fileUri;
        }
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            this.filePath = data.getData();
        }
        try {
            if (value == "1") {//Getting the Bitmap from Gallery
                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), this.filePath);
                //Setting the Bitmap to ImageView
                bitmap = bitmap1;
                bitmap11 = getResizedBitmap(bitmap1,800);
            }
            else
            if(value == "2"){
                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), this.filePath);
                bitmap = bitmap2;
                bitmap22 = getResizedBitmap(bitmap2,800);
            }
            else
            if(value == "3")
            {

                bitmap3 = MediaStore.Images.Media.getBitmap(getContentResolver(), this.filePath);
                bitmap = bitmap3;
                bitmap33 = getResizedBitmap(bitmap3,800);
            }

            Bitmap originalBitmap = bitmap;
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                    originalBitmap, 270, 290, false);
            imageView.setImageBitmap(resizedBitmap);}
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();


        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    //  private void launchUploadActivity(boolean isImage){
    // Intent i = new Intent(AddPlace.this, UploadActivity.class);
    //  i.putExtra("filePath", fileUri.getPath());
    //  i.putExtra("isImage", isImage);
    // startActivity(i);
    // }

    /**
     * ------------ Helper Methods ----------------------
     * */

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void getLocation(){locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,200000, 10, this);

        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
        }}

    @Override
    public void onLocationChanged(Location location) {

        double latitude1 = location.getLatitude();
        double longitude1 = location.getLongitude();
        String latitude2 = new Double(latitude1).toString();
        String longitude2 = new Double(longitude1).toString();
        _latitude.setText(latitude2);
        _longitude.setText(longitude2);
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

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
                        Toast.makeText(Updateplace.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(Updateplace.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                int selectedId1=_type.getCheckedRadioButtonId();
                int selectedId2=_status.getCheckedRadioButtonId();

                RadioButton value1 = (RadioButton)findViewById(selectedId1);
                RadioButton value2 = (RadioButton)findViewById(selectedId2);

                String type = value1.getText().toString();
                String area = _area.getText().toString();
                String rate = _rate.getText().toString();
                String description = _description.getText().toString();
                String address = _address.getText().toString();
                String latitude = _latitude.getText().toString();
                String longitude = _longitude.getText().toString();
                String status = value2.getText().toString();
                String image1 = getStringImage(bitmap11);
                String image2 = getStringImage(bitmap22);
                String image3 = getStringImage(bitmap33);

                HashMap<String, String> user = session.getUserDetails();

                String email = user.get(SessionManager.KEY_EMAIL);
                //Getting Image Name

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_placeid, placeid1);
                params.put(KEY_type, type);
                params.put(KEY_area, area);
                params.put(KEY_rate, rate);
                params.put(KEY_description, description);
                params.put(KEY_latitude, latitude);
                params.put(KEY_longitude, longitude);
                params.put(KEY_address, address);
                params.put(KEY_status, status);
                params.put(KEY_IMAGE1, image1);
                params.put(KEY_IMAGE2, image2);
                params.put(KEY_IMAGE3, image3);
                params.put(KEY_EMAIL, email);

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
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public boolean validate() {
        boolean valid = true;

        String area = _area.getText().toString();
        String rate = _rate.getText().toString();
        String description = _description.getText().toString();
        String address = _address.getText().toString();
        String latitude = _latitude.getText().toString();
        String longitude = _longitude.getText().toString();

        if (area.isEmpty()){
            _area.setError("Area is required.");
            valid = false;
        } else if (isNumeric(area) == false) {
            _area.setError("Please enter Numerical value !");
            valid = false;
        }
        else{
            _area.setError(null);
        }

        if (rate.isEmpty()) {
            _rate.setError("Rate is required.");
            valid = false;
        } else if (isNumeric(rate) == false) {
            _rate.setError("Please enter Numerical value !");
            valid = false;
        }
        else {
            _rate.setError(null);
        }
        if (description.isEmpty()) {
            _description.setError("Description of place is required.");
            valid = false;
        }
        else {
            _description.setError(null);
        }
        if (address.isEmpty()) {
            _address.setError("Address of place is required.");
            valid = false;
        }
        else {
            _address.setError(null);
        }



        /*if (!latitude.isEmpty() && isNumeric(latitude) == false) {
            _latitude.setError("Please enter Numerical value !");
            valid = false;}
            else if (!latitude.isEmpty() && longitude.isEmpty() ){
            _longitude.setError("Please enter Longitude also!");

            }
        else{
            _latitude.setError(null);
        }*/
        if (!latitude.isEmpty() && isNumeric(latitude) == false) {
            _latitude.setError("Please enter Numerical value !");
            valid = false;
        }
        else{
            _latitude.setError(null);
        }




        if (!longitude.isEmpty() && isNumeric(longitude) == false) {
            _longitude.setError("Please enter Numerical value !");
            valid = false;
        }
        else if (isNumeric(longitude) == true && latitude.isEmpty() ){
            _latitude.setError("Please enter latitude also!");}
        else if (isNumeric(latitude) == true && longitude.isEmpty() ){
            _longitude.setError("Please enter latitude also!");}

        else{
            _longitude.setError(null);
        }


/*
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if (password.equals(repassword)) {
            _repasswordText.setError(null);
        }
        else {
            _repasswordText.setError("Password you entered don't match.");
            valid = false;
        }*/

        return valid;
    }

}




