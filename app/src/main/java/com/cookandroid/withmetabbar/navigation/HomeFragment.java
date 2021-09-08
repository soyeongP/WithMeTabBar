package com.cookandroid.withmetabbar.navigation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.Meet;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private final String uid="";
    private String[] values;
    private String[] images;



    //storage에 있는 데이터 가져오기

    FirebaseStorage storage= FirebaseStorage.getInstance();
    //StorageReference storageRef = storage.getReference("gs://withmetabbar-93021.appspot.com");
    //가져올 이미지 경로 지정
    //root.child('images').listAll()
    //StorageReference pathReference = storage.getReference().child("gs://withmetabbar-93021.appspot.com/meetImages");

    StorageReference pathReference = storage.getReference();

    //strage에서 모든 이미지 불러오기
    // imageView에 연결


    //데이터 베이스 배울 때 수정하기
    Integer[] postingID = {
            R.drawable.photo1, R.drawable.photo2, R.drawable.photo3,
            R.drawable.photo4, R.drawable.photo5, R.drawable.photo6,
            R.drawable.photo7, R.drawable.photo8, R.drawable.photo9,
            R.drawable.photo10, R.drawable.photo11, R.drawable.photo12,
            R.drawable.photo13, R.drawable.photo14
    };

    //연관검색어 textview에 띄울 내용

    private final List<String> items = Arrays.asList("부산", "부산바다", "부산어묵", "서울", "서울경복궁");
    private SearchView searchBar;
    private TextView resultTextView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.fragment_grid, container, false);
        final GridView gv = vGroup.findViewById(R.id.gridView);
//        MyGridAdapter gAdapter = new MyGridAdapter(getContext());
//        gv.setAdapter(gAdapter);

        Bundle bundle = getArguments();

        //uid=bundle.getString("uid");
        //getFireBaseStorageImage(uid);

        //21/8/3

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("meet").child(uid).child("imgUrl");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    int i=0;
                    if (i<19){
                        Meet meet = dataSnapshot.getValue(Meet.class);
                        images[i]=meet.imgUrl;
                        //values[i]=post.getpTitle(); 모임 제목
                        i++;
                    }
                }
                MyGridAdapter gAdapter = new MyGridAdapter(getContext(),images);
                gv.setAdapter(gAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });





        resultTextView = vGroup.findViewById(R.id.textView3);//연관검색어

        searchBar = vGroup.findViewById(R.id.searchView);

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.setIconified(false);
                gv.setVisibility(View.INVISIBLE);
                resultTextView.setVisibility(View.VISIBLE);
            }
        });

        searchBar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                gv.setVisibility(View.VISIBLE);
                resultTextView.setVisibility(View.INVISIBLE);
                return false;
            }
        });



        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                resultTextView.setText(search(nextText));
                return true;
            }
        });
        return vGroup;
    }
    /**이미지 파이어베이스 스토리지에서 가져오기 메서드*/
    private void getFireBaseStorageImage(String uid ){
        //디렉토리 파일 생성
        File file = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/meetImages");
        //이 file안에 저 디렉토리가 있는지 확인
        if (!file.isDirectory()){
            //디렉토리가 없으면, 디렉토리를 생성
            file.mkdir();
        }
        downloadImg(uid);//이미지 다운로드해서 가져오기 메서드
    }
    /** 이미지 다운로드해서 가져오기 메서드*/
    private void downloadImg(String uid) {
        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("meetImages/"+"meet"+uid+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //성공시 이미지를 띄운다.getView
                Uri partImageUri;

                //GridView gridView = new GridView(getContext());
                //MyGridAdapter gAdapter = new MyGridAdapter(getContext(),partImageUri);//이미지 url을 가져와야 한다.
                //gridView.setAdapter(gAdapter);
                //Glide.with(getContext()).load(uri).into(gAdapter.getView());
                //Glide.with(getContext()).load(uri).into(imageView);

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                //실패시
            }
        });//스토리지 내 이미지 경로
    }

    private String search(String query){
        StringBuilder sb = new StringBuilder();//for append 사용
        for(int i = 0; i<items.size(); i++){
            String item = items.get(i);

            if(item.toLowerCase().trim().contains(query.toLowerCase())) {
                sb.append(item);
                if(i != items.size() - 1) {
                    sb.append("  ");
                }
            }
        }
        return sb.toString();
    }


    private String getResult(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<items.size(); i++){
            String item = items.get(i);
            sb.append(item);
            if(i != items.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        private final String[] images;
        View view;
        LayoutInflater layoutInflater;
        //private Uri[] imageUrls;


        public MyGridAdapter(Context context,String [] images)
        {
            this.context = context;
            this.images = images;
        }

        public int getCount() {
            int count=3;
            /*
            try {
                //데이터 가져와서

                String rst = new Task2().execute().get();
                JSONObject json = new JSONObject(rst);
                JSONArray jArr = json.getJSONArray("List");
                //return jArr.length();
                count = jArr.length();
                return count;


            } catch (Exception e) {
                e.printStackTrace();
            }

 */
            return images.length;
        }

        public Object getItem(int position) {

            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            //21.08.03
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
            {
                view = new View(context);
                //view=layoutInflater.inflate(R.layout.single_item,null);
                ImageView imageView = view.findViewById(R.id.imageView);
                //TextView textView = view.findViewById(R.id.textView);
                Glide.with(context).load(images[position]).into(imageView);
                //textView.setText(values[position]);
            }
            return view;


            //imageView생성 사용될
//            ImageView imageview = new ImageView(context);
//            imageview.setLayoutParams(new ViewGroup.LayoutParams(350,350));
//            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            Glide.with(getContext())
//                    //.load(task.getResult())
//                    .load(imageUrls[position])
//                    .override(1024, 980)
//                    .into(imageview);
            //Glide.with(getContext()).load(uri).into(imageView);
            //data
            /*
            String urlString="http://52.79.251.210:8080/MobileTP/images/";
            Bitmap bitmap = null;
            try {
                String rst = new Task2().execute().get();
                JSONObject json = new JSONObject(rst);
                JSONArray jArr = json.getJSONArray("List");

                json = jArr.getJSONObject(position);

                String msg1 = json.getString("postId");
                msg2 = json.getString("postPicture");

                postIds[position] = msg1;

                urlString += msg2;

                ImageLoadTask task = new ImageLoadTask(urlString,imageview);
                task.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

             */
            /*
            pathReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        Glide.with(getContext())
                                //.load(task.getResult())
                                .load(pathReference)
                                .override(1024, 980)
                                .into(imageview);
                    } else {
                        // URL을 가져오지 못하면 토스트 메세지
                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
             */

//데이터 베이스 배울 때 수정
            //imageview.setImageResource(postingID[position]);
            //Glide.with(context).load(pathReference).into(imageview);

            //return imageview;

        }
    }


    /*
    private void storageDataDownload() {
        try{
            //로컬에 저장할 폴더의 위치
            File path = new File("Folder path");

            //저장하는 파일의 이름
            final File file = new File(path, "File name");
            try {
                if (!path.exists()) {
                    //저장할 폴더가 없으면 생성
                    path.mkdirs();
                }
                file.createNewFile();

                //파일을 다운로드하는 Task 생성, 비동기식으로 진행
                final FileDownloadTask fileDownloadTask = pathReference.getFile(file);
                fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        //다운로드 성공 후 할 일
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //다운로드 실패 후 할 일
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    //진행상태 표시
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

     */



}
