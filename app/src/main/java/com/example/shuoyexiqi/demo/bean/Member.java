package com.example.shuoyexiqi.demo.bean;

import com.example.shuoyexiqi.demo.annotation.Constraints;
import com.example.shuoyexiqi.demo.annotation.DBTable;
import com.example.shuoyexiqi.demo.annotation.SQLInteger;
import com.example.shuoyexiqi.demo.annotation.SQLString;

/**
 * Created by shuoyexiqi on 2018/3/17.
 */

@DBTable(name = "member")
public class Member {
    @SQLString(26)
    String job;

    @SQLString(30)
    String firstName;

    @SQLString(50)
    String lastName;

    @SQLInteger
    Integer age;

    @SQLString(value = 30, constraints = @Constraints(primaryKey = true))
    String handle;

}
