package com.example.parkingapp.view.edit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.WidgetHelper;

public class EditActivity extends AppCompatActivity {

    private EditController controller;
    private ImageButton backButton;
    private TextView editTitleTV;
    private ImageView editIconIV;
    private EditText editET;
    private Button editButton;
    private ProgressBar spinner;

    @SuppressLint({"UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        setContentView(R.layout.activity_edit);

        controller = new EditController(this);
        backButton = findViewById(R.id.edit_back_btn);
        editTitleTV = findViewById(R.id.edit_title_tv);
        editET = findViewById(R.id.edit_edit_text);
        editIconIV = findViewById(R.id.edit_icon_iv);
        editButton = findViewById(R.id.edit_edit_btn);
        spinner = findViewById(R.id.edit_spiner);

        controller.index = getIntent().getIntExtra("index", 0);
        controller.command = getIntent().getStringExtra("command");
        controller.prev = getIntent().getStringExtra("prev");

        editTitleTV.setText(!controller.command.isEmpty() ? "Edit Kendaraan" : "Tambah Kendaraan");

        int drawable = controller.index == 0 ? R.drawable.ic_motorcycle
        : controller.index == 1 ? R.drawable.ic_car : R.drawable.ic_sedan;
        editIconIV.setImageDrawable(ContextCompat.getDrawable(this, drawable));

        backButton.setOnClickListener(view -> finish());

        editButton.setText(!controller.command.isEmpty() ? "edit" : "tambah");
        editButton.setOnClickListener(view -> {
            spinner.setVisibility(View.VISIBLE);
            WidgetHelper.shared.hideKeyboard(this);
            controller.edit(this, editET.getText().toString(), spinner);
        });
    }
}