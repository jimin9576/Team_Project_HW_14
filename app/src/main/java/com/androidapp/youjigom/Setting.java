package com.androidapp.youjigom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Setting extends AppCompatActivity {

    private Button buttonSetting;
    private Button buttonSetting2;
    private Button buttonSetting3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        buttonSetting = (Button) findViewById(R.id.setting_btn);

        buttonSetting2 = (Button) findViewById(R.id.setting2_btn);

        buttonSetting3 = (Button) findViewById(R.id.setting3_btn);

        buttonSetting.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent intent = new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);

            }
        });

        buttonSetting2.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent intent = new Intent(android.provider.Settings.ACTION_SOUND_SETTINGS);
                startActivity(intent);
            }
        });

        buttonSetting3.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                startActivity(intent);
            }
        });

    }
}