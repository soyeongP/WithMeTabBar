package com.cookandroid.withmetabbar.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.MainActivity;
import com.cookandroid.withmetabbar.R;

public class OndoFragment extends Fragment {
    Button btn_ondo_check;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.fragment_ondo, container, false);

        btn_ondo_check= vGroup.findViewById(R.id.btn_ondo_check);
        btn_ondo_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageFragment myPageFragment= new MyPageFragment();
                ((MainActivity)getActivity()).replaceFragment(myPageFragment);
            }
        });

        return vGroup;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
