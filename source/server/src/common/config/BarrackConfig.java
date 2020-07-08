package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.BarrackLevelInfo;
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
public class BarrackConfig {
    public Map<String, Map<Integer, BarrackLevelInfo>> barrackInfos;

    public BarrackConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        barrackInfos = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        Iterator<Map.Entry<String, JsonNode>> barrackConfigFields = node.fields();
        while (barrackConfigFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = barrackConfigFields.next();
            Type t = new TypeToken<HashMap<Integer, BarrackLevelInfo>>() {
            }.getType();

            HashMap<Integer, BarrackLevelInfo> map = gson.fromJson(entry.getValue().toString(), t);

            barrackInfos.put(entry.getKey(), map);
        }
    }

    public Area getAreaByType(BuildingType type) {
        BarrackLevelInfo info = barrackInfos.get(type.toString()).get(1);
        if (info != null) {
            int width = info.width;
            int height = info.height;
            return new Area(width, height);
        } else {
            return null;
        }
    }

    public Map<Short, Integer> getRequiredResources(BuildingType type, int level) {
        BarrackLevelInfo info = barrackInfos.get(type.toString()).get(level);
        int elixir = info.elixir;
        int darkElixir = info.darkElixir;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.ELIXIR_TYPE, elixir);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
