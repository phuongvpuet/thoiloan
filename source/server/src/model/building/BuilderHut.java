package model.building;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class BuilderHut extends Building {
    private int desBuildingId;

    public BuilderHut(int id, int posX, int posY) {
        this.buildingId = id;
        this.buildingType = BuildingType.BDH_1;
        this.level = 1;
        this.posX = posX;
        this.posY = posY;
        this.startTime = 0;
        this.desBuildingId = -1;
    }

    public int getDesBuildingId() {
        return desBuildingId;
    }

    public void setDesBuildingId(int desBuildingId) {
        this.desBuildingId = desBuildingId;
    }

    @Override
    public long getRemainingTime() {
        return 0;
    }
}
