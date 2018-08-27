package com.example.hellojnicallback.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}