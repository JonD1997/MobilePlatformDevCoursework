package labstuff.gcu.me.org.mobileplatformdevelopmentcoursework;

//
// Name                 Jon Doherty
// Student ID           S1514958
// Programme of Study   Computing
//

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    ArrayList<Earthquake> quakesMapped = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        quakesMapped = (ArrayList<Earthquake>) getIntent().getSerializableExtra("Earthquake");

        initMap();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng marker = new LatLng(53.982188, -2.116653);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 5));

        for (int i = 0; i < quakesMapped.size(); i++) {
            displayMarker(quakesMapped.get(i).getGeolat(), quakesMapped.get(i).getGeolong(), quakesMapped.get(i).getLocation(), quakesMapped.get(i).getMagnitude());
        }
    }


    protected Marker displayMarker(double lat, double lng, String location, double mag) {
        return myMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .anchor(0.5f, 0.5f)
                .title(location)
                .snippet("Magnitude: " + mag));
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mapsmenu, menu);

        MenuItem backbutton = menu.findItem(R.id.back);
        return true;
    }

    public void Back (MenuItem menuItem){
        onBackPressed();
    }
}
