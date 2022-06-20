package com.example.parkingapp.view.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parkingapp.R;
import com.example.parkingapp.helper.DBHelper;
import com.example.parkingapp.helper.WidgetHelper;
import com.example.parkingapp.view.edit.EditActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private final int index;
    private String[] names;

    private TextView titleTV, subtitleTV;
    private ListView listView;
    private BaseAdapter adapter;

    private DBHelper dbHelper;

    public HomeFragment(Integer index) {
        this.index = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();

        adapter = new ListAdapter(context);
        dbHelper = new DBHelper(context);

        titleTV = view.findViewById(R.id.home_fragment_title_tv);
        subtitleTV = view.findViewById(R.id.home_fragment_subtitle_tv);
        listView = view.findViewById(R.id.home_fragment_lv);

        titleTV.setText(
            index == 0 ? "Sepeda Motor": index == 1
            ? "Mobil": "Mobil VIP"
        );

        listView.setAdapter(adapter);

        fetch();
    }

    private class ListAdapter extends BaseAdapter {

        private final Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            try {
                return names.length;
            } catch (NullPointerException e) {
                return 0;
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return Long.valueOf(i);
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = getLayoutInflater();

            View rendered = layoutInflater.inflate(R.layout.item_home, viewGroup, false);

            ImageView itemIconIV = rendered.findViewById(R.id.home_item_icon_iv);
            TextView titleTV = rendered.findViewById(R.id.home_item_title_tv);

            int img = index == 0 ? R.drawable.ic_motorcycle : index == 1
            ? R.drawable.ic_car : R.drawable.ic_sedan;

            itemIconIV.setImageDrawable(ContextCompat.getDrawable(context, img));

            String name = String.valueOf(Array.get(names, position));

            titleTV.setText(name);

            rendered.setOnClickListener(render -> {
                showEditMenuDialog(name);
            });

            return rendered;
        }
    }

    public void showEditMenuDialog(String prev) {
        final Context context = getContext();
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.getDefaultFeatures(context));
        dialog.setContentView(R.layout.dialog_home);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.show();

        final LinearLayout dialogEdit = dialog.findViewById(R.id.home_dialog_edit);
        final LinearLayout dialogCheckout = dialog.findViewById(R.id.home_dialog_checkout);

        dialogEdit.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("index", index);
            intent.putExtra("command", "update");
            intent.putExtra("prev", prev);
            context.startActivity(intent);
        });

        dialogCheckout.setOnClickListener(view -> {
            DBHelper dbHelper = new DBHelper(context);
            int img = index == 0 ? R.drawable.ic_motorcycle
                    : index == 1 ? R.drawable.ic_car : R.drawable.ic_sedan;
            if (dbHelper.change("delete", index, prev, "")) {
                WidgetHelper.shared.showBaseDialog(context, img, "Yeayy!!", "Kendaraan berhasil dihapus.", false);
                dialog.dismiss();
            } else {
                WidgetHelper.shared.showBaseDialog(context, img, "Ooops!!", "Kendaraan gagal dihapus.", false);
                dialog.dismiss();
            }
            refresh();
        });
    }

    private void fetch() {
        Cursor cursor = dbHelper.get(index);
        names = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            names[i] = cursor.getString(1);
        }
        subtitleTV.setText("Slot terisi: " + names.length + "/20");
    }

    private void refresh() {
        names = new String[0];
        Arrays.fill(names, null);
        fetch();
        adapter.notifyDataSetChanged();
    }
}