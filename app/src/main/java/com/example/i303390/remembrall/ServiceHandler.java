package com.example.i303390.remembrall;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.i303390.remembrall.POJO.LocationListJson;
import com.example.i303390.remembrall.POJO.TaskListJson;
import com.example.i303390.remembrall.backgroundService.VolleyCallbackBackground;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by I303390 on 12/15/2016.
 */

public class ServiceHandler {

    public List<LocationListJson> getLocations(Context c, LatLng latlang) {

        final List<LocationListJson> locationListJson = new ArrayList<LocationListJson>();
        RequestQueue queue = Volley.newRequestQueue(c);
        String url ="https://rsmsandboxxe47fe16d.neo.ondemand.com/sap/LBR/GeoReminder/GeoApp/services/apiTestNew.xsjs?$format=json&lat="+latlang.latitude+"&long="+latlang.longitude;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //create the response JSON
                        ArrayList responseList = getOfromJSON(response,ArrayList.class);
                        locationListJson.addAll(getAllLocation(responseList));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText(error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return locationListJson;
    }

    private ArrayList getAllLocation(ArrayList responseList) {
        ArrayList list = new ArrayList();
        for(Object obj : responseList){
            LinkedHashMap map = (LinkedHashMap) obj;
            LocationListJson json = new LocationListJson();
            json.setID((String)map.get("ID"));
            json.setKeyword((String)map.get("Keyword"));
            json.setGoogleID((String)map.get("GoogleID"));
            json.setLatitude((Double) map.get("latitude"));
            json.setLongitude((Double)map.get("longitude"));
            json.setName((String)map.get("name"));
            json.setVicinity((String)map.get("vicinity"));
            json.setIcon((String)map.get("icon"));

            list.add(json);
        }
        return list;
    }

    public List<TaskListJson> getTasks(Context c) {
        RequestQueue queue = Volley.newRequestQueue(c);
        String url ="https://rsmsandboxxe47fe16d.neo.ondemand.com/sap/LBR/GeoReminder/GeoApp/services/getTask.xsjs";

        final List<TaskListJson> taskListJson = new ArrayList<TaskListJson>();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //create the response JSON
                        ArrayList responseList = getOfromJSON(response,ArrayList.class);
                        taskListJson.addAll(getAllTasks(responseList));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText(error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return taskListJson;
    }

    private ArrayList getAllTasks(ArrayList responseList) {
        ArrayList list = new ArrayList();
        for(Object obj : responseList){
            LinkedHashMap map = (LinkedHashMap) obj;
            TaskListJson json = new TaskListJson();
            json.setID((String)map.get("ID"));
            json.setTask((String)map.get("Task"));
            list.add(json);
        }
        return list;
    }

    public void postTask(Context context,String task){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = "https://rsmsandboxxe47fe16d.neo.ondemand.com/sap/LBR/GeoReminder/GeoApp/services/InsertText.xsjs";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("text", task);
            final String mRequestBody = jsonBody.toString();

            StringRequest postRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
//                    mTextView.setText("Response is: "+ response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
//                    mTextView.setText(error.getMessage());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(postRequest);
        } catch (JSONException e) {
            Log.e("JSON",e.toString());
        }
    }

    public void deleteTask(Context context,String taskID){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://rsmsandboxxe47fe16d.neo.ondemand.com/sap/LBR/GeoReminder/GeoApp/services/deleteText.xsjs?&ID="+taskID;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO do something
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText(error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private <T> T getOfromJSON(String json,Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JodaModule());
            return objectMapper.readValue(json,clazz);
        } catch (Exception e) {

        }
        return null;
    }


    public static void getLocations(Context c, LatLng latlang, final VolleyCallbackBackground callback) {

        final List<LocationListJson> locationListJson = new ArrayList<LocationListJson>();
        RequestQueue queue = Volley.newRequestQueue(c);
        String url ="https://rsmsandboxxe47fe16d.neo.ondemand.com/sap/LBR/GeoReminder/GeoApp/services/apiTestNew.xsjs?$format=json&lat="+latlang.latitude+"&long="+latlang.longitude;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //create the response JSON
                        //ArrayList responseList = getOfromJSON(response,ArrayList.class);
                        //locationListJson.addAll(getAllLocation(responseList));
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText(error.getMessage());
                callback.onError(error.getStackTrace().toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
