package com.example.shuoyexiqi.demo;

import android.util.Log;

import com.example.shuoyexiqi.demo.annotation.UseCase;

import java.lang.reflect.Method;

/**
 * Created by shuoyexiqi on 2018/3/16.
 */

public class AnnotationTracker {
    private static final String TAG = "Annotation";

    public static void track(Class<?> clazz){
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            UseCase useCase = method.getAnnotation(UseCase.class);
            if(useCase!=null){
                int id = useCase.id();
                String description = useCase.description();
                Log.d(TAG, "track id: " + id + " desc: " + description);
            }
        }
    }
}
