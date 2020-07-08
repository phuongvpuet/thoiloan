package model;

import common.GameConfig;
import model.building.Area;
import model.building.Building;
import model.building.BuildingFactory;
import model.building.BuildingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class UserLogicalMap {
    public static final short USER_MAP_WIDTH = 40;
    public static final short USER_MAP_HEIGHT = 40;

    private short[][] userMap;
    private Map<BuildingType, ArrayList<Integer>> buildingIdsByTypeMap;

    public UserLogicalMap(UserMapData userMapData) {
        buildingIdsByTypeMap = new HashMap<>();
        userMap = new short[USER_MAP_WIDTH][USER_MAP_HEIGHT];
        for (int y = 0; y < USER_MAP_HEIGHT; y++) {
            for (int x = 0; x < USER_MAP_WIDTH; x++) {
                userMap[x][y] = 0;
            }
        }

        userMapData.getBuildings().forEach((id, value) -> {
            Building building = value.getRight();
            BuildingType type = building.buildingType;
            ArrayList<Integer> buildingIdList;
            if (buildingIdsByTypeMap.containsKey(type)) {
                buildingIdList = buildingIdsByTypeMap.get(type);
            } else {
                buildingIdList = new ArrayList<>();
            }
            buildingIdList.add(id);
            buildingIdsByTypeMap.put(type, buildingIdList);

            int posX = building.getPosX();
            int posY = building.getPosY();
            Area area = BuildingFactory.getAreaByType(type);
            if (area != null) {
                for (int y = posY; y < posY + area.getHeight(); y++) {
                    for (int x = posX; x < posX + area.getWidth(); x++) {
                        userMap[x][y] = 1;
                    }
                }
            }
        });

        userMapData.getObstacles().forEach((id, obs) -> {
            int posX = obs.getPosX();
            int posY = obs.getPosY();
            Area area = GameConfig.obstacleConfig.getAreaByType(obs.obstacleType);
            if (area != null) {
                for (int y = posY; y < posY + area.getHeight(); y++) {
                    for (int x = posX; x < posX + area.getWidth(); x++) {
                        if (x < 40 && y < 40) {
                            userMap[x][y] = 1;
                        } else {
                            System.out.println("Obs: " + id);
                        }
                    }
                }
            }
        });
    }

    public boolean isAvailableArea(int posX, int posY, Area area) {
        if (area != null) {
            for (int y = posY; y < posY + area.getHeight(); y++) {
                for (int x = posX; x < posX + area.getWidth(); x++) {
                    if (userMap[x][y] == 1) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int getBuildingQuantityByType(BuildingType type) {
        if (this.buildingIdsByTypeMap.containsKey(type)) {
            return this.buildingIdsByTypeMap.get(type).size();
        } else {
            return 0;
        }
    }

    public int getTownHallId() {
        if (buildingIdsByTypeMap.containsKey(BuildingType.TOW_1)) {
            return buildingIdsByTypeMap.get(BuildingType.TOW_1).get(0);
        } else {
            return -1;
        }
    }

    public ArrayList<Integer> getBuildingsByType(BuildingType type) {
        if (buildingIdsByTypeMap.containsKey(type)) {
            return buildingIdsByTypeMap.get(type);
        } else {
            return null;
        }
    }
}
