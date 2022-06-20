package com.example.parkingapp.view.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.CommonHelper;
import com.example.parkingapp.helper.DBHelper;
import com.example.parkingapp.view.home.HomeActivity;
import com.example.parkingapp.view.home.HomeAdapter;
import com.example.parkingapp.view.login.LoginActivity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private ViewPager2 viewPager;
    private HomeAdapter pagerAdapter;
    private ChipNavigationBar navigationBar;
    private ImageButton plusButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getUser();

        Intent intent;

        if (cursor.getCount() != 0) {
            intent = new Intent(this, HomeActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }
}