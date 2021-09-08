package com.cookandroid.withmetabbar.certify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class LivePlaceFragment extends Fragment {
    Button btn_done;
  

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        ViewGroup vGroup = (ViewGroup)
                inflater.inflate(R.layout.live_place_fragment, container, false);
        btn_done= vGroup.findViewById(R.id.btn_done);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinStartFragment joinStartFragment= new JoinStartFragment();
                ((MainActivity2)getActivity()).replaceFragment(joinStartFragment);
            }
        });


        return vGroup;
    }



}
