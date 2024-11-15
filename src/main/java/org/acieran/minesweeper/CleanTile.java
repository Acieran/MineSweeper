package org.acieran.minesweeper;

public class CleanTile extends Tile{
    protected int proximityMineCount;

    public CleanTile(int x, int y)
    {
        super(x, y);
    }

    public int getProximityMineCount() {
        return proximityMineCount;
    }

    public void setProximityMineCount(int proximityMineCount) {
        this.proximityMineCount = proximityMineCount;
    }

    @Override
    public void Open() {

    }
}
