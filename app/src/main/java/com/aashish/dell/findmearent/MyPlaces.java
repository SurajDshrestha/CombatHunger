package com.aashish.dell.findmearent;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.aashish.dell.findmearent.adapter.CustomListAdapter;
import com.aashish.dell.findmearent.adapter.ListAdapter_myplaces;
import com.aashish.dell.findmearent.model.VacantPlaces;
import com.aashish.dell.findmearent.model.config;
import com.aashish.dell.findmearent.volleycustomlistview.AppController;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell on 03-Apr-16.
 */
public class MyPlaces extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = MyPlaces.class.getSimpleName();

    private static final String Url = config.Localhost+"/myplaces.php?email=";
    private static final String url2 = config.Localhost+"/deleteplace.php";
    private ProgressDialog pDialog;
    private List<VacantPlaces> myPlaces = new ArrayList<VacantPlaces>();
    private ListView listView;
    private ListAdapter_myplaces adapter;
    private String KEY_placeid = "placeid";
    SessionManager session;
    private String email;
    private SwipeRefreshLayout swipeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myplaces);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        adapter = new ListAdapter_myplaces(this, myPlaces);
        listView.setAdapter(adapter);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        task();
    }
    private void task() {
        swipeLayout.setRefreshing(true);
        if (myPlaces.isEmpty() == true) {
//
//        pDialog = new ProgressDialog(this);
//        // Showing progress dialog before making http request
//        pDialog.setMessage("Loading...");
//        pDialog.show();

            String url = Url + email;
            // Creating volley request obj
            JsonArrayRequest myplaces = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                        //    hidePDialog();
                            swipeLayout.setRefreshing(false);

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    VacantPlaces myplaces = new VacantPlaces();
                                    myplaces.setPlaceid(obj.getString("placeid"));

                                    myplaces.setArea(obj.getInt("area"));
                                    myplaces.setRate(obj.getString("rate"));
                                    myplaces.setLocation(obj.getString("location"));
                                    myplaces.setDescription(obj.getString("description"));
                                    myplaces.setLatitude(obj.getDouble("latitude"));
                                    myplaces.setLongitude(obj.getDouble("longitude"));
                                    myplaces.setDate(obj.getString("date"));
                                    myplaces.setType(obj.getString("type"));
//
                                    Log.i("LOG_TAG", ""+obj.getString("type"));

                                    JSONArray jsonArray = obj.getJSONArray("images");


                                    if (jsonArray != null) {
                                            for (int j = 0; j < jsonArray.length(); j++) {
                                                JSONObject ob = jsonArray.getJSONObject(j);

                                                if (j == 0) {
                                                    myplaces.setThumbnailUrl(ob.getString("url"));


                                                     //myplaces.setThumbnailUrl(ul);
                                                    Log.i("LOG_TAG", ""+jsonArray.length()+",");
                                        //loadImage(btnCapturePicture1, image0);

                                                }
                                                if (j == 1) {

                                                    myplaces.setThumbnailUrl1(ob.getString("url"));
                                                    //myplaces.setThumbnailUrl(ul);
                                                }
                                                if (j == 2) {

                                                    myplaces.setThumbnailUrl2(ob.getString("url"));
                                                }

                                                }
                                        }



                                    // adding Billionaire to worldsBillionaires array
                                    myPlaces.add(myplaces);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(myplaces);

        }
        else{swipeLayout.setRefreshing(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
    @Override
    public void onRefresh() {
        Log.i("LOG_TAG", "onRefresh called from SwipeRefreshLayout");
        myPlaces.clear();
        task();
    }

    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        final String placeid = myPlaces.get(position).getPlaceid();
        String type = myPlaces.get(position).getType();
        double area = myPlaces.get(position).getArea();
        String rate = myPlaces.get(position).getRate();
        String description = myPlaces.get(position).getDescription();
        String address = myPlaces.get(position).getLocation();
        Double latitude = myPlaces.get(position).getLatitude();
        Double longitude = myPlaces.get(position).getLongitude();
        String status = myPlaces.get(position).getStatus();
        String url0 = myPlaces.get(position).getThumbnailUrl();
        String url1 = myPlaces.get(position).getThumbnailUrl1();
        String url2 = myPlaces.get(position).getThumbnailUrl2();
        final Intent intent = new Intent();
        intent.setClass(this, Updateplace.class);
        intent.putExtra("placeid", placeid);
        intent.putExtra("type", type);
        intent.putExtra("area", area);
        intent.putExtra("rate", rate);
        intent.putExtra("description", description);
        intent.putExtra("address", address);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("status", status);
        intent.putExtra("url0", url0);
        intent.putExtra("url1", url1);
        intent.putExtra("url2", url2);

        //  Log.i("HelloListView", "You clicked Item: " + id + " at position:" + studentList.get(position));
        // studentList.get(position);       // Then you start a new Activity via Intent

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("What would you like to do?");
            alertDialogBuilder
                   // .setMessage("Click yes to exit!")
                    .setCancelable(true)
                    .setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //moveTaskToBack(true);
                                    startActivity(intent);
                                    Log.i("HelloListView", "You clicked Update: " + id + " at position:"+ placeid);
                                    dialog.cancel();
                                }
                            })

                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String url2 = config.Localhost+"/deleteplace.php";
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("Success")) {
                                                Toast.makeText(MyPlaces.this, response, Toast.LENGTH_LONG).show();

                                            }
                                            else {
                                                Toast.makeText(MyPlaces.this, response, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(MyPlaces.this, error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put(KEY_placeid, placeid);
                                    Log.i(TAG, "getParams: "+ placeid+"");

                                    return map;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                            requestQueue.add(stringRequest2);



                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        //Intent intent = new Intent();

        //intent.setClass(this, Updateplace.class);
        //intent.putExtra("placeid", placeid);

        //startActivity(intent);
    }

}

