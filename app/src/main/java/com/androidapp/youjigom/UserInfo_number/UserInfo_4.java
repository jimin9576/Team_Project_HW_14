package com.androidapp.youjigom.UserInfo_number;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

public class UserInfo_4 extends AppCompatActivity {

    ListView dBListView;
    ArrayAdapter<String> adapter;
    static ArrayList<String> arrayIndex=new ArrayList<String>();
    static ArrayList<String> arrayData=new ArrayList<String>();
    static com.androidapp.youjigom.Locations locations = new Locations();
    double[][] locationsdata = locations.LatLng;
    String[] CountryName = locations.countryName;

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

                Intent intent = new Intent(view.getContext(), CameraActivity.class);
                view.getContext().startActivity(intent);

            }
        });

    }



    private void getFirebaseDataBase() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayData.clear();
                arrayIndex.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String key=dataSnapshot.getKey();
                    com.androidapp.youjigom.FirebasePost get=dataSnapshot.getValue(com.androidapp.youjigom.FirebasePost.class);
                    String info[]={get.fullName, get.country};
                    if(info[1].equals(CountryName[4])){
                        String result = "사용자 이름 : " + info[0] + "\n국적 : " + info[1];
                        //String result=info[2];
                        arrayData.add(result);
                        arrayIndex.add(key);
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