package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.WallInfoByLevel;
import model.building.Area;
import model.common.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zyot on 7/2/2020.
 */
public class WallConfig {
    public Map<Integer, WallInfoByLevel> wallInfoMap;

    public WallConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        JsonNode builderHutNode = node.get("WAL_1");
        Type t = new TypeToken<HashMap<Integer, WallInfoByLevel>>() {
        }.getType();
        wallInfoMap = gson.fromJson(builderHutNode.toString(), t);
    }

    public Area getArea() {
        int width = wallInfoMap.get(1).width;
        int height = wallInfoMap.get(1).height;
        return new Area(width, height);
    }

    public Map<Short, Integer> getRequiredResources(int level) {
        WallInfoByLevel info = wallInfoMap.get(level);
        int gold = info.gold;
        int darkElixir = info.darkElixir;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.GOLD_TYPE, gold);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
