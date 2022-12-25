package ru.ggproject.ticneontactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import ru.ggproject.ticneontactoe.view.activity.ModeSelectActivity;
import ru.ggproject.ticneontactoe.view.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.bt_ai).setOnClickListener(onClickListener);
        findViewById(R.id.bt_friends).setOnClickListener(onClickListener);

        ModeSelectActivity.startActivity(this);
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_ai:
                    break;
                case R.id.bt_friends:
                    break;
            }
        }
    };
}