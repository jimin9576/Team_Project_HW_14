package com.androidapp.youjigom.UserInfo_number;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidapp.youjigom.CameraActivity;
import com.androidapp.youjigom.Locations;
import com.androidapp.youjigom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo_7 extends AppCompatActivity {

    ListView dBListView;
    ArrayAdapter<String> adapter;
    static ArrayList<String> arrayIndex=new ArrayList<String>();
    static ArrayList<String> arrayData=new ArrayList<String>();
    static com.androidapp.youjigom.Locations locations = new Locations();
    double[][] locationsdata = locations.LatLng;
    String[] CountryName = locations.countryName;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public String receiverName;
    public String receiverCountry;

    public ArrayList<String> userName = new ArrayList<String>();
    public ArrayList<String> userCountry = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        dBListView=findViewById(R.id.DBListView);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        dBListView.setAdapter(adapter);
        getFirebaseDataBase();


        dBListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                receiverName = userName.get(position);
                receiverCountry = userCountry.get(position);

                showMessage();


            }
        });

    }

    public void showMessage(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 교환");
        builder.setMessage( receiverName + "님께 사진을 전송하시겠습니까?" );
        builder.setIcon(R.drawable.adialog);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> childUpdates=new HashMap<>();
                Map<String, Object> postValues=null;
                CameraActivity ca = new CameraActivity();
                String StringImage=ca.getoriginalBm();

                com.androidapp.youjigom.FirebasePost post=
                        new com.androidapp.youjigom.FirebasePost
                                (StringImage, receiverName, receiverCountry);
                postValues=post.toMap();

                childUpdates.put("users/" + receiverName, postValues);
                databaseReference.updateChildren(childUpdates);
            }
        });

        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    private void getFirebaseDataBase() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName.clear();
                userCountry.clear();
                arrayData.clear();
                arrayIndex.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String key=dataSnapshot.getKey();
                    com.androidapp.youjigom.FirebasePost get=dataSnapshot.getValue(com.androidapp.youjigom.FirebasePost.class);
                    String info[]={get.fullName, get.country};
                    if(info[1].equals(CountryName[7])){
                        String result = "사용자 이름 : " + info[0] + "\n국적 : " + info[1];
                        //String result=info[2];
                        arrayData.add(result);
                        arrayIndex.add(key);
                        userName.add(info[0]);
                        userCountry.add(info[1]);
                    }
                }
                adapter.clear();
                adapter.addAll(arrayData);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query data= FirebaseDatabase.getInstance().getReference().
                child("users").orderByChild("country");
        data.addListenerForSingleValueEvent(eventListener);
    }
}