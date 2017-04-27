package com.example.airuser.soyf10;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity{
    Button bLogin;
    LoginButton login;
    CallbackManager cbManager;
    LoginButton register;
    String facebookID;

    EditText etUsername, etPassword;
    TextView tvRegisterLink;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        cbManager = CallbackManager.Factory.create();
        login = (LoginButton) findViewById(R.id.fbLogin);

        login.registerCallback(cbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tvRegisterLink.setText("It seems like you have not registered yet. Click here to register.");
                String userID = loginResult.getAccessToken().getUserId();
                facebookID = loginResult.getAccessToken().getUserId();
                final AccessToken accessToken = loginResult.getAccessToken();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                //String name = jsonResponse.getString("name");
                                //int age = jsonResponse.getInt("age");
                                facebookID = jsonResponse.getString("facebookID");




                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("facebookID", facebookID);
                                intent.putExtra("accessToken", accessToken);

                                //intent.putExtra("name", name);
                                //intent.putExtra("age", age);

                                Login.this.startActivity(intent);
                            } else {

                                facebookID = jsonResponse.getString("facebookID");
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Login failed. Try to register instead?")
                                        /*.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");

                                                            if (success) {


                                                                Intent intent = new Intent(Login.this, MainActivity.class);
                                                                intent.putExtra("facebookID",facebookID);
                                                                Login.this.startActivity(intent);
                                                            } else {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                                                builder.setMessage("Register Failed")
                                                                        .setNegativeButton("Retry", null)
                                                                        .create()
                                                                        .show();
                                                            }

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                FacebookRegisterRequest registerRequest = new FacebookRegisterRequest(facebookID, responseListener);
                                                RequestQueue queue = Volley.newRequestQueue(Login.this);
                                                queue.add(registerRequest);
                                            }
                                        })*/
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                FacebookLoginRequest facebookLoginRequest  = new FacebookLoginRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(facebookLoginRequest);
            }

            @Override
            public void onCancel() {
                tvRegisterLink.setText("canceled");
            }


            @Override
            public void onError(FacebookException error) {
                tvRegisterLink.setText("error");

            }


        });
        /*register = (LoginButton) findViewById(R.id.fbRegister);

        register.registerCallback(cbManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                String userID = loginResult.getAccessToken().getUserId();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String facebookID = jsonResponse.getString("facebookID");
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("facebookID", facebookID);
                                Login.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };

                FacebookRegisterRequest registerRequest = new FacebookRegisterRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(registerRequest);

                //User user = new User(name,age,username,password);

                //registerUser(user);
            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {

            }

        });*/


        tvRegisterLink= (TextView) findViewById(R.id.tvRegisterLink);

        tvRegisterLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                /*Intent registerIntent = new Intent(Login.this, Register.class);
                registerIntent.putExtra("facebookID", facebookID);
                Login.this.startActivity(registerIntent);*/
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("facebookID",facebookID);
                                intent.putExtra("registration", true);
                                Login.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };

                FacebookRegisterRequest registerRequest = new FacebookRegisterRequest(facebookID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(registerRequest);
            }
        });
        /*bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String name = jsonResponse.getString("name");
                                int age = jsonResponse.getInt("age");

                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("age", age);
                                intent.putExtra("username", username);
                                Login.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);
            }
        });*/


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        cbManager.onActivityResult(requestCode, resultCode,data);
    }

    //userLocalStore = new UserLocalStore(this);



}


    /*public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogin:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();


                User user = new User(username,password);

                authenticate(user);

                break;
            case R.id.bLogout:

                break;

            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
    private void authenticate(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback(){
            public void done(User returnedUser)
            {
                if(returnedUser == null)
                {
                    showErrorMessage();
                }
                else {


                    logUserIn(returnedUser);
                }
            }

        });
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Invalid user details");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();
    }
    private void logUserIn(User returnedUser){
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }*/

