package org.acieran.minesweeper;

public enum Mark {
    NONE,
    MINE,
    QUESTION;

    private int current = 0;

    public Mark next() {
        current = (current + 1) % values().length; // Increment and wrap around
        Mark nextMark = values()[current]; // Get the next value
        return nextMark;
    }
}