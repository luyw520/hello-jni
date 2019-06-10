package com.example.lu;

import com.lu.aoplib.annotation.DebugTrace;

public class AopTest {


    @DebugTrace
    public String testAop(int a,int b) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(a+b);
    }
}
