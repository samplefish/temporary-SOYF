package com.example.airuser.soyf10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginRegisterMain extends ActionBarActivity implements View.OnClickListener {
 Button bLogout;
    EditText etName, etAge, etUsername;

    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_main);


        etName= (EditText) findViewById(R.id.etName);
        etAge= (EditText) findViewById(R.id.etAge);
        etUsername= (EditText) findViewById(R.id.etUsername);

        bLogout = (Button) findViewById(R.id.bLogout);

        bLogout.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);


    }

    protected void onStart(){
        super.onStart();
        if(authenticate()==true){
            displayUserDetails();
        }
        else{
            startActivity(new Intent(LoginRegisterMain.this, Login.class));
        }
    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();

        etUsername.setText(user.username);

    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bLogout:
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;
        }
    }
}
