package com.example.lancer.lancermusic.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.lancer.lancermusic.R;

public class ThemeActivity extends AppCompatActivity {

    private Toolbar themeToolbar;
    private RecyclerView themeRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        initView();
        setSupportActionBar(themeToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

    private void initView() {
        themeToolbar = findViewById(R.id.theme_toolbar);
        themeRv = findViewById(R.id.theme_rv);
    }
}
