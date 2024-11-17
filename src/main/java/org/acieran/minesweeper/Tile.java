package org.acieran.minesweeper;

public class Tile {
    protected boolean isOpen = false;
    protected int x;
    protected int y;
    protected Mark mark = Mark.NONE;

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

    //TODO Finish testMark for Tile and implement mark changing on left Click
    public Mark Mark()
    {
        mark = mark.next();
        return mark;
    }
}
