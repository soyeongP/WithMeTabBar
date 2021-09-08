package com.cookandroid.withmetabbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.cookandroid.withmetabbar.certify.MainActivity2;
import com.cookandroid.withmetabbar.model.Member;
import com.cookandroid.withmetabbar.navigation.AlarmFragment;
import com.cookandroid.withmetabbar.navigation.ChatFragment;
import com.cookandroid.withmetabbar.navigation.CoinMainFragment;
import com.cookandroid.withmetabbar.navigation.DetailViewFragment;
import com.cookandroid.withmetabbar.navigation.FragmentPlus;
import com.cookandroid.withmetabbar.navigation.HomeFragment;
import com.cookandroid.withmetabbar.navigation.MyPageFragment;
import com.cookandroid.withmetabbar.navigation.PlaceCheckFragment;
import com.cookandroid.withmetabbar.navigation.TalkPlaceFragment;
import com.cookandroid.withmetabbar.navigation.UserFragment;
import com.cookandroid.withmetabbar.toolbar.MainActivity3;
import com.cookandroid.withmetabbar.toolbar.MainActivity4;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    //주석으로 test

    UserFragment userFragment= new UserFragment();
    AlarmFragment alarmFragment= new AlarmFragment();
    //HomeFragment homeFragment= new HomeFragment();
    MainActivityHome mainActivityHome=new MainActivityHome();
    FragmentPlus fragmentPlus= new FragmentPlus();
    MyPageFragment myPageFragment = new MyPageFragment();
    ChatFragment chatFragment= new ChatFragment();//채팅프래그먼트
    //DetailViewFragment detailViewFragment = new DetailViewFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();//for 프래그먼트 전환
    CoinMainFragment coinMainFragment = new CoinMainFragment(); //코인 충전 프레그 먼트
    PlaceCheckFragment placeCheckFragment = new PlaceCheckFragment();//위치인증 프레그 먼트
    TalkPlaceFragment talkPlaceFragment = new TalkPlaceFragment();//톡방 프레그먼트 아직 xml없음

    Toolbar tb;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction = fragmentManager.beginTransaction();//트랜잭션 생성
        //FragmentTransaction.add(R.id.main_content, MyPageFragment.newInstance()).commit();//new

        //transaction.replace(R.id.main_content, homeFragment).commitAllowingStateLoss();//처음에 띄우는 fragment
        transaction.replace(R.id.main_content, mainActivityHome).commitAllowingStateLoss();//처음에 띄우는 fragment



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()   {

            //로그인에서 넣어준 intent 받기
            final Intent intentMain = getIntent();
            public final String uid = intentMain.getStringExtra("uid");
            final Member member = new Member();



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                switch (item.getItemId()) {
                    case R.id.action_home:
                        //Intent intentHome= new Intent(getApplicationContext(), MainActivityHome.class);
                        //startActivity(intentHome);
                        transaction.replace(R.id.main_content, mainActivityHome).commitAllowingStateLoss();

                        //homeFragment.setArguments(bundle);
                        //transaction.replace(R.id.main_content, homeFragment).commitAllowingStateLoss();
                        //transaction.addToBackStack(null);
                        //transaction.commit();
                        //fragmentManager.beginTransaction().remove(detailViewFragment.this).commit;
                        //fragmentManager.popBackStack();

                        break;
                    case R.id.action_love:
                        transaction.replace(R.id.main_content, talkPlaceFragment).commitAllowingStateLoss();
                        break;
                    case R.id.action_plus:
                        //transaction.add(R.id.main_content,fragmentPlus).addToBackStack(null).commit();
                        transaction.replace(R.id.main_content, fragmentPlus).commitAllowingStateLoss();
                        Bundle bundle = new Bundle();
                        bundle.putString("uid",uid);
                        fragmentPlus.setArguments(bundle);
                        break;
                    case R.id.action_chatroom:
                        transaction.replace(R.id.main_content, chatFragment).commitAllowingStateLoss();
                        break;
                    case R.id.action_mypage:
                        transaction.replace(R.id.main_content, myPageFragment).commitAllowingStateLoss();
                        break;

                }


                return true;
            }
        });


        //toolbar선언
        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(tb);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로 가기 버튼 생성


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (item.getItemId()){
            case R.id.action_category:
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
                return  true;
            case R.id.action_alarm:
                Intent intent1 = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent1);

            default:
                return super.onOptionsItemSelected(item);

        }

    }













    public void replaceFragment(Fragment fragment){
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_content2, fragment).commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment).commit();
        
    }
    public void removeFragment(Fragment fragment) {
        //getSupportFragmentManager().beginTransaction().remove(R.id.main_content2, fragment).commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment).commit();                                //로그아웃, 뒤로가기버튼 누를시
    }

    public void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_content, fragment).addToBackStack(null).commit();
    }


    @Override
    public void onBackPressed() {

//        if (mOnkeybackPressedListener != null){
//            mOnkeybackPressedListener.onBackKey();
//
//        }else {
//            if (getSupportFragmentManager().getBackStackEntryCount()==0){
//                //종료
//            }else {
//                super.onBackPressed();
//            }
//        }
        Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
        //super.onBackPressed();
    }

    //뒤로가기 버튼을 뺏어올 리스너 등록
//    public interface onKeyBackPressedListener{
//        void onBackKey();
//
//    }
//    private onKeyBackPressedListener mOnkeybackPressedListener;
//    public void setOnKeyBackPressedListener (onKeyBackPressedListener listener){
//        mOnkeybackPressedListener = listener;
//    }

}

    /*

    implements BottomNavigationView.OnNavigationItemSelectedListener


    public void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_home:
                //DetailViewFragment detailViewFragment = null;
                detailViewFragment = new DetailViewFragment();
                this.replaceFragment(detailViewFragment);
                //getSupportFragmentManager().beginTransaction().replace(R.id.main_content, detailViewFragment).commit();
                return true;
            case R.id.action_love:
                //GridFragment gridFragment = null;
                gridFragment = new GridFragment();
                this.replaceFragment(gridFragment);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, gridFragment).commit();
                return true;

            case R.id.action_plus:

                return true;

            case R.id.action_chatroom:
                //AlarmFragment alarmFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, alarmFragment).commit();
                return true;

            case R.id.action_mypage:
                //UserFragment userFragment = null;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, userFragment).commit();
                return true;
            //break;
        }

        return false;
    }

 */