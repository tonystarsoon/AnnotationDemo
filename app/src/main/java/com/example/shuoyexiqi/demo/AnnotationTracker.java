package com.example.shuoyexiqi.demo;

import android.text.TextUtils;
import android.util.Log;

import com.example.shuoyexiqi.demo.annotation.Constraints;
import com.example.shuoyexiqi.demo.annotation.DBTable;
import com.example.shuoyexiqi.demo.annotation.SQLInteger;
import com.example.shuoyexiqi.demo.annotation.SQLString;
import com.example.shuoyexiqi.demo.annotation.UseCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuoyexiqi on 2018/3/16.
 */

public class AnnotationTracker {
    private static final String TAG = "Annotation";

    public static void track(Class<?> clazz) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            UseCase useCase = method.getAnnotation(UseCase.class);
            if (useCase != null) {
                int id = useCase.id();
                String description = useCase.description();
                Log.d(TAG, "track id: " + id + " desc: " + description);
            }
        }
    }

    public static void trackTable(String[] args) {
        for (String clsName : args) {
            try {
                Class<?> clazz = Class.forName(clsName);
                DBTable dbTable = clazz.getAnnotation(DBTable.class);
                if (dbTable == null) {
                    Log.d(TAG, "trackTable dbTable: " + clsName);
                    continue;
                }
                String tableName = dbTable.name();
                if (TextUtils.isEmpty(tableName)) {
                    tableName = clsName.toUpperCase();
                }
                List<String> columnsDefs = new ArrayList<>();
                for (Field field : clazz.getDeclaredFields()) {
                    Annotation[] annotations = field.getDeclaredAnnotations();
                    if (annotations.length < 1) {
                        continue;
                    }
                    String columnName = null;
                    if (annotations[0] instanceof SQLInteger) {
                        SQLInteger sInt = (SQLInteger) annotations[0];
                        if (sInt.name().length() < 1) {
                            columnName = field.getName().toUpperCase();
                        } else {
                            columnName = sInt.name();
                        }
                        columnsDefs.add(columnName + " INT " + getConstraints(sInt.constraints()));
                    } else if (annotations[0] instanceof SQLString) {
                        SQLString qStr = (SQLString) annotations[0];
                        if (qStr.name().length() < 1) {
                            columnName = field.getName().toUpperCase();
                        } else {
                            columnName = qStr.name();
                        }
                        columnsDefs.add(columnName + " VARCHAR(" + qStr.value() + ")" + getConstraints(qStr.constraints()));
                    }
                }
                StringBuilder sb = new StringBuilder("CREATE TABLE " + tableName + "(");
                for (int index = 0; index < columnsDefs.size(); index++) {
                    String def = columnsDefs.get(index);
                    sb.append(" " + def);
                    if (index == columnsDefs.size() - 1) {
                        sb.append(")");
                    } else {
                        sb.append(",");
                    }
                }
                Log.d(TAG, "SQL : " + sb.toString());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getConstraints(Constraints con) {
        String constrains = "";
        if (!con.allowNull()) {
            constrains += " NOT NULL";
        }
        if (con.primaryKey()) {
            constrains += " PRIMARY KEY";
        }
        if (con.unique()) {
            constrains += " UNIQUE";
        }
        return constrains;
    }
}















