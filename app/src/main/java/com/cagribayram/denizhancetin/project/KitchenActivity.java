package com.cagribayram.denizhancetin.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class KitchenActivity extends AppCompatActivity {

    private int temperature;
    private String light;
    private int carbon;

    private String result;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        Bundle extras = getIntent().getExtras();
        tv = (TextView) findViewById(R.id.tv_kitchen);

        if(extras != null){
            temperature = extras.getInt("temperature");
            light = extras.getString("light");
//            carbon = extras.getInt("carbon");
            result = temperature + ", " + light;
            tv.setText(result);
        }
    }
}
