package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class TileTest
{

    @Test
    void open()
    {
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4})
    void mark(int markTimes)
    {
        Tile tile = new Tile(0,0);
        for (int i = 0; i < markTimes; i++)
        {
            tile.Mark();
        }
        if (markTimes > 3)
            markTimes %= 3;
        switch (markTimes)
        {
            case 1:
                assertEquals(Mark.MINE,tile.mark);
                break;
            case 2:
                assertEquals(Mark.QUESTION,tile.mark);
                break;
            case 3:
                assertEquals(Mark.NONE,tile.mark);
                break;
        }
    }
}