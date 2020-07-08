package model.building;

import common.GameConfig;

import java.util.Map;

/**
 * Created by Fresher_LOCAL on 6/26/2020.
 */
public class BuildingFactory {
    public static Building createNewBuilding(int id, BuildingType type, int posX, int posY, long startTime) {
        switch (type) {
            case AMC_1: {
                return new ArmyCamp(id, posX, posY, startTime);
            }
            case BDH_1: {
                return new BuilderHut(id, posX, posY);
            }
            case CLC_1: {
                return new ClanCastle(id, posX, posY);
            }
            case RES_1: {
                return new GoldMine(id, posX, posY, startTime);
            }
            case RES_2: {
                return new ElixirPit(id, posX, posY, startTime);
            }
            case RES_3: {
                return new DarkElixirPit(id, posX, posY, startTime);
            }
            case STO_1: {
                return new GoldWarehouse(id, posX, posY, startTime);
            }
            case STO_2: {
                return new ElixirWarehouse(id, posX, posY, startTime);
            }
            case STO_3: {
                return new DarkElixirWarehouse(id, posX, posY, startTime);
            }
            case TOW_1: {
                return new TownHall(id, posX, posY);
            }
            default:
                return null;
        }
    }

    public static Area getAreaByType(BuildingType type) {
        switch (type) {
            case AMC_1: {
                return GameConfig.armyCampConfig.getArea();
            }
            case BDH_1: {
                return GameConfig.builderHutConfig.getArea();
            }
            case BAR_1:
            case BAR_2: {
                return GameConfig.barrackConfig.getAreaByType(type);
            }
            case RES_1:
            case RES_2:
            case RES_3: {
                return GameConfig.resourceConfig.getAreaByType(type);
            }
            case STO_1:
            case STO_2:
            case STO_3: {
                return GameConfig.storageConfig.getAreaByType(type);
            }
            case CLC_1: {
                return GameConfig.clanCastleConfig.getArea();
            }
            case TOW_1: {
                return GameConfig.townHallConfig.getArea();
            }
            case LAB_1: {
                return GameConfig.laboratoryConfig.getArea();
            }
            case WAL_1: {
                return GameConfig.wallConfig.getArea();
            }
            // TODO: handle for Defence type
            default:
                return null;
        }
    }

    public static Map<Short, Integer> getRequiredResources(BuildingType type, int level) {
        switch (type) {
            case AMC_1: {
                return GameConfig.armyCampConfig.getRequiredResources(level);
            }
            case BDH_1: {
                // TODO: Handle case BuilderHut
                return null;
            }
            case BAR_1:
            case BAR_2: {
                return GameConfig.barrackConfig.getRequiredResources(type, level);
            }
            case RES_1:
            case RES_2:
            case RES_3: {
                return GameConfig.resourceConfig.getRequiredResources(type, level);
            }
            case STO_1:
            case STO_2:
            case STO_3: {
                return GameConfig.storageConfig.getRequiredResources(type, level);
            }
            case CLC_1: {
                return GameConfig.clanCastleConfig.getRequiredResources(level);
            }
            case TOW_1: {
                return GameConfig.townHallConfig.getRequiredResources(level);
            }
            case LAB_1: {
                return GameConfig.laboratoryConfig.getRequiredResources(level);
            }
            case WAL_1: {
                return GameConfig.wallConfig.getRequiredResources(level);
            }
            // TODO: handle for Defence type
            default:
                return null;
        }
    }
}
