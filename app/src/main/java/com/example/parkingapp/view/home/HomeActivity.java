package com.example.parkingapp.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.WidgetHelper;
import com.example.parkingapp.view.edit.EditActivity;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {

    private HomeController controller;
    private HomeAdapter pagerAdapter;
    private ImageButton plusButton, menuButton;
    private ChipNavigationBar navigationBar;
    private FragmentManager fragmentManager;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        setContentView(R.layout.activity_home);

        controller = new HomeController();
        menuButton = findViewById(R.id.home_menu_btn);
        plusButton = findViewById(R.id.home_plus_button);
        navigationBar = findViewById(R.id.home_navbar);
        fragmentManager = getSupportFragmentManager();
        viewPager = findViewById(R.id.home_view_pager);

        pagerAdapter = new HomeAdapter(fragmentManager, getLifecycle());
        viewPager.setAdapter(pagerAdapter);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                controller.index = position;
                if (position == 0) {
                    navigationBar.setItemSelected(R.id.menu_motorcycle, true);
                } else if (position == 1) {
                    navigationBar.setItemSelected(R.id.menu_car, true);
                } else if (position == 2) {
                    navigationBar.setItemSelected(R.id.menu_sedan, true);
                }
            }
        });

        navigationBar.setItemSelected(R.id.menu_motorcycle, true);
        navigationBar.setOnItemSelectedListener(i -> {
            if (i == R.id.menu_car) {
                controller.index = 1;
                viewPager.setCurrentItem(1, true);
            } else if (i == R.id.menu_sedan) {
                controller.index = 2;
                viewPager.setCurrentItem(2, true);
            } else {
                controller.index = 0;
                viewPager.setCurrentItem(0, true);
            }
        });

        plusButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("index", controller.index);
            intent.putExtra("command", "");
            startActivity(intent);
//            showDialog();
        });

        menuButton.setOnClickListener(view -> showSideBar());
    }

    private void showSideBar() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.getDefaultFeatures(this));
        dialog.setContentView(R.layout.menu_sidebar_home);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.SideBarAnimation;
        dialog.getWindow().setGravity(Gravity.START);
        dialog.show();
    }
}