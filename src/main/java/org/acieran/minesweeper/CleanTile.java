package org.acieran.minesweeper;

public class CleanTile extends Tile{
    protected int proximityMineCount;
    protected boolean mineCountSet;
    protected final static CleanTile nullTile = new CleanTile(-1,-1);

    public CleanTile(int x, int y)
    {
        super(x, y);
    }

    public int getProximityMineCount() {
        return proximityMineCount;
    }

    public void countProximityMineCount(Tile[][] smalltile) {
        for (Tile[] tiles : smalltile) {
            for (Tile tile : tiles) {
                if (tile instanceof Mine)
                    proximityMineCount++;
            }
        }
        mineCountSet = true;
    }

    @Override
    public void Open() {
        //TODO Create Open Class in CleanTile
    }
}
