package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.BarrackLevelInfo;
import common.config.obj.TroopInfoByLevel;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zyot on 7/2/2020.
 */
public class TroopConfig {
    public Map<String, Map<Integer, TroopInfoByLevel>> troopInfoMap;

    public TroopConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        troopInfoMap = new HashMap<>();

        JsonNode node = objectMapper.readTree(reader);
        Iterator<Map.Entry<String, JsonNode>> troopConfigFields = node.fields();
        while (troopConfigFields.hasNext()) {
            Map.Entry<String, JsonNode> entry = troopConfigFields.next();
            Type t = new TypeToken<HashMap<Integer, TroopInfoByLevel>>() {
            }.getType();

            HashMap<Integer, TroopInfoByLevel> map = gson.fromJson(entry.getValue().toString(), t);

            troopInfoMap.put(entry.getKey(), map);
        }
    }
}
