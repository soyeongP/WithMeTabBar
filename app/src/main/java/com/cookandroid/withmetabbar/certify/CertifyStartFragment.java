package com.cookandroid.withmetabbar.certify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class CertifyStartFragment extends Fragment {
    Button btn4;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.certify_start_fragment, container, false);

        btn4= vGroup.findViewById(R.id.btn_check);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinStartFragment joinStartFragment= new JoinStartFragment();
                ((MainActivity2)getActivity()).replaceFragment(joinStartFragment);
            }
        });

        return vGroup;
    }


}


