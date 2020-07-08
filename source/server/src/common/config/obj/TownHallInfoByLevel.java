package common.config.obj;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zyot on 7/2/2020.
 */
public class TownHallInfoByLevel {
    public int hitpoints;
    public int darkElixir;
    public int gold;
    public int buildTime;
    public int goldRopRate;
    public int elixirRopRate;
    public int darkElixirRopRate;
    public int maxDarkElixirRop;
    public int capacity;
    public int capacityGold;
    public int capacityElixir;
    public int capacityDarkElixir;
    public int width;
    public int height;
    public Map<String, Integer> conditionBuildings;

    public TownHallInfoByLevel() {
        conditionBuildings = new HashMap<>();
    }
}
