package com.example.i303390.remembrall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TaskMapContainer extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_map_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.map_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_profile:
                openProfilePage();
                return true;
            case R.id.action_new_task:
                openAddTask();
                return true;
            case R.id.action_task_list:
                openTaskList();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openTaskList(){
        Intent tasksListIntent = new Intent(this, TasksList.class);
        startActivity(tasksListIntent);
    }

    public void openAddTask(){
        Intent addTaskIntent = new Intent(this, MainActivity.class);
        startActivity(addTaskIntent);
    }

    public void openProfilePage(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GPSTracker gps;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        gps = new GPSTracker(TaskMapContainer.this);
//        if(gps.canGetLocation()){

//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//            LatLng currentLocation  = new LatLng(latitude,longitude);
        LatLng currentLocation;
            LatLng Indranagar = new LatLng(12.971891, 77.641151);
        LatLng kormangala = new LatLng(12.9279,77.6271);
        currentLocation = Indranagar;

        NotificationActivity.openNotification(this,"Try new Beer in Vapours",Indranagar.latitude+"",Indranagar.longitude+"");

        mMap.addMarker(new MarkerOptions().position(Indranagar).title("You are here"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(12.9697,77.6410)).title("Try new Beer in Vapours"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(12.9705,77.6455)).title("Have Breakfast in Monkey Bar"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(Indranagar));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Indranagar,15f));
    }


//            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker"));
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//        }else{
//            // can't get location
//            // GPS or Network is not enabled
//            // Ask user to enable GPS/network in settings
//            gps.showSettingsAlert();
//        }



    }



