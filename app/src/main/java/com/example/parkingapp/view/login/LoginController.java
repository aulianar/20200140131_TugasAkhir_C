package com.example.parkingapp.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.CommonHelper;
import com.example.parkingapp.helper.DBHelper;
import com.example.parkingapp.helper.WidgetHelper;
import com.example.parkingapp.view.home.HomeActivity;

public class LoginController {

    private Context context;
    private Handler handler;

    private DBHelper dbHelper;

    public LoginController(Context context) {
        this.context = context;
        handler = new Handler();
        dbHelper = new DBHelper(context);
    }

    public void login(String username, String password, ProgressBar spinner) {
        handler.postDelayed(() -> {
            spinner.setVisibility(View.GONE);
            if (!(username.isEmpty() && password.isEmpty())) {
                dbHelper.createUser(username, password);
                context.startActivity(new Intent(context, HomeActivity.class));
                ((Activity) context).finish();
            } else {
                WidgetHelper.shared.showBaseDialog(
                    context, R.drawable.ic_sad, "Login Gagal",
                    "Mohon periksa kembali username dan password anda.", false
                );
            }
        }, 2000);
    }
}
