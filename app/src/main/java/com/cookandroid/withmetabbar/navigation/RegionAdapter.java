package com.cookandroid.withmetabbar.navigation;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.cookandroid.withmetabbar.R;

import java.util.List;

public class RegionAdapter extends BaseAdapter {

    private final List<String> cities;

    private final LayoutInflater inflater;

    private int selectedPos = 0;

    public RegionAdapter(Context context, List<String> cities){
        this.cities = cities;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return cities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = inflater.inflate(R.layout.item_region_city, viewGroup, false);
        }

        TextView tvRegion = view.findViewById(R.id.tv_item_region_city);
        tvRegion.setText(cities.get(i));

        if (selectedPos == i){
            Log.d("TAGTAG", "getView : "+i);
            view.setSelected(true);
        }

        return view;
    }
}