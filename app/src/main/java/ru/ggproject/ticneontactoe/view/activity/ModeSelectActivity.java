package ru.ggproject.ticneontactoe.view.activity;



import androidx.appcompat.app.AlertDialog;
import ru.ggproject.ticneontactoe.App;
import ru.ggproject.ticneontactoe.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import ru.ggproject.ticneontactoe.utils.FontDisplayUtil;
import ru.ggproject.ticneontactoe.utils.SharedPreferencesManager;
import ru.ggproject.ticneontactoe.view.base.BaseActivity;

public class ModeSelectActivity extends BaseActivity {

    public static void startActivity(Context mContext){
        mContext.startActivity(new Intent(mContext, ModeSelectActivity.class));
    }

    SharedPreferencesManager instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);


        findViewById(R.id.img_ai).setOnClickListener(onClickListener);
        findViewById(R.id.img_friends).setOnClickListener(onClickListener);
        findViewById(R.id.img_history).setOnClickListener(onClickListener);
        findViewById(R.id.img_setting).setOnClickListener(onClickListener);
        findViewById(R.id.img_quit).setOnClickListener(onClickListener);

        instance = SharedPreferencesManager.getInstance(this);
        music = instance.getMusic();
        sounds = instance.getSound();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            ((App)getApplication()).starttip();
            switch (v.getId()){
                case R.id.img_ai:
                    ChessPieceActivity.startActivity(ModeSelectActivity.this, 0);
                    break;
                case R.id.img_friends:
                    ChessPieceActivity.startActivity(ModeSelectActivity.this, 1);
                    break;
                case R.id.img_history:
                    RecordActivity.startActivity(ModeSelectActivity.this);
                    break;
                case R.id.img_setting:
                    setting();
                    break;
                case R.id.img_quit:
                    finish();
                    break;
            }
        }
    };

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setting(){
        AlertDialog alertDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View inflate = LayoutInflater.from(this).inflate(R.layout.view_alert_setting, null);
        final ImageView imgMusic, imgSounds;
        imgMusic = inflate.findViewById(R.id.img_music);
        if(music){
            imgMusic.setImageDrawable(getResources().getDrawable(R.mipmap.on_switch));
        }else {
            imgMusic.setImageDrawable(getResources().getDrawable(R.mipmap.off_switch));
        }
        imgSounds = inflate.findViewById(R.id.img_sounds);
        if(sounds){
            imgSounds.setImageDrawable(getResources().getDrawable(R.mipmap.on_switch));
        }else {
            imgSounds.setImageDrawable(getResources().getDrawable(R.mipmap.off_switch));
        }

        inflate.findViewById(R.id.img_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App)getApplication()).starttip();
                music = !music;
                instance.saveMusic(music);
                if(music){
                    ((App)getApplication()).startBgm();
                    imgMusic.setImageDrawable(getResources().getDrawable(R.mipmap.on_switch));
                }else {
                    ((App)getApplication()).stopBgm();
                    imgMusic.setImageDrawable(getResources().getDrawable(R.mipmap.off_switch));
                }
            }
        });
        inflate.findViewById(R.id.img_sounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App)getApplication()).starttip();
                sounds = !sounds;
                instance.saveSound(sounds);
                if(sounds){
                    imgSounds.setImageDrawable(getResources().getDrawable(R.mipmap.on_switch));
                }else {
                    imgSounds.setImageDrawable(getResources().getDrawable(R.mipmap.off_switch));
                }
            }
        });
        builder.setView(inflate);
        alertDialog = builder.create();
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = FontDisplayUtil.dip2px(this, 272);
        params.height = FontDisplayUtil.dip2px(this, 182);
        alertDialog.getWindow().setAttributes(params);
        inflate.setBackground(getResources().getDrawable(R.mipmap.settings_sheet));
    }
}