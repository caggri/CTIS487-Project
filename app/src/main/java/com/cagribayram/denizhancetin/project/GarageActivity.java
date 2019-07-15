package com.cagribayram.denizhancetin.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GarageActivity extends AppCompatActivity {
    private int temperature;
    private String light;
    private int carbon;

    private String result;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        Bundle extras = getIntent().getExtras();

        tv = (TextView) findViewById(R.id.tv_garage);

        if(extras != null){
            //How to cast Integer to int?
            //temperature = Integer.parseInt(extras.getString("temperature"));
            light = extras.getString("light");
            //carbon = Integer.parseInt(extras.getString("carbon"));

            result = temperature + ", " + light + ", " + carbon;
            tv.setText(result);
        }


    }
}
