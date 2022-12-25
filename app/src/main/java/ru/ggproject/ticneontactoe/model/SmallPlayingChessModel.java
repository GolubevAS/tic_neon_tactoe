package ru.ggproject.ticneontactoe.model;


public class SmallPlayingChessModel {

    long time;         //    Timestamp for the end of the game
    int chessModel;    //    Select AI or Player
    int chessPiece;      //     Shape shape 0 round 1 fork
    int[][] chessData; //     Game played in each small game
    int win;           //    Win or lose 0 users 1 artificial intelligence or friends 2 draw


    public void setTime(long time) {
        this.time = time;
    }

    public void setChessModel(int chessModel) {
        this.chessModel = chessModel;
    }

    public void setChessPiece(int chessPiece) {
        this.chessPiece = chessPiece;
    }

    public void setChessData(int[][] chessData) {
        this.chessData = chessData;
    }

    public void setWin(int win) {
        this.win = win;
    }


}

