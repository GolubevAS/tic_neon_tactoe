package ru.ggproject.ticneontactoe.view.base;



import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ru.ggproject.ticneontactoe.App;

public class BaseActivity extends AppCompatActivity {

    public static boolean isShow = true;
    public boolean music;
    public boolean sounds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((App)getApplication()).startBgm();
        isShow = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!isShow)
            ((App)getApplication()).stopBgm();
        else
            isShow = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

