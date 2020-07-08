package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.TroopBaseInfoByType;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zyot on 7/2/2020.
 */
public class TroopBaseConfig {
    public Map<String, TroopBaseInfoByType> troopBaseInfoByTypeMap;

    public TroopBaseConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        Type t = new TypeToken<HashMap<String, TroopBaseInfoByType>>() {
        }.getType();
        troopBaseInfoByTypeMap = gson.fromJson(node.toString(), t);
    }
}
