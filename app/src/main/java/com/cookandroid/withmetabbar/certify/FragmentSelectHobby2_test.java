package com.cookandroid.withmetabbar.certify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.Hobby;
import com.cookandroid.withmetabbar.model.HobbyBig;

import java.util.ArrayList;

public class FragmentSelectHobby2_test extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //ImageView iv;
    ImageButton imgBtn;
    TextView tv;//취미분야 선택
    ProgressBar progressBar;
    private String selectedHobbyBig;
    //GridView gridView;
    //ImageView iv2;
    //MyGridViewAdapter adapter;

    //중첩recyclerView 구현
    private ArrayList<ArrayList<Hobby>> allHobbyList = new ArrayList();
    private ArrayList<HobbyBig> HobbyBigList2 = new ArrayList();//대분류 test



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentSelectHobby2_test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSelectHobby.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSelectHobby newInstance(String param1, String param2) {
        FragmentSelectHobby fragment = new FragmentSelectHobby();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedHobbyBig = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup vg =  (ViewGroup)inflater.inflate(R.layout.fragment_select_hobby2, container, false);

        //imgBtn = vg.findViewById(R.id.imageButton);
        tv=vg.findViewById(R.id.textView);
        progressBar=vg.findViewById(R.id.progressBar);

        tv.setText("취미 분야 선택 : "+selectedHobbyBig);
        //final GridView gridView= vg.findViewById(R.id.gridView);
        //ViewGroup vg2 =  (ViewGroup)inflater.inflate(R.layout.select_hobby_grid_in, container, false);
        //iv2= vg2.findViewById(R.id.imageView_sel_hb);

//        int[] img ={
//                R.drawable.hobby_music,R.drawable.hb_craft,R.drawable.hb_sport
//
//        };
        String [] arrayHobby_music = {
          "클래식","k-pop","뉴에이지","발라드","랩/힙합","게임","팬모임","여행","반려동물"
        };
        Varargs varargs = new Varargs();
        varargs.makeArray("음악","공예","요리","문화","스포츠","게임","팬모임","여행","반려동물");
        //str 안에 담겼다.
        //tv.setText(varargs.test2);

        //바로 해보기
        ArrayList<ArrayList<String>> hbLists = new ArrayList<ArrayList<String>>();

        ArrayList<String> hbList_music = new ArrayList<>();
        hbList_music.add("음악");
        hbList_music.add("클래식");
        hbList_music.add("k-pop");
        hbList_music.add("뉴에이지");
        hbList_music.add("발라드");
        hbList_music.add("랩/힙합");
        hbList_music.add("인디음악");
        hbList_music.add("록/메탈");
        hbList_music.add("포크/블루스");

        //2차원배열에 답기
        hbLists.add(hbList_music);

        //tv.setText((CharSequence) hbLists.get(0).get(0));

        //응용참고
        /*
        System.out.println(datas.size());
        	//결과: 3
		System.out.println(datas.toString());
        	//결과: [[1, 2, 3], [11, 22, 33], [111, 222, 333]]

		System.out.println(datas.get(0));
		//결과: [1, 2, 3]
         */


        //MyAdapter2 myAdapter2= new MyAdapter2(getContext());
        //gridView.setAdapter(myAdapter);

        //adapter 생성

        /*
        for (int i=0;i<hbLists.size();i++){
            for (int j=0;j<hbLists.get(i).size();j++){
                hbLists.get(i).get(j);
            }

        }


        MyAdapter2 adapter2= new MyAdapter2(
                getContext(),//액티비티의상태
                R.layout.select_hobby2_grid_in2,
                arrayHobby_music);
                //hbLists.get(0);

        gridView.setAdapter(adapter2);


        //gridview를 띄어주어야한다.
        for (int i=0; i<arrayHobby_music.length; i++){
            //해당 이미지배열의 값을 가져오고
            adapter2.getItem(i);
            //그리드뷰에 얹어줘야한다.
            //adapter.getView(i,iv2,vg2);

        }


        gridView.setAdapter(adapter2);//adapter를 그리드뷰에 적용.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //목록을 클릭하면 세부 선택화면으로 넘어가도록.
            }
        });

         */

        //여기부터 리사이클러뷰 예제
        this.initializeData();
        this.initializeData2();
        //test
//        ArrayList<HobbyBig> HobbyBigList3= new ArrayList();
//        HobbyBigList3.add(new HobbyBig("음악"));
//        HobbyBigList3.add(new HobbyBig("스포츠"));



        RecyclerView view = vg.findViewById(R.id.recyclerViewVertical);

        VerticalAdapter verticalAdapter = new VerticalAdapter(getContext(), allHobbyList, HobbyBigList2);
        //HorizontalAdapter adapter = new HorizontalAdapter(AllHobbyList.get(position));

        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        view.setAdapter(verticalAdapter);

        return vg;
    }
    //소분류 취미목록 데이터 입력
    public void initializeData()
    {
        ArrayList<Hobby> hobbyList1 = new ArrayList();

        hobbyList1.add(new Hobby( "클래식"));
        hobbyList1.add(new Hobby("K-pop"));
        hobbyList1.add(new Hobby("뉴에이지"));
        hobbyList1.add(new Hobby("발라드"));
        hobbyList1.add(new Hobby("랩/힙합"));
        hobbyList1.add(new Hobby("인디음악"));
        hobbyList1.add(new Hobby("록/메탈"));
        hobbyList1.add(new Hobby("포크.블루스"));


        allHobbyList.add(hobbyList1);

        ArrayList<Hobby> hobbyList2 = new ArrayList();

        hobbyList2.add(new Hobby( "야구"));
        hobbyList2.add(new Hobby( "축구"));
        hobbyList2.add(new Hobby( "농구"));

        allHobbyList.add(hobbyList2);

        ArrayList<Hobby> hobbyList3 = new ArrayList();

        hobbyList3.add(new Hobby( "캠핑"));
        hobbyList3.add(new Hobby( "바다"));
        hobbyList3.add(new Hobby( "국내"));

        allHobbyList.add(hobbyList3);
    }
    //대분류 취미목록 데이터 입력
    public void initializeData2()
    {
//        ArrayList<HobbyBig> hobbyBigList = new ArrayList();
//        hobbyBigList.add(new HobbyBig( "음악"));
//        hobbyBigList.add(new HobbyBig("스포츠"));
//        hobbyBigList.add(new HobbyBig("여행"));
//        hobbyBigList.add(new HobbyBig("문화"));
//        hobbyBigList.add(new HobbyBig("공예"));
//        hobbyBigList.add(new HobbyBig("요리"));
//        hobbyBigList.add(new HobbyBig("게임"));
//        hobbyBigList.add(new HobbyBig("팬모임"));

        HobbyBigList2.add(new HobbyBig( "음악"));
        HobbyBigList2.add(new HobbyBig( "스포츠"));
        HobbyBigList2.add(new HobbyBig( "여행"));
        HobbyBigList2.add(new HobbyBig( "문화"));
        HobbyBigList2.add(new HobbyBig( "공예"));

    }




}


