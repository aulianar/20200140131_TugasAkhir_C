package com.example.parkingapp.view.home;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.parkingapp.helper.DBHelper;

public class HomeController {

    public int index;

    public static class FragmentController {

        public String[] names;
        public int index;

        private DBHelper dbHelper;

        public FragmentController(Context context) {
            dbHelper = new DBHelper(context);
        }

        public void fetch() {
            Cursor cursor = dbHelper.get(index);
            names = new String[cursor.getCount()];
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                names[i] = cursor.getString(1);
            }
        }
    }
}
