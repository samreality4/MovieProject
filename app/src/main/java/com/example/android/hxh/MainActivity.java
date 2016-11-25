package com.example.android.hxh;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.android.hxh.model.Height;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter();
        viewPager.setAdapter(adapter);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner);
        ArrayList<Height> heights = new ArrayList<>();
        for (int i = 48; i <= 96; i++) {
            heights.add(new Height(i));
        }
        ArrayAdapter<Height> adapter2 = new ArrayAdapter<Height>(this, android.R.layout.simple_spinner_dropdown_item, heights);
        dropdown.setAdapter(adapter2);
    }

    private class ImagePagerAdapter extends PagerAdapter {
        private int[] images = new int[] {
            R.drawable.pika,
            R.drawable.eee,
        };

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = MainActivity.this;
            ImageView imageView = new ImageView(context);
            int padding = context.getResources().getDimensionPixelSize(
                    R.dimen.padding_medium);
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setImageResource(images[position]);
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
    //change layout to chat_room//
    public void changelayout(View view){
        setContentView(R.layout.chat_main);
    }
    //change layout to activity_main2//
    public void changelayoutyou(View view){
        setContentView(R.layout.activity_profile);
    }
    //change layout to activity_main//
    public void changelayouther(View view){
        setContentView(R.layout.activity_main);
    }
    public void changelayoutlibrary(View view){
        setContentView(R.layout.library_main);
    }
    public void chatroom1(View view){
        setContentView(R.layout.chat_room);
    }
}
