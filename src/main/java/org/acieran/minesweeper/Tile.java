package org.acieran.minesweeper;

public class Tile {
    protected boolean isOpen = false;
    protected int x;
    protected int y;
    protected static Mark mark;

    private enum Mark {
        NONE,
        MINE,
        QUESTION;

        private static int current = 0;

        public static Mark next() {
            Mark nextMark = values()[current]; // Get the current value
            current = (current + 1) % values().length; // Increment and wrap around
            return nextMark;
        }
    }

    public Tile(int x,int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void Open()
    {
        isOpen = true;
    }

    public Mark getMark() {
        return mark;
    }

    public void Mark()
    {
        mark = Mark.next();
    }
}
