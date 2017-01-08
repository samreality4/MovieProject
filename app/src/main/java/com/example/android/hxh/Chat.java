package com.example.android.hxh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Chat extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View windows = inflater.inflate(R.layout.chat_main, container, false);
        ((TextView)windows.findViewById(R.id.textView)).setText("Chat");
        return windows;
    }}