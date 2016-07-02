package com.aashish.dell.combathunger;

/**
 * Created by Dell on 07-Mar-16.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.aashish.dell.combathunger.R;
import com.aashish.dell.combathunger.adapter.CustomListAdapter;
import com.aashish.dell.combathunger.model.VacantPlaces;
import com.aashish.dell.combathunger.model.config;
import com.aashish.dell.combathunger.volleycustomlistview.AppController;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;


public class OneFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = OneFragment.class.getSimpleName();
    private SwipeRefreshLayout swipeLayout;
    private static final String url = "http://192.168.43.209/fypbakckend/vacantrooms.php";
    //private static final String url = "http://172.20.0.59/test.php";
    private ProgressDialog pDialog;
    private List<VacantPlaces> feedList = new ArrayList<VacantPlaces>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag1 = inflater.inflate(R.layout.fragment_one, container, false);

        swipeLayout = (SwipeRefreshLayout) frag1.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);

        listView = (ListView) frag1.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        swipeLayout.setRefreshing(true);
        if (feedList.isEmpty() == true) {
            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");


            pDialog.show();
        }
        task();

        return frag1;
    }
    public void task(){


        swipeLayout.setRefreshing(true);
        if (feedList.isEmpty() == true) {
            // Creating volley request obj

            JsonArrayRequest getPlaces = new JsonArrayRequest(url,

                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            Log.d(TAG, response.toString());
                            hidePDialog();
                            swipeLayout.setRefreshing(false);
                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    VacantPlaces vacantPlaces = new VacantPlaces();
//                                    vacantPlaces.setPlaceid(obj.getString("placeid"));
//
//                                    vacantPlaces.setArea(obj.getInt("area"));
//                                    vacantPlaces.setRate(obj.getInt("rate"));
                                    vacantPlaces.setLocation("obj.getString()");
//                                    vacantPlaces.setDescription(obj.getString("description"));
////                                    vacantPlaces.setLatitude(obj.getDouble("latitude"));
////                                    vacantPlaces.setLongitude(obj.getDouble("longitude"));
//                                    vacantPlaces.setDate(obj.getString("date"));
//                                    vacantPlaces.setType(obj.getString("type"));
//                                    vacantPlaces.setaccountid(obj.getString("accountid"));
//
                                    Log.i("LOG_TAG", ""+obj.getString("accountid"));

                                    JSONArray jsonArray = obj.getJSONArray("images");


                                    if (jsonArray != null) {
                                        for (int j = 0; j < jsonArray.length(); j++) {
                                            JSONObject ob = jsonArray.getJSONObject(j);

                                            if (j == 0) {
                                                vacantPlaces.setThumbnailUrl(ob.getString("url"));


                                                //myplaces.setThumbnailUrl(ul);
                                                Log.i("LOG_TAG", ""+jsonArray.length()+",");
                                                //loadImage(btnCapturePicture1, image0);

                                            }
                                            if (j == 1) {

                                                vacantPlaces.setThumbnailUrl1(ob.getString("url"));
                                                //myplaces.setThumbnailUrl(ul);
                                            }
                                            if (j == 2) {

                                                vacantPlaces.setThumbnailUrl2(ob.getString("url"));
                                            }

                                        }
                                    }

                                    feedList.add(vacantPlaces);
                                    //                            hidePDialog();
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
                    // hidePDialog();
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(getPlaces);
        }
        else{
            swipeLayout.setRefreshing(false);

        }adapter = new CustomListAdapter(getActivity(), feedList);
        listView.setAdapter(adapter);
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
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {

        //  Log.i("HelloListView", "You clicked Item: " + id + " at position:" + studentList.get(position));
        // studentList.get(position);       // Then you start a new Activity via Intent

//        String mytab = feedList.get(position).getPlaceid();
//
//        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position + "name:" + mytab);
//        String placeid = feedList.get(position).getPlaceid();
//        String accountid = feedList.get(position).getaccountid();
//        String type = feedList.get(position).getType();
//        double area = feedList.get(position).getArea();
//        double rate = feedList.get(position).getRate();
//        String description = feedList.get(position).getDescription();
//        String address = feedList.get(position).getLocation();
//        Double latitude = feedList.get(position).getLatitude();
//        Double longitude = feedList.get(position).getLongitude();
//        String status = feedList.get(position).getStatus();
//        String url0 = feedList.get(position).getThumbnailUrl();
//        String url1 = feedList.get(position).getThumbnailUrl1();
//        String url2 = feedList.get(position).getThumbnailUrl2();
//
//        //passing all the data once retrived from server to next intent saving the loading loading time.
//
//        final Intent intent = new Intent();
//        intent.setClass(this.getActivity(), PlaceDetail.class);
//        Log.i(TAG, "onItemClick: " + placeid + "     " + area);
//        intent.putExtra("placeid", placeid);
//        intent.putExtra("accountid", accountid);
//        intent.putExtra("type", type);
//        intent.putExtra("area", area);
//        intent.putExtra("rate", rate);
//        intent.putExtra("description", description);
//        intent.putExtra("address", address);
//        intent.putExtra("latitude", latitude);
//        intent.putExtra("longitude", longitude);
//        intent.putExtra("status", status);
//        intent.putExtra("url0", url0);
//        intent.putExtra("url1", url1);
//        intent.putExtra("url2", url2);
//        Log.i(TAG, "onItemClick-----------: "+accountid);
//        startActivity(intent);
    }

    //Swipe refresh starts this method below.
    @Override
    public void onRefresh() {
        Log.i("LOG_TAG", "onRefresh called from SwipeRefreshLayout");
        feedList.clear();
        task();
    }

}


