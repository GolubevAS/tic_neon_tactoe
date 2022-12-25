package ru.ggproject.ticneontactoe;


import android.app.Application;
import android.media.MediaPlayer;
import com.flurry.android.FlurryAgent;
import ru.ggproject.ticneontactoe.utils.SharedPreferencesManager;

public class App extends Application {

    public MediaPlayer fail;
    public MediaPlayer victory;
    public MediaPlayer bgm;
    public MediaPlayer tip;

    SharedPreferencesManager sharedPreferencesManager;

    boolean sound;
    boolean music;

    @Override
    public void onCreate() {
        super.onCreate();

        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this.getApplicationContext(), "87QSYNFKR4ZKCMJBQTCF");

        fail = MediaPlayer.create(this, R.raw.fail);
        victory = MediaPlayer.create(this, R.raw.victory);
        bgm = MediaPlayer.create(this, R.raw.music);
        tip = MediaPlayer.create(this, R.raw.sound);

    }

    public void startBgm() {
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        music = sharedPreferencesManager.getMusic();
        if(music && !bgm.isPlaying()){
            bgm = MediaPlayer.create(this, R.raw.music);
            bgm.start();
            bgm.setLooping(true);
        }
    }

    public void stopBgm(){
        if(bgm.isPlaying()){
            bgm.stop();
        }
    }

    public void startfail() {
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        music = sharedPreferencesManager.getMusic();
        if(music && !fail.isPlaying()){
            fail.start();
        }
    }

    public void startvictory() {
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        music = sharedPreferencesManager.getMusic();
        if(music && !victory.isPlaying()){
            victory.start();
        }
    }

    public void starttip() {
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        sound = sharedPreferencesManager.getSound();
        if(sound && !tip.isPlaying()){
            tip.start();
        }
    }
}


