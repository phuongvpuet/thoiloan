package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import common.config.obj.TownHallInfoByLevel;
import model.building.Area;
import model.building.BuildingType;
import model.common.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zyot on 7/2/2020.
 */
public class TownHallConfig {
    public Map<Integer, TownHallInfoByLevel> townHallInfoByLevelMap;

    public TownHallConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        townHallInfoByLevelMap = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        JsonNode townHallNode = node.get("TOW_1");
        Iterator<Map.Entry<String, JsonNode>> townHallConfigLevels = townHallNode.fields();
        while (townHallConfigLevels.hasNext()) {
            Map.Entry<String, JsonNode> entry = townHallConfigLevels.next();
            JsonNode jsonNode = entry.getValue();
            Iterator<Map.Entry<String, JsonNode>> attrEntries = jsonNode.fields();
            TownHallInfoByLevel townHallInfoByLevel = new TownHallInfoByLevel();
            townHallInfoByLevel.hitpoints = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.darkElixir = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.gold = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.buildTime = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.goldRopRate = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.elixirRopRate = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.darkElixirRopRate = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.maxDarkElixirRop = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.capacity = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.capacityGold = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.capacityElixir = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.capacityDarkElixir = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.width = attrEntries.next().getValue().asInt();
            townHallInfoByLevel.height = attrEntries.next().getValue().asInt();
            while (attrEntries.hasNext()) {
                Map.Entry<String, JsonNode> attrEntry = attrEntries.next();
                int amount = attrEntry.getValue().asInt();
                if (amount != 0) {
                    townHallInfoByLevel.conditionBuildings.put(attrEntry.getKey(), amount);
                }
            }
            townHallInfoByLevelMap.put(Integer.parseInt(entry.getKey()), townHallInfoByLevel);
        }
    }

    public Area getArea() {
        int width = townHallInfoByLevelMap.get(1).width;
        int height = townHallInfoByLevelMap.get(1).height;
        return new Area(width, height);
    }

    public int getMaxBuildingQuantity(int townHallLevel, BuildingType type) {
        TownHallInfoByLevel info = townHallInfoByLevelMap.get(townHallLevel);
        if (info != null) {
            if (info.conditionBuildings.containsKey(type.toString())) {
                return info.conditionBuildings.get(type.toString());
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Map<Short, Integer> getRequiredResources(int level) {
        TownHallInfoByLevel info = townHallInfoByLevelMap.get(level);
        int gold = info.gold;
        int darkElixir = info.darkElixir;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.GOLD_TYPE, gold);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
