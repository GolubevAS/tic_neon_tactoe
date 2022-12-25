package ru.ggproject.ticneontactoe.view.activity;



import ru.ggproject.ticneontactoe.App;
import ru.ggproject.ticneontactoe.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import ru.ggproject.ticneontactoe.Constant;
import ru.ggproject.ticneontactoe.utils.SharedPreferencesManager;
import ru.ggproject.ticneontactoe.view.base.BaseActivity;

public class ChessPieceActivity extends BaseActivity {

    public static void startActivity(Context mContext, int mode){
        Intent intent = new Intent(mContext, ChessPieceActivity.class);
        intent.putExtra(Constant.INTENT_KEY_MODE, mode);
        mContext.startActivity(intent);

    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
        @Override
        public void onClick(View v) {
            ((App)getApplication()).starttip();
            switch (v.getId()){
                case R.id.img_continue:
                    if(etPlayerName1.getText().toString().isEmpty()){
                        etPlayerName1.setText("User");
                    }
                    if(mode == 1 && etPlayerName2.getText().toString().isEmpty()){
                        etPlayerName2.setText("Friend");
                    }
                    savePlayerName(etPlayerName1.getText().toString(), etPlayerName2.getText().toString(), selectChessman);
                    GameTicTacActivity.startActivity(ChessPieceActivity.this, mode, selectChessman);
                    break;
                case R.id.img_back:
                    finish();
                    break;
            }
        }
    };

    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ((App)getApplication()).starttip();
            if(isChecked){
                switch (buttonView.getId()){
                    case R.id.radio_one:
                        selectChessman = 0;
                        break;
                    case R.id.radio_two:
                        selectChessman = 1;
                        break;
                }
            }
        }
    };

    TextView tvPlayer2;
    EditText etPlayerName1, etPlayerName2;
    RadioButton rbOne, rbTwo;

    int selectChessman = 0;
    int mode;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chesspiece);


        mode = getIntent().getIntExtra(Constant.INTENT_KEY_MODE, 0);

        tvPlayer2 = findViewById(R.id.tv_palyer2);

        etPlayerName1 = findViewById(R.id.et_palyer1_name);
        etPlayerName2 = findViewById(R.id.et_palyer2_name);

        rbOne = findViewById(R.id.radio_one);
        rbTwo = findViewById(R.id.radio_two);

        rbOne.setOnCheckedChangeListener(onCheckedChangeListener);
        rbTwo.setOnCheckedChangeListener(onCheckedChangeListener);

        findViewById(R.id.img_continue).setOnClickListener(onClickListener);
        findViewById(R.id.img_back).setOnClickListener(onClickListener);

        if(mode == 0){
            tvPlayer2.setVisibility(View.GONE);
            etPlayerName2.setVisibility(View.GONE);
        }else {
            tvPlayer2.setVisibility(View.VISIBLE);
            etPlayerName2.setVisibility(View.VISIBLE);
        }

        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        etPlayerName1.setText(sharedPreferencesManager.getPlayer1Name());
        etPlayerName2.setText(sharedPreferencesManager.getPlayer2Name());
    }

    private void savePlayerName(String playerName1, String playerName2, int selectChessman){
        sharedPreferencesManager.putPlayer1Name(playerName1);
        sharedPreferencesManager.putPlayer2Name(playerName2);
        sharedPreferencesManager.putChessman(selectChessman);
    }
}