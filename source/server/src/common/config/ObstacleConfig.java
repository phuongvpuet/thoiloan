package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import common.config.obj.ObstacleInfoByType;
import model.building.Area;
import model.common.ObstacleType;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class ObstacleConfig {
    public Map<String, ObstacleInfoByType> obstacleInfoByTypeMap;

    public ObstacleConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        obstacleInfoByTypeMap = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        Iterator<Map.Entry<String, JsonNode>> obstacleConfigFields = node.fields();
        while (obstacleConfigFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = obstacleConfigFields.next();
            ObstacleInfoByType obstacleInfoByType = gson.fromJson(entry.getValue().get("1").toString(), ObstacleInfoByType.class);
            obstacleInfoByTypeMap.put(entry.getKey(), obstacleInfoByType);
        }
    }

    public Area getAreaByType(ObstacleType type) {
        ObstacleInfoByType info = obstacleInfoByTypeMap.get(type.toString());
        if (info != null) {
            int width = info.width;
            int height = info.height;
            return new Area(width, height);
        } else {
            return null;
        }
    }
}
