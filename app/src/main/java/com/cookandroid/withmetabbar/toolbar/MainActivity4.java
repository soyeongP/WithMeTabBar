package com.cookandroid.withmetabbar.toolbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.navigation.AlarmFragment;
import com.cookandroid.withmetabbar.navigation.HomeFragment;
import com.cookandroid.withmetabbar.navigation.PlaceCheckFragment;

public class MainActivity4 extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();//1
    AlarmFragment alarmFragment = new AlarmFragment();//2
    HomeFragment homeFragment = new HomeFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);



    }
}