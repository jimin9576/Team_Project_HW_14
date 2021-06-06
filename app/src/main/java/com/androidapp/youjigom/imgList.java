package com.androidapp.youjigom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class imgList extends AppCompatActivity {
    FirebaseDatabase fDatabase;
    CustomAdapter adapter;
    String user;
    String image;
    Bitmap bitmap;
    String Name; //받는 사람
    String fullName; //로그인한 사람

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_list);

        ListView listView = findViewById(R.id.listView);
        ImageView imgMsg = findViewById(R.id.imgMsg);

        ArrayList<String> items = new ArrayList<>();
        MainActivity main = new MainActivity();

        adapter = new CustomAdapter(this, 0, items);

        fDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = fDatabase.getReference();

        reference.child("users/abc").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("fullName")){
                        user = dataSnapshot.getValue().toString();
                        items.add(user);
                        Log.d("TAG","items: "+user);
                        listView.setAdapter(adapter);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String data = (String)adapterView.getItemAtPosition(position);
                //    imgMsg.setImageDrawable(null);
                Log.d("TAG","text: "+data);

                setContentView(R.layout.image);

                TextView txtNameGet = findViewById(R.id.txtNameGet);
                ImageView imgGet = findViewById(R.id.imgGet);

                reference.child("users/abc").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.getKey().equals("image")){
                                image = dataSnapshot.getValue().toString();

                                byte[] b = Base64.decode(image, Base64.DEFAULT);

                                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                                Log.d("TAG","bitmap: "+bitmap);

                                imgGet.setImageBitmap(bitmap);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

                txtNameGet.setText(user);
            }
        });
    }

    public class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects){
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            View v = convertView;
            if (v == null){
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.item, null);
            }

            TextView txtName = (TextView)v.findViewById(R.id.txtName);

            if (user.equals(items.get(position))) {
                txtName.setText(items.get(position));
            }

            return v;
        }
    }

    public static byte[] binaryStringToByteArray(String s) {
        int count = s.length() / 8;
        byte[] b = new byte[count];
        for (int i = 1; i < count; ++i) {
            String t = s.substring((i - 1) * 8, i * 8);
            b[i - 1] = binaryStringToByte(t);
        }
        return b;
    }

    public static byte binaryStringToByte(String s) {
        byte ret = 0, total = 0;
        for (int i = 0; i < 8; ++i) {
            ret = (s.charAt(7 - i) == '1') ? (byte) (1 << i) : 0;
            total = (byte) (ret | total);
        }
        return total;
    }
}