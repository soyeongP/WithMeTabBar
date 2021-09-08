package com.cookandroid.withmetabbar.certify;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.HobbyBig;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.YELLOW;

class HobbyBigAdapter extends RecyclerView.Adapter<HobbyBigAdapter.HobbyBigViewHolder>{


    //private ArrayList<ArrayList<Hobby>> AllHobbyList;//전체 취미목록들을 2차원 배열에 넣어준다.
    private Context context;
    //test 취미목록 대분류 넣기
    private final ArrayList<HobbyBig> DataListBig;
    private String selectedHobbyBig;
    private final SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);//클릭하면 색상변경

    //test
    public HobbyBigAdapter(ArrayList<HobbyBig> data)
    {
        this.DataListBig = data;
    }

    public HobbyBigAdapter(Context context, ArrayList<HobbyBig> data2)
    {
        this.context = context;
        //this.AllHobbyList = data;
        this.DataListBig = data2;//대분류
    }
    //VerticalViewHolder 맨 처음 10개의 뷰객체를 기억하고 있을(홀딩) 객체
    public class HobbyBigViewHolder extends RecyclerView.ViewHolder{
        protected RecyclerView recyclerView;
        protected TextView textView;

        public HobbyBigViewHolder(View view)
        {
            super(view);
            textView = view.findViewById(R.id.tv_select_hobbyBig);
            this.recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewHobbyBig);

            //클릭하면 색상변경
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if ( mSelectedItems.get(position, false) ){
                        mSelectedItems.put(position, false);
                        v.setBackgroundColor(GRAY);
                    } else {
                        mSelectedItems.put(position, true);
                        v.setBackgroundColor(YELLOW);
                        selectedHobbyBig =textView.getText().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("selectedHobbyBig",selectedHobbyBig);
                        FragmentSelectHobby fragmentSelectHobby= new FragmentSelectHobby();
                        fragmentSelectHobby.setArguments(bundle);
                    }
                    Log.d("test", "position = " + position);



                }
            });
        }
    }

    @NonNull
    @Override
    public HobbyBigViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.select_hobby_grid_in, null);
        return new HobbyBigViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HobbyBigViewHolder hobbyBigViewHolder, int position) {
        hobbyBigViewHolder
                .textView
                .setText(DataListBig.get(position).getHobbyBigname());

        //클릭하면 색상 변경
        if ( mSelectedItems.get(position, false) ){
            hobbyBigViewHolder.itemView.setBackgroundColor(Color.MAGENTA);//선택했을 때
        } else {
            hobbyBigViewHolder.itemView.setBackgroundColor(YELLOW);//기본, 선택 안했을 때
        }

        //대분류 배열 처리- textView에 넣기
//        hobbyBigViewHolder
//                .textView
//                .setText(DataListBig.get(position).getHobbyBigname());
//
//        hobbyBigViewHolder.recyclerView.setHasFixedSize(true);
//        hobbyBigViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context,3));

        //verticalViewHolder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return DataListBig.size();
    }
}


