package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.DefenceInfoByLevel;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class DefenceConfig {
    public Map<String, Map<Integer, DefenceInfoByLevel>> defenceInfoByLevelMap;

    public DefenceConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        defenceInfoByLevelMap = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        Iterator<Map.Entry<String, JsonNode>> defenseConfigFields = node.fields();
        while (defenseConfigFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = defenseConfigFields.next();
            Type t = new TypeToken<HashMap<Integer, DefenceInfoByLevel>>() {
            }.getType();

            HashMap<Integer, DefenceInfoByLevel> map = gson.fromJson(entry.getValue().toString(), t);

            defenceInfoByLevelMap.put(entry.getKey(), map);
        }
    }
}
