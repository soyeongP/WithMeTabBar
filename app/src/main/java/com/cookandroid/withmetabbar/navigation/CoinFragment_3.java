package com.cookandroid.withmetabbar.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class CoinFragment_3 extends Fragment {
    private ListView list;
    private View view;

    public static CoinFragment_3 newinstance(){
        CoinFragment_3 frag_4_3 = new CoinFragment_3();
        return frag_4_3;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.coin_fragment_3,container,false);

    
        return view;

    }
}
