package com.example.android.hxh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    /**
     * Go to main
     *
     * @param view
     */
    public void goToMain(View view) {
        goTo(MainActivity.class);
    }

    /**
     * Go to chatroom
     *
     * @param view
     */
    public void goToChatroom(View view) {
        goTo(ChatRoom.class);
    }

    /**
     * Go to profile tab
     *
     * @param view
     */
    public void goToProfile(View view) {
        goTo(ProfileActivity.class);
    }

    private void goTo(Class className) {
        Intent intent = new Intent(getApplicationContext(), className);
        startActivity(intent);
        finish();
    }
}
