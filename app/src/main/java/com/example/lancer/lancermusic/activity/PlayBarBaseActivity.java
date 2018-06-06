package com.example.lancer.lancermusic.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.example.lancer.lancermusic.R;
import com.example.lancer.lancermusic.fragment.playbarFragment;

public abstract class PlayBarBaseActivity extends AppCompatActivity {

    private playbarFragment playBarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        show();
    }

    private void show() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (playBarFragment == null) {
            playBarFragment = new playbarFragment();
            ft.add(R.id.fragment_playbar, playBarFragment).commit();
        } else {
            ft.show(playBarFragment).commit();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
