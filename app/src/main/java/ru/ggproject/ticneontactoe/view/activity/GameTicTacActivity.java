package ru.ggproject.ticneontactoe.view.activity;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import ru.ggproject.ticneontactoe.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ru.ggproject.ticneontactoe.App;
import ru.ggproject.ticneontactoe.Constant;
import ru.ggproject.ticneontactoe.model.PlayingChessModel;
import ru.ggproject.ticneontactoe.model.SmallPlayingChessModel;
import ru.ggproject.ticneontactoe.utils.FontDisplayUtil;
import ru.ggproject.ticneontactoe.utils.SharedPreferencesManager;
import ru.ggproject.ticneontactoe.view.base.BaseActivity;
import ru.ggproject.ticneontactoe.view.manager.ManagerGameTicTac;

public class GameTicTacActivity extends BaseActivity {

    public static void startActivity(Context mContext, int mode, int chessman){
        Intent intent = new Intent(mContext, GameTicTacActivity.class);
        intent.putExtra(Constant.INTENT_KEY_MODE, mode);
        intent.putExtra(Constant.INTENT_KEY_CHESSMAN, chessman);
        mContext.startActivity(intent);
    }

    private Bitmap bitmap;
    Paint paint;
    Canvas canvas;

    LinearLayout linePlayer1, linePlayer2, lineParent;
    TextView tvPlayer1Name, tvPlayer2Name, tvPlayer1WinNumber, tvPlayer2WinNumber;
    RelativeLayout relativeLayout;

    View view;
    boolean userIsFirst;
    PlayingChessModel playingChessModel;
    int mode, selectChessPiece;
    SharedPreferencesManager sharedPreferencesManager;
    String player1Name, player2Name;
    int player1WinNumber, player2WinNumber, draw; // Number of wins by the user, number of wins by artificial intelligence or friends, number of draws
    ManagerGameTicTac managerGameTicTac;

