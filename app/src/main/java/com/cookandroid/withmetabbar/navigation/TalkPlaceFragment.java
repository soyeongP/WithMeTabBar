package com.cookandroid.withmetabbar.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class TalkPlaceFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);


        //return vGroup;
        return inflater.inflate(R.layout.activity_home,container,false);
    }
}
