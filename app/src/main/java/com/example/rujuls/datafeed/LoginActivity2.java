package com.example.rujuls.datafeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText reg_user = (EditText) findViewById(R.id.username);
        final EditText reg_pass = (EditText) findViewById(R.id.pass);

        final Button bLogin = (Button) findViewById(R.id.button2);
    }

    public void SignIn(View v){

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }

    public void onRegister(View v){

        Intent r = new Intent (this, Register.class);
        startActivity(r);
    }
}
