package com.cookandroid.withmetabbar.navigation;

import
        android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class CoinFragment_1 extends Fragment {
    private View view;
    ArrayAdapter<String> cAdapter;

    public static CoinFragment_1 newinstance() {
        CoinFragment_1 frag_4_1 = new CoinFragment_1();
        return frag_4_1;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.coin_fragment_1, container, false);

        return view;
    }



}
