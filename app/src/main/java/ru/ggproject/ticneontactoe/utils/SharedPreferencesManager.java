package ru.ggproject.ticneontactoe.utils;


import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import ru.ggproject.ticneontactoe.Constant;
import ru.ggproject.ticneontactoe.model.PlayingChessModel;

public class SharedPreferencesManager {

    public static SharedPreferencesManager INSTANCE;

    public static SharedPreferencesManager getInstance(Context mContext){
        if(INSTANCE == null){
            synchronized (SharedPreferencesManager.class){
                if(INSTANCE == null){
                    INSTANCE = new SharedPreferencesManager(mContext);
                }
            }
        }
        return INSTANCE;
    }

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEdit;

    private SharedPreferencesManager(Context mContext){
        mSharedPreferences = mContext.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        mEdit = mSharedPreferences.edit();
    }

    public void putPlayer1Name(String player1Name){
        mEdit.putString(Constant.SHAREDPREFERENCES_KEY_PLAY1_NAME, player1Name);
        mEdit.commit();
    }

    public String getPlayer1Name(){
        return mSharedPreferences.getString(Constant.SHAREDPREFERENCES_KEY_PLAY1_NAME, "");
    }

    public void putPlayer2Name(String player2Name){
        mEdit.putString(Constant.SHAREDPREFERENCES_KEY_PLAY2_NAME, player2Name);
        mEdit.commit();
    }

    public String getPlayer2Name(){
        return mSharedPreferences.getString(Constant.SHAREDPREFERENCES_KEY_PLAY2_NAME, "");
    }

    public void putChessman(int chessman){
        mEdit.putInt(Constant.SHAREDPREFERENCES_KEY_CHESSMAN, chessman);
        mEdit.commit();
    }

    public void saveAllChess(List<PlayingChessModel> playingChessModelList){
        Gson gson = new Gson();
        String s = gson.toJson(playingChessModelList);
        mEdit.putString(Constant.SHAREDPREFERENCES_KEY_ALL_CHESS, s);
        mEdit.commit();
    }

    public List<PlayingChessModel> getAllChess(){
        String string = mSharedPreferences.getString(Constant.SHAREDPREFERENCES_KEY_ALL_CHESS, null);
        if(string != null && !"".equals(string)){
            Gson gson = new Gson();
            return gson.fromJson(string, new TypeToken<List<PlayingChessModel>>(){}.getType());
        }
        return null;
    }

    public void saveSound(boolean sound){
        mEdit.putBoolean(Constant.SHAREDPREFERENCES_KEY_SOUND, sound);
        mEdit.commit();
    }

    public boolean getSound(){
        return mSharedPreferences.getBoolean(Constant.SHAREDPREFERENCES_KEY_SOUND, true);
    }

    public void saveMusic(boolean music){
        mEdit.putBoolean(Constant.SHAREDPREFERENCES_KEY_MUSIC, music);
        mEdit.commit();
    }

    public boolean getMusic(){
        return mSharedPreferences.getBoolean(Constant.SHAREDPREFERENCES_KEY_MUSIC, true);
    }

}


