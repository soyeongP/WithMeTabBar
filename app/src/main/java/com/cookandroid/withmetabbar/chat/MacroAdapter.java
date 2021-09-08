package com.cookandroid.withmetabbar.chat;

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

public class MacroAdapter extends BaseAdapter {

    private final List<String> macro;

    private final LayoutInflater inflater;

    private int selectedPos = 0;

    public MacroAdapter(Context context, List<String> macro){
        this.macro = macro;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return macro.size();
    }

    @Override
    public Object getItem(int i) {
        return macro.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = inflater.inflate(R.layout.item_macro, viewGroup, false);
        }

        TextView tvRegion = view.findViewById(R.id.macroitem_textview_message);
        tvRegion.setText(macro.get(i));

        if (selectedPos == i){
            Log.d("TAGTAG", "getView : "+i);
            view.setSelected(true);
        }

        return view;
    }
}