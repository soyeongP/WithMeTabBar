package com.cookandroid.withmetabbar.navigation;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cookandroid.withmetabbar.MainActivity;
import com.cookandroid.withmetabbar.MainActivityHome;
import android.Manifest;
import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.certify.FragmentSelectHobby2;
import com.cookandroid.withmetabbar.certify.LivePlaceFragment;
import com.cookandroid.withmetabbar.certify.MainActivity2;
import com.cookandroid.withmetabbar.model.Meet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;


@SuppressWarnings("deprecation")
public class FragmentPlus extends Fragment {

    private DatabaseReference mDatabase;
    private final int GET_GALLERY_IMAGE = 200;//?무슨의미
    private ImageView imageView, imageView7;
    private ImageButton imageButton;
    //private ToggleButton toggleButton, toggleButton1,toggleButton2,toggleButton3,toggleButton4;
    private Button btnMeet;
    private MediaPlayer MP;
    private Uri imageUri;//모임이미지
    private String uid="";
    TimePickerDialog mTimePicker;
    private Date meetDate;
    private int meetDateInt;//int형 데이터 바꿈



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.fragment_plus, container, false);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_launcher);
        //setTitle("레이아웃 선택");


        btnMeet= vGroup.findViewById(R.id.btn_meet);//버튼
        EditText etTitle=vGroup.findViewById(R.id.etTitle);
        EditText etAge=vGroup.findViewById(R.id.etMeetAge);
        EditText etNumMem=vGroup.findViewById(R.id.etNumMem);
        EditText etContent=vGroup.findViewById(R.id.etContent);
        EditText etHobby=vGroup.findViewById(R.id.etHobby);
        EditText et_date = vGroup.findViewById(R.id.Date);
        CheckBox cb_male = vGroup.findViewById(R.id.check_male);
        CheckBox cb_female = vGroup.findViewById(R.id.check_female);
        CheckBox cb_no = vGroup.findViewById(R.id.checkNo);

        int count=0;


        Intent intent = new Intent();


        Bundle bundle = getArguments();
        uid=bundle.getString("uid");//null?

        //선택한 취미값받기
        while (bundle.getString("hobby"+count)!=null){
            String str = bundle.getString("hobby"+count);
            etHobby.append(","+str);
        }

        //날짜 선택

        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA); //string형태로 바뀐다.

                et_date.setText(sdf.format(myCalendar.getTime()));
                DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");//필요x

                //데이터 firebase저장위해 meetDate 변수에 대입
                try {
                    //meetDate=myCalendar.getTime(); //캘린더타입
                    //meetDate.=myCalendar.get(Calendar.DAY_OF_MONTH);

                    //meetDate.setYear(myCalendar.get(Calendar.YEAR));//년
                    //meetDate.setMonth(myCalendar.get(Calendar.MONTH));//월
                    //meetDate.setDate(myCalendar.get(Calendar.DAY_OF_MONTH));//일

                    //meetDate=myCalendar.get(Calendar.YEAR);
                    //meetDate=myCalendar.get(Calendar.DAY_OF_WEEK);



                    Log.d("meetDate", String.valueOf(meetDate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //취미선택
        etHobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentPlusSelectHobby fragmentPlusSelectHobby= new FragmentPlusSelectHobby();
                ((MainActivity)getActivity()).addFragment(fragmentPlusSelectHobby);

            }
        });

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //시간선택
        final EditText et_time = vGroup.findViewById(R.id.Time);
        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY); //선택한 시간(24시간제)
                int minute = mcurrentTime.get(Calendar.MINUTE); //선택한 분
                //TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        myCalendar.set(Calendar.AM_PM, 0);//오전이면 0

                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                            myCalendar.set(Calendar.AM_PM, 1);//오후면 1
                        }
                        // EditText에 출력할 형식 지정
                        et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                        myCalendar.set(Calendar.HOUR, selectedHour);
                        myCalendar.set(Calendar.MINUTE, selectedMinute);

                        meetDate=myCalendar.getTime();


                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });




        imageView = (ImageView)vGroup.findViewById(R.id.imageView7);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_GALLERY_IMAGE);


            }
        });
        //버튼 누르면 데이터에 저장
        btnMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseStorage storage= FirebaseStorage.getInstance();
                //StorageReference storageRef = storage.getReference();
                //파일명 만들기
                String filename="meet"+uid+".jpeg";
                Uri file = imageUri;//
                Log.d("imageUri", String.valueOf(file));
                Log.d("filename", filename);
                //여기서 원하는 이름 넣어준다.(filename 넣어주기)
                //StorageReference riversRef = storageRef.child("meetImage/"+filename);
                //UploadTask uploadTask = riversRef.putFile(file);//storage에 이미지 업로드

                

                //이미지 strage에 저장
                FirebaseStorage.getInstance().getReference().child("meetImages/"+imageUri.getLastPathSegment()).child(uid).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        Meet meet = new Meet();
                        meet.uid =uid; //uid정보 추가
                        meet.title = etTitle.getText().toString();
                        meet.meetAge = Integer.parseInt(etAge.getText().toString());
                        meet.numMember = Integer.parseInt(etNumMem.getText().toString());
                        meet.content = etContent.getText().toString();
                        meet.imgUrl = file.toString();
                        meet.meetDate =meetDate;

                        //성별체크
                        if (cb_male.isChecked()){
                            meet.meetGen =1; //남자는 1
                        }else if (cb_female.isChecked()){
                            meet.meetGen =2; //여자는 2
                        }else if (cb_no.isChecked()){
                            meet.meetGen =0; //무관은 0
                        }else {
                            Toast.makeText(getContext(),"성별을 체크하세요.",Toast.LENGTH_SHORT);
                        }

                        //member.mAge = Integer.parseInt(etAge.getText().toString());

                        //meet라는 데이터 넣기
                        FirebaseDatabase.getInstance().getReference().child("meet").push().setValue(meet).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                MainActivityHome mainActivityHome= new MainActivityHome();
                                ((MainActivity)getActivity()).replaceFragment(mainActivityHome);

                                //이 코드는 fragement 뒤로가기 코드
                                //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                //fragmentManager.beginTransaction().remove(FragmentPlus.this).commit();
                                //fragmentManager.popBackStack();

                            }
                        });
                    }
                });
                //모임정보 업데이트
                //mDatabase.child("users").child(userId).child("username").setValue(name);
            }
        });

        return vGroup;
    }
    //내용 업로드
    private void contentUpload() {

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());

    }

    //갤러리로 가는 법
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(data.getData());
                imageUri = data.getData();
                Log.d("갤러리에서 불러온 이미지 경로", String.valueOf(imageUri));

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    } //갤러리에서 사진 불러와서 넣기



    protected void onMainActivity(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_GALLERY_IMAGE&&resultCode == RESULT_OK&&data !=
                null && data.getData()!=null){
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }


    /*public class MyGalleryAdapter extends BaseAdapter {

        Context context;
        Integer[] posterID = { R.drawable.layout1, R.drawable.layout2,
                R.drawable.layout3, R.drawable.layout4, R.drawable.layout5,
                R.drawable.layout6, R.drawable.layout7, R.drawable.layout8,
                R.drawable.layout9, R.drawable.layout10};
        String[] title = {"레이아웃1","레이아웃2", "레이아웃3",
                "레이아웃4","레이아웃5","레이아웃6","레이아웃7","레이아웃8","레이아웃9","레이아웃10"};

        public MyGalleryAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return posterID.length;
        }



        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        /*public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageview = new ImageView(context);
            imageview.setLayoutParams(new Gallery.LayoutParams(200, 300));
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(5, 5, 5, 5);
            imageview.setImageResource(posterID[position]);


            final int pos = position;
            imageview.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch (View v, MotionEvent event) {
                    ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
                    ivPoster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ivPoster.setImageResource(posterID[pos]);
                    Toast.makeText(getApplicationContext(),title[pos],Toast.LENGTH_SHORT).show();

                    //dlg.setIcon(R.drawable.movie_icon);

                    return false;
                }
            });*/

    //return imageview
}
