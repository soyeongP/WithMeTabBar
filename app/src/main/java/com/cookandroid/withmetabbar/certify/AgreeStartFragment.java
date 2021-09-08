package com.cookandroid.withmetabbar.certify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class AgreeStartFragment extends Fragment {

    Button btn_yes;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.agree_start_fragment, container, false);

        btn_yes= vGroup.findViewById(R.id.btn_yes);
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CertifyStartFragment certifyStartFragment= new CertifyStartFragment();
                ((MainActivity2)getActivity()).replaceFragment(certifyStartFragment);
            }
        });

        return vGroup;
    }


}

