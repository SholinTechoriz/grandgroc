package com.e.sholinpaul.grandgroc.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.e.sholinpaul.grandgroc.R;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.OrderDetailsListener;
import com.e.sholinpaul.grandgroc.cloud.CloudCallBAck.PostStatusChangeListener;
import com.e.sholinpaul.grandgroc.cloud.CloudManager.OrdersCloudManager;
import com.e.sholinpaul.grandgroc.databinding.LocationFragmentBinding;
import com.e.sholinpaul.grandgroc.model.Model.OrderModel;
import com.e.sholinpaul.grandgroc.model.Model.PlaceModel;
import com.e.sholinpaul.grandgroc.model.Model.ProductModel;
import com.e.sholinpaul.grandgroc.utils.BusinessDetailsGenerator;

import java.util.List;

public class LocationFragment extends BaseFragments implements OrderDetailsListener, PostStatusChangeListener {
    View view;
    int Order_id, id;
    String accessToken, deviceId, status;
    LocationFragmentBinding binding;

//    //google map
//    GoogleMap map;
//
//    private static final String TAG = LocationFragment.class.getSimpleName();
//    private CameraPosition cameraPosition;
//
//    // The entry point to the Places API.
//    private PlacesClient placesClient;
//
//    // The entry point to the Fused Location Provider.
//    private FusedLocationProviderClient fusedLocationProviderClient;
//
//    // A default location (Sydney, Australia) and default zoom to use when location permission is
//    // not granted.
//    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);
//    private static final int DEFAULT_ZOOM = 15;
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    private boolean locationPermissionGranted;
//
//    // The geographical location where the device is currently located. That is, the last-known
//    // location retrieved by the Fused Location Provider.
//    private Location lastKnownLocation;
//
//    // Keys for storing activity state.
//    private static final String KEY_CAMERA_POSITION = "camera_position";
//    private static final String KEY_LOCATION = "location";
//
//    // Used for selecting the current place.
//    private static final int M_MAX_ENTRIES = 5;
//    private String[] likelyPlaceNames;
//    private String[] likelyPlaceAddresses;
//    private List[] likelyPlaceAttributions;
//    private LatLng[] likelyPlaceLatLngs;
//    double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
//    int flag = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }
        binding = LocationFragmentBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        init();
        return view;
    }

    private void init() {

//        // Construct a PlacesClient
//        Places.initialize(getActivity().getApplicationContext(), " AIzaSyDmOLj-qFxyQmpV1pW8MGBR587fej2cWfk");
//        placesClient = Places.createClient(getActivity());
//
//        // Construct a FusedLocationProviderClient.
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
//
//        // Build the map.
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        String apiKey = getString(R.string.map_key);
//        Places.initialize(getActivity().getApplicationContext(), apiKey);


        final OrderModel orderModel = getArguments().getParcelable("order");
        final String ScanState = getArguments().getString("ActivityState");
        final String AllOrdersState = getArguments().getString("OrderLIST");


        if (ScanState.equals("scanActivity")) {

            Toast.makeText(getActivity(), " from Scanning Activity", Toast.LENGTH_SHORT).show();
            final String OId = getArguments().getString("orderscanned");
            final String ID = getArguments().getString("ID");

            Order_id = Integer.parseInt(OId);
            id = Integer.parseInt(ID);

            fetchAllOrderList();

        } else if (AllOrdersState.equals("OrderListActivity")) {


            Toast.makeText(getActivity(), " from OrderList Activity", Toast.LENGTH_SHORT).show();


            Order_id = orderModel.getOrder_id();
            id = orderModel.getId();
            fetchAllOrderList();

        }


        binding.btnCollected.setOnClickListener(v -> {
            if (doubleClickPrevent()) {
                status = "collected";
                if (ScanState.equals("ScanState")) {
                    postChangesByScanner();
//                showCurrentPlace();
                } else {
                    postChangeStatus();
//                showCurrentPlace();
                }
            }

        });

        binding.btnDelivered.setOnClickListener(v -> {
            if (doubleClickPrevent()) {
                status = "delivered";
                if (ScanState.equals("ScanState")) {
                    postChangesByScanner();
//                showCurrentPlace();
                } else {
                    postChangeStatus();
//                showCurrentPlace();
                }
            }

        });

        binding.btnCompleted.setOnClickListener(v -> {
            if (doubleClickPrevent()) {
                status = "completed";
                if (ScanState.equals("ScanState")) {
                    postChangesByScanner();
                } else {
                    postChangeStatus();

                }
            }
        });
    }

    private void fetchAllOrderList() {

        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.fetchAllOrdersList(deviceId, accessToken, id, Order_id, this);

        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, false);
        }
    }


    private void postChangesByScanner() {
        Toast.makeText(getActivity(), "scanner", Toast.LENGTH_SHORT).show();
        OrderModel orderModel = new OrderModel();
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();

        orderModel.setDevice_id(deviceId);
        orderModel.setApi_token(accessToken);
        orderModel.setStatus(status);
        orderModel.setAssignordersID(id);
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.PostStatusByScanner(orderModel, this);

    }

    private void postChangeStatus() {
        Toast.makeText(getActivity(), "Activity", Toast.LENGTH_SHORT).show();

        OrderModel orderModel = new OrderModel();
        accessToken = BusinessDetailsGenerator.getInstance(getActivity()).getApi_token();
        deviceId = BusinessDetailsGenerator.getInstance(getActivity()).getDeviceId();

        orderModel.setDevice_id(deviceId);
        orderModel.setApi_token(accessToken);
        orderModel.setStatus(status);
        orderModel.setAssignordersID(id);
        OrdersCloudManager ordersCloudManager = new OrdersCloudManager(getActivity());
        ordersCloudManager.PostChangeStatusBy(orderModel, this);

        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, false);
        }

    }


    private void setDataToTextview(PlaceModel placeModel) {

        binding.tvName.setText(placeModel.getName());
        binding.tvHomeAddress.setText(placeModel.getLocation());
        binding.tvLocation1.setText(placeModel.getTown() + " , " + placeModel.getPin());
        binding.tvDistrict.setText(placeModel.getState_name() + " , " + placeModel.getCountry_name());
        binding.tvLandmark.setText(placeModel.getLandmark());
        binding.tvPhone.setText(placeModel.getPhone());


    }

    @Override
    public void fetchOrderDetails(OrderModel AssignedOrder, PlaceModel order, List<ProductModel> order_details) {

        Order_id = AssignedOrder.getOrder_id();
        id = AssignedOrder.getId();
        status = AssignedOrder.getStatus();

        setDataToTextview(order);


        Drawable drawable = getResources().getDrawable(R.drawable.ic_green_tick);

        switch (status) {

            case "delivered":
                binding.btnDelivered.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));

                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));
                break;


            case "collected":

                binding.btnCollected.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));

                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));
                break;


            case "completed":
                binding.btnCompleted.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorPrimary));

                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));

                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));
                break;

            default:

                binding.btnCompleted.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCompleted.setTextColor(getResources().getColor(R.color.colorWhite));


                binding.btnCollected.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnCollected.setTextColor(getResources().getColor(R.color.colorWhite));


                binding.btnDelivered.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                binding.btnDelivered.setTextColor(getResources().getColor(R.color.colorWhite));

        }


        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
    }

    @Override
    public void fetchOrderDetailsFailed(String errorMessage) {

        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
    }


    @Override
    public void postChangeStatus(OrderModel data, String Message) {
        Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
        fetchAllOrderList();


    }

    @Override
    public void postChangeStatusFailed(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < binding.rlLMain.getChildCount(); i++) {
            View view = binding.rlLMain.getChildAt(i);
            enableDisableView(view, true);
        }
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }


