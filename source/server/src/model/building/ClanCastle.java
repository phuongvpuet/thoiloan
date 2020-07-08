package model.building;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class ClanCastle extends Building {
    public ClanCastle(int id, int posX, int posY) {
        this.buildingId = id;
        this.buildingType = BuildingType.CLC_1;
        this.level = 1;
        this.posX = posX;
        this.posY = posY;
        this.startTime = 0;
    }

    @Override
    public long getRemainingTime() {
        return 0;
    }
}
