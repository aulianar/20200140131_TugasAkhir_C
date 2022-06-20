package com.example.parkingapp.view.edit;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.DBHelper;
import com.example.parkingapp.helper.WidgetHelper;

public class EditController {

    public int index;
    public String command;
    public String prev;

    private Handler handler;
    private DBHelper dbHelper;

    public EditController(Context context) {
        handler = new Handler();
        dbHelper = new DBHelper(context);
    }

    public void edit(Context context, String name, ProgressBar spinner) {
        int img = index == 0 ? R.drawable.ic_motorcycle
        : index == 1 ? R.drawable.ic_car : R.drawable.ic_sedan;
        String subtitleAddOn = !command.isEmpty() ? "diubah" : "ditambahkan";
        handler.postDelayed(() -> {
            spinner.setVisibility(View.GONE);
            if (!name.isEmpty()) {
                if (command == "update") {
                    if (dbHelper.change(command, index, prev, name)) {
                        WidgetHelper.shared.showBaseDialog(
                                context, img, "Yeayy!!",
                                "Kendaraan berhasil " + subtitleAddOn + ".", true
                        );

                    } else {
                        WidgetHelper.shared.showBaseDialog(
                                context, img, "Ooops!!",
                                "Kendaraan gagal " + subtitleAddOn + ".", false
                        );
                    }
                } else {
                    if (dbHelper.change(command, index, name, name)) {
                        WidgetHelper.shared.showBaseDialog(
                                context, img, "Yeayy!!",
                                "Kendaraan berhasil " + subtitleAddOn + ".", true
                        );
                    } else {
                        WidgetHelper.shared.showBaseDialog(
                                context, img, "Ooops!!",
                                "Kendaraan gagal " + subtitleAddOn + ".", false
                        );
                    }
                }
            } else {
                WidgetHelper.shared.showBaseDialog(
                        context, img, "Ooops!!",
                        "Plat nomor kendaraan harus diisi.", false
                );
            }
        }, 3000);
    }
}
