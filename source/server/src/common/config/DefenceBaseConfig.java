package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.DefenceBaseInfoByType;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class DefenceBaseConfig {
    public Map<String, DefenceBaseInfoByType> defenceBaseInfoByTypeMap;

    public DefenceBaseConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        Type t = new TypeToken<HashMap<String, DefenceBaseInfoByType>>() {
        }.getType();
        defenceBaseInfoByTypeMap = gson.fromJson(node.toString(), t);
    }
}
