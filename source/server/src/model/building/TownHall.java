package model.building;

import model.common.Resource;

import java.util.HashMap;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class TownHall extends Warehouse {
    public TownHall(int id, int posX, int posY) {
        this.buildingId = id;
        this.buildingType = BuildingType.TOW_1;
        this.level = 1;
        this.posX = posX;
        this.posY = posY;
        this.startTime = 0;

        this.resources = new HashMap<>();
        this.resources.put(Resource.GOLD_TYPE, 0);
        this.resources.put(Resource.ELIXIR_TYPE, 0);
        this.resources.put(Resource.DARK_ELIXIR_TYPE, 0);
    }

    @Override
    public long getRemainingTime() {
        if (this.startTime == 0) {
            return 0;
        } else {
            long remainingTime = 0;
            // TODO: read config TOW_1 and calculate remaining time for updating
            return remainingTime;
        }
    }
}
