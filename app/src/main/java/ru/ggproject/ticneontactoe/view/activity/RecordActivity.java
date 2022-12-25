package ru.ggproject.ticneontactoe.view.activity;



import ru.ggproject.ticneontactoe.App;
import ru.ggproject.ticneontactoe.R;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ru.ggproject.ticneontactoe.utils.SharedPreferencesManager;
import ru.ggproject.ticneontactoe.view.adapter.MainAdapter;
import ru.ggproject.ticneontactoe.view.base.BaseActivity;

public class RecordActivity extends BaseActivity {

    public static void startActivity(Context mContext){
        mContext.startActivity(new Intent(mContext, RecordActivity.class));
    }

    RecyclerView rvRecord;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);


        rvRecord = findViewById(R.id.rv_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvRecord.setLayoutManager(linearLayoutManager);
        rvRecord.setAdapter(new MainAdapter(this, SharedPreferencesManager.getInstance(this).getAllChess()));
        DividerItemDecoration decor = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        decor.setDrawable(getResources().getDrawable(R.drawable.divide_gray_ten));
        rvRecord.addItemDecoration(decor);

        findViewById(R.id.img_back).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            ((App)getApplication()).starttip();
            switch (v.getId()){
                case R.id.img_back:
                    finish();
                    break;
            }
        }
    };
}