package com.cagribayram.denizhancetin.project;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

public class RoomActivity extends AppCompatActivity {

    private int temperature;
    private String light;
    private int carbon;

    private Switch mSwitch;
    private String result;
    private TextView tv, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Bundle extras = getIntent().getExtras();
        tv = (TextView) findViewById(R.id.tv_kitchen);
        title = (TextView) findViewById(R.id.welcomeTV);
        mSwitch = (Switch) findViewById(R.id.sw);
        if(extras != null){
            title.setText(extras.getString("roomName"));
            temperature = extras.getInt("temperature");
            light = extras.getString("light");

            mSwitch.setChecked(light.equals("on") ? true : false);

            if(mSwitch.isChecked()){
                mSwitch.setText("ON");
            }
            else if(!mSwitch.isChecked()){
                mSwitch.setText("OFF");
            }

            result = "Temperature (C):  " + temperature + "\nLights: " + light;
            tv.setText(result);
        }
    }
}
