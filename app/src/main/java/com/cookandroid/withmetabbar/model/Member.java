package com.cookandroid.withmetabbar.model;

import java.util.HashMap;
import java.util.Map;

public class Member {

    public String uid;
    public String id;
    public String pw;
    public String mName;
    public String nick;
    public int mAge;
    public int mGen;
    public int mDegree;//매너온도
    public String profileImageUrl;

    //2021.09.06
    public Map<String, Hobby> mHobbyList = new HashMap<>(); // member의 관심 취미 리스트

    public Member(){

    }

    public Member(String uid, String id, String pw, String mName, String nick, int mAge, String profileImageUrl) {
        this.uid = uid;
        this.id = id;
        this.pw = pw;
        this.mName = mName;
        this.nick = nick;
        this.mAge = mAge;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public int getmGen() {
        return mGen;
    }

    public void setmGen(int mGen) {
        this.mGen = mGen;
    }

    public int getmDegree() {
        return mDegree;
    }

    public void setmDegree(int mDegree) {
        this.mDegree = mDegree;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
