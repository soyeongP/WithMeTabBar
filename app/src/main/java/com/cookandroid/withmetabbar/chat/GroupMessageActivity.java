package com.cookandroid.withmetabbar.chat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cookandroid.withmetabbar.R;
import com.cookandroid.withmetabbar.model.ChatModel;
import com.cookandroid.withmetabbar.model.Meet;
import com.cookandroid.withmetabbar.model.Member;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class GroupMessageActivity extends AppCompatActivity {
    //그룹채팅
    Map<String, Member> users = new HashMap<>();
    String destinationRoom;
    String uid;
    private final List<String> chatmacro = new ArrayList<>();
    private ListView listView;
    private RecyclerView recyclerView;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH:mm");

    List<ChatModel.Comment> comments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);
        ListView listView = (ListView)findViewById(R.id.groupmessageactivity_listview);
        chatmacro.addAll(Arrays.asList(getResources().getStringArray(R.array.macro)));
        destinationRoom = getIntent().getStringExtra("destinationRoom");
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //editText = (EditText)findViewById(R.id.groupmessageactivity_edittext);
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren()){
                    users.put(item.getKey(),item.getValue(Member.class));
                }
                init();
                recyclerView = (RecyclerView)findViewById(R.id.groupmessageactivity_recyclerview);
                recyclerView.setAdapter(new GroupMessageRecyclerviewAdapter());
                recyclerView.setLayoutManager(new LinearLayoutManager(GroupMessageActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void init(){
        ListView listView = (ListView) findViewById(R.id.groupmessageactivity_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    comments.clear();
                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.uid = uid;
                    comment.message = chatmacro.get(position);
                    comment.timestamp = ServerValue.TIMESTAMP;
                    FirebaseDatabase.getInstance().getReference().child("chatrooms")
                            .child(destinationRoom).child("comments").push()
                            .setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //editText.setText("");
                        }
                    });
                }

        });

    }
    class GroupMessageRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public GroupMessageRecyclerviewAdapter(){
            getMessageList();
        }
        void getMessageList(){

            FirebaseDatabase.getInstance().getReference().child("chatrooms").child(destinationRoom).child("comments").addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments.clear();

                    for (DataSnapshot item : snapshot.getChildren()){
                        comments.add(item.getValue(ChatModel.Comment.class));
                    }
                    notifyDataSetChanged(); // 리스트 갱신
                    recyclerView.scrollToPosition(comments.size() - 1); // 하단으로 내려감 -> 코멘트 사이즈의 -1
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message,parent,false);

            setListViews();
            return new GroupMessageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            GroupMessageViewHolder messageViewHolder = ((GroupMessageViewHolder)holder);

            // 내가 보낸 메세지
            if(comments.get(position).uid.equals(uid)){ // 앞은 코멘트의 uid 뒤는 로그인한 나의 uid
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.rightbubble);
                messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);
                // 상대방이 보낸 메세지
            }else{
                Glide.with(holder.itemView.getContext())
                        .load(users.get(comments.get(position).uid).profileImageUrl)
                        .apply(new RequestOptions().circleCrop())
                        .into(messageViewHolder.imageView_profile);
                messageViewHolder.textView_name.setText(users.get(comments.get(position).uid).mName);
                messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                messageViewHolder.textView_message.setBackgroundResource(R.drawable.leftbubble);
                messageViewHolder.textView_message.setText(comments.get(position).message);
                messageViewHolder.textView_message.setTextSize(25);
                messageViewHolder.linearLayout_main.setGravity(Gravity.LEFT);

            }
            // 이거 안쓰면 밀리언즈 시간? 이상한 숫자 나옴 한국 시간으로 초기화 시키기
            long unixTime = (long) comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            messageViewHolder.textView_timestamp.setText(time);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class GroupMessageViewHolder extends RecyclerView.ViewHolder {
            public TextView textView_message;
            public TextView textView_name;
            public ImageView imageView_profile;
            public LinearLayout linearLayout_destination;
            public LinearLayout linearLayout_main;
            public TextView textView_timestamp;
            public GroupMessageViewHolder(View view) {
                super(view);

                textView_message = (TextView)view.findViewById(R.id.messageItem_textView_message);
                textView_name = (TextView)view.findViewById(R.id.messageItem_textView_name);
                imageView_profile = (ImageView)view.findViewById(R.id.messageItem_imageView_profile);
                linearLayout_destination = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_destination);
                linearLayout_main = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_main);
                textView_timestamp = (TextView)view.findViewById(R.id.messageItem_textView_timestamp);
            }
        }
        private void setListViews() {
            ListView listView = (ListView)findViewById(R.id.groupmessageactivity_listview);
            MacroAdapter macroAdapter = new MacroAdapter(getBaseContext(), chatmacro);
            listView.setAdapter(macroAdapter);
            listView.setItemChecked(0, true);
            listView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        }
    }

    public static class MessageActivity extends AppCompatActivity {
        //1:1 채팅

        private String destinationUid;
        private Button button;
        private EditText editText;
        private String uid,meetTitle;
        private int meetAge,meetNumMember;
        private String chatRoomUid;
        private RecyclerView recyclerView;
        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH:mm");
        private Member destinationUserModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_message);
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            destinationUid = getIntent().getStringExtra("destinationUid");
            meetTitle=getIntent().getStringExtra("meetTitle");
            meetAge=getIntent().getIntExtra("meetAge",1);
            meetNumMember=getIntent().getIntExtra("meetNumMember",1);

            button = (Button) findViewById(R.id.messageActivity_button);
            editText = (EditText) findViewById(R.id.messageActivity_editText);

            recyclerView = (RecyclerView) findViewById(R.id.messageActivity_recyclerciew);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ChatModel chatModel = new ChatModel();
                    chatModel.users.put(uid, true);
                    chatModel.users.put(destinationUid, true);
                    //chatModel에 넣기
                    chatModel.meetInfo.put(meetTitle,true);
                    chatModel.meetInfo.put(String.valueOf(meetAge),true);
                    chatModel.meetInfo.put(String.valueOf(meetNumMember),true);

                    if (chatRoomUid == null) {
                        button.setEnabled(false);
                        //2021/08/12 추가(chatModel 변경 위해)
                        //Meet meet = new Meet();//meet 정보 chatroom data에 저장하기 위해서
                        //meet.title =meetTitle;
                        //meet.meetAge = meetAge;
                        //meet.numMember = meetNumMember;

                        //
                        FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                checkChatRoom();
                            }
                        });
                    } else {
                        ChatModel.Comment comment = new ChatModel.Comment();
                        comment.uid = uid;
                        comment.message = editText.getText().toString();
                        comment.timestamp = ServerValue.TIMESTAMP; // 파이어베이스에서 제공하는 서버시간
                        FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //sendGcm();
                                editText.setText("");
                            }
                        });

                    }
                }
            });
            checkChatRoom();
        }
        /*void sendGcm(){
            Gson gson = new Gson();
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.to = destinationUserModel.pushToken;
            notificationModel.notification.title = "보낸이 아이디";
            notificationModel.notification.text = editText.getText().toString();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(notificationModel));

            Request request = new Request.Builder()
                    .header("Content-Type","application/json")
                    .addHeader("Autoriztion","key=AAAAz4hSJDk:APA91bGe7EvHWIvaiP1gE84xEXrrZevqV1A9yPpDjWK1wI9k9ia-Dq-E3A-mjFW0ebNSc40OXs6QfWZFZSa_Qkz_Im9ZM3WptObdDegjJKFrvANQPCwek2dsIa8S5kNcVvPsoDQGZGw9")
                    .url("https://fcm.googleapis.com/fcm/send")
                    .post(requestBody)
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                }
            });
        }*/

        void checkChatRoom() { // 방의 중복 체크
            FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("users/" + uid).equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot item : snapshot.getChildren()) {
                        ChatModel chatModel = item.getValue(ChatModel.class);
                        if (chatModel.users.containsKey(destinationUid) && chatModel.users.size() == 2){
                            chatRoomUid = item.getKey();
                            button.setEnabled(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MessageActivity.this));
                            recyclerView.setAdapter(new RecyclerViewAdapter());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

            List<ChatModel.Comment> comments;
            public RecyclerViewAdapter() {
                comments = new ArrayList<>();

                FirebaseDatabase.getInstance().getReference().child("users").child(destinationUid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        destinationUserModel = snapshot.getValue(Member.class);
                        getMessageList();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            void getMessageList(){

                FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomUid).child("comments").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        comments.clear();

                        for (DataSnapshot item : snapshot.getChildren()){
                            comments.add(item.getValue(ChatModel.Comment.class));
                        }
                        notifyDataSetChanged(); // 리스트 갱신
                        recyclerView.scrollToPosition(comments.size() - 1); // 하단으로 내려감 -> 코멘트 사이즈의 -1
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);

                return new MessageViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

                // 내가 보낸 메세지
                if(comments.get(position).uid.equals(uid)){ // 앞은 코멘트의 uid 뒤는 로그인한 나의 uid
                    messageViewHolder.textView_message.setText(comments.get(position).message);
                    messageViewHolder.textView_message.setBackgroundResource(R.drawable.rightbubble);
                    messageViewHolder.linearLayout_destination.setVisibility(View.INVISIBLE);
                    messageViewHolder.textView_message.setTextSize(25);
                    messageViewHolder.linearLayout_main.setGravity(Gravity.RIGHT);
                    // 상대방이 보낸 메세지
                }else{
                    Glide.with(holder.itemView.getContext())
                            .load(destinationUserModel.profileImageUrl)
                            .apply(new RequestOptions().circleCrop())
                            .into(messageViewHolder.imageView_profile);
                    messageViewHolder.textView_name.setText(destinationUserModel.mName);
                    messageViewHolder.linearLayout_destination.setVisibility(View.VISIBLE);
                    messageViewHolder.textView_message.setBackgroundResource(R.drawable.leftbubble);
                    messageViewHolder.textView_message.setText(comments.get(position).message);
                    messageViewHolder.textView_message.setTextSize(25);
                    messageViewHolder.linearLayout_main.setGravity(Gravity.LEFT);

                }
                // 이거 안쓰면 밀리언즈 시간? 이상한 숫자 나옴 한국 시간으로 초기화 시키기
                long unixTime = (long) comments.get(position).timestamp;
                Date date = new Date(unixTime);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                String time = simpleDateFormat.format(date);
                messageViewHolder.textView_timestamp.setText(time);
            }

            @Override
            public int getItemCount() {
                return comments.size();
            }

            private class MessageViewHolder extends RecyclerView.ViewHolder {
                public TextView textView_message;
                public TextView textView_name;
                public ImageView imageView_profile;
                public LinearLayout linearLayout_destination;
                public LinearLayout linearLayout_main;
                public TextView textView_timestamp;

                public MessageViewHolder(View view) {
                    super(view);
                    textView_message = (TextView)view.findViewById(R.id.messageItem_textView_message);
                    textView_name = (TextView)view.findViewById(R.id.messageItem_textView_name);
                    imageView_profile = (ImageView)view.findViewById(R.id.messageItem_imageView_profile);
                    linearLayout_destination = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_destination);
                    linearLayout_main = (LinearLayout)view.findViewById(R.id.messageItem_linearlayout_main);
                    textView_timestamp = (TextView)view.findViewById(R.id.messageItem_textView_timestamp);
                }
            }
        }
        // back 키 옵션
        @Override
        public void onBackPressed() {
            finish();
            overridePendingTransition(R.anim.fromleft,R.anim.toright);
        }
    }
}