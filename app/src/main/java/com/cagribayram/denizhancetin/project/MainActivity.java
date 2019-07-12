package com.cagribayram.denizhancetin.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//<div>Icons made by <a href="https://www.flaticon.com/authors/roundicons" title="Roundicons">Roundicons</a> from <a href="https://www.flaticon.com/"                 title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/"                 title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

public class MainActivity extends AppCompatActivity {

    private Button kitchenButton;
    private Button livingRoomButton;
    private Button garageButton;
    Intent intent;
    Toast mToast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //this.getSupportActionBar().hide();// hiding title bar

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.garageButton:
                intent = new Intent(this, garage.class);
                startActivity(intent);
                break;

            case R.id.kitchenButton:
                intent = new Intent(this, kitchen.class);
                startActivity(intent);
                break;

            case R.id.livingRoomButton:
                intent = new Intent(this, living_room.class);
                startActivity(intent);
                break;

            case R.id.infoImageView:
                intent = new Intent(this, info.class);
                startActivity(intent);
                break;

        }

    }

    private void displayToast(String msg) {

        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();

    }

}
