package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.StorageInfoByLevel;
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
 * Created by Zyot on 7/2/2020.
 */
public class StorageConfig {
    public Map<String, Map<Integer, StorageInfoByLevel>> storageInfoMap;

    public StorageConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        storageInfoMap = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        Iterator<Map.Entry<String, JsonNode>> storageConfigFields = node.fields();
        while (storageConfigFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = storageConfigFields.next();
            Type t = new TypeToken<HashMap<Integer, StorageInfoByLevel>>() {
            }.getType();
            HashMap<Integer, StorageInfoByLevel> map = gson.fromJson(entry.getValue().toString(), t);
            storageInfoMap.put(entry.getKey(), map);
        }
    }

    public Area getAreaByType(BuildingType type) {
        StorageInfoByLevel info = storageInfoMap.get(type.toString()).get(1);
        if (info != null) {
            int width = info.width;
            int height = info.height;
            return new Area(width, height);
        } else {
            return null;
        }
    }

    public Map<Short, Integer> getRequiredResources(BuildingType type, int level) {
        StorageInfoByLevel info = storageInfoMap.get(type.toString()).get(level);
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
