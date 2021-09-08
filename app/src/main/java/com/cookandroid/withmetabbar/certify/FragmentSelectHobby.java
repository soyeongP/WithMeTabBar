package com.cookandroid.withmetabbar.certify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.HobbyBig;

import java.util.ArrayList;

public class FragmentSelectHobby extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //ImageView iv;
    ImageButton imgBtn;
    TextView tv;
    ProgressBar progressBar;
    Button btnNext;

    //GridView gridView;
    //ImageView iv2;
    //MyGridViewAdapter adapter;
    private final ArrayList<HobbyBig> hobbyBigList2 = new ArrayList();// 취미목록 대분류를 담을 배열리스트


    // TODO: Rename and change types of parameters
    private String selectedHobbyBig;
    private String mParam2;




    public FragmentSelectHobby() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg =  (ViewGroup)inflater.inflate(R.layout.fragment_select_hobby, container, false);

        //imgBtn = vg.findViewById(R.id.imageButton);
        tv=vg.findViewById(R.id.textView);
        progressBar=vg.findViewById(R.id.progressBar);
        btnNext =vg.findViewById(R.id.selectHobby1_btn_next);

        //2021.08.22
        //선택한 대분류 ( hobbyBigAdapter에서 받은 것) bundle로 받기
        //Bundle bundle = getArguments();
        //selectedHobbyBig= bundle.getString("selectedHobbyBig");

        //ViewGroup vg2 =  (ViewGroup)inflater.inflate(R.layout.select_hobby_grid_in, container, false);
        //iv2= vg2.findViewById(R.id.imageView_sel_hb);

//        int[] img ={
//                R.drawable.hobby_music,R.drawable.hb_craft,R.drawable.hb_sport
//
//        };

        //MyAdapter myAdapter= new MyAdapter(getContext());
        //gridView.setAdapter(myAdapter);

        //adapter 생성

//        GridAdapter adapter= new GridAdapter(
//                getContext(),//액티비티의상태
//                R.layout.select_hobby_grid_in,
//                hobbyBigList2);
//
//        gridView.setAdapter(adapter);


        //gridview를 띄어주어야한다.
//        for (int i=0; i<img.length; i++){
//            //해당 이미지배열의 값을 가져오고
//            adapter.getItem(i);
//            //그리드뷰에 얹어줘야한다.
//            //adapter.getView(i,iv2,vg2);
//
//        }


//        gridView.setAdapter(adapter);//adapter를 그리드뷰에 적용.
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //목록을 클릭하면 세부 선택화면으로 넘어가도록.
//            }
//        });

        this.initializeData2();

        RecyclerView view = vg.findViewById(R.id.recyclerViewHobbyBig);
        HobbyBigAdapter hobbyBigAdapter= new HobbyBigAdapter(getContext(),hobbyBigList2);

        view.setHasFixedSize(true);//사이즈를 일정하게 변경, false시 성능 저하
        view.setLayoutManager(new GridLayoutManager(getContext(), 3));
        view.setAdapter(hobbyBigAdapter);


        //화면전환
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),selectedHobbyBig,Toast.LENGTH_SHORT);
                FragmentSelectHobby2 fragmentSelectHobby2= new FragmentSelectHobby2();
                //FragmentSelectHobby2_test fragmentSelectHobby2_test= new FragmentSelectHobby2_test();
                //fragmentSelectHobby2.setArguments(bundle);
                ((MainActivity2)getActivity()).replaceFragment(fragmentSelectHobby2);
            }
        });

        return vg;
    }
    public void initializeData2()
    {

        hobbyBigList2.add(new HobbyBig( "음악"));
        hobbyBigList2.add(new HobbyBig( "스포츠"));
        hobbyBigList2.add(new HobbyBig( "여행"));
        hobbyBigList2.add(new HobbyBig( "문화"));
        hobbyBigList2.add(new HobbyBig( "공예"));
        hobbyBigList2.add(new HobbyBig( "요리"));
        hobbyBigList2.add(new HobbyBig( "반려동물"));

    }

}

