package common.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.config.obj.ClanCastleInfoByLevel;
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
public class ClanCastleConfig {
    public Map<Integer, ClanCastleInfoByLevel> clanCastleInfoByLevelMap;

    public ClanCastleConfig(FileReader reader, ObjectMapper objectMapper, Gson gson) throws IOException {
        JsonNode node = objectMapper.readTree(reader);
        JsonNode clanCastleNode = node.get("CLC_1");
        Type t = new TypeToken<HashMap<Integer, ClanCastleInfoByLevel>>() {
        }.getType();
        clanCastleInfoByLevelMap = gson.<HashMap<Integer, ClanCastleInfoByLevel>>fromJson(clanCastleNode.toString(), t);
    }

    public Area getArea() {
        int width = clanCastleInfoByLevelMap.get(1).width;
        int height = clanCastleInfoByLevelMap.get(1).height;
        return new Area(width, height);
    }

    public Map<Short, Integer> getRequiredResources(int level) {
        ClanCastleInfoByLevel info = clanCastleInfoByLevelMap.get(level);
        int gold = info.gold;
        int darkElixir = info.darkElixir;
        Map<Short, Integer> requiredResources = new HashMap<>();
        requiredResources.put(Resource.GOLD_TYPE, gold);
        requiredResources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        return requiredResources;
    }
}
