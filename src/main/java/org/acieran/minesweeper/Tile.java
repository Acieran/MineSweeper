package org.acieran.minesweeper;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile implements CanBeOpened,CanBeMarked{
    private final int x;
    private final int y;

    public enum MarkItem {
        NONE,
        MINE,
        QUESTION;

        //Making static List with MarkItems to not Initialize new Variable every time
        private static final List<MarkItem> items = init();

        //Initializing static List with MarkItem Values
        static List<MarkItem> init() {
            List<MarkItem> items = new ArrayList<>();
            for (int i = 0; i < MarkItem.values().length; i++) {
                items.add(i, MarkItem.values()[i]);
            }
            return items;
        }

        //Method to return next item without size concerns
        private static MarkItem next(MarkItem item) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).equals(item)) {
                    return items.get((i + 1) % items.size());
                }
            }
            try {
                throw new Exception("Mark Not Found");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private MarkItem mark = MarkItem.NONE;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public ArrayList<Tile> open(GameBoard gameBoard)
    {
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(this);
        return tile;
    }

    public MarkItem getMark() {
        return mark;
    }

    //Get next Mark on Tile Score
    public MarkItem mark() {
        mark = MarkItem.next(mark);
        return mark;
    }
}
