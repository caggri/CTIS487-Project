package com.cagribayram.denizhancetin.project;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private Button kitchenButton;
    private Button livingRoomButton;
    private Button garageButton;
    private Button mExit;
    private ImageView mImageView;
    private ConstraintLayout frameLayout;
    private GestureDetectorCompat mDetector;


    private TextView tvAir, tvLights;
    Intent intent;
    Toast mToast;

    String mainJ = "";
    int garageCo2, livingroomCo2, kitchenCo2;
    String garageLights, livingroomLights, kitchenLights;
    int garageTemparature, kitchenTemparature, livingroomTemparature;
    String[] rooms = {"living_room", "kitchen", "garage"};
    String part;
    private InfoFragment mInfoFragment;
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

                                Log.i("Value check", livingroomLights + "\n" + livingroomTemparature);
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
                                garageLights = jsonPart.getString("Lights");
                                garageTemparature = Integer.parseInt(jsonPart.getString("Temparature"));
                                mainJ += "Garage:\n" + "Temp: " + kitchenTemparature + "\nLigths: " + kitchenLights + "\nCo2 Level: " + garageCo2 + "\n\n";
                                break;
                        }
                    }
                }

                Log.i("Result Check", mainJ);
                tvAir.setText("Temparature:\nLiving Room: " + livingroomTemparature + "\nKitchen: " + kitchenTemparature + "\nGarage: " + garageTemparature);
                tvLights.setText("Lights:\nLiving Room:" + livingroomLights + "\nKitchen:" + kitchenLights + "\nGarage: " + garageLights );

                System.out.println("Result Check " + mainJ);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public HomeFragment() {
        // Required empty public constructor
        mInfoFragment = new InfoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView =  inflater.inflate(R.layout.fragment_home, container, false);
        DownloadTask task = new DownloadTask();

        String result = null;

        tvAir = (TextView) mView.findViewById(R.id.tvAir);
        tvLights = (TextView) mView.findViewById(R.id.tvLights);
        garageButton = (Button) mView.findViewById(R.id.garageButton);
        kitchenButton = (Button) mView.findViewById(R.id.kitchenButton);
        livingRoomButton = (Button) mView.findViewById(R.id.livingRoomButton);
        mExit = (Button) mView.findViewById(R.id.exitButton);
        frameLayout = (ConstraintLayout) mView.findViewById(R.id.frameLayout);

        mExit.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) {
               // makeAndShowDialogBox("Are you sure want to exit?");
                String result = null;
                DownloadTask task = new DownloadTask();
                try {
                    result = task.execute("http://18.206.216.144:8080/get").get();
                } catch (Exception e) {
                    e.getStackTrace();
                }



            }
        });

        //kitchen
        kitchenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), RoomActivity.class);
                Bundle bundleK = new Bundle();
                bundleK.putInt("temperature", kitchenTemparature);
                bundleK.putString("light", kitchenLights);
                bundleK.putString("roomName", "Kitchen");

                //bundleK.putInt("carbon", kitchenCo2);

                intent.putExtras(bundleK);
                startActivity(intent);
            }
        });
        //Living room

        livingRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), RoomActivity.class);
                Bundle bundleL = new Bundle();
                bundleL.putInt("temperature", livingroomTemparature);
                bundleL.putString("light", livingroomLights);
                bundleL.putString("roomName", "Living Room");
                //bundleL.putInt("carbon", livingroomCo2);

                intent.putExtras(bundleL);
                startActivity(intent);
            }
        });

        //Garage Button on click
        garageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), RoomActivity.class);
                Bundle bundleG = new Bundle();
                bundleG.putInt("temperature", garageTemparature);
                bundleG.putString("light", garageLights);
                bundleG.putString("roomName", "Garage");
                //bundleG.putInt("carbon", garageCo2);

                intent.putExtras(bundleG);
                startActivity(intent);

            }
        });





        try {
            result = task.execute("http://18.206.216.144:8080/get").get();
        } catch (Exception e) {
            e.getStackTrace();
        }

        mDetector = new GestureDetectorCompat(getContext(), new MyGestureListener());

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return HomeFragment.this.mDetector.onTouchEvent(motionEvent);
            }
        });

        return mView;
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);

            //MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, infoFragment, null).addToBackStack(null).commit();
            MainActivity.mFragmentManager.beginTransaction().replace(R.id.fragment_container, mInfoFragment, null).addToBackStack(null).commit();

        }
    }

    private void displayToast(String msg) {

        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        mToast.show();

    }

    public void makeAndShowDialogBox(String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                onStop();
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

   /* private boolean haveNetworkConnection() {
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
    }*/
}
