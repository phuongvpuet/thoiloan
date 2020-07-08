package model.building;

/**
 * Created by Fresher_LOCAL on 6/26/2020.
 */
public class ArmyCamp extends Building {
    public ArmyCamp(int id, int posX, int posY, long startTime) {
        this.buildingId = id;
        this.buildingType = BuildingType.AMC_1;
        this.level = 1;
        this.posX = posX;
        this.posY = posY;
        this.startTime = startTime;
    }

    @Override
    public long getRemainingTime() {
        if (this.startTime == 0) {
            return 0;
        } else {
            long remainingTime = 0;
            // TODO: read config AMC_1 and calculate remaining time for construct or update
            return remainingTime;
        }
    }
}
