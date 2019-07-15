package com.cagribayram.denizhancetin.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private TextView tv;
    Intent intent;
    Toast mToast;

    String mainJ= "";
    Integer garageCarbon, kitchenCarbon, livingroomCarbon;
    String garageLight, livingroomLight, kitchenLight;
    Integer garageTemparature, kitchenTemparature, livingroomTemparature;
    String[] rooms ={"LivingroomActivity", "KitchenActivity", "GarageActivity"};


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

                while (data != -1){
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

                for (int j = 0; j < rooms.length; j++){
                    weatherInfo = jsonObject.getString(rooms[j]);


                    arr = new JSONArray(weatherInfo);

                    for (int i = 0; i < arr.length(); i++) {
                        jsonPart = arr.getJSONObject(i);
                        switch (rooms[j]){

                            case "LivingroomActivity":
                                Log.i("Room Check", rooms[j]);

                                livingroomLight = jsonPart.getString("Light");
                                livingroomTemparature = Integer.parseInt(jsonPart.getString( "Temparature"));
                                //Log.i("Value checj", livingroomLight + "\n"  + livingroomTemparature);

                                mainJ += "Living Room:\n" + "Temp: " + livingroomTemparature + "\nLigths: " + livingroomLight + "\n\n";
                                break;

                            case "KitchenActivity":
                                Log.i("Room Check", rooms[j]);
                                kitchenLight = jsonPart.getString("Light");
                                kitchenTemparature = Integer.parseInt(jsonPart.getString("Temparature"));
                                mainJ += "Kitchen:\n" + "Temp: " + kitchenTemparature + "\nLigths: " + kitchenLight + "\n\n";
                                break;

                            case "GarageActivity":
                                Log.i("Room Check", rooms[j]);
                                garageCarbon = Integer.parseInt(jsonPart.getString("Carbon level"));
                                garageLight = jsonPart.getString("Light");
                                garageTemparature = Integer.parseInt(jsonPart.getString("Temparature"));
                                mainJ += "Garage:\n" + "Temp: " + kitchenTemparature + "\nLigths: " + kitchenLight + "\nCarbon Level: " + garageCarbon + "\n\n";
                                break;


                        }

                    }

                }

                Log.i("Result Check", mainJ);
                tv.setText(mainJ);
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

        DownloadTask task = new DownloadTask();

        String result = null;

        tv = (TextView) findViewById(R.id.tv);

        try {
            result = task.execute("https://gist.githubusercontent.com/caggri/369f0d9143218bb3f4297cbebb9408ab/raw/f550d0677488322d53c4721908de872c54f163e6/data.json").get();
        }
        catch (Exception e){

            e.getStackTrace();
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.garageButton:
                intent = new Intent(this, GarageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("temperature", "hej temp");
                bundle.putString("light", "hej light");
                bundle.putString("carbon", "hej carbon");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.kitchenButton:
                /*bundle = new Bundle();
                bundle.putInt("temperature", kitchenTemparature);
                bundle.putString("light", kitchenLight);
                bundle.putInt("carbon", kitchenCarbon);
                intent.putExtras(bundle);
                intent = new Intent(this, KitchenActivity.class);
                startActivity(intent);*/
                break;

            case R.id.livingRoomButton:
                /*bundle = new Bundle();
                bundle.putInt("temperature", livingroomTemparature);
                bundle.putString("light", livingroomLight);
                bundle.putInt("carbon", livingroomCarbon);
                intent.putExtras(bundle);
                intent = new Intent(this, LivingroomActivity.class);
                startActivity(intent);*/
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
