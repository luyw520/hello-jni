// ILuAidlInterface.aidl
package com.lu.aidl;

// Declare any non-default types here with import statements

interface ILuAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

            void callFunction(String str);
    int add(int a,int b);
}
