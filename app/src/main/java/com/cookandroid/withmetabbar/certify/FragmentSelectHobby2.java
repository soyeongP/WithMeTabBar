package com.cookandroid.withmetabbar.certify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.withmetabbar.MainActivity;
import com.cookandroid.withmetabbar.MainActivityHome;
import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.Hobby;
import com.cookandroid.withmetabbar.model.HobbyBig;
import com.cookandroid.withmetabbar.model.Meet;
import com.cookandroid.withmetabbar.model.Member;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.YELLOW;

public class FragmentSelectHobby2 extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //ImageView iv;
    ImageButton imgBtn;
    TextView tv;//취미분야 선택
    ProgressBar progressBar;
    private String selectedHobbyBig;
    Button btnFinish; //메인화면으로 화면전환버튼
    //GridView gridView;
    //ImageView iv2;
    //MyGridViewAdapter adapter;

    //중첩recyclerView 구현
    private final ArrayList<ArrayList<Hobby>> allHobbyList = new ArrayList();
    private final ArrayList<HobbyBig> HobbyBigList2 = new ArrayList();//대분류 test



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentSelectHobby2() {
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
    public static com.cookandroid.withmetabbar.certify.FragmentSelectHobby newInstance(String param1, String param2) {
        com.cookandroid.withmetabbar.certify.FragmentSelectHobby fragment = new com.cookandroid.withmetabbar.certify.FragmentSelectHobby();
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
        btnFinish= vg.findViewById(R.id.selectHobby2_btn_next);

        tv.setText("취미 분야 선택 : "+selectedHobbyBig);
        //Log.d("selectedHobbyBig",selectedHobbyBig);
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


        //화면전환
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        //데이터 저장을 위한 객체 참조
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference();
//        DatabaseReference usersRef = ref.child("users");
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();//사용자 uid
//        DatabaseReference hopperRef = usersRef.child(uid); //  /users/uid

        //push 데이터 저장
//        DatabaseReference pushRef = hopperRef.child("hobby");
//        pushRef.push().setValue(new Hobby("탁구"));
//        pushRef.push().setValue(new Hobby("배구"));

        //update 데이터 저장
//        Map<String, Object> hopperUpdates = new HashMap<>();
//        hopperUpdates.put("hobby1", "베이킹");
//        hopperUpdates.put("hobby2", new Hobby("공예"));
//        hopperUpdates.put("hobby2", new Hobby("베이킹"));
        //hopperUpdates.put("hobby", hobbyListTest);
//        hopperRef.updateChildren(hopperUpdates); //데이터 업데이트



//        Bundle data = getArguments();
//        String data1 = data.getString("selectedList");
//        pushRef.push().setValue(new Hobby(data1));

        //선택한 hobby값을 가져오기 위한, 커스텀 리스너 객체 생성 및 전달
//        HorizontalAdapter horizontalAdapter= new HorizontalAdapter();
//        horizontalAdapter.setOnItemClickListener(new HorizontalAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                //받아온 값을 데이터에 저장
//                Bundle data = getArguments();
//                String data1 = data.getString("selectedList");
//                pushRef.push().setValue(new Hobby(data1));
//
//            }
//        });

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
        hobbyList2.add(new Hobby( "볼링"));
        hobbyList2.add(new Hobby( "당구"));
        hobbyList2.add(new Hobby( "수영"));
        hobbyList2.add(new Hobby( "탁구"));
        hobbyList2.add(new Hobby( "폴댄스"));
        hobbyList2.add(new Hobby( "골프"));
        hobbyList2.add(new Hobby( "배드민턴"));
        hobbyList2.add(new Hobby( "클라이밍"));

        allHobbyList.add(hobbyList2);

        ArrayList<Hobby> hobbyList3 = new ArrayList();

        hobbyList3.add(new Hobby( "캠핑"));
        hobbyList3.add(new Hobby( "바다"));
        hobbyList3.add(new Hobby( "국내"));
        hobbyList3.add(new Hobby( "해외"));
        hobbyList3.add(new Hobby( "당일치기"));
        hobbyList3.add(new Hobby( "배낭여행"));
        hobbyList3.add(new Hobby( "익스트림"));
        hobbyList3.add(new Hobby( "기타"));

        allHobbyList.add(hobbyList3);
        ArrayList<Hobby> hobbyList4 = new ArrayList();

        hobbyList4.add(new Hobby( "전시"));
        hobbyList4.add(new Hobby( "뮤지컬"));
        hobbyList4.add(new Hobby( "영화"));
        hobbyList4.add(new Hobby( "해외"));
        hobbyList4.add(new Hobby( "콘서트"));
        hobbyList4.add(new Hobby( "연극"));
        hobbyList4.add(new Hobby( "오페라"));
        hobbyList4.add(new Hobby( "연주회"));
        hobbyList4.add(new Hobby( "오케스트라"));
        hobbyList4.add(new Hobby( "기타"));

        allHobbyList.add(hobbyList4);

        ArrayList<Hobby> hobbyList5 = new ArrayList();

        hobbyList5.add(new Hobby( "리본공예"));
        hobbyList5.add(new Hobby( "비즈공예"));
        hobbyList5.add(new Hobby( "도자기공예"));
        hobbyList5.add(new Hobby( "넵킨공예"));
        hobbyList5.add(new Hobby( "포크아트"));
        hobbyList5.add(new Hobby( "북아트"));
        hobbyList5.add(new Hobby( "클레이아트"));
        hobbyList5.add(new Hobby( "가죽공예"));
        hobbyList5.add(new Hobby( "유리공예"));
        hobbyList5.add(new Hobby( "플라워아트"));

        allHobbyList.add(hobbyList5);

        ArrayList<Hobby> hobbyList6 = new ArrayList();

        hobbyList6.add(new Hobby( "양식"));
        hobbyList6.add(new Hobby( "한식"));
        hobbyList6.add(new Hobby( "일식"));
        hobbyList6.add(new Hobby( "중식"));
        hobbyList6.add(new Hobby( "베이킹"));
        hobbyList6.add(new Hobby( "설탕공예"));


        allHobbyList.add(hobbyList6);

        ArrayList<Hobby> hobbyList7 = new ArrayList();

        hobbyList6.add(new Hobby( "강아지"));
        hobbyList6.add(new Hobby( "고양이"));
        hobbyList6.add(new Hobby( "조류"));
        hobbyList6.add(new Hobby( "햄스터"));
        hobbyList6.add(new Hobby( "파충류"));
        hobbyList6.add(new Hobby( "곤충"));


        allHobbyList.add(hobbyList7);
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
        HobbyBigList2.add(new HobbyBig( "요리"));
        HobbyBigList2.add(new HobbyBig( "반려동물"));

    }




}

