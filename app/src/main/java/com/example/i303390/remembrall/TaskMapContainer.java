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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.i303390.remembrall.db.TrackGPS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_map_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
                                onNavigationItemSelected(menuItem);
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
            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

        } else {
            gps.showSettingsAlert();
        }
        LatLng currentLocation;
        LatLng Indranagar = new LatLng(12.971891, 77.641151);
        LatLng kormangala = new LatLng(12.9279, 77.6271);
        currentLocation = new LatLng(latitude,longitude);

        NotificationActivity.openNotification(this, "Try new Beer in Vapours", Indranagar.latitude + "", Indranagar.longitude + "");

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(12.9697, 77.6410)).title("Try new Beer in Vapours"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(12.9705, 77.6455)).title("Have Breakfast in Monkey Bar"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        gps.stopUsingGPS();
    }
}




