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

    protected void countProximityMineCount(Tile[][] smalltile) {
        if (smalltile == null)
            throw new IllegalArgumentException("Передано пустое поле для подсчета мин");
        proximityMineCount = 0;
        for (Tile[] tiles : smalltile)
        {
            for (Tile tile : tiles)
            {
                if (tile instanceof Mine)
                    proximityMineCount++;
            }
        }
        mineCountSet = true;
    }

    @Override
    public void Open() {
        super.Open();
        //TODO Create Open Class in CleanTile
    }
}
