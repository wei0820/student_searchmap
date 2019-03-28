package com.student.student_searchmap;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.student.student_searchmap.Data.GoogleMapPlaceDetailsData;
import com.student.student_searchmap.Data.GoogleResponseData;

/**
 * Created by JackPan on 2018/5/26.
 */

public class GoogleMapAPISerive {
    private static final String TAG = "GoogleMapAPISerive";
    static  RequestQueue queue;
    static  GetResponse getResponse;
    public static  final  String  TYPE_CARWASH  = "car_wash";
    public static  final  String  TYPE_CARREPAIR  = "car_repair";
    public static  final  String  TYPE_PARKING  = "parking";
    public static  final  String  TYPE_LATLON = "latlon";
    public static  final  String  TYPE  = "type";
    public static  final  String  TYPE_PLACEID = "place_id";



    public static  void setPlaceForRestaurant(final Context context,String latlon,String type,GetResponse Response){
        queue = Volley.newRequestQueue(context);
        getResponse = Response;
//        latlon = "22.649959, 120.306139";

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latlon+"&radius=500&type="+type+"&key=AIzaSyDeRZ8FEeGk0G9leGjbs316tbFUZu45J3I";
        Log.d(TAG, "setPlaceForRestaurant: "+url);

        StringRequest getRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, "onResponse: "+s);
                        Gson gson = new Gson();
                        GoogleResponseData googleResponseData =gson.fromJson(s, GoogleResponseData.class);
                        if(googleResponseData!=null){
                            Log.d(TAG, "onResponse: "+googleResponseData.status);
                            getResponse.getData(googleResponseData);
                            if (googleResponseData.results.length!=0&&googleResponseData.results!=null){
                                for (GoogleResponseData.Results results : googleResponseData.results) {
                                    Log.d(TAG, "onResponse: "+results.geometry.location.lat);
                                    Log.d(TAG, "onResponse: "+results.geometry.location.lng);

                                    if(results.photos!=null){
                                        for (GoogleResponseData.Results.Photos photo : results.photos) {
                                            Log.d(TAG, "photos: "+photo.photo_reference);
//                                            getPhotos(context,photo.photo_reference);
                                        }

                                    }

                                }
                            }
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "onErrorResponse: "+volleyError.getMessage());

                    }
                });
        queue.add(getRequest);
    }


    public  static  String getPhotos(Context context,String photo){
        String url ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+photo+"&key=AIzaSyDeRZ8FEeGk0G9leGjbs316tbFUZu45J3I";

        return  url;
    }
    public static  void nextPage(final Context context,String nextpage,GetResponse Response){
        queue = Volley.newRequestQueue(context);
        getResponse = Response;
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyDeRZ8FEeGk0G9leGjbs316tbFUZu45J3I&pagetoken="+nextpage;
        Log.d(TAG, "nextPage: "+url);

        StringRequest getRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, "onResponse: "+s);
                        Gson gson = new Gson();
                        GoogleResponseData googleResponseData =gson.fromJson(s, GoogleResponseData.class);
                        if(googleResponseData!=null){
                            Log.d(TAG, "onResponse: "+googleResponseData.status);
                            getResponse.getData(googleResponseData);
                            if (googleResponseData.results.length!=0&&googleResponseData.results!=null){
                                for (GoogleResponseData.Results results : googleResponseData.results) {
                                    Log.d(TAG, "onResponse: "+results.geometry.location.lat);
                                    Log.d(TAG, "onResponse: "+results.geometry.location.lng);
                                    if(results.photos!=null){
                                        for (GoogleResponseData.Results.Photos photo : results.photos) {
                                            Log.d(TAG, "photos: "+photo.photo_reference);
//                                            getPhotos(context,photo.photo_reference);
                                        }

                                    }

                                }
                            }
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "onErrorResponse: "+volleyError.getMessage());

                    }
                });
        queue.add(getRequest);

    }

    public static  void getPlaceDeatail(final Context context,String id ,GetResponse Response){
        queue = Volley.newRequestQueue(context);
        getResponse = Response;

        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+id+"&key=AIzaSyDeRZ8FEeGk0G9leGjbs316tbFUZu45J3I";
        Log.d(TAG, "setPlaceForRestaurant: "+url);

        StringRequest getRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d(TAG, "onResponse: "+s);
                        Gson gson = new Gson();
                        GoogleMapPlaceDetailsData googleMapPlaceDetailsData =gson.fromJson(s,GoogleMapPlaceDetailsData.class);
                        if (googleMapPlaceDetailsData!=null){
                            getResponse.getDetailData(googleMapPlaceDetailsData);

                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d(TAG, "onErrorResponse: "+volleyError.getMessage());

                    }
                });
        queue.add(getRequest);
    }

    public interface  GetResponse{
        void  getData(GoogleResponseData googleResponseData);
        void getDetailData(GoogleMapPlaceDetailsData googleMapPlaceDetailsData);

    }

}
