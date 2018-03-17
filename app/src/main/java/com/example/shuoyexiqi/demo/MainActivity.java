package com.example.shuoyexiqi.demo;

import android.app.Activity;
import android.os.Bundle;

import com.example.shuoyexiqi.demo.bean.Member;


public class MainActivity extends Activity {
    @Deprecated
    private int a = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test test = new Test();

//        AnnotationTracker.track(test.getClass());
        AnnotationTracker.trackTable(new String[]{Member.class.getName()});
    }
}



