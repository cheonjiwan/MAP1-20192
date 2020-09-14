package com.example.realunimarket;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap mGoogleMap = null;
    private Marker currentMarker = null;

    private static final String TAG = "googlemap_example";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 1000;  // 1초
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500; // 0.5초

    private AppCompatActivity mActivity;
    boolean askPermissionOnceAgain = false;
    boolean mRequestingLocationUpdates = false;
    Location mCurrentLocatiion;
    boolean mMoveMapByUser = true;
    boolean mMoveMapByAPI = true;
    LatLng currentPosition,clickPosition,searchPosition;

    LocationRequest locationRequest = new LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(UPDATE_INTERVAL_MS)
            .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);


    ImageButton backButton;
    ImageButton move,pack,car,animal,etc;
    ImageButton plus,minus;
    ImageButton publishButton;
    ImageView img1,img2,img3,img4,location_find;

    int startyear,startmonth,startday;
    int endyear,endmonth,endday;
    String input_lattitude,input_longitude,input_address;

    EditText price,content,location,title_content;
    TextView t1,t2,startdateText,enddateText;
    ImageButton startDateButton,endDateButton;
    int size=4;
    int icon;
    int icon2;
    int category=0; // 0: move , 1: pack, 2: car, 3:animal, 4: etc
    int purpose=0; // 0: 구해요, 1: 찾아요
    int people1=0,people2=0,people3=0,people4=0;
    String send_location,send_money; // db로 보낼 위치 및 시급

    EditText starthour,startminute,endhour,endminute;

    private FirebaseDatabase mDatabase; // 데이터베이스
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private FirebaseAuth firebaseAuth;

    Geocoder geocoder = new Geocoder(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Log.d(TAG, "onCreate");
        mActivity = this;

        //Geocoder geocoder = new Geocoder(this);

        startDateButton = (ImageButton)findViewById(R.id.startDateButton);
        endDateButton = (ImageButton)findViewById(R.id.endDateButton);
        startdateText = (TextView)findViewById(R.id.startDateText);
        enddateText = (TextView)findViewById(R.id.endDateText);
        starthour = (EditText)findViewById(R.id.starthour);
        startminute = (EditText)findViewById(R.id.startminute);
        endhour = (EditText)findViewById(R.id.endhour);
        endminute = (EditText)findViewById(R.id.endminute);

        price = (EditText)findViewById(R.id.price);
        title_content = (EditText)findViewById(R.id.title_content);
        content = (EditText)findViewById(R.id.content);
        location = (EditText)findViewById(R.id.location_info);
        backButton = (ImageButton)findViewById(R.id.backButton);
        move = (ImageButton)findViewById(R.id.move);
        pack = (ImageButton)findViewById(R.id.pack);
        car = (ImageButton)findViewById(R.id.car);
        animal = (ImageButton)findViewById(R.id.animal);
        etc = (ImageButton)findViewById(R.id.etc);
        plus = (ImageButton)findViewById(R.id.plus);
        minus = (ImageButton)findViewById(R.id.minus);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img4 = (ImageView)findViewById(R.id.img4);
        location_find=(ImageView)findViewById(R.id.location_find);

        mReference=mDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click_GetDateTime();
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Click_GetDateTime2();
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t1.getText().toString().equals("PM"))
                    t1.setText("AM");
                else
                    t1.setText("PM");
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(t2.getText().toString().equals("AM"))
                    t2.setText("PM");
                else
                    t2.setText("AM");
            }
        });

        location_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String str=location.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                System.out.println(address);

                input_lattitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                input_longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(input_lattitude);
                System.out.println(input_longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(input_lattitude), Double.parseDouble(input_longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();

                searchPosition = new LatLng(Double.parseDouble(input_lattitude),Double.parseDouble(input_longitude));
                // 마커 타이틀
                input_address=getCurrentAddress(searchPosition);
                mOptions2.title(input_address);
                mOptions2.snippet(input_lattitude+" , "+input_longitude);
                mOptions2.position(point);
                // 마커 추가
                mGoogleMap.addMarker(mOptions2);
                // 해당 좌표로 화면 줌
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });


        publishButton = (ImageButton)findViewById(R.id.publishButton);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                send_money=price.getText().toString();

                String start_day =startyear+"."+startmonth+"."+startday;
                String end_day = endyear+"."+endmonth+"."+endday;
                String start_time= t1.getText().toString()+" "+starthour.getText().toString()+" : "+startminute.getText().toString();
                String end_time = t2.getText().toString()+" "+endhour.getText().toString()+" : "+endminute.getText().toString();
                String user_id = (String)user.getUid();

                send_location=location.getText().toString();

                String title = title_content.getText().toString();

                Calendar calendar = Calendar.getInstance();
                java.util.Date date = calendar.getTime();
                String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

                MarketItem marketItem = new MarketItem(today,user_id,category,title,input_address,people1,people2,people3,people4,send_money,
                        content.getText().toString(),start_day,end_day,start_time,end_time,input_lattitude,input_longitude);
                mReference.child("MainBoard").child(user.getUid()).push().setValue(marketItem);
                startActivity(intent);
                finish();


            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(size>=4)
                    return;
                else{
                    size++;
                    if(size==1) {
                        people1=1;
                        people2=0;
                        people3=0;
                        people4=0;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                    else if(size == 2){
                        people1=1;
                        people2=1;
                        people3=0;
                        people4=0;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                    else if(size == 3){
                        people1=1;
                        people2=1;
                        people3=1;
                        people4=0;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                    else if(size == 4){
                        people1=1;
                        people2=1;
                        people3=1;
                        people4=1;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(size<=0)
                    return;
                else {
                    size--;
                    if(size==1) {
                        people1=1;
                        people2=0;
                        people3=0;
                        people4=0;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                    else if(size == 2){
                        people1=1;
                        people2=1;
                        people3=0;
                        people4=0;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                    else if(size == 3){
                        people1=1;
                        people2=1;
                        people3=1;
                        people4=0;
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                    else if(size == 0){
                        people1=0;
                        people2=0;
                        people3=0;
                        people4=0;
                        img1.setVisibility(View.INVISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        img3.setVisibility(View.INVISIBLE);
                        img4.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    public void Click_GetDateTime() {
        DatePickerDialog dialog = new DatePickerDialog(this, listener, 2019, 11, 18);
        dialog.show();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(getApplicationContext(), year + "년" + (monthOfYear+1) + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
            startyear=year; startmonth = monthOfYear+1; startday = dayOfMonth;
            startdateText.setText(startyear+"."+startmonth+"."+startday);
        }

    };

    public void Click_GetDateTime2() {
        DatePickerDialog dialog = new DatePickerDialog(this, listener2, 2019, 11, 18);
        dialog.show();
    }
    private DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(getApplicationContext(), year + "년" + (monthOfYear+1) + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
            endyear=year; endmonth = monthOfYear+1; endday = dayOfMonth;
            enddateText.setText(endyear+"."+endmonth+"."+endday);
        }

    };

    public void onClick(View view){
        switch(view.getId()){
            case R.id.move:
                move.setImageResource(R.drawable.cat_move_on);
                pack.setImageResource(R.drawable.cat_pack);
                car.setImageResource(R.drawable.cat_car);
                animal.setImageResource(R.drawable.cat_pet);
                etc.setImageResource(R.drawable.cat_etc);
                icon = R.drawable.move;
                category=0;
                break;
            case R.id.pack:
                move.setImageResource(R.drawable.cat_move);
                pack.setImageResource(R.drawable.cat_pack_on);
                car.setImageResource(R.drawable.cat_car);
                animal.setImageResource(R.drawable.cat_pet);
                etc.setImageResource(R.drawable.cat_etc);
                icon = R.drawable.pack;
                category=1;
                break;
            case R.id.car:
                move.setImageResource(R.drawable.cat_move);
                pack.setImageResource(R.drawable.cat_pack);
                car.setImageResource(R.drawable.cat_car_on);
                animal.setImageResource(R.drawable.cat_pet);
                etc.setImageResource(R.drawable.cat_etc);
                icon = R.drawable.car;
                category=2;
                break;
            case R.id.animal:
                move.setImageResource(R.drawable.cat_move);
                pack.setImageResource(R.drawable.cat_pack);
                car.setImageResource(R.drawable.cat_car);
                animal.setImageResource(R.drawable.cat_pet_on);
                etc.setImageResource(R.drawable.cat_etc);
                icon= R.drawable.pet;
                category=3;
                break;
            case R.id.etc:
                move.setImageResource(R.drawable.cat_move);
                pack.setImageResource(R.drawable.cat_pack);
                car.setImageResource(R.drawable.cat_car);
                animal.setImageResource(R.drawable.cat_pet);
                etc.setImageResource(R.drawable.cat_etc_on);
                icon = R.drawable.etc;
                category=4;
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        if (mGoogleApiClient.isConnected()) {

            Log.d(TAG, "onResume : call startLocationUpdates");
            if (!mRequestingLocationUpdates) startLocationUpdates();
        }


        //앱 정보에서 퍼미션을 허가했는지를 다시 검사해봐야 한다.
        if (askPermissionOnceAgain) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                askPermissionOnceAgain = false;

                checkPermissions();
            }
        }
    }


    private void startLocationUpdates() {
        if (!checkLocationServicesStatus()) {
            Log.d(TAG, "startLocationUpdates : call showDialogForLocationServiceSetting");
            showDialogForLocationServiceSetting();
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음");
                return;
            }

            Log.d(TAG, "startLocationUpdates : call FusedLocationApi.requestLocationUpdates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            mRequestingLocationUpdates = true;

            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    private void stopLocationUpdates() {
        Log.d(TAG,"stopLocationUpdates : LocationServices.FusedLocationApi.removeLocationUpdates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        mRequestingLocationUpdates = false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady :");
        mGoogleMap = googleMap;

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에
        //지도의 초기위치를 서울로 이동
        setDefaultLocation();

        //mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
            @Override
            public boolean onMyLocationButtonClick() {
                Log.d( TAG, "onMyLocationButtonClick : 위치에 따른 카메라 이동 활성화");
                mMoveMapByAPI = true;
                return true;
            }
        });
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                MarkerOptions mOptions = new MarkerOptions();
//
//                Double latitude = latLng.latitude; // 위도
//                Double longitude = latLng.longitude; // 경도
//
//                clickPosition = new LatLng(latitude,longitude);
//                // 마커 타이틀
//                mOptions.title(getCurrentAddress(currentPosition));
//
//                // 마커의 스니펫(간단한 텍스트) 설정
//                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
//                // LatLng: 위도 경도 쌍을 나타냄
//                mOptions.position(new LatLng(latitude, longitude));
//                // 마커(핀) 추가
//                mGoogleMap.addMarker(mOptions);
                Log.d( TAG, "onMapClick :");
            }
        });

        mGoogleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {
                if (mMoveMapByUser == true && mRequestingLocationUpdates){
                    Log.d(TAG, "onCameraMove : 위치에 따른 카메라 이동 비활성화");
                    mMoveMapByAPI = false;
                }
                mMoveMapByUser = true;
            }
        });

        mGoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {

        currentPosition
                = new LatLng( location.getLatitude(), location.getLongitude());


        Log.d(TAG, "onLocationChanged : ");

        String markerTitle = getCurrentAddress(currentPosition);
        String markerSnippet = "위도:" + String.valueOf(location.getLatitude())
                + " 경도:" + String.valueOf(location.getLongitude());

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet);

        mCurrentLocatiion = location;
    }


    @Override
    protected void onStart() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected() == false){

            Log.d(TAG, "onStart: mGoogleApiClient connect");
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {

        if (mRequestingLocationUpdates) {
            Log.d(TAG, "onStop : call stopLocationUpdates");
            stopLocationUpdates();
        }
        if ( mGoogleApiClient.isConnected()) {
            Log.d(TAG, "onStop : mGoogleApiClient disconnect");
            mGoogleApiClient.disconnect();
        }

        super.onStop();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        if ( mRequestingLocationUpdates == false ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                } else {
                    Log.d(TAG, "onConnected : 퍼미션 가지고 있음");
                    Log.d(TAG, "onConnected : call startLocationUpdates");
                    startLocationUpdates();
                    mGoogleMap.setMyLocationEnabled(true);
                }
            }else{
                Log.d(TAG, "onConnected : call startLocationUpdates");
                startLocationUpdates();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed");
        setDefaultLocation();
    }


    @Override
    public void onConnectionSuspended(int cause) {

        Log.d(TAG, "onConnectionSuspended");
        if (cause == CAUSE_NETWORK_LOST)
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(TAG, "onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");
    }

    public String getCurrentAddress(LatLng latlng) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latlng.latitude,
                    latlng.longitude,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        } else {
            Address address = addresses.get(0);
            return address.getAddressLine(0).toString();
        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {

        mMoveMapByUser = false;

        if (currentMarker != null)
            currentMarker.remove();

        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLatLng);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);

        //currentMarker = mGoogleMap.addMarker(markerOptions);
        if ( mMoveMapByAPI ) {
            Log.d( TAG, "setCurrentLocation :  mGoogleMap moveCamera "
                    + location.getLatitude() + " " + location.getLongitude() ) ;
            // CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
            mGoogleMap.moveCamera(cameraUpdate);
        }
    }

    public void setDefaultLocation() {

        mMoveMapByUser = false;

        //디폴트 위치, Seoul
        LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
        String markerTitle = "위치정보 가져올 수 없음";
        String markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요";

        if (currentMarker != null) currentMarker.remove();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        currentMarker = mGoogleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);
    }


    //여기부터는 런타임 퍼미션 처리을 위한 메소드들
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        boolean fineLocationRationale = ActivityCompat
                .shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager
                .PERMISSION_DENIED && fineLocationRationale)
            showDialogForPermission("앱을 실행하려면 퍼미션을 허가하셔야합니다.");

        else if (hasFineLocationPermission
                == PackageManager.PERMISSION_DENIED && !fineLocationRationale) {
            showDialogForPermissionSetting("퍼미션 거부 + Don't ask again(다시 묻지 않음) " +
                    "체크 박스를 설정한 경우로 설정에서 퍼미션 허가해야합니다.");
        } else if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {


            Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");

            if ( mGoogleApiClient.isConnected() == false) {
                Log.d(TAG, "checkPermissions : 퍼미션 가지고 있음");
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (permsRequestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION && grantResults.length > 0) {
            boolean permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (permissionAccepted) {
                if ( mGoogleApiClient.isConnected() == false) {
                    Log.d(TAG, "onRequestPermissionsResult : mGoogleApiClient connect");
                    mGoogleApiClient.connect();
                }
            } else {
                checkPermissions();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        });

        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }

    private void showDialogForPermissionSetting(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                askPermissionOnceAgain = true;

                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mActivity.getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(myAppSettings);
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.create().show();
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d(TAG, "onActivityResult : 퍼미션 가지고 있음");

                        if ( mGoogleApiClient.isConnected() == false ) {
                            Log.d( TAG, "onActivityResult : mGoogleApiClient connect ");
                            mGoogleApiClient.connect();
                        }
                        return;
                    }
                }
                break;
        }
    }
}