//
class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder> {

    private ArrayList<Hobby> dataList;
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);//클릭하면 색상변경//클릭 안하면 0 하면 1??
    private ArrayList<Hobby> selectedList= new ArrayList<>(); //선택한 취미 값

    public HorizontalAdapter(){}

    public HorizontalAdapter(ArrayList<Hobby> data)
    {
        this.dataList = data;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }//어텝터 내에서 커스텀 리스너 인터페이스 정의

    //리스너 객체 참조를 저장하는 변수
//    private OnItemClickListener mListener = null;
//    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
//    public  void setOnItemClickListener(OnItemClickListener listener){
//        this.mListener = listener;
//    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        protected TextView tv;
        private ArrayList<String> selectData = null ;
        private String selected="";


        public HorizontalViewHolder(View view)
        {
            super(view);
            tv = view.findViewById(R.id.btn_hbList_in);
            //클릭하면 색상변경
            view.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    //데이터 저장을 위한 객체 참조
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference();
                    DatabaseReference usersRef = ref.child("users");

                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();//사용자 uid
                    DatabaseReference hopperRef = usersRef.child(uid); //  /users/uid
                    DatabaseReference pushRef = hopperRef.child("hobby");
                    pushRef.push().setValue(new Hobby(tv.getText().toString()));

                    if ( mSelectedItems.get(position, false) ){

                        mSelectedItems.put(position, false);
                        v.setBackgroundColor(MAGENTA);

                    } else {
                        mSelectedItems.put(position, true);
                        v.setBackgroundColor(YELLOW);
                    }
                    Log.d("test", "position = " + position);

                }
            });

        }
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.select_hobby2_grid_in2, null);




        return new HorizontalAdapter.HorizontalViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(HorizontalViewHolder horizontalViewHolder, int position)
    {
//        horizontalViewHolder
//                .image
//                .setImageResource(dataList.get(position).getResourceID());
        horizontalViewHolder
                .tv
                .setText(dataList.get(position).getHobbyname());

        //클릭하면 색상 변경
        if ( mSelectedItems.get(position, false) ){
            horizontalViewHolder.itemView.setBackgroundColor(YELLOW);//선택했을 때
        } else {
            horizontalViewHolder.itemView.setBackgroundColor(MAGENTA);//기본, 선택 안했을 때
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public ArrayList<Hobby> getSelected(){

        return selectedList;
    }




}
class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>{


    private ArrayList<ArrayList<Hobby>> AllHobbyList;//전체 취미목록들을 2차원 배열에 넣어준다.
    private Context context;
    //test 취미목록 대분류 넣기
    private ArrayList<HobbyBig> DataListBig;

    //test
    public VerticalAdapter(ArrayList<HobbyBig> data)
    {
        this.DataListBig = data;
    }
    //

    public VerticalAdapter(Context context, ArrayList<ArrayList<Hobby>> data, ArrayList<HobbyBig> data2)
    {
        this.context = context;
        this.AllHobbyList = data;
        this.DataListBig = data2;//대분류
    }
    //VerticalViewHolder 맨 처음 10개의 뷰객체를 기억하고 있을(홀딩) 객체
    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        protected RecyclerView recyclerView;
        protected TextView textView;

        public VerticalViewHolder(View view)
        {
            super(view);
            textView = view.findViewById(R.id.tv_hobby);
            this.recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewVertical);
        }
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.select_hobby2_grid_in, null);
        return new VerticalAdapter.VerticalViewHolder(v);
    }
    //수직 RecyclerView의 데이터 항목은 앞서 구현한 수평 RecyclerView가 배치된다고 하였습니다.
    // 그러기 위해 onBindViewHolder에서 HorizontalAdapter 객체를 생성
    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder verticalViewHolder, int position) {
        HorizontalAdapter adapter = new HorizontalAdapter(AllHobbyList.get(position));
        //adapter.getSelected();
        //현재 위치의 arrayList를 AllHobbyList로 받아서 adapter생성.

        //대분류 배열 처리- textView에 넣기
        verticalViewHolder
                .textView
                .setText(DataListBig.get(position).getHobbyBigname());

        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context
                , LinearLayoutManager.HORIZONTAL
                ,false));
        verticalViewHolder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return AllHobbyList.size();
    }
}


