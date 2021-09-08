package com.cookandroid.withmetabbar.model;

import java.util.HashMap;
import java.util.Map;

public class ChatModel {

    public Map<String,Boolean> users = new HashMap<>(); // 채팅방 유저들
    public Map<String,Boolean> meetInfo = new HashMap<>(); // 모임방 정보들
    public Map<String,Comment> comments = new HashMap<>(); // 채팅방 내용들


    public static  class Comment{
        public String uid;
        public String message;
        public Object timestamp;

    }

}
