package com.example.parkingapp.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.parkingapp.R;
import com.example.parkingapp.view.edit.EditActivity;

public class WidgetHelper {
    public static WidgetHelper shared = new WidgetHelper();

    public void showBaseDialog(Context context, int img, String title, String subtitle, boolean finish) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.getDefaultFeatures(context));
        dialog.setContentView(R.layout.dialog_base);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

        ImageView dialogIV = dialog.findViewById(R.id.base_dialog_iv);
        TextView titleTV = dialog.findViewById(R.id.base_dialog_title_tv);
        TextView subtitleTV = dialog.findViewById(R.id.base_dialog_subtitle_tv);
        Button dialogBtn = dialog.findViewById(R.id.base_dialog_btn);

        dialogIV.setImageDrawable(ContextCompat.getDrawable(context, img));
        titleTV.setText(title);
        subtitleTV.setText(subtitle);

        dialogBtn.setOnClickListener(view -> {
            dialog.dismiss();
            if (finish) ((Activity) context).finish();
        });
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
