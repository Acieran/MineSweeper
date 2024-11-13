package org.acieran.minesweeper;

public class CleanTile extends Tile{
    protected int proximityMineCount;

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