//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        map = googleMap;
//        LatLng Thodupuzha = new LatLng(9.895553, 76.692993);
//        map.addMarker(new MarkerOptions().position(Thodupuzha).title("Thodupuzha"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(Thodupuzha));
//        map.getUiSettings().setZoomControlsEnabled(true);
//
//
//        // Use a custom info window adapter to handle multiple lines of text in the
//        // info window contents.
//        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
//                        (FrameLayout) view.findViewById(R.id.map), false);
//
//                TextView title = infoWindow.findViewById(R.id.title);
//                title.setText(marker.getTitle());
//
//                TextView snippet = infoWindow.findViewById(R.id.snippet);
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });
//
//        // Prompt the user for permission.
//        getLocationPermission();
//
//        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
//
//        // Get the current location of the device and set the position of the map.
//        getDeviceLocation();
//
//
//    }
//
//    /**
//     * Prompts the user for permission to use the device location.
//     */
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(getActivity(),
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//
//    /**
//     * Updates the map's UI settings based on whether the user has granted location permission.
//     */
//    private void updateLocationUI() {
//        if (map == null) {
//            return;
//        }
//        try {
//            if (locationPermissionGranted) {
//                map.setMyLocationEnabled(true);
//                map.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//                map.setMyLocationEnabled(false);
//                map.getUiSettings().setMyLocationButtonEnabled(false);
//                lastKnownLocation = null;
//                getLocationPermission();
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//
//    /**
//     * Gets the current location of the device, and positions the map's camera.
//     */
//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (locationPermissionGranted) {
//                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener((Executor) this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(lastKnownLocation.getLatitude(),
//                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            map.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                            map.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage(), e);
//        }
//    }
//
//
//    /**
//     * Handles the result of the request for location permissions.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        locationPermissionGranted = false;
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    locationPermissionGranted = true;
//                }
//            }
//        }
//        updateLocationUI();
//    }
//
//    private void showCurrentPlace() {
//        if (map == null) {
//            return;
//        }
//
//        if (locationPermissionGranted) {
//            // Use fields to define the data types to return.
//            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                    Place.Field.LAT_LNG);
//
//            // Use the builder to create a FindCurrentPlaceRequest.
//            FindCurrentPlaceRequest request =
//                    FindCurrentPlaceRequest.newInstance(placeFields);
//
//            // Get the likely places - that is, the businesses and other points of interest that
//            // are the best match for the device's current location.
//            @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
//                    placesClient.findCurrentPlace(request);
//            placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
//                @Override
//                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        FindCurrentPlaceResponse likelyPlaces = task.getResult();
//
//                        // Set the count, handling cases where less than 5 entries are returned.
//                        int count;
//                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                            count = likelyPlaces.getPlaceLikelihoods().size();
//                        } else {
//                            count = M_MAX_ENTRIES;
//                        }
//
//                        int i = 0;
//                        likelyPlaceNames = new String[count];
//                        likelyPlaceAddresses = new String[count];
//                        likelyPlaceAttributions = new List[count];
//                        likelyPlaceLatLngs = new LatLng[count];
//
//                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
//                            // Build a list of likely places to show the user.
//                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
//                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
//                            likelyPlaceAttributions[i] = placeLikelihood.getPlace()
//                                    .getAttributions();
//                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
//
//                            i++;
//                            if (i > (count - 1)) {
//                                break;
//                            }
//                        }
//
//                        // Show a dialog offering the user the list of likely places, and add a
//                        // marker at the selected place.
//                        LocationFragment.this.openPlacesDialog();
//                    } else {
//                        Log.e(TAG, "Exception: %s", task.getException());
//                    }
//                }
//            });
//        } else {
//            // The user has not granted permission.
//            Log.i(TAG, "The user did not grant location permission.");
//
//            // Add a default marker, because the user hasn't selected a place.
//            map.addMarker(new MarkerOptions()
//                    .title(getString(R.string.default_info_title))
//                    .position(defaultLocation)
//                    .snippet(getString(R.string.default_info_snippet)));
//
//            // Prompt the user for permission.
//            getLocationPermission();
//        }
//    }
//
//    /**
//     * Displays a form allowing the user to select a place from a list of likely places.
//     */
//    private void openPlacesDialog() {
//        // Ask the user to choose the place where they are now.
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // The "which" argument contains the position of the selected item.
//                LatLng markerLatLng = likelyPlaceLatLngs[which];
//                String markerSnippet = likelyPlaceAddresses[which];
//                if (likelyPlaceAttributions[which] != null) {
//                    markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
//                }
//
//                // Add a marker for the selected place, with an info window
//                // showing information about that place.
//                map.addMarker(new MarkerOptions()
//                        .title(likelyPlaceNames[which])
//                        .position(markerLatLng)
//                        .snippet(markerSnippet));
//
//                // Position the map's camera at the location of the marker.
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
//                        DEFAULT_ZOOM));
//            }
//        };
//
//        // Display the dialog.
//        AlertDialog dialog = new AlertDialog.Builder(getActivity())
//                .setTitle(R.string.pick_place)
//                .setItems(likelyPlaceNames, listener)
//                .show();
//    }


}