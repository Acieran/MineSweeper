package org.acieran.minesweeper;

public class Mode {
    protected static int height;
    protected static int width;
    protected static int numberOfMines;

    public enum Difficulty {
        BEGINNER, INTERMEDIATE, EXPERT, CUSTOM
    }

    protected Difficulty difficulty;

    public Mode(int height, int width, int numberOfMines, Difficulty difficulty) {
        this.difficulty = difficulty;
        Mode.height = height;
        Mode.width = width;
        Mode.numberOfMines = numberOfMines;
    }

    public Mode() {
        new Mode(Difficulty.BEGINNER);
    }

    public Mode(Difficulty difficulty) {
        switch (difficulty)
        {
            case BEGINNER -> new Mode(9,9,10,Difficulty.BEGINNER);
            case INTERMEDIATE -> new Mode(16,16,40,Difficulty.INTERMEDIATE);
            case EXPERT -> new Mode(16,30,99,Difficulty.EXPERT);
        }
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Mode.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Mode.width = width;
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

    public static void setNumberOfMines(int numberOfMines) {
        Mode.numberOfMines = numberOfMines;
    }
}