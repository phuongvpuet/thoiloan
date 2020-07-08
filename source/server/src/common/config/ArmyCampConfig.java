package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.ArmyCampInfoByLevel;
import model.building.Area;
import model.common.Resource;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class ArmyCampConfig {
    public Map<Integer, ArmyCampInfoByLevel> armyCampInfoByLevelMap;

    public ArmyCampConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        JsonNode armyCampNode = node.get("AMC_1");
        Type t = new TypeToken<HashMap<Integer, ArmyCampInfoByLevel>>() {
        }.getType();
        armyCampInfoByLevelMap = gson.<HashMap<Integer, ArmyCampInfoByLevel>>fromJson(armyCampNode.toString(), t);
    }

    public Area getArea() {
        int width = armyCampInfoByLevelMap.get(1).width;
        int height = armyCampInfoByLevelMap.get(1).height;
        return new Area(width, height);
    }

    public Map<Short, Integer> getRequiredResources(int level) {
        ArmyCampInfoByLevel info = armyCampInfoByLevelMap.get(level);
        int elixir = info.elixir;
        int darkElixir = info.darkElixir;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.ELIXIR_TYPE, elixir);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
