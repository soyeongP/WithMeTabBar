package com.cookandroid.withmetabbar.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cookandroid.withmetabbar.R;

import java.util.List;

public class DetailedRegionAdapter extends BaseAdapter {

    private final List<String> regions;

    private final LayoutInflater inflater;

    public DetailedRegionAdapter(Context context, List<String> regions){
        this.regions = regions;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return regions.size();
    }

    @Override
    public Object getItem(int i) {
        return regions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = inflater.inflate(R.layout.item_region_detailed, viewGroup, false);
        }

        TextView tvRegion = view.findViewById(R.id.tv_item_detailed_region);
        tvRegion.setText(regions.get(i));

        return view;
    }
}