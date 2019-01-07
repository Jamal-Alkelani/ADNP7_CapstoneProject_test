package com.example.gamal.adnp7_capstoneproject.Activities;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gamal.adnp7_capstoneproject.Adapters.AppointmentsAdapter;
import com.example.gamal.adnp7_capstoneproject.Database.AppDatabase;
import com.example.gamal.adnp7_capstoneproject.Executors.AppExecutors;
import com.example.gamal.adnp7_capstoneproject.Geofences.Geofencing;
import com.example.gamal.adnp7_capstoneproject.Initializers.Database_Initializers;
import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.Models.Geofence;
import com.example.gamal.adnp7_capstoneproject.R;
import com.example.gamal.adnp7_capstoneproject.Utilitize.SwipeDetector;
import com.example.gamal.adnp7_capstoneproject.Utilitize.SwipeToDeleteCallback;
import com.example.gamal.adnp7_capstoneproject.ViewModel.AppointmentsViewModelFactory;
import com.example.gamal.adnp7_capstoneproject.ViewModel.AppointmetsViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements AppointmentsAdapter.OnItemClickListener
        , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PLACE_PICKER_REQUEST = 1;
    AppointmentsAdapter.OnItemClickListener listener;
    List<Appointment> data;
    RecyclerView rv;
    AppointmentsAdapter adapter;
    AppDatabase appDatabase;
    private GoogleApiClient mClient;
    private Geofencing mGeofencing;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.mi_addPlace) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.need_location_permission_message), Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_FINE_LOCATION);
                return false;
            }
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent i = builder.build(this);
                startActivityForResult(i, PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                Log.e("xx", String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.e("xx", String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
            } catch (Exception e) {
                Log.e("xx", String.format("PlacePicker Exception: %s", e.getMessage()));
            }
        }
        if (item.getItemId() == R.id.mi_geofences) {
            final Dialog dialog = new Dialog(this);
            final List<String> IDs = new ArrayList<>();
            dialog.setContentView(R.layout.li_places);
            dialog.setTitle("My Places");
            ListView listView = dialog.findViewById(R.id.lv_places);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            listView.setAdapter(arrayAdapter);
            final SwipeDetector swipeDetector = new SwipeDetector();
            listView.setOnTouchListener(swipeDetector);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    if (swipeDetector.swipeDetected()) {
                        if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    appDatabase.geofenceDao().deletePlaceById(IDs.get(i));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(HomeActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            });
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    List<Geofence> geofences = appDatabase.geofenceDao().getAllGeofences();
                    String[] items = new String[geofences.size()];
                    if (items.length == 0) {
                        dialog.findViewById(R.id.no_places_err_msg).setVisibility(View.VISIBLE);
                        dialog.findViewById(R.id.lv_places).setVisibility(View.GONE);
                    } else {
                        dialog.findViewById(R.id.no_places_err_msg).setVisibility(View.GONE);
                        dialog.findViewById(R.id.lv_places).setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < items.length; i++) {
                        items[i] = geofences.get(i).getName();
                        IDs.add(geofences.get(i).getID());
                    }
                    arrayAdapter.addAll(items);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.show();

                        }
                    });

                }
            });
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        setTitle("");
        listener = this;
        data = randomInit();
        rv = findViewById(R.id.rv_appointments);
        adapter = new AppointmentsAdapter(this, randomInit(), listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
        final ViewGroup viewGroup = this.findViewById(R.id.scene);
        boolean x = false;
        if (viewGroup != null) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i) instanceof Button) {
                    x = true;
                }
            }
        }
        if (x) {
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, this));
            itemTouchHelper.attachToRecyclerView(rv);
        }

        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        mGeofencing = new Geofencing(this, mClient);

    }

    public List<Appointment> randomInit() {
        Database_Initializers.mContext = this;
        final LifecycleOwner lifecycleOwner = this;
        Database_Initializers.initAppointments();
        appDatabase = AppDatabase.getAppDatabase(getBaseContext());
        AppointmentsViewModelFactory factory = new AppointmentsViewModelFactory(appDatabase);
        final AppointmetsViewModel viewModel = factory.create(AppointmetsViewModel.class);

        viewModel.getAppoointments().observe(lifecycleOwner, new Observer<List<Appointment>>() {
            @Override
            public void onChanged(@Nullable List<Appointment> list) {
                data = list;
                adapter.setDate(data);
                adapter.notifyDataSetChanged();
            }
        });
        return null;


    }


    @Override
    public void onItemClick(int pos) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.ID_APPOINTMENT, data.get(pos));
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        refreshPlacesData();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            if (place == null) {
                Log.i("xx", "No place selected");
                return;
            }

            // Extract the place information from the API
            final String placeName = place.getName().toString();
            final String placeID = place.getId();
            double Lat = place.getLatLng().latitude;
            double Lng = place.getLatLng().longitude;
            final Pair<Double, Double> LatLng = new Pair<>(Lat, Lng);
            appDatabase = AppDatabase.getAppDatabase(this);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Geofence geofence = new Geofence(placeName, placeID, LatLng.first, LatLng.second);
                    appDatabase.geofenceDao().insertGeofence(geofence);
                    Log.i("xx", "geofence inserted");
                }
            });
            refreshPlacesData();


        }
    }


    public void refreshPlacesData() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Geofence> geofences;

                geofences = appDatabase.geofenceDao().getAllGeofences();
                List<String> guids = new ArrayList<>();
                for (int i = 0; i < geofences.size(); i++) {
                    guids.add(geofences.get(i).getID());
                }
                if (guids.size() != 0) {
                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mClient,
                            guids.toArray(new String[guids.size()]));
                    placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                        @Override
                        public void onResult(@NonNull PlaceBuffer places) {
                            mGeofencing.updateGeofencesList(places);

                        }
                    });
                }
            }
        });

    }
}
