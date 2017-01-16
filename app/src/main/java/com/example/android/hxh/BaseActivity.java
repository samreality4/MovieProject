package com.example.android.hxh;

import android.animation.Animator;
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
        goTo(ChatActivity.class);
    }

    /**
     * Go to profile tab
     *
     * @param view
     */
    public void goToProfile(View view) {
        goTo(ProfileActivity.class);
    }

    /**
     * Go to library tab
     *
     * @param view
     */
    public void goToLibrary(View view) {
        goTo(LibraryActivity.class);
    }

    private void goTo(Class className) {
        Intent intent = new Intent(getApplicationContext(), className);
        startActivity(intent);
        finish();
    }
    public void didTapButton(View view) {
        Button button = (Button)findViewById(R.id.button);
        final Animator myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        button.startanimator(myAnim);
    }
}
