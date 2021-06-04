package com.androidapp.youjigom;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;



public class Locations {

    public final static double[][] LatLng = {

            {39.09843569786831, -77.03655778803672},//0
            {51.5144591527327, -0.12975831845500382},//1
            {48.88028742149057, 2.3474012992212248},//2
            {-24.522055716521134, 28.47810742951559},//3
            {41.95902295384582, 12.711770438185214},//4
            {23.753324485943654, 46.07277628960479},//5
            {22.964344723026613, 79.90706558759162},//6
            {62.26194541591081, 98.83211815684011},//7
            {24.253291376171024, -102.43459619514199},//8
            {-8.295766465239065, -55.799366138407},//9
            {-35.166365771070495, -65.12564218853217},//10
            {15.481129032797819, 101.2347620718381},//11
            {40.0424870042855, 116.38425286915037},//12
            {35.74306540022329, 139.77245318272045},//13
            {37.557667, 126.926546}//14

    };

    public final static String[] countryName = {
            "미국",//0
            "영국",//1
            "프랑스",//2
            "남아프리카공화국",//3
            "이탈리아",//4
            "사우디아라비아",//5
            "인도",//6
            "러시아",//7
            "멕시코",//8
            "브라질",//9
            "아르헨티나",//10
            "태국",//11
            "중국",//12
            "일본",//13
            "대한민국"//14
    };
}
