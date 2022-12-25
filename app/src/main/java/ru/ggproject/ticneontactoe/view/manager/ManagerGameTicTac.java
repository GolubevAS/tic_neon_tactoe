package ru.ggproject.ticneontactoe.view.manager;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import ru.ggproject.ticneontactoe.R;

public class ManagerGameTicTac {

    Activity parent;
    int mode;           //    Режим игры 0: искусственный интеллект режим 1: с друзьями
    int selectChessPiece; //    Внешний вид  фигуры, выбранной пользователем 0: Нолик 1: Крестик
    int playing;        //    0 :пользователь 1 : ai- друзья
    boolean started;    //    Начинается ли игра?
    int gameOver;       //    Результат игры 0: Не более 1: пользователь выиграл 2: искусственный интеллект или друзья выиграли 3: ничья

    ImageView imgOne, imgTwo, imgThree, imgFour, imgFive, imgSix, imgSeven, imgEight, imgNine;
    int[][] chessData;          //    партия, двумерный массив, 3*3, 0 означает отсутствие фигур, 1 означает фигуры пользователя, 2 означает фигуры ИИ или друзей
    ImageView[][] chessViews;   //    Виды, представляющие фигуры
    ticTacGameListener ticTacGameListener;

    public ManagerGameTicTac(Activity activity, int mode, int selectChessman){
        parent = activity;
        this.mode = mode;
        this.selectChessPiece = selectChessman;
        initChess();
    }

    private void initChess(){
        initView();
        initData();
        setChessViewsEnable(false);
    }

    private void initView(){

        imgOne = parent.findViewById(R.id.img_one);
        imgTwo = parent.findViewById(R.id.img_two);
        imgThree = parent.findViewById(R.id.img_three);
        imgFour = parent.findViewById(R.id.img_four);
        imgFive = parent.findViewById(R.id.img_five);
        imgSix = parent.findViewById(R.id.img_six);
        imgSeven = parent.findViewById(R.id.img_seven);
        imgEight = parent.findViewById(R.id.img_eight);
        imgNine = parent.findViewById(R.id.img_nine);

    }

    private void initData(){
        started = false;
        gameOver = 0;
        chessData = new int[3][3];
        chessViews = new ImageView[3][3];
        chessViews[0][0] = imgOne;
        chessViews[1][0] = imgTwo;
        chessViews[2][0] = imgThree;
        chessViews[0][1] = imgFour;
        chessViews[1][1] = imgFive;
        chessViews[2][1] = imgSix;
        chessViews[0][2] = imgSeven;
        chessViews[1][2] = imgEight;
        chessViews[2][2] = imgNine;
    }

