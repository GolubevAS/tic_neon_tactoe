package ru.ggproject.ticneontactoe.model;


import java.util.List;

public class PlayingChessModel {

    long time;       //    Timestamp for the end of the game
    int chessModel;  //    Select AI or Player
    int chessPiece;    //    Shape shape 0 round 1 fork

    List<SmallPlayingChessModel> smallPlayingChessModels;//    Every little game

    int player1WinNumber;  //    user Number of wins
    int player2WinNumber;
    int draw;             //    Number of draws

    String player1;      //    nic Player
    String player2;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setChessModel(int chessModel) {
        this.chessModel = chessModel;
    }

    public void setChessPiece(int chessPiece) {
        this.chessPiece = chessPiece;
    }

    public List<SmallPlayingChessModel> getSmallPlayingChessModels() {
        return smallPlayingChessModels;
    }

    public void setSmallPlayingChessModels(List<SmallPlayingChessModel> smallPlayingChessModels) {
        this.smallPlayingChessModels = smallPlayingChessModels;
    }

    public int getPlayer1WinNumber() {
        return player1WinNumber;
    }

    public void setPlayer1WinNumber(int player1WinNumber) {
        this.player1WinNumber = player1WinNumber;
    }

    public int getPlayer2WinNumber() {
        return player2WinNumber;
    }

    public void setPlayer2WinNumber(int player2WinNumber) {
        this.player2WinNumber = player2WinNumber;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}