//Adapter
class MyAdapter2 extends BaseAdapter{
    Context context;
    int layout;
    String[] btnNames;
    LayoutInflater inflater;
    public MyAdapter2(Context c) {context=c;}

    public MyAdapter2(Context context, int layout, String[] strings){
        this.context = context;
        this.layout = layout;
        this.btnNames = strings;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        //이미지배열의 크기를 return
        return btnNames.length;
    }

    @Override
    public Object getItem(int position) {
        return btnNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //view전환
        Button btn=null;
        if (null != convertView) {
            btn = (Button)convertView;
        }

        else {
            //------------------------------------------------------------
            // 버튼을 생성하고 그것의 이름을 정합니다.
            btn = new Button(context);
            btn.setText(btnNames[position]);

            //------------------------------------------------------------
            // 버튼 클릭에 대한 처리는 추후 구현 예정입니다.
        }

        return btn;


        //imageview.setImageResource(img[position]); //데이터 베이스 배울 때 수정


        /*
        if (convertView==null)
            convertView = inflater.inflate(layout, null);
        ImageView iv = convertView.findViewById(R.id.imageView_sel_hb);
        iv.setImageResource(img[position]);
        return convertView;

         */
    }
}

final class  Varargs{
    //String test2="";
    List<String> str = new ArrayList<String>();
    //1차원 배열 생성
    public void makeArray(String... strigs){

        for (String s:strigs){
            str.add(s);
            //test2= test2+s;

        }

    }
    //2차원배열 생성
    public void makeArray2nds(String... strigs){

        List<String> str2nd = new ArrayList<String>();

        for (String s:strigs){
            str2nd.add(s);
        }

    }


}