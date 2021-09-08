package com.cookandroid.withmetabbar.navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    // 프래그먼트 교체를 보여주는 처리를 구
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return CoinFragment_1.newinstance();
            case 1:
                return CoinFragment_2.newinstance();
            case 2:
                return CoinFragment_3.newinstance();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {

        return 3;
    }

    //상단의 탭 레이아웃 인디케이터 쪽에 텍스트를 선언해주는 곳
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "코인충전";
           case 1:
                return "무료충전";
            case 2:
                return "게임/충전";
            default:
                return null;
        }
    }
}
