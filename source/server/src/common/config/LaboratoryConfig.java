package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.LaboratoryInfoByLevel;
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
public class LaboratoryConfig {
    public Map<Integer, LaboratoryInfoByLevel> laboratoryInfoByLevelMap;

    public LaboratoryConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        JsonNode laboratoryNode = node.get("LAB_1");
        Type t = new TypeToken<HashMap<Integer, LaboratoryInfoByLevel>>() {
        }.getType();
        laboratoryInfoByLevelMap = gson.fromJson(laboratoryNode.toString(), t);
    }

    public Area getArea() {
        int width = laboratoryInfoByLevelMap.get(1).width;
        int height = laboratoryInfoByLevelMap.get(1).height;
        return new Area(width, height);
    }

    public Map<Short, Integer> getRequiredResources(int level) {
        LaboratoryInfoByLevel info = laboratoryInfoByLevelMap.get(level);
        int elixir = info.elixir;
        int darkElixir = info.darkElixir;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.ELIXIR_TYPE, elixir);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
