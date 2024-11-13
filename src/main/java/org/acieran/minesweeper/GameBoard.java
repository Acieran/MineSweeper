package org.acieran.minesweeper;

public class GameBoard {
    protected static Mode mode;

    public GameBoard() {
        mode = new Mode();
    }


    public static void setMode(Mode gameDifficulty) {
        mode = gameDifficulty;
    }

    public static Mode getGameDifficulty()
    {
        return mode;
    }






}
