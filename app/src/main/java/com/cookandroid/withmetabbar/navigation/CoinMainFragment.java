package com.cookandroid.withmetabbar.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class CoinMainFragment extends Fragment {
    private FragmentPagerAdapter fragmentPagerAdapter;//코인충전어뎁터
    private FragmentManager getSupportFragmentManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.coin_main_fragment, container, false);

        //뷰페이저 세팅
        ViewPager area_pager1 = vGroup.findViewById(R.id.area_pager1);
        fragmentPagerAdapter = new ViewPagerAdapter(getFragmentManager());

        TabLayout tabLayout = vGroup.findViewById(R.id.tab_regi_area);
        area_pager1.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(area_pager1);


        return vGroup;
    }


}