    private void setChessData(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                chessData[i][j] = 0;
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                chessViews[i][j].setImageDrawable(null);
                final int finalI = i;
                final int finalJ = j;
                chessViews[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(chessData[finalI][finalJ] == 0){
                            if(mode == 0){
                                userPlay(finalI, finalJ);
                            }else {
                                if(playing == 0){
                                    userPlay(finalI, finalJ);
                                }else {
                                    friendsPlay(finalI, finalJ);
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public void start(){
        started = true;
        setChessData();
        setChessViewsEnable(true);
        if(playing == 1 && mode == 0){
            aiPlay();
        }
    }

    private void gameOver(){
        gameOver = isGameOver();
        started = false;
        setChessViewsEnable(false);
        if(ticTacGameListener != null){
            ticTacGameListener.gameOver(gameOver, chessData);
        }
    }

    public void setPlaying(int playing) {
        if(started){
            try {
                throw new Exception("Игра началась");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            this.playing = playing;
            playingChanged(true);
        }
    }

    private void playingChanged(boolean hasChanged){
        if(!hasChanged){
            playing = playing == 0 ? 1 : 0;
        }
        if(ticTacGameListener != null){
            ticTacGameListener.playingChange(playing);
        }
    }

    public void setTicTacGameListener(ticTacGameListener ticTacGameListener) {
        this.ticTacGameListener = ticTacGameListener;
    }


    private void setChessViewsEnable(boolean enable){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                chessViews[i][j].setEnabled(enable);
            }
        }
    }


    public int isGameOver(){
        if(isUserWin()){
            return 1;
        }else if(isAIWin()){
            return 2;
        }else if(isDraw()){
            return 3;
        }
        return 0;
    }

    private boolean isUserWin(){
        for(int i = 0; i < 3; i++){
            if(chessData[i][0] == 1 && chessData[i][1] == 1 && chessData[i][2] == 1){
                return true;
            }
            if(chessData[0][i] == 1 && chessData[1][i] == 1 && chessData[2][i] == 1){
                return true;
            }
        }
        if(chessData[0][0] == 1 && chessData[1][1] == 1 && chessData[2][2] == 1){
            return true;
        }
        if(chessData[0][2] == 1 && chessData[1][1] == 1 && chessData[2][0] == 1){
            return true;
        }
        return false;
    }

    private boolean isAIWin(){
        for(int i = 0; i < 3; i++){
            if(chessData[i][0] == 2 && chessData[i][1] == 2 && chessData[i][2] == 2){
                return true;
            }
            if(chessData[0][i] == 2 && chessData[1][i] == 2 && chessData[2][i] == 2){
                return true;
            }
        }
        if(chessData[0][0] == 2 && chessData[1][1] == 2 && chessData[2][2] == 2){
            return true;
        }
        if(chessData[0][2] == 2 && chessData[1][1] == 2 && chessData[2][0] == 2){
            return true;
        }
        return false;
    }


    private boolean isDraw(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(chessData[i][j] == 0){
                    return false;
                }
            }
        }
        return true;
    }

    private void aiPlay(){
        int[] ints = inspectHasAITwoChessman();
        if((ints[0] == -1) && (ints[1] == -1)){
            ints = inspectHasUserTwoChessman();
            if((ints[0] == -1) && (ints[1] == -1)){
                if(chessData[1][1] == 0){
                    ints = new int[]{1, 1};
                }else {
                    ints = inspectStrategyOne();
                    if((ints[0] == -1) && (ints[1] == -1)){
                        ints = inspectStrategyTwo();
                        if((ints[0] == -1) && (ints[1] == -1)){
                            ints = inspectStrategyThree();
                            if((ints[0] == -1) && (ints[1] == -1)){
                                for(int i = 0; i < 3; i++){
                                    boolean has = false;
                                    for(int j = 0; j < 3; j++){
                                        if(chessData[i][j] == 0){
                                            ints = new int[]{i, j};
                                            has = true;
                                            break;
                                        }
                                    }
                                    if(has){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        chessData[ints[0]][ints[1]] = 2;
        StringBuffer stringBuffer = new StringBuffer("\n");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                stringBuffer.append(chessData[i][j]+",");
            }
            stringBuffer.append("\n");
        }
        if(selectChessPiece == 0){
            chessViews[ints[0]][ints[1]].setImageResource(R.mipmap.btn_select);
        }else {
            chessViews[ints[0]][ints[1]].setImageResource(R.mipmap.btn_normal);
        }
        if(isGameOver() == 0){
            //    Измените исполнителя
            playingChanged(false);
        }else {
            gameOver();
        }
    }

    private void userPlay(int col, int row){

        //  Выполнить пользовательские шахматы
        chessData[col][row] = 1;
        StringBuffer stringBuffer = new StringBuffer("\n");
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                stringBuffer.append(chessData[i][j]+",");
            }
            stringBuffer.append("\n");
        }
        if(selectChessPiece == 0){
            chessViews[col][row].setImageResource(R.mipmap.btn_normal);
        }else {
            chessViews[col][row].setImageResource(R.mipmap.btn_select);
        }
        //  Определите, заканчивается ли игра после игры в шахматы
        if(isGameOver() == 0){
            //    Если это еще не конец
            //    Измените исполнителя
            playingChanged(false);
            //  Определите, является ли это искусственным интеллектом
            if(mode == 0){
                aiPlay();
            }
        }else {
            gameOver();
        }
    }

    private void friendsPlay(int col, int row){

        chessData[col][row] = 2;
        if(selectChessPiece == 0){
            chessViews[col][row].setImageResource(R.mipmap.btn_select);
        }else {
            chessViews[col][row].setImageResource(R.mipmap.btn_normal);
        }
        if(isGameOver() == 0){
            playingChanged(false);
        }else {
            gameOver();
        }
    }

    private int[] inspectHasAITwoChessman(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(hasAITwoChessman(i, j)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private int[] inspectHasUserTwoChessman(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(hasUserTwoChessman(i, j)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private int[] inspectStrategyOne(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(strategy_one(i, j)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private int[] inspectStrategyTwo(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(strategy_two(i, j)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private int[] inspectStrategyThree(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(strategy_three(i, j)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private boolean hasUserTwoChessman(int x, int y){
        if(chessData[x][y] == 1 || chessData[x][y] == 2){
            return false;
        }
        //  поперек
        if(chessData[x][0] == 0 && chessData[x][1] == 1 && chessData[x][2] == 1){
            return true;
        }else if(chessData[x][0] == 1 && chessData[x][1] == 0 && chessData[x][2] == 1){
            return true;
        }else if(chessData[x][0] == 1 && chessData[x][1] == 1 && chessData[x][2] == 0){
            return true;
        }
        // вертикальный
        if(chessData[0][y] == 0 && chessData[1][y] == 1 && chessData[2][y] == 1){
            return true;
        } else if(chessData[0][y] == 1 && chessData[1][y] == 0 && chessData[2][y] == 1){
            return true;
        } else if(chessData[0][y] == 1 && chessData[1][y] == 1 && chessData[2][y] == 0){
            return true;
        }
        //   Точка на правой косой черте
        if(chessData[0][0] == 0 && chessData[1][1] == 1 && chessData[2][2] == 1 && x == 0 && y == 0){
            return true;
        } else if(chessData[0][0] == 1 && chessData[1][1] == 0 && chessData[2][2] == 1 && x == 1 && y == 1){
            return true;
        } else if(chessData[0][0] == 1 && chessData[1][1] == 1 && chessData[2][2] == 0 && x == 2 && y == 2){
            return true;
        }
        // Точка на левой косой черте
        if(chessData[0][2] == 0 && chessData[1][1] == 1 && chessData[2][0] == 1 && x == 0 && y == 2){
            return true;
        } else if(chessData[0][2] == 1 && chessData[1][1] == 0 && chessData[2][0] == 1 && x == 1 && y == 1){
            return true;
        } else if(chessData[0][2] == 1 && chessData[1][1] == 1 && chessData[2][0] == 0 && x == 2 && y == 0){
            return true;
        }
        return false;
    }

    private boolean hasAITwoChessman(int x, int y){
        if(chessData[x][y] == 1 || chessData[x][y] == 2){
            return false;
        }

        if(chessData[x][0] == 0 && chessData[x][1] == 2 && chessData[x][2] == 2){
            return true;
        }else if(chessData[x][0] == 2 && chessData[x][1] == 0 && chessData[x][2] == 2){
            return true;
        }else if(chessData[x][0] == 2 && chessData[x][1] == 2 && chessData[x][2] == 0){
            return true;
        }

        if(chessData[0][y] == 0 && chessData[1][y] == 2 && chessData[2][y] == 2){
            return true;
        } else if(chessData[0][y] == 2 && chessData[1][y] == 0 && chessData[2][y] == 2){
            return true;
        } else if(chessData[0][y] == 2 && chessData[1][y] == 2 && chessData[2][y] == 0){
            return true;
        }

        if(chessData[0][0] == 0 && chessData[1][1] == 2 && chessData[2][2] == 2 && x == 0 && y == 0){
            return true;
        } else if(chessData[0][0] == 2 && chessData[1][1] == 0 && chessData[2][2] == 2 && x == 1 && y == 1){
            return true;
        } else if(chessData[0][0] == 2 && chessData[1][1] == 2 && chessData[2][2] == 0 && x == 2 && y == 2){
            return true;
        }

        if(chessData[0][2] == 0 && chessData[1][1] == 2 && chessData[2][0] == 2 && x == 0 && y == 2){
            return true;
        } else if(chessData[0][2] == 2 && chessData[1][1] == 0 && chessData[2][0] == 2 && x == 1 && y == 1){
            return true;
        } else if(chessData[0][2] == 2 && chessData[1][1] == 2 && chessData[2][0] == 0 && x == 2 && y == 0){
            return true;
        }
        return false;
    }


    private boolean strategy_one(int x, int y){
        if(chessData[x][y] == 1 || chessData[x][y] == 2){
            return false;
        }

        if(chessData[x][0] == 0 && chessData[x][1] == 0 && chessData[x][2] == 1 && y != 2){
            return true;
        } else if(chessData[x][0] == 0 && chessData[x][1] == 1 && chessData[x][2] == 0 && y != 1){
            return true;
        } else if(chessData[x][0] == 1 && chessData[x][1] == 0 && chessData[x][2] == 0 && y != 0){
            return true;
        }

        if(chessData[0][y] == 0 && chessData[1][y] == 0 && chessData[2][y] == 1  && x != 2){
            return true;
        } else if(chessData[0][y] == 0 && chessData[1][y] == 1 && chessData[2][y] == 0  && x != 1){
            return true;
        } else if(chessData[0][y] == 1 && chessData[1][y] == 0 && chessData[2][y] == 0  && x != 0){
            return true;
        }

        if(chessData[0][0] == 0 && chessData[1][1] == 0 && chessData[2][2] == 1 && x != 2 && y != 2){
            return true;
        } else if(chessData[0][0] == 0 && chessData[1][1] == 1 && chessData[2][2] == 0 && x != 1 && y != 1){
            return true;
        } else if(chessData[0][0] == 1 && chessData[1][1] == 0 && chessData[2][2] == 0 && x != 0 && y != 0){
            return true;
        }

        if(chessData[0][2] == 0 && chessData[1][1] == 0 && chessData[2][0] == 1 && x != 2 && y != 0){
            return true;
        } else if(chessData[0][2] == 0 && chessData[1][1] == 1 && chessData[2][0] == 0 && x != 1 && y != 1){
            return true;
        } else if(chessData[0][2] == 1 && chessData[1][1] == 0 && chessData[2][0] == 0 && x != 0 && y != 2){
            return true;
        }

        if(!(x == 1 && y == 1) && x == 1 || y == 1){
            return false;
        }
        return false;
    }


    private boolean strategy_two(int x, int y){

        if(chessData[x][y] == 2){
            return false;
        }

        if(chessData[x][0] == 0 && chessData[x][1] == 0 && chessData[x][2] == 2){
            return true;
        } else if(chessData[x][0] == 0 && chessData[x][1] == 2 && chessData[x][2] == 0){
            return true;
        } else if(chessData[x][0] == 2 && chessData[x][1] == 0 && chessData[x][2] == 0){
            return true;
        }

        if(chessData[0][y] == 0 && chessData[1][y] == 0 && chessData[2][y] == 2){
            return true;
        } else if(chessData[0][y] == 0 && chessData[1][y] == 2 && chessData[2][y] == 0){
            return true;
        } else if(chessData[0][y] == 2 && chessData[1][y] == 0 && chessData[2][y] == 0){
            return true;
        }

        if(x == 0 && y == 0 || (x == 1 && y == 1) || (x == 2 && y == 2)){
            if(chessData[0][0] == 0 && chessData[1][1] == 0 && chessData[2][2] == 2){
                return true;
            } else if(chessData[0][0] == 0 && chessData[1][1] == 2 && chessData[2][2] == 0){
                return true;
            } else if(chessData[0][0] == 2 && chessData[1][1] == 0 && chessData[2][2] == 0){
                return true;
            }
        }

        if(x == 2 && y == 0 || (x == 1 && y == 1) || (x == 0 && y == 2)) {
            if(chessData[0][2] == 0 && chessData[1][1] == 0 && chessData[2][0] == 2){
                return true;
            } else if(chessData[0][2] == 0 && chessData[1][1] == 2 && chessData[2][0] == 0){
                return true;
            } else if(chessData[0][2] == 2 && chessData[1][1] == 0 && chessData[2][0] == 0){
                return true;
            }
        }

        if(!(x == 1 && y == 1) && x == 1 || y == 1){
            return false;
        }
        return false;
    }

    private boolean strategy_three(int x, int y){

        if(chessData[x][0] == 0 && chessData[x][1] == 0 && chessData[x][2] == 0){
            return true;
        }

        if(chessData[0][y] == 0 && chessData[1][y] == 0 && chessData[2][y] == 0){
            return true;
        }

        if(chessData[0][0] == 0 && chessData[1][1] == 0 && chessData[2][2] == 0){
            return true;
        }

        if(chessData[0][2] == 0 && chessData[1][1] == 0 && chessData[2][0] == 0){
            return true;
        }

        if(!(x == 1 && y == 1) && x == 1 || y == 1){
            return false;
        }
        return false;
    }

    public interface ticTacGameListener {

        void playingChange(int playing);

        void gameOver(int gameOver, int[][] chessData);
    }
}


