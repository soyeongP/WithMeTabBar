package com.cookandroid.withmetabbar.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.R;

public class FriendsInviteFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.fragment_friends_invite, container, false);



        return vGroup;
    }


}


