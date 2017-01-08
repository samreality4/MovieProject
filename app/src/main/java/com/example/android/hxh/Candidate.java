package com.example.android.hxh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Candidate extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View android = inflater.inflate(R.layout.activity_main, container, false);
        ((TextView)android.findViewById(R.id.textView)).setText("Candidate");
        return android;
    }}