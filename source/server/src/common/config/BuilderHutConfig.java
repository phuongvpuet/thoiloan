package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.BuilderHutInfoByQuantity;
import model.building.Area;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class BuilderHutConfig {
    public Map<Integer, BuilderHutInfoByQuantity> builderHutInfoByQuantityMap;

    public BuilderHutConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        JsonNode builderHutNode = node.get("BDH_1");
        Type t = new TypeToken<HashMap<Integer, BuilderHutInfoByQuantity>>() {
        }.getType();
        builderHutInfoByQuantityMap = gson.fromJson(builderHutNode.toString(), t);
    }

    public Area getArea() {
        int width = builderHutInfoByQuantityMap.get(1).width;
        int height = builderHutInfoByQuantityMap.get(1).height;
        return new Area(width, height);
    }
}
