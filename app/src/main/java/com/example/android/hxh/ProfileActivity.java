package com.example.android.hxh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.hxh.model.Height;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Spinner dropdown = (Spinner)findViewById(R.id.height_value);
        ArrayList<Height> heights = new ArrayList<>();
        for (int i = 48; i <= 96; i++) {
            heights.add(new Height(i));
        }
        ArrayAdapter<Height> adapter = new ArrayAdapter<Height>(this, android.R.layout.simple_spinner_dropdown_item, heights);
        dropdown.setAdapter(adapter);
    }
}
