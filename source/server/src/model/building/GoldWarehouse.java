package model.building;

import model.common.Resource;

import java.util.HashMap;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class GoldWarehouse extends Warehouse {
    public GoldWarehouse(int id, int posX, int posY, long startTime) {
        this.buildingId = id;
        this.buildingType = BuildingType.STO_1;
        this.level = 1;
        this.posX = posX;
        this.posY = posY;
        this.startTime = startTime;

        this.resources = new HashMap<>();
        this.resources.put(Resource.GOLD_TYPE, 0);
    }

    @Override
    public long getRemainingTime() {
        if (this.startTime == 0) {
            return 0;
        } else {
            long remainingTime = 0;
            // TODO: read config STO_1 and calculate remaining time for construct or update
            return remainingTime;
        }
    }
}
