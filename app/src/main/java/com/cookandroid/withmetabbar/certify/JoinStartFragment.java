package com.cookandroid.withmetabbar.certify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cookandroid.withmetabbar.DaumWebViewActivity;
import com.cookandroid.withmetabbar.MainActivity;
import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static android.content.ContentValues.TAG;

public class JoinStartFragment extends Fragment {

    Button btn_join,btn_live;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.join_start_fragment, container, false);
        EditText etId = vGroup.findViewById(R.id.etId);
        EditText etPw = vGroup.findViewById(R.id.etPw);
        EditText etName = vGroup.findViewById(R.id.etName);
        EditText etNick = vGroup.findViewById(R.id.etNick);
        EditText etBirth = vGroup.findViewById(R.id.etBirth);
        EditText etAge = vGroup.findViewById(R.id.etAge);
        EditText etPhoneNum = vGroup.findViewById(R.id.etPhoneNum);
        btn_join= vGroup.findViewById(R.id.button6);
        btn_live = vGroup.findViewById(R.id.btn_live);
        CheckBox cb_male = vGroup.findViewById(R.id.check_male);
        CheckBox cb_female = vGroup.findViewById(R.id.check_female);
        CheckBox cb_no = vGroup.findViewById(R.id.checkNo);


        btn_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaumWebViewActivity daumWebViewActivity = new DaumWebViewActivity();
                ((MainActivity2)getActivity()).replaceFragment(daumWebViewActivity);
                //LivePlaceFragment livePlaceFragment= new LivePlaceFragment();
                //((MainActivity2)getActivity()).replaceFragment(livePlaceFragment);
            }
        });



        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etId.getText().toString() == null || etName.getText().toString() == null || etPw.getText().toString() == null){

                    Toast.makeText(getContext(),"정보 입력을 완료하세요 :)",Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(etId.getText().toString(),etPw.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    final String uid = task.getResult().getUser().getUid();
                                    Member member = new Member();
                                    member.uid =FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    member.id = etId.getText().toString().trim();
                                    member.pw = etPw.getText().toString().trim();
                                    member.mName = etName.getText().toString().trim();
                                    member.nick =etNick.getText().toString().trim();
                                    member.mAge = Integer.parseInt(etAge.getText().toString());
                                    //성별체크
                                    if (cb_male.isChecked()){
                                        member.mGen =1; //남자는 1
                                    }else if (cb_female.isChecked()){
                                        member.mGen =2; //여자는 2
                                    }else if (cb_no.isChecked()){
                                        member.mGen =0; //무관은 0
                                    }else {
                                        Toast.makeText(getContext(),"성별을 체크하세요.",Toast.LENGTH_SHORT);
                                    }


                                    //member.meetDate
                                    FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {

                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //프래그먼트 종료
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            fragmentManager.beginTransaction().remove(JoinStartFragment.this).commit();
                                            FragmentSelectHobby fragmentSelectHobby= new FragmentSelectHobby();
                                            ((MainActivity2)getActivity()).replaceFragment(fragmentSelectHobby);
//                                            fragmentManager.popBackStack();
//                                            Intent intent = new Intent(getContext(), MainActivity.class);
//                                            startActivity(intent);
                                        }
                                    });
                                }else{
                                    Log.e(TAG, "Error getting sign in methods for user", task.getException());
                                }
                            }
                        });
            }
        });

        return vGroup;
    }

    //이부분 데이터 TaskJOin 수정
    public class TaskJoin extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;
        String serverIp = "http://13.125.233.177:8080/AppWithMe/member/insert.jsp";

        @Override
        protected String doInBackground(String... strings) {//뒤에서 돌아가는 가장 중요한 메소드
            try {
                String str;
                URL url = new URL(serverIp);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");//POST 방식, GET방식으로 넘겨주면 한글처리부터 문제가 생기게 됨
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
                //sendMsg = "memberId="+ strings[0]+"&password="+ strings[1];

                //sendMsg = "meetId="+ strings[0]+"&meetName="+ strings[1];
                sendMsg = "memberId="+ strings[0]+"&password="+ strings[1]+"&memberName="+ strings[2]+"&phoneNum="+ strings[3]
                        +"&birth="+ strings[4];
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