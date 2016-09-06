package com.example.rujuls.datafeed;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    EditText reg_user;
    EditText reg_pass;
    EditText reg_ward;
    EditText reg_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_user = (EditText) findViewById(R.id.username);
        reg_pass = (EditText) findViewById(R.id.pass);
        reg_ward = (EditText) findViewById(R.id.editText9);
        reg_name = (EditText) findViewById(R.id.reg_name);

    }

    public void onReg(View v) {

        final String name = reg_name.getText().toString();
        final String username = reg_user.getText().toString();
        final String password = reg_pass.getText().toString();
        final int age = Integer.parseInt(reg_ward.getText().toString());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(Register.this, LoginActivity2.class);
                        Register.this.startActivity(intent);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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

        RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        queue.add(registerRequest);
    }
}