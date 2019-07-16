package com.cagribayram.denizhancetin.project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//<div>Icons made by <a href="https://www.flaticon.com/authors/roundicons" title="Roundicons">Roundicons</a> from <a href="https://www.flaticon.com/"                 title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/"                 title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

public class MainActivity extends AppCompatActivity {

    private Button kitchenButton;
    private Button livingRoomButton;
    private Button garageButton;

    private TextView tvAir, tvLights;
    Intent intent;
    Toast mToast;

    String mainJ = "";
    int garageCo2, livingroomCo2, kitchenCo2;
    String garageLights, livingroomLights, kitchenLights;
    int garageTemparature, kitchenTemparature, livingroomTemparature;
    String[] rooms = {"living_room", "kitchen", "garage"};
    String part;

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) throws RuntimeException {

            String result = "";
            HttpURLConnection urlConnection;
            URL url;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray arr;
                JSONObject jsonPart;
                String weatherInfo;

                for (int j = 0; j < rooms.length; j++) {
                    weatherInfo = jsonObject.getString(rooms[j]);

                    arr = new JSONArray(weatherInfo);

                    for (int i = 0; i < arr.length(); i++) {
                        jsonPart = arr.getJSONObject(i);
                        switch (rooms[j]) {
                            case "living_room":
                                Log.i("Room Check", rooms[j]);
                                livingroomLights = jsonPart.getString("Lights");
                                livingroomTemparature = Integer.parseInt(jsonPart.getString("Temparature"));

                                Log.i("Value checj", livingroomLights + "\n" + livingroomTemparature);
                                mainJ += "Living Room:\n" + "Temp: " + livingroomTemparature + "\nLigths: " + livingroomLights + "\n\n";
                                break;

                            case "kitchen":
                                Log.i("Room Check", rooms[j]);
                                kitchenLights = jsonPart.getString("Lights");
                                kitchenTemparature = Integer.parseInt(jsonPart.getString("Temparature"));

                                mainJ += "Kitchen:\n" + "Temp: " + kitchenTemparature + "\nLigths: " + kitchenLights + "\n\n";
                                break;

                            case "garage":
                                Log.i("Room Check", rooms[j]);
                                garageCo2 = Integer.parseInt(jsonPart.getString("CO2 level"));
                                garageLights = jsonPart.getString("Lights");
                                garageTemparature = Integer.parseInt(jsonPart.getString("Temparature"));
                                mainJ += "Garage:\n" + "Temp: " + kitchenTemparature + "\nLigths: " + kitchenLights + "\nCo2 Level: " + garageCo2 + "\n\n";
                                break;
                        }
                    }
                }

                Log.i("Result Check", mainJ);
                tvAir.setText("Air Quality & Temparature:\nLiving Room: " + livingroomTemparature + "\nKitchen: " + kitchenTemparature + "\nGarage: " + garageTemparature + "\nGarage Co2: " + garageCo2);
                tvLights.setText("Lights:\n Living Room:" + livingroomLights + "\nKitchen:" + kitchenLights + "\nGarage: " + garageLights );

                System.out.println("Result Check " + mainJ);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.welcome);
        mp.start();

        DownloadTask task = new DownloadTask();

        String result = null;

        tvAir = (TextView) findViewById(R.id.tvAir);
        tvLights = (TextView) findViewById(R.id.tvLights);

        try {
            result = task.execute("https://gist.githubusercontent.com/caggri/369f0d9143218bb3f4297cbebb9408ab/raw/f550d0677488322d53c4721908de872c54f163e6/data.json").get();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.garageButton:
                intent = new Intent(this, GarageActivity.class);
                Bundle bundleG = new Bundle();
                bundleG.putInt("temperature", garageTemparature);
                bundleG.putString("light", garageLights);
                bundleG.putInt("carbon", garageCo2);

                intent.putExtras(bundleG);
                startActivity(intent);
                break;
            case R.id.kitchenButton:
                intent = new Intent(this, KitchenActivity.class);
                Bundle bundleK = new Bundle();
                bundleK.putInt("temperature", kitchenTemparature);
                bundleK.putString("light", kitchenLights);
                //bundleK.putInt("carbon", kitchenCo2);

                intent.putExtras(bundleK);
                startActivity(intent);
                break;
            case R.id.livingRoomButton:
                intent = new Intent(this, LivingroomActivity.class);
                Bundle bundleL = new Bundle();
                bundleL.putInt("temperature", livingroomTemparature);
                bundleL.putString("light", livingroomLights);
                //bundleL.putInt("carbon", livingroomCo2);

                intent.putExtras(bundleL);
                startActivity(intent);
                break;
            case R.id.infoImageView:
                intent = new Intent(this, info.class);
                startActivity(intent);
                break;
            case R.id.exitButton:
                makeAndShowDialogBox("Are you sure want to exit?");
        }
    }

    private void displayToast(String msg) {

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();

    }

    public void makeAndShowDialogBox(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finishAffinity();
            }
        });


        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}