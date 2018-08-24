package com.example.hellojnicallback.binder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.example.hellojnicallback.binder
 * @description: ${TODO}{ 类注释}
 * @date: 2018/8/21 0021
 */
public class Book implements Parcelable {
    public int bookId;
    public String bookName;

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    protected Book(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
