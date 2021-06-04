package com.androidapp.youjigom;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.youjigom.UserInfo_number.UserInfo_0;
import com.androidapp.youjigom.UserInfo_number.UserInfo_1;
import com.androidapp.youjigom.UserInfo_number.UserInfo_10;
import com.androidapp.youjigom.UserInfo_number.UserInfo_11;
import com.androidapp.youjigom.UserInfo_number.UserInfo_12;
import com.androidapp.youjigom.UserInfo_number.UserInfo_13;
import com.androidapp.youjigom.UserInfo_number.UserInfo_14;
import com.androidapp.youjigom.UserInfo_number.UserInfo_2;
import com.androidapp.youjigom.UserInfo_number.UserInfo_3;
import com.androidapp.youjigom.UserInfo_number.UserInfo_4;
import com.androidapp.youjigom.UserInfo_number.UserInfo_5;
import com.androidapp.youjigom.UserInfo_number.UserInfo_6;
import com.androidapp.youjigom.UserInfo_number.UserInfo_7;
import com.androidapp.youjigom.UserInfo_number.UserInfo_8;
import com.androidapp.youjigom.UserInfo_number.UserInfo_9;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    FirebaseFirestore fStore;
    TextView fullName, email, phone, country, MyCountry;
    FirebaseAuth fAuth;
    String userId;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;
    Button profile;
    static Locations locations = new Locations();
    double[][] locationsdata = locations.LatLng;
    String[] CountryName = locations.countryName;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        phone = findViewById(R.id.profilePhone);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        country = findViewById(R.id.profileCountry);
        MyCountry = findViewById(R.id.MyCountry);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

            }


        });
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


        for (int x = 0; x < 15; x++) {
            // 위치 설정
            double lat = locationsdata[x][0];
            double lon = locationsdata[x][1];
            LatLng latLng = new LatLng(lat, lon);
            //LatLng latLng = new LatLng(37.557667, 126.926546);

            // 카메라를 설정 위치로 옮긴다
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //구글 맵에 표시할 마커에 대한 옵션 설정
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(CountryName[x]);
            // 마커 생성
            googleMap.addMarker(markerOptions);
        }
        // 카메라 줌 정도를 설정한다
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(0));

        googleMap.setOnMarkerClickListener(this::OnMarkerClick);
    }

    public boolean OnMarkerClick(Marker marker) {

        LatLng countryLocation = marker.getPosition();

        //미국 마커 클릭시
        double lat0 = locationsdata[0][0];
        double lon0 = locationsdata[0][1];
        LatLng latLng0 = new LatLng(lat0, lon0);

        if (countryLocation.equals(latLng0)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_0.class));
        }

        //영국 마커 클릭시
        double lat1 = locationsdata[1][0];
        double lon1 = locationsdata[1][1];
        LatLng latLng1 = new LatLng(lat1, lon1);

        if (countryLocation.equals(latLng1)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_1.class));
        }

        //프랑스 마커 클릭시
        double lat2 = locationsdata[2][0];
        double lon2 = locationsdata[2][1];
        LatLng latLng2 = new LatLng(lat2, lon2);

        if (countryLocation.equals(latLng2)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_2.class));
        }

        //남아프리카공화국 마커 클릭시
        double lat3 = locationsdata[3][0];
        double lon3 = locationsdata[3][1];
        LatLng latLng3 = new LatLng(lat3, lon3);

        if (countryLocation.equals(latLng3)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_3.class));
        }

        //이탈리아 마커 클릭시
        double lat4 = locationsdata[4][0];
        double lon4 = locationsdata[4][1];
        LatLng latLng4 = new LatLng(lat4, lon4);

        if (countryLocation.equals(latLng4)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_4.class));
        }

        //사우디아라비아 마커 클릭시
        double lat5 = locationsdata[5][0];
        double lon5 = locationsdata[5][1];
        LatLng latLng5 = new LatLng(lat5, lon5);

        if (countryLocation.equals(latLng5)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_5.class));
        }

        //인도 마커 클릭시
        double lat6 = locationsdata[6][0];
        double lon6 = locationsdata[6][1];
        LatLng latLng6 = new LatLng(lat6, lon6);

        if (countryLocation.equals(latLng6)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_6.class));
        }

        //러시아 마커 클릭시
        double lat7 = locationsdata[7][0];
        double lon7 = locationsdata[7][1];
        LatLng latLng7 = new LatLng(lat7, lon7);

        if (countryLocation.equals(latLng7)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_7.class));
        }

        //멕시코 마커 클릭시
        double lat8 = locationsdata[8][0];
        double lon8 = locationsdata[8][1];
        LatLng latLng8 = new LatLng(lat8, lon8);

        if (countryLocation.equals(latLng8)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_8.class));
        }

        //브라질 마커 클릭시
        double lat9 = locationsdata[9][0];
        double lon9 = locationsdata[9][1];
        LatLng latLng9 = new LatLng(lat9, lon9);

        if (countryLocation.equals(latLng9)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_9.class));
        }

        //아르헨티나 마커 클릭시
        double lat10 = locationsdata[10][0];
        double lon10 = locationsdata[10][1];
        LatLng latLng10 = new LatLng(lat10, lon10);

        if (countryLocation.equals(latLng10)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_10.class));
        }

        //태국 마커 클릭시
        double lat11 = locationsdata[11][0];
        double lon11 = locationsdata[11][1];
        LatLng latLng11 = new LatLng(lat11, lon11);

        if (countryLocation.equals(latLng11)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_11.class));
        }

        //중국 마커 클릭시
        double lat12 = locationsdata[12][0];
        double lon12 = locationsdata[12][1];
        LatLng latLng12 = new LatLng(lat12, lon12);

        if (countryLocation.equals(latLng12)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_12.class));
        }

        //일본 마커 클릭시
        double lat13 = locationsdata[13][0];
        double lon13 = locationsdata[13][1];
        LatLng latLng13 = new LatLng(lat13, lon13);

        if (countryLocation.equals(latLng13)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_13.class));
        }

        //대한민국 마커 클릭시
        double lat14 = locationsdata[14][0];
        double lon14 = locationsdata[14][1];
        LatLng latLng14 = new LatLng(lat14, lon14);

        if (countryLocation.equals(latLng14)) {
            startActivity(new Intent(getApplicationContext(), UserInfo_14.class));
        }

        return false;
    }
}