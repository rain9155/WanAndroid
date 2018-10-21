package com.example.hy.wanandroid.view.homepager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_common)
    TextView tvCommon;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.fl_main_container)
    FrameLayout flMainContainer;
    @BindView(R.id.fbtn_main_up)
    FloatingActionButton fbtnMainUp;
    @BindView(R.id.bnv_main_btm)
    BottomNavigationView bnvMainBtm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bnvMainBtm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        bnvMainBtm.setOnNavigationItemSelectedListener(menuItem -> );
    }

}
