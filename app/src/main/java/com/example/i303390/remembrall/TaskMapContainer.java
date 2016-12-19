package com.example.i303390.remembrall;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.i303390.remembrall.POJO.LocationListJson;
import com.example.i303390.remembrall.backgroundService.JsonMethods;
import com.example.i303390.remembrall.backgroundService.VolleyCallbackBackground;
import com.example.i303390.remembrall.db.TrackGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

public class TaskMapContainer extends AppCompatActivity implements OnMapReadyCallback {
    private static final int ACCESS_FINE_LOCATION_CODE = 23;
    private GoogleMap mMap;
    private TrackGPS gps;
    double latitude;
    double longitude;
    private NavigationView mNavigationView;
    Toolbar toolbar;
    DrawerLayout androidDrawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    float COLOURS[] = {HUE_AZURE,HUE_BLUE,HUE_CYAN,HUE_GREEN,HUE_MAGENTA,HUE_ORANGE,HUE_YELLOW,HUE_ROSE,HUE_VIOLET};
    Map<String,Float> idMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_map_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        idMap = new HashMap<>();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.profile :
                                openProfilePage();
                                return true;
                            case R.id.newtask:
                                openAddTask();
                                return true;
                            case R.id.tasklist:
                                openTaskList();
                                return true;
                            default:
//                                onNavigationItemSelected(menuItem);
                                return true;

                        }
                    }
                });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        check if location permissions are already available
        if(checkLocationPermissions()){
            Toast.makeText(TaskMapContainer.this,"You already have location permissions",Toast.LENGTH_LONG).show();
        }else{
            requestLocationPermissions();
        }
        initInstancesDrawer();
    }
    private void initInstancesDrawer() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        androidDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(TaskMapContainer.this, androidDrawerLayout, R.string.app_name, R.string.app_name);
        androidDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }




//    protected void onStart(){
//        if(checkLocationPermissions()){
//            Toast.makeText(TaskMapContainer.this,"You already have location permissions",Toast.LENGTH_LONG).show();
//        }else{
//            requestLocationPermissions();
//        }
//    }

    private boolean checkLocationPermissions(){
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestLocationPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
//            explain why you need the location permissions
        }
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_FINE_LOCATION_CODE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == ACCESS_FINE_LOCATION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission granted to access location",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                openProfilePage();
                return true;
            case R.id.action_new_task:
                openAddTask();
                return true;
            case R.id.notify:
                ServiceHandler services = new ServiceHandler();
                Toast.makeText(TaskMapContainer.this,services.getTasks(this).toString(),Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_task_list:
                openTaskList();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openTaskList() {
        Intent tasksListIntent = new Intent(this, TasksList.class);
        startActivity(tasksListIntent);
    }

    public void openAddTask() {
        Intent addTaskIntent = new Intent(this, MainActivity.class);
        startActivity(addTaskIntent);
    }

    public void openProfilePage() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(gps != null){
        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
        } else {
            gps.showSettingsAlert();
        }
        LatLng currentLocation = new LatLng(latitude,longitude);
        mMap.clear();
//        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
        setMapData(currentLocation);
        }}

/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gps = new TrackGPS(TaskMapContainer.this);

        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
//            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

        } else {
            gps.showSettingsAlert();
        }
        LatLng currentLocation;
        currentLocation = new LatLng(latitude,longitude);
        setMapData(currentLocation);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        gps.stopUsingGPS();
    }

    private void setMapData(LatLng currentLocation){
        ServiceHandler.getLocations(this, currentLocation, new VolleyCallbackBackground() {
            @Override
            public void onSuccess(String result) {
                List<LocationListJson> locationListJson = new ArrayList<>();
                ArrayList responseList = JsonMethods.getOfromJSON(result,ArrayList.class);
                locationListJson.addAll(JsonMethods.getAllLocation(responseList));
                for (LocationListJson item:locationListJson) {

                    Log.i(item.getName(), (new Date(System.currentTimeMillis())).toString());
                    LatLng marker = new LatLng(item.getLatitude(),item.getLongitude());
                    mMap.addMarker(getMarkerOptions(marker,item.getKeyword(),item.getID()));
                }
            }

            @Override
            public void onError(String error) {

                Log.i("service call error: " + error, (new Date(System.currentTimeMillis())).toString());

            }
        });

        mMap.addMarker(getMarkerOptions(currentLocation,"You are here",null));
    }

    private MarkerOptions getMarkerOptions(LatLng position,String title,String taskID){
        MarkerOptions mo = new MarkerOptions().position(position).title(title).icon(BitmapDescriptorFactory
                .defaultMarker(getColor(taskID)));
        return mo;
    }


    public Float getColor(String id){
        if(id != null){
        if(idMap == null ){
            idMap = new HashMap<>();
        }else {
            Float color = idMap.get(id);
            if(color == null){
                int col = new Random().nextInt(9)%8;
                color = COLOURS[col];
                idMap.put(id,color);
            }
            return color;
        }}
        return HUE_RED;
    }
}




