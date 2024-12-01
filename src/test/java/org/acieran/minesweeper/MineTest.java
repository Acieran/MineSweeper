package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MineTest {

    @Test
    void open() {
        GameBoard gameBoard = new GameBoard(1,1,1);
        Mine m = new Mine(0, 0);
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Mine(0,0));
        assertEquals(tiles,m.open(gameBoard));
    }
}