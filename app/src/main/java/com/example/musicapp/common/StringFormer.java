package com.example.musicapp.common;

public class StringFormer {

    public static String formString(String... str){
        StringBuilder sb = new StringBuilder();
        for(String s : str)
            sb.append(s);
        return sb.toString();
    }
}