    AlertDialog alertDialog = null;
    int parmsWidth = 0, parmsHeight = 0;
    Handler handler = new Handler(){
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    showDialog(parmsWidth, parmsHeight);
                    break;
            }
        }
    };

    ManagerGameTicTac.ticTacGameListener ticTacGameListener = new ManagerGameTicTac.ticTacGameListener() {
        @Override
        public void playingChange(int playing) {
            ((App)getApplication()).starttip();
            if(playing == 0){
                tvPlayer1Name.setTextColor(getResources().getColor(R.color.select));
                tvPlayer1WinNumber.setTextColor(getResources().getColor(R.color.select));
                tvPlayer2Name.setTextColor(getResources().getColor(R.color.white));
                tvPlayer2WinNumber.setTextColor(getResources().getColor(R.color.white));
            }else {
                tvPlayer1Name.setTextColor(getResources().getColor(R.color.white));
                tvPlayer1WinNumber.setTextColor(getResources().getColor(R.color.white));
                tvPlayer2Name.setTextColor(getResources().getColor(R.color.select));
                tvPlayer2WinNumber.setTextColor(getResources().getColor(R.color.select));
            }
        }

        @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
        @Override
        public void gameOver(int gameOver, int[][] chessData) {
            SmallPlayingChessModel smallPlayingChessModel = new SmallPlayingChessModel();
            smallPlayingChessModel.setTime(System.currentTimeMillis());
            smallPlayingChessModel.setChessModel(mode);
            smallPlayingChessModel.setChessPiece(selectChessPiece);
            smallPlayingChessModel.setWin(gameOver);
            smallPlayingChessModel.setChessData(chessData);
            playingChessModel.getSmallPlayingChessModels().add(smallPlayingChessModel);

            paint = new Paint();
            paint.setStrokeWidth(5);//Pen width 5 pixels
            paint.setColor(Color.RED);//Set on red pen

            bitmap = Bitmap.createBitmap(800, 480, Bitmap.Config.ARGB_8888); //Set the width and height of the bitmap and the bitmap will become transparent
            canvas = new Canvas(bitmap);
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//If set to transparent, the canvas will also be transparent
            canvas.drawLine(0, 20, 750, 200, paint);

            AlertDialog.Builder builder = new AlertDialog.Builder(GameTicTacActivity.this);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    restart();
                }
            });
            View inflate = LayoutInflater.from(GameTicTacActivity.this).inflate(R.layout.view_alert_dialog, null);
            int width = FontDisplayUtil.dip2px(GameTicTacActivity.this, 272+30);
            int height = FontDisplayUtil.dip2px(GameTicTacActivity.this, 290+60);
            int height2 = FontDisplayUtil.dip2px(GameTicTacActivity.this, 245+60);
            ImageView img1, img2;
            TextView tvName1, tvName2, tvWin1, tvWin2;
            img1 = inflate.findViewById(R.id.img_play_1);
            img2 = inflate.findViewById(R.id.img_play_2);
            tvName1 = inflate.findViewById(R.id.tv_name_play_1);
            tvName2 = inflate.findViewById(R.id.tv_name_play_2);
            tvWin1 = inflate.findViewById(R.id.tv_win_count_play_1);
            tvWin2 = inflate.findViewById(R.id.tv_win_count_play_2);

            inflate.findViewById(R.id.img_again).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((App)getApplication()).starttip();
                    restart();
                    alertDialog.dismiss();
                    view.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                }
            });

            inflate.findViewById(R.id.img_home).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((App)getApplication()).starttip();
                    finish();
                }
            });
            builder.setView(inflate);
            alertDialog = builder.create();

            parmsWidth = 0;
            parmsHeight = 0;

            switch (gameOver){
                case 1:
                    img1.setImageDrawable(getResources().getDrawable(R.mipmap.trophy_icon));
                    img2.setImageDrawable(getResources().getDrawable(R.mipmap.cry_icon));
                    tvName1.setText(player1Name);
                    tvName2.setText(player2Name);
                    tvWin1.setText("Победил");
                    tvWin2.setText("Проиграл");
                    tvWin1.setTextColor(getResources().getColor(R.color.num_win));
                    tvWin2.setTextColor(getResources().getColor(R.color.num_fail));
                    parmsWidth = width;
                    parmsHeight = height;
                    inflate.setBackground(getResources().getDrawable(R.mipmap.score_sheet));
                    ((App)getApplication()).startvictory();
                    player1WinNumber++;
                    tvPlayer1WinNumber.setText(player1WinNumber+"");
                    break;
                case 2:
                    img2.setImageDrawable(getResources().getDrawable(R.mipmap.trophy_icon));
                    img1.setImageDrawable(getResources().getDrawable(R.mipmap.cry_icon));
                    tvName1.setText(player1Name);
                    tvName2.setText(player2Name);
                    tvWin2.setText("Победил");
                    tvWin1.setText("Проиграл");
                    tvWin2.setTextColor(getResources().getColor(R.color.num_win));
                    tvWin1.setTextColor(getResources().getColor(R.color.num_fail));
                    parmsWidth = width;
                    parmsHeight = height;
                    inflate.setBackground(getResources().getDrawable(R.mipmap.score_sheet));
                    ((App)getApplication()).startfail();
                    player2WinNumber++;
                    tvPlayer2WinNumber.setText(player2WinNumber+"");
                    break;
                case 3:
                    img1.setVisibility(View.GONE);
                    img2.setVisibility(View.GONE);
                    tvName1.setText(player1Name);
                    tvName2.setText(player2Name);
                    tvWin2.setText("Ничья");
                    tvWin1.setText("Ничья");
                    tvWin1.setTextColor(getResources().getColor(R.color.num_draw));
                    tvWin2.setTextColor(getResources().getColor(R.color.num_draw));
                    parmsWidth = width;
                    parmsHeight = height2;
                    inflate.setBackground(getResources().getDrawable(R.mipmap.draw_sheet));
                    draw++;
                    break;
            }

            view = findViewById(R.id.view);
            view.setBackgroundColor(Color.RED);
            view.setTranslationX(0);
            view.setTranslationY(0);
            view.setRotation(0);
            final int finalParmsWidth = parmsWidth;
            final int finalParmsHeight = parmsHeight;

            if(gameOver == 3){
                showDialog(finalParmsWidth, finalParmsHeight);
            }else if(gameOver == 1 || gameOver == 2) {
                Animation animation = null;

                if(chessData[0][0] == chessData[1][0] && chessData[1][0] == chessData[2][0] && chessData[2][0] != 0){
                    view.setTranslationY(-lineParent.getWidth()/3);
                    animation = new ScaleAnimation(0f, 1.0f, 1.0f, 1.0f, 0, 0);
                }else if(chessData[0][1] == chessData[1][1] && chessData[1][1] == chessData[2][1] && chessData[2][1] != 0){
                    view.setTranslationY(0);
                    animation = new ScaleAnimation(0f, 1.0f, 1.0f, 1.0f, 0, 0);
                }else if(chessData[0][2] == chessData[1][2] && chessData[1][2] == chessData[2][2] && chessData[2][2] != 0){
                    view.setTranslationY(lineParent.getWidth()/3);
                    animation = new ScaleAnimation(0f, 1.0f, 1.0f, 1.0f, 0, 0);
                }else if(chessData[0][0] == chessData[0][1] && chessData[0][1] == chessData[0][2] && chessData[0][2] != 0){
                    animation = new ScaleAnimation(1.0f, 1.0f, 0f, 1.0f, 0, 0);
                    view.setRotation(90);
                    view.setTranslationX(-lineParent.getWidth()/3);
                }else if(chessData[1][0] == chessData[1][1] && chessData[1][1] == chessData[1][2] && chessData[1][2] != 0){
                    animation = new ScaleAnimation(1.0f, 1.0f, 0f, 1.0f, 0, 0);
                    view.setRotation(90);
                }else if(chessData[2][0] == chessData[2][1] && chessData[2][1] == chessData[2][2] && chessData[2][2] != 0){
                    animation = new ScaleAnimation(1.0f, 1.0f, 0f, 1.0f, 0, 0);
                    view.setRotation(90);
                    view.setTranslationX(lineParent.getWidth()/3);
                }else if(chessData[0][0] == chessData[1][1] && chessData[1][1] == chessData[2][2] && chessData[2][2] != 0){
                    double z = Math.hypot(lineParent.getWidth(), lineParent.getHeight());
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = (int) z;
                    view.setLayoutParams(layoutParams);
                    view.setRotation(45);
                    animation = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, 0, 0);
                }else if(chessData[0][2] == chessData[1][1] && chessData[1][1] == chessData[2][0] && chessData[2][0] != 0){
                    double z = Math.hypot(lineParent.getWidth(), lineParent.getHeight());
                    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.width = (int) z;
                    view.setLayoutParams(layoutParams);
                    view.setRotation(135);
                    animation = new ScaleAnimation(0f, 1.0f, 1.0f, 0f, 1, 1);
                    animation = new TranslateAnimation(-lineParent.getWidth(), 0, lineParent.getHeight(), 0);
                }
                animation.setDuration(2000);
                relativeLayout.startAnimation(animation);
                view.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(0, 800);

            }
        }
    };

    private void showDialog(int finalParmsWidth, int finalParmsHeight){
        alertDialog.show();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = finalParmsWidth;
        params.height = finalParmsHeight;
        alertDialog.getWindow().setAttributes(params);
    }

    private void restart(){
        userIsFirst = !userIsFirst;
        managerGameTicTac.setPlaying(userIsFirst ? 0 : 1);
        managerGameTicTac.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tictac);


        initView();
        initData();
        setData();

        managerGameTicTac = new ManagerGameTicTac(this, mode, selectChessPiece);
        managerGameTicTac.setTicTacGameListener(ticTacGameListener);
        managerGameTicTac.setPlaying(userIsFirst ? 0 : 1);
        managerGameTicTac.start();
    }

    private void initView() {
        linePlayer1 = findViewById(R.id.line_player1);
        linePlayer2 = findViewById(R.id.line_player2);

        lineParent = findViewById(R.id.line_parent);

        tvPlayer1Name = findViewById(R.id.tv_player1);
        tvPlayer2Name = findViewById(R.id.tv_player2);
        tvPlayer1WinNumber = findViewById(R.id.tv_player1_win_number);
        tvPlayer2WinNumber = findViewById(R.id.tv_player2_win_number);

        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((App)getApplication()).starttip();
                finish();
            }
        });

        view = findViewById(R.id.view);
        view.setBackgroundColor(Color.RED);
        view.setVisibility(View.GONE);

        relativeLayout = findViewById(R.id.rela_view);
        relativeLayout.setVisibility(View.GONE);
    }

    private void initData() {
        Intent intent = getIntent();
        mode = intent.getIntExtra(Constant.INTENT_KEY_MODE, 0);
        selectChessPiece = intent.getIntExtra(Constant.INTENT_KEY_CHESSMAN, 0);

        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        player1Name = "Игрок";
        if(mode == 0){
            player2Name = "Противник";
        }else {
            player2Name = sharedPreferencesManager.getPlayer2Name();
        }

        userIsFirst = true;

        player1WinNumber = 0;
        player2WinNumber = 0;
        draw = 0;

        playingChessModel = new PlayingChessModel();
        playingChessModel.setChessModel(mode);
        playingChessModel.setChessPiece(selectChessPiece);
        List<SmallPlayingChessModel> list = new ArrayList<>();
        playingChessModel.setSmallPlayingChessModels(list);

    }

    @SuppressLint("SetTextI18n")
    private void setData(){
        tvPlayer1Name.setText(player1Name);
        tvPlayer2Name.setText(player2Name);
        tvPlayer1WinNumber.setText(player1WinNumber+"");
        tvPlayer2WinNumber.setText(player2WinNumber+"");
    }

    @Override
    protected void onDestroy() {

        if(player1WinNumber == 0 && player2WinNumber == 0 && draw == 0){

        }else {
            List<PlayingChessModel> allChess = sharedPreferencesManager.getAllChess();
            if(allChess == null){
                allChess = new ArrayList<>();
            }
            playingChessModel.setTime(System.currentTimeMillis());
            playingChessModel.setPlayer1(player1Name);
            playingChessModel.setPlayer2(player2Name);
            playingChessModel.setPlayer1WinNumber(player1WinNumber);
            playingChessModel.setPlayer2WinNumber(player2WinNumber);
            playingChessModel.setDraw(draw);
            allChess.add(playingChessModel);
            while (allChess.size() > 10){
                allChess.remove(0);
            }
            sharedPreferencesManager.saveAllChess(allChess);
        }
        super.onDestroy();
    }
}