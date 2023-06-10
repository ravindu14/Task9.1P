package com.example.lostfoundapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewItem extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private EditText name_input, phone_input, description_input, date_input, location_input;
    private AppCompatButton save_button, back_button, get_location;

    private String post_type = "";

    private String ltgLng = "";
    private RadioButton lostButton, foundButton;
    private DatabaseHelper databaseHelper;

    private final static int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        name_input = findViewById(R.id.name_input);
        phone_input = findViewById(R.id.phone_input);
        description_input = findViewById(R.id.description_input);
        date_input = findViewById(R.id.date_input);
        location_input = findViewById(R.id.location_input);
        save_button = findViewById(R.id.save_button);
        lostButton = findViewById(R.id.radio_lost);
        foundButton = findViewById(R.id.radio_found);
        back_button = findViewById(R.id.back_button);
        get_location = findViewById(R.id.get_location);

        Places.initialize(getApplicationContext(), "AIzaSyBKeEoNF5iJzE3VlbzeXWD7I3JEg6pItiI");
        location_input.setFocusable(false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        location_input.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(NewItem.this);

                startActivityForResult(intent, 100);
            }
        });

        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("onclick");
                getLastLocation();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_input.getText().toString();
                String phone = phone_input.getText().toString();
                String description = description_input.getText().toString();
                String date = date_input.getText().toString();
                String location = location_input.getText().toString();
                String status = post_type;


                resetFormErrors();

                if(validateInputs(name, phone, description, date, location)) {
                    databaseHelper.insertItem(new DatabaseItem(name, phone, description, date, location, status, ltgLng));
                    Toast.makeText(NewItem.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                    resetForm();
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainMenu = new Intent(NewItem.this, MainActivity.class);
                startActivity(mainMenu);
            }
        });
    }

    private void getLastLocation() {
        System.out.println("outside");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println("inside");
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            System.out.println((location));
                            if (location != null) {
                                Geocoder geocoder = new Geocoder(NewItem.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    System.out.println(addresses.get(0).getAddressLine(0));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                ltgLng = "lat/lng: ("+ addresses.get(0).getLatitude()+","+ addresses.get(0).getLongitude() +")";
                                location_input.setText(addresses.get(0).getAddressLine(0));
                            }
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(NewItem.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void resetForm() {
        name_input.setText("");
        phone_input.setText("");
        description_input.setText("");
        date_input.setText("");
        location_input.setText("");
        this.post_type = "";
    }

    private boolean validateInputs(String name, String phone, String description, String date, String location) {
        boolean validated = true;

        if (name.equals("")) {
            name_input.setError("Please enter valid name");
            validated = false;
        }
        if(phone.equals("") ) {
            phone_input.setError("Please enter valid phone");
            validated = false;
        }
        if (description.equals("")) {
            description_input.setError("Please enter valid description");
            validated = false;
        }
        if (date.equals("")) {
            date_input.setError("Please enter valid date");
            validated = false;
        }
        if (location.equals("")) {
            location_input.setError("Please enter valid location");
            validated = false;
        }
        if (!(post_type.equals("lost") || post_type.equals("found"))) {
            lostButton.setError("Select");
            foundButton.setError("Select");
            validated = false;
        }

        return validated;
    }

    private void resetFormErrors() {
        name_input.setError(null);
        phone_input.setError(null);
        description_input.setError(null);
        date_input.setError(null);
        location_input.setError(null);
        lostButton.setError(null);
        foundButton.setError(null);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_lost:
                if (checked)
                    post_type = "lost";
                    break;
            case R.id.radio_found:
                if (checked)
                    post_type = "found";
                    break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place =  Autocomplete.getPlaceFromIntent(data);
            location_input.setText(place.getAddress());

            ltgLng = String.valueOf(place.getLatLng());
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Toast.makeText(NewItem.this, "Something wrong with places API", Toast.LENGTH_SHORT).show();
        }
    }
}