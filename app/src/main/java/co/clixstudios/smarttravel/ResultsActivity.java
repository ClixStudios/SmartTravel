package co.clixstudios.smarttravel;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ResultsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mMapFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + MainActivity.endLocationToResults);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLocation));

        mMapFragment.getMapAsync(this);

        //requestData("https://maps.googleapis.com/maps/api/geocode/json?address=Hebden+Bridge&key=AIzaSyDcs7z294l_CVyhJe_j1GIIAtu1-CB-R00");

        int price =  MainActivity.finalResult;
        double finalPrice = (double) price;
        finalPrice /= 100;
        String strFinalPrice = Double.toString(finalPrice);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
        System.out.println(formatter.format(finalPrice));

        TextView textView1 = (TextView) findViewById(R.id.TEXT);
        if (textView1 != null) {
            textView1.setText(formatter.format(finalPrice));
        }

    }



    private void requestData(String uri) {

        StringRequest request = new StringRequest(uri,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


//                        extractData(response);

//                        if (findDistance(response) == 0){
//
//                        } else {
//                            calculateFinalResult(URLResponse, MPG);
//                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResultsActivity.this, "There was a Network error, try again.", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

//    public LatLng extractData(String response) {
//
//        int fromLatIndex;
//        int toLatIndex;
//        int fromLngIndex;
//        int toLngIndex;
//        double lat;
//        double lng;
//        String Latresult;
//        String Lngresult;
//        String safeLat;
//        String safeLng;
//
//        fromLatIndex = response.indexOf("\"location\" : {");
//        fromLatIndex = fromLatIndex + 38;
//        toLatIndex = fromLatIndex + 9;
//        Latresult = response.substring(fromLatIndex, toLatIndex);
//
//        fromLngIndex = response.indexOf("\"lng\" : ", fromLatIndex);
//        fromLngIndex = fromLngIndex + 8;
//        toLngIndex = fromLngIndex + 9;
//        Lngresult = response.substring(fromLngIndex, toLngIndex);
//
//        safeLat = Latresult.replaceAll("[^\\d-]+", "");
//        safeLng = Lngresult.replaceAll("[^\\d-]+", "");
//
//
//
//        double dblLat = Double.parseDouble(safeLat);
//        double dblLng = Double.parseDouble(safeLng);
//
//        globalLat = dblLat;
//        globalLng = dblLng;
//
//        Toast.makeText(ResultsActivity.this, "The Lat is " + globalLat, Toast.LENGTH_SHORT).show();
//        Toast.makeText(ResultsActivity.this, "The Lng is " + globalLng, Toast.LENGTH_SHORT).show();
//

//
//
//        return latlng;
//    }


    @Override
    public void onMapReady(GoogleMap map) {
        //DO WHATEVER YOU WANT WITH GOOGLEMAP
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);

        LatLng latlng = new LatLng(51.507308, -0.127659);

        map.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        map.getUiSettings().setZoomControlsEnabled(true);
    }


}
