package com.cagribayram.denizhancetin.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    private HomeFragment mHomeFragment;
    public static FragmentManager mFragmentManager;

    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("COnnection stst", String.valueOf(haveNetworkConnection()));
        if(haveNetworkConnection()) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.welcome);
            mp.start();
            mFragmentManager = getSupportFragmentManager();

            mFragmentTransaction = mFragmentManager.beginTransaction();
            mHomeFragment = new HomeFragment();
            mFragmentTransaction.add(R.id.fragment_container, mHomeFragment, null);
            mFragmentTransaction.commit();
        }
        else{
        makeAndShowDialogBox("No internet connection detected.");

        }


    }

    public void makeAndShowDialogBox(String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                finishAffinity();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;

    }
}