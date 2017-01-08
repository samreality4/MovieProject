package com.example.android.hxh;

import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

public class Profile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ios = inflater.inflate(R.layout.activity_profile, container, false);
        ((TextView)ios.findViewById(R.id.textView)).setText("Profile");
        return ios;
    }}