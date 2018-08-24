package com.example.hellojnicallback.binder;
import com.example.hellojnicallback.binder.Book;
interface IBookManager{
    List<Book> getBookList();
    void addBook(in Book book);
}