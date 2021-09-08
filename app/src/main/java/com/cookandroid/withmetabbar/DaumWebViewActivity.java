package com.cookandroid.withmetabbar;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

public class DaumWebViewActivity extends Fragment {

    private WebView daum_webView;

    private TextView daum_result;

    private Handler handler;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ViewGroup vGroup = (ViewGroup) inflater.inflate(R.layout.activity_daum_web_view, container, false);

        daum_result = (TextView) vGroup.findViewById(R.id.daum_result);
        daum_webView = (WebView) vGroup.findViewById(R.id.daum_webview);


        // WebView 초기화
        init_webView();


        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();

        return vGroup;
    }

//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_daum_web_view);
//
//        daum_result = (TextView) findViewById(R.id.daum_result);
//
//
//        // WebView 초기화
//
//        init_webView();
//
//
//        // 핸들러를 통한 JavaScript 이벤트 반응
//
//        handler = new Handler();
//
//    }


    public void init_webView() {

        // WebView 설정

//        daum_webView = (WebView) findViewById(R.id.daum_webview);


        // JavaScript 허용

        daum_webView.getSettings().setJavaScriptEnabled(true);


        // JavaScript의 window.open 허용

        daum_webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);


        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌

        daum_webView.addJavascriptInterface(new AndroidBridge(), "TestApp");


        // web client 를 chrome 으로 설정

        daum_webView.setWebChromeClient(new WebChromeClient(){

        });


        // webview url load. php 파일 주소

        daum_webView.loadUrl("http://3.34.140.2/daum.html");

    }


    private class AndroidBridge {

        @JavascriptInterface

        public void setAddress(final String arg1, final String arg2, final String arg3) {

            handler.post(new Runnable() {

                @Override

                public void run() {

                    daum_result.setText(String.format("(%s) %s %s", arg1, arg2, arg3));

                    // WebView를 초기화 하지않으면 재사용할 수 없음

                    init_webView();

                }

            });

        }

    }

}
