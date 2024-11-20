package org.acieran.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    protected boolean isOpen = false;
    protected int x;
    protected int y;

    protected enum MarkItem {
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

    protected MarkItem mark = MarkItem.NONE;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOpen() {
        return isOpen;
    }

    protected ArrayList<Tile> open(GameBoard gameBoard)
    {
        isOpen = true;
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(this);
        return tile;
    }

    protected MarkItem getMark() {
        return mark;
    }

    //Get next Mark on Tile Score
    protected MarkItem mark() {
        mark = MarkItem.next(mark);
        return mark;
    }
}
