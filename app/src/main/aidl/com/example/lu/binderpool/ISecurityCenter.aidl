package com.example.lu.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}