package com.cookandroid.withmetabbar.certify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.cookandroid.withmetabbar.MainActivity;
import com.cookandroid.withmetabbar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginStartFragment extends Fragment {

    Button btnLogin, btnJoin;
    EditText etId, etPw;
    private FirebaseRemoteConfig firebaseRemoteConfig;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.login_start_fragment, container, false);

        etId = vGroup.findViewById(R.id.etId);
        etPw = vGroup.findViewById(R.id.etPassword);
        btnLogin= vGroup.findViewById(R.id.btnLogin);
        btnJoin= vGroup.findViewById(R.id.btnJoin);

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AgreeStartFragment agreeStartFragment= new AgreeStartFragment();
                ((MainActivity2)getActivity()).replaceFragment(agreeStartFragment);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEvent();

                //Toast.makeText(MainActivity.this,"m",Toast.LENGTH_SHORT).show();
            }
        });//btnLogin
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    
                    Intent intent = new Intent(getContext(), MainActivity.class);//넘어가는 화면
                    intent.putExtra("uid", firebaseAuth.getCurrentUser().getUid());
                    startActivity(intent);

                    //((MainActivity2)getActivity()).replaceFragment(LoginStartFragment.this);
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction().remove(LoginStartFragment.this).commit();
//                    fragmentManager.popBackStack();

                } else {

                }

            }
        };

        return vGroup;
    }//onCreateView
    void loginEvent(){
        firebaseAuth.signInWithEmailAndPassword(etId.getText().toString(),etPw.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
    @Override
    public void onStop(){
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public class TaskLogin extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String serverIp = "http://13.125.233.177:8080/AppWithMe/member/login.jsp";

        @Override
        protected String doInBackground(String... strings) {//뒤에서 돌아가는 가장 중요한 메소드
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//POST 방식, GET방식으로 넘겨주면 한글처리부터 문제가 생기게 됨
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                sendMsg = "memberId="+ strings[0]+"&password="+ strings[1];

                osw.write(sendMsg);
                osw.flush();
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);//UTF-8로 데이터 읽어옴
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str);
                    }
                    receiveMsg = buffer.toString();
                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return receiveMsg;//String 타입인데 안에 형식이 json임
        }
    }


}