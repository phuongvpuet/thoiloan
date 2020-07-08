package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.ResourceInfoByLevel;
import model.building.Area;
import model.building.BuildingType;
import model.common.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class ResourceConfig {
    public Map<String, Map<Integer, ResourceInfoByLevel>> resourceInfoMap;

    public ResourceConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        resourceInfoMap = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        Iterator<Map.Entry<String, JsonNode>> resourceConfigFields = node.fields();
        while (resourceConfigFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = resourceConfigFields.next();
            Type t = new TypeToken<HashMap<Integer, ResourceInfoByLevel>>() {
            }.getType();

            HashMap<Integer, ResourceInfoByLevel> map = gson.fromJson(entry.getValue().toString(), t);

            resourceInfoMap.put(entry.getKey(), map);
        }
    }

    public Area getAreaByType(BuildingType type) {
        ResourceInfoByLevel info = resourceInfoMap.get(type.toString()).get(1);
        if (info != null) {
            int width = info.width;
            int height = info.height;
            return new Area(width, height);
        } else {
            return null;
        }
    }

    public Map<Short, Integer> getRequiredResources(BuildingType type, int level) {
        ResourceInfoByLevel info = resourceInfoMap.get(type.toString()).get(level);
        int elixir = info.elixir;
        int darkElixir = info.darkElixir;
        int gold = info.gold;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.GOLD_TYPE, gold);
        requiredResources.put(Resource.ELIXIR_TYPE, elixir);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
