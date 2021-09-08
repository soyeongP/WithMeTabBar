package com.cookandroid.withmetabbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.withmetabbar.model.Meet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivityHome extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Meet> arrayList;//? 검색을 보여줄 리스트 변수
    private ArrayList<Meet> arrayList_copy;
    private ArrayList<Meet> arrayList_uniqe;
    //
    private List<Meet> list;
    private CustomAdapter customAdapter;
    //
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private List<String> list_search; //자동완성 단어들을 담을 리스트
    private AutoCompleteTextView autoCompleteTextView; //검색어입력창
    private SearchView searchView; //검색어 입력 창
    private SearchAdapter searchAdapter;
    private EditText editSearch;//검색어를 입력할 Input창
    private ListView listView;//검색을 보여줄 리스변수
    private ArrayList<String> arrayList_search;

    //2021-08-16 검색기능 구현
    private List<String> list_search_recycle; //meet의 제목만 따로 담을 리스트
    private ArrayList<String> arrayList_search_recycle;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.activity_main_home, container, false);

        recyclerView=vGroup.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);//기존 리사이클러뷰의 성능 강화
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();//Meet객체를 담을 어레이리스트 (어뎁터 쪽으로)
        arrayList_copy = new ArrayList<>();


        //Button btn_back= vGroup.findViewById(R.id.btn_back);
        Button btn_search= vGroup.findViewById(R.id.btn_search);



        //2021-08-16 검색기능 구현
        list_search_recycle= new ArrayList<String>();
        arrayList_search_recycle= new ArrayList<String>();//리스트의 모든 데이터를 arraylist_search에 복사


        //data
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("meet");//DB Table Connect
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot dataSnapshot) {
                //실제적으로 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); //기존 배열리스트가 존재하지 않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){//반복문으로 데이터 List를 추출해냄.
                    Meet meet = snapshot.getValue(Meet.class); // 만들어놨던 Meet 객체에 데이터를 담는다.
                    arrayList.add(meet); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비.
                    Log.d("arrayList", String.valueOf(arrayList));

                    arrayList_copy.addAll(arrayList);//arrayList_copy에 복사

                    //단어 검색
                    //2021-08-16 검색기능 구현
                    // meet의 값이 null값이 아니면, list_search_recycle이라는 리스트에 넣어라.
                    if (meet.title!=null){
                        list_search_recycle.add(meet.title);//list_search_recycle에 title값 저장
                        //Log.d("list_search_recycle", String.valueOf(list_search_recycle));
                    }


                }


                //2021-08-16 검색기능 구현
                arrayList_search_recycle.addAll(list_search_recycle);//제목으로 모임검색 구현,복사해준다.


                customAdapter= new CustomAdapter(arrayList,getContext());
                recyclerView.setAdapter(customAdapter);
                //adapter.notifyDataSetChanged();//리스트 저장 및 새로고침

                //test
                for (int i=0;i<arrayList_copy.size();i++){
                    Log.d("arrayList_copy vaule", String.valueOf(arrayList_copy.get(i).getTitle()));
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                //디비를 가져오는 도중 에러 발생 시
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("MainActivityHome",String.valueOf(error.toException()));

            }
        });


        //검색기능
        editSearch=(EditText)vGroup.findViewById(R.id.editSearch);
        listView=(ListView)vGroup.findViewById(R.id.listView);


        //arrayList_search.addAll(list_search); 궅이 필요 x 제거해도 된다.
        searchAdapter = new SearchAdapter(list_search_recycle,getContext());
        listView.setAdapter(searchAdapter);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전에 조치
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //입력란에 변화가 있을 시 조치
                listView.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
                // input창에 문자를 입력할때마다 호출된다.
                // search 메소드를 호출한다.
                String searchText = editSearch.getText().toString();
                searchInMeet(searchText);

                //2021-08-16 검색기능 구현
                //검색된 단어가 제목에 들어가는 게시물 띄어보이기(recycler view)
                //검색한 단어와 meetName이 같은 모임만 다시 띄운다.
                //customAdapter= new CustomAdapter(arrayList_search_recycle,getContext());
                //recyclerView.setAdapter(customAdapter);

            }

        });

        //검색버튼 클릭하면면
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText =editSearch.getText().toString();//검색어를 가져와서
                searchInMeetToRecyclerView(searchText);//검색기능 메서드호출

            }
        });
        return vGroup;
    }

    //검색기능메서드
    private void searchInMeet(String searchText) {

        list_search_recycle.clear();//되랏, 수박 등 실제 meet에서 가져온 정보들 list_search와 동일

         //문자 입력이 없을때는 모든 데이터를 보여준다.
        if (searchText.length()==0){
            list_search_recycle.addAll(arrayList_search_recycle);//list_search_recycler실제 검색이 끝난 후 리스트, arrayList_search_recycle: 모든 값이 들어있는 리스트

        }
        //문자 입력 있을 때
        else {
            // 리스트의 모든 데이터를 검색
            for (int i=0;i<arrayList_search_recycle.size();i++){

                // arraylist_search의 모든 데이터에 입력받은 단어(searchText)가 포함되어 있으면 true를 반환
                if(arrayList_search_recycle.get(i).toLowerCase().contains(searchText)){
                    list_search_recycle.add(arrayList_search_recycle.get(i));//검색된 데이터를 리스트에 추가
                    //검색한 값만 잘 들어온다.
                    Log.d("list_search_recycle", String.valueOf(list_search_recycle));

                }
            }//for
        }//else
        searchAdapter.notifyDataSetChanged();

    }

    //검색기능-> recyclerview
    private void searchInMeetToRecyclerView(String searchText) {

        arrayList.clear();//되랏, 수박 등 실제 meet에서 가져온 정보들 list_search와 동일

        //문자 입력이 없을때는 모든 데이터를 보여준다.
        if (searchText.length()==0){
            arrayList.addAll(arrayList_copy);
            Log.d("arrayList_copy_method", String.valueOf(arrayList));//값이 안나온다

        }
        //문자 입력 있을 때
        else {
            // 리스트의 모든 데이터를 검색
            for (int i=0;i<arrayList_copy.size();i++){

                // arraylist_search의 모든 데이터에 입력받은 단어(searchText)가 포함되어 있으면 true를 반환
                if(arrayList_copy.get(i).getTitle().toLowerCase().contains(searchText)){
                    if (UniqueCheckAndAdd(arrayList,arrayList_copy.get(i)) == true){
                        arrayList.add(arrayList_copy.get(i));//검색된 데이터를 리스트에 추가
                        //검색한 값만 잘 들어온다.
                        Log.d("arrayList_new", String.valueOf(arrayList));
                        Log.d("size", String.valueOf(arrayList_copy.size()));//253??
                    }
                }
            }//for
        }//else
        //arrayList_uniqe = new ArrayList<>();
        //arrayList_uniqe = new Set<arrayList>();
        customAdapter.notifyDataSetChanged();

    }
    public boolean UniqueCheckAndAdd(ArrayList<Meet> array, Meet meetPresent){
        return !array.contains(meetPresent);
    }


}