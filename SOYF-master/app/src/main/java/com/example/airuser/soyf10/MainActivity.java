package com.example.airuser.soyf10;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.facebook.*;
import com.facebook.Profile;

import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent fromLogin = getIntent();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Profile profile = Profile.getCurrentProfile();


        /*new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{user-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        textView.setText("Greetings, " + response);

                    }
                }
        ).executeAsync();*/
        //textView.setText("Greetings, "+ Profile.getCurrentProfile().getFirstName());
        /*GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                    }
                });*/
        Bundle parameters = new Bundle();
        //parameters.putString("fields", "id,name,link");
        //request.setParameters(parameters);
        //request.executeAsync();
        //String facebookID = fromLogin.getStringExtra("facebookID");
        Intent intent = new Intent(this, StepCounterService.class);
        startService(intent);
        SharedPreferences settings = getSharedPreferences("Pref_data", 0);
        editor = settings.edit();

        Calendar calendar = Calendar.getInstance();
        int day = settings.getInt("dayOfYear", 0);
        int year = settings.getInt("year", 0);
        if(calendar.get(Calendar.DAY_OF_YEAR) != day || calendar.get(Calendar.YEAR) != year){
            editor.putInt("dailyStep", 0);
        }

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("dayOfYear", calendar.get(Calendar.DAY_OF_YEAR));
        editor.putInt("year", calendar.get(Calendar.YEAR));
        editor.commit();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToSettingsActivity();
            }
        });

        int total = settings.getInt("totalSteps", -1);
        int daily = settings.getInt("totalSteps", -1);
        textView = (TextView) findViewById(R.id.textView);


        textView2 = (TextView) findViewById(R.id.textView2);
        if(fromLogin.getBooleanExtra("registration",false))
        {
            textView2.setText("Registration successful. Welcome to Step On Your Friends, "+ profile.getName());
        }
        else{
            textView2.setText("Welcome back to Step On Your Friends, "+ profile.getName());
        }

        textView.setText("Total steps: " + total +" Daily steps: " + daily);


    }





    protected void onStop() {
        super.onStop();


    }

    private void goToSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}

