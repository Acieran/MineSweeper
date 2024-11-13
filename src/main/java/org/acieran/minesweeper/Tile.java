package org.acieran.minesweeper;

public abstract class Tile {
    protected boolean isOpen = false;
    private enum Mark{
        NONE,
        MINE,
        QUESTION
    }
    protected Mark mark;

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

    public void Mark(Mark mark) {
        this.mark = mark;
    }
}
