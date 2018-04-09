package com.example.hellojnicallback;

/**
 * Created by Administrator on 2018/3/28 0028.
 */

public class BugTest {

    @Replace(clazz = "com.example.hellojnicallback.BugTest",methoName = "testBug")
    public  int testBug() throws NullPointerException {

        int i=0;
        int j=10;
        return j/i;
    }
}
