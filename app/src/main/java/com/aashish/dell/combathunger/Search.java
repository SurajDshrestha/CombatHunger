//package com.aashish.dell.combathunger;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
///**
// * Created by Dell on 07-Mar-16.
// */
//
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//
//import com.aashish.dell.combathunger.model.Searchtext;
//import com.aashish.dell.combathunger.model.VacantPlaces;
//import com.aashish.dell.combathunger.model.config;
//import com.aashish.dell.combathunger.volleycustomlistview.AppController;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonArrayRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Search extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
//
//    private static final String TAG = Search.class.getSimpleName();
//    private static final String Url = config.Localhost+"/places.php?query=";
//    private ProgressDialog pDialog;
//    private List<VacantPlaces> myPlaces = new ArrayList<VacantPlaces>();
//    private ListView listView;
//    private ListAdapter_myplaces adapter;
//    SessionManager session;
//    private String query;
//    private SwipeRefreshLayout swipeLayout;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        View frag1 = inflater.inflate(R.layout.search_room, container, false);
//
//        swipeLayout = (SwipeRefreshLayout) frag1.findViewById(R.id.swipe_container);
//        swipeLayout.setOnRefreshListener(this);
//
//        listView = (ListView) frag1.findViewById(R.id.list);
//        listView.setOnItemClickListener(this);
//
//        adapter = new ListAdapter_myplaces(this.getActivity(), myPlaces);
//        listView.setAdapter(adapter);
//        swipeLayout.setRefreshing(true);
//        // pDialog = new ProgressDialog(this);
////        // Showing progress dialog before making http request
////        pDialog.setMessage("Loading...");
////        pDialog.show();
////
//
//        if (query != Searchtext.getsearchtext().toString() || query == null){
//
//            query = Searchtext.getsearchtext().toString();
//            myPlaces.clear();
//            search();
//
//        }else if (query == Searchtext.getsearchtext().toString()){
//        search();}
//
//
//
//        return frag1;
//    }
//
//
//
//
//
//
//    private void search() {
//        swipeLayout.setRefreshing(true);
//        if (myPlaces.isEmpty() == true) {
////
////        pDialog = new ProgressDialog(this);
////        // Showing progress dialog before making http request
////        pDialog.setMessage("Loading...");
////        pDialog.show();
//
//            String url = Url + query;
//            // Creating volley request obj
//            JsonArrayRequest myplaces = new JsonArrayRequest(url,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            Log.d(TAG, response.toString());
//                            //    hidePDialog();
//                            swipeLayout.setRefreshing(false);
//
//                            // Parsing json
//                            for (int i = 0; i < response.length(); i++) {
//                                try {
//
//                                    JSONObject obj = response.getJSONObject(i);
//                                    VacantPlaces myplaces = new VacantPlaces();
//                                    myplaces.setPlaceid(obj.getString("placeid"));
//
//                                    myplaces.setArea(obj.getInt("area"));
//                                    myplaces.setRate(obj.getInt("rate"));
//                                    myplaces.setLocation(obj.getString("location"));
//                                    myplaces.setDescription(obj.getString("description"));
//                                    myplaces.setLatitude(obj.getDouble("latitude"));
//                                    myplaces.setLongitude(obj.getDouble("longitude"));
//                                    myplaces.setDate(obj.getString("date"));
//                                    myplaces.setType(obj.getString("type"));
//                                    myplaces.setaccountid(obj.getString("accountid"));
////
//                                    Log.i("LOG_TAG", ""+obj.getString("type"));
//
//                                    JSONArray jsonArray = obj.getJSONArray("images");
//
//
//                                    if (jsonArray != null) {
//                                        for (int j = 0; j < jsonArray.length(); j++) {
//                                            JSONObject ob = jsonArray.getJSONObject(j);
//
//                                            if (j == 0) {
//                                                myplaces.setThumbnailUrl(ob.getString("url"));
//
//
//                                                //myplaces.setThumbnailUrl(ul);
//                                                Log.i("LOG_TAG", ""+jsonArray.length()+",");
//                                                //loadImage(btnCapturePicture1, image0);
//
//                                            }
//                                            if (j == 1) {
//
//                                                myplaces.setThumbnailUrl1(ob.getString("url"));
//                                                //myplaces.setThumbnailUrl(ul);
//                                            }
//                                            if (j == 2) {
//
//                                                myplaces.setThumbnailUrl2(ob.getString("url"));
//                                            }
//
//                                        }
//                                    }
//
//
//
//                                    // adding Billionaire to worldsBillionaires array
//                                    myPlaces.add(myplaces);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//
//                            // notifying list adapter about data changes
//                            // so that it renders the list view with updated data
//                            adapter.notifyDataSetChanged();
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//
//                }
//            });
//
//            // Adding request to request queue
//            AppController.getInstance().addToRequestQueue(myplaces);
//
//        }
//        else{swipeLayout.setRefreshing(false);
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        hidePDialog();
//    }
//
//    private void hidePDialog() {
//        if (pDialog != null) {
//            pDialog.dismiss();
//            pDialog = null;
//        }
//    }
//    @Override
//    public void onRefresh() {
//        Log.i("LOG_TAG", "onRefresh called from SwipeRefreshLayout");
//        myPlaces.clear();
//        search();
//    }
//
//    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
//        final String placeid = myPlaces.get(position).getPlaceid();
//        String accountid = myPlaces.get(position).getaccountid();
//        String type = myPlaces.get(position).getType();
//        double area = myPlaces.get(position).getArea();
//        double rate = myPlaces.get(position).getRate();
//        String description = myPlaces.get(position).getDescription();
//        String address = myPlaces.get(position).getLocation();
//        Double latitude = myPlaces.get(position).getLatitude();
//        Double longitude = myPlaces.get(position).getLongitude();
//        String status = myPlaces.get(position).getStatus();
//        String url0 = myPlaces.get(position).getThumbnailUrl();
//        String url1 = myPlaces.get(position).getThumbnailUrl1();
//        String url2 = myPlaces.get(position).getThumbnailUrl2();
//        final Intent intent = new Intent();
//      //  intent.setClass(this.getActivity(), PlaceDetail.class);
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
//
//        //  Log.i("HelloListView", "You clicked Item: " + id + " at position:" + studentList.get(position));
//        // studentList.get(position);       // Then you start a new Activity via Intent
//
//                                startActivity(intent);
//
//        //Intent intent = new Intent();
//
//        //intent.setClass(this, Updateplace.class);
//        //intent.putExtra("placeid", placeid);
//
//        //startActivity(intent);
//    }
//
//}
//
//
//
//
