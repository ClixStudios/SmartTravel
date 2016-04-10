package co.clixstudios.smarttravel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    public String startLocation;
    public String endLocation;
    public boolean isCustomMPG = false;
    public double URLResponse;
    public final int petrolPrice = 481;
    public final int dieselPrice = 477;
    public boolean isPetrol;
    public int MPG;
    public static int finalResult;
    public static String endLocationToResults;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //This code is executed on creation of the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initSpinners();
        RadioButton radioPetrol = (RadioButton) findViewById(R.id.radioButton1);
        if (radioPetrol != null) {
            radioPetrol.setChecked(true);
        }
        isPetrol = true;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    boolean failed = false;
                    initInputs();

                    if (checkInput(startLocation)) {
                        Toast.makeText(MainActivity.this, "Start Location is empty", Toast.LENGTH_SHORT).show();
                        failed = true;
                    }
                    if(ValidateInput(startLocation)) {
                        Toast.makeText(MainActivity.this, "Start Location contains non-alphanumeric character", Toast.LENGTH_SHORT).show();
                        failed = true;
                    }
                    if (checkInput(endLocation)) {
                        Toast.makeText(MainActivity.this, "End Location is empty", Toast.LENGTH_SHORT).show();
                        failed = true;
                    }
                    if (ValidateInput(endLocation)) {
                        Toast.makeText(MainActivity.this, "End Location contains non-alphanumeric character", Toast.LENGTH_SHORT).show();
                        failed = true;
                    }
                    if (MPG == 0){
                        Toast.makeText(MainActivity.this, "Enter a car using the drop down, or a custom MPG using the checkbox", Toast.LENGTH_SHORT).show();
                        failed = true;
                    }
                    else {

                    }
                    if (!failed) {
                        if (isOnline()) {
                            requestData(constructURL(startLocation, endLocation));
                        } else {
                            Toast.makeText(MainActivity.this, "Network is not available", Toast.LENGTH_SHORT).show();
                        }
                    }














                }
            });
        }
    }

    private int calculateFinalResult(double dblDistance, int MPG) {
        int fuelPrice;
        Double firstStage;
        Double finalStage;

        Double realDistance;
        if (isPetrol){
            fuelPrice = petrolPrice;
        } else {
            fuelPrice = dieselPrice;

        }

        realDistance = dblDistance / 1.609347;
        Toast.makeText(MainActivity.this, "The Distance is " + realDistance, Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "The MPG is " + MPG, Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "The Price is " + fuelPrice, Toast.LENGTH_SHORT).show();

        firstStage = realDistance / MPG;
        finalStage = firstStage * fuelPrice;

        finalResult = finalStage.intValue();

        Toast.makeText(MainActivity.this, finalResult + " is the final result", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), ResultsActivity.class));
        return finalResult;
    }

    private double findDistance(String response) {
        int fromIndex;
        int toIndex;
        String result;
        double dblResult;
        String error = "ZERO_RESULTS";
        String error2 = "NOT_FOUND";

        if (response.contains(error)){
            Toast.makeText(MainActivity.this, "There has no way to drive between locations, try another location.", Toast.LENGTH_SHORT).show();
            return 0;
        }
        if (response.contains(error2)){
            Toast.makeText(MainActivity.this, "There has been an unexpected response, try another location.", Toast.LENGTH_SHORT).show();
            return 0;
        }

        fromIndex = response.indexOf("\"distance\" : {\n" +
                "                  \"text\" : \"");
        fromIndex = fromIndex + 43;
        toIndex = fromIndex + 4;
        result = response.substring(fromIndex, toIndex);

        dblResult = Double.parseDouble(result);

        URLResponse = dblResult;

        return dblResult;
    }


    public String constructURL(String origin, String destination) {

        final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
        final String MID_URL = "&destinations=";
        final String END_URL = "&key=AIzaSyDcs7z294l_CVyhJe_j1GIIAtu1-CB-R00";
        String fullURL;
        String safeStartLocation;
        String safeEndLocation;

        safeStartLocation = origin.replace(' ', '+');
        safeEndLocation = destination.replace(' ', '+');

        endLocationToResults = safeEndLocation;

        fullURL = BASE_URL + safeStartLocation + MID_URL + safeEndLocation + END_URL;

        return fullURL;
    }


    public void initSpinners() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.car_make_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.no_model_array, R.layout.spinner_item);
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner2.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Spinner spinner = (Spinner) findViewById(R.id.spinner);
                Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                switch (position) {
                    case 0:
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.no_model_array, R.layout.spinner_item);
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinner2.setAdapter(adapter);
                        MPG = 0;
                        break;
                    case 1:
                        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.ford_model_array, R.layout.spinner_item);
                        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinner2.setAdapter(adapter2);
                        MPG = 50;
                        break;
                    case 2:
                        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.vauxhall_model_array, R.layout.spinner_item);
                        adapter3.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinner2.setAdapter(adapter3);
                        MPG = 55;
                        break;
                    case 3:
                        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(MainActivity.this,
                                R.array.volkswagen_model_array, R.layout.spinner_item);
                        adapter4.setDropDownViewResource(R.layout.spinner_dropdown_item);
                        spinner2.setAdapter(adapter4);
                        MPG = 70;
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Select a make and model, or tap the check box.", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    private boolean checkInput(String check) {
        boolean result = check.isEmpty();
        return result;
    }

    public void initInputs() {
        EditText EditTextStart = (EditText) findViewById(R.id.input_start_location);
        EditText EditTextEnd = (EditText) findViewById(R.id.input_end_location);

        if (EditTextStart != null) {
            startLocation = EditTextStart.getText().toString();
        }

        if (EditTextEnd != null) {
            endLocation = EditTextEnd.getText().toString();
        }

    }

    private void requestData(String uri) {

        StringRequest request = new StringRequest(uri,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (findDistance(response) == 0){

                        } else {
                            calculateFinalResult(URLResponse, MPG);
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "There was a Network error, try again.", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public boolean ValidateInput(String check) {
        return check.matches("^.*[^a-zA-Z0-9 ].*$");
    }

    public void onRadioClicked(View view){
        RadioButton radioPetrol = (RadioButton) findViewById(R.id.radioButton1);
        RadioButton radioDiesel = (RadioButton) findViewById(R.id.radioButton2);

        switch (view.getId()) {
            case R.id.radioButton1:
                //IS PETROL
                if (radioDiesel != null) {
                    radioDiesel.setChecked(false);
                }
                isPetrol = true;
                break;
            case R.id.radioButton2:
                //IS DIESEL
                if (radioPetrol != null) {
                    radioPetrol.setChecked(false);
                }
                isPetrol = false;
                break;
        }

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.chkNotThere:
                if (checked) {
                    isCustomMPG = true;
                    if (spinner != null) {
                        spinner.setEnabled(false);
                    }
                    if (spinner2 != null) {
                        spinner2.setEnabled(false);
                    }
                    showDialogBox();
                } else {
                    isCustomMPG = false;
                    if (spinner != null) {
                        spinner.setEnabled(true);
                    }
                    if (spinner2 != null) {
                        spinner2.setEnabled(true);
                    }
                    Toast.makeText(MainActivity.this, "Undone", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    String result;

    private void showDialogBox() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        edt.setRawInputType(Configuration.KEYBOARD_12KEY);
        dialogBuilder.setTitle("Custom MPG");
        dialogBuilder.setMessage("Please enter your custom MPG below:");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                result = edt.getText().toString();
                if (checkMPG(result)) {
                    MPG = Integer.parseInt(result);

                } else {
                    CheckBox checkBox = (CheckBox) findViewById(R.id.chkNotThere);
                    Toast.makeText(MainActivity.this, "Enter only numerical characters", Toast.LENGTH_SHORT).show();
                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                    Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
                    if (spinner != null) {
                        spinner.setEnabled(true);
                    }
                    if (spinner2 != null) {
                        spinner2.setEnabled(true);
                    }
                    if (checkBox != null) {
                        checkBox.toggle();
                    }
                    MPG = 0;
                }
            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();


    }

    private boolean checkMPG(String res) {
        if (res.isEmpty()){
            return false;
        }
        for (int i = 0; i < res.length(); i++) {
            if (!Character.isDigit(res.charAt(i)))
                return false;
        }
        return true;

    }
}


