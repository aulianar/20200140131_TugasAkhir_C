package com.example.parkingapp.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.WidgetHelper;
import com.example.parkingapp.view.about.AboutActivity;
import com.example.parkingapp.view.home.HomeController;

public class LoginActivity extends AppCompatActivity {

    private LoginController controller;
    private TextView aboutUsTV;
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBtn;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        setContentView(R.layout.activity_login);

        controller = new LoginController(this);
        usernameET = findViewById(R.id.login_username_et);
        passwordET = findViewById(R.id.login_password_et);
        loginBtn = findViewById(R.id.login_btn);
        spinner = findViewById(R.id.login_spinner);
        aboutUsTV = findViewById(R.id.login_about_us_tv);

        loginBtn.setOnClickListener(view -> {
            spinner.setVisibility(View.VISIBLE);
            WidgetHelper.shared.hideKeyboard(this);
            controller.login(usernameET.getText().toString(), passwordET.getText().toString(), spinner);
        });

        aboutUsTV.setOnClickListener(view -> {

        });


    }
}