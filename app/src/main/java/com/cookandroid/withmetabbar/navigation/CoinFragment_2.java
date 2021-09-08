package com.cookandroid.withmetabbar.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class CoinFragment_2 extends Fragment {
    private View view;

    public static CoinFragment_2 newinstance(){
        CoinFragment_2 frag_4_2 = new CoinFragment_2();
        return frag_4_2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.coin_fragment_2,container,false);

        return view;
    }
}
