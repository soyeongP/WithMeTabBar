package com.cookandroid.withmetabbar.certify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.certify.LoginStartFragment;

public class MainActivity2 extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();//1
    LoginStartFragment loginStartFragment = new LoginStartFragment();//2
    JoinStartFragment joinstartfragment = new JoinStartFragment(); //3 new

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FragmentTransaction transaction = fragmentManager.beginTransaction();//3
        transaction.replace(R.id.main_content2, loginStartFragment).commitAllowingStateLoss();//실제 프래그 먼트 전환하는 코드
    }



    public void replaceFragment(Fragment fragment){
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_content2, fragment).commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content2, fragment).commit();
    }
} //new


