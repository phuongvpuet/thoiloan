package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import common.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Fresher_LOCAL on 6/26/2020.
 */
public class GameConfig {
    private static final Logger logger = LoggerFactory.getLogger("GameConfig");
    public static final String SEPARATOR = File.separator;
    public static final String configPath = new StringBuilder().append("conf").append(SEPARATOR).append("config_json").append(SEPARATOR).toString();

    public static InitGameConfig initGameConfig;
    public static ArmyCampConfig armyCampConfig;
    public static BarrackConfig barrackConfig;
    public static BuilderHutConfig builderHutConfig;
    public static ClanCastleConfig clanCastleConfig;
    public static DefenceConfig defenseConfig;
    public static DefenceBaseConfig defenceBaseConfig;
    public static LaboratoryConfig laboratoryConfig;
    public static ObstacleConfig obstacleConfig;
    public static ResourceConfig resourceConfig;
    public static StorageConfig storageConfig;
    public static TownHallConfig townHallConfig;
    public static TroopConfig troopConfig;
    public static TroopBaseConfig troopBaseConfig;
    public static WallConfig wallConfig;


//    public static TypeSafeMap mapConfigForAll = new TypeSafeMap();
//
//
//    public static Key<Bar1Config> keyBarConfig = new Key<Bar1Config>() {
//    };



    public static void init() {
        Gson gson = new Gson();
        ObjectMapper objectMapper = new ObjectMapper();
        FileReader reader;
        try {
            reader = new FileReader(configPath + "InitGame.json");
            initGameConfig = gson.fromJson(reader, InitGameConfig.class);

            reader = new FileReader(configPath + "ArmyCamp.json");
            armyCampConfig = new ArmyCampConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Barrack.json");
            barrackConfig = new BarrackConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "BuilderHut.json");
            builderHutConfig = new BuilderHutConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "ClanCastle.json");
            clanCastleConfig = new ClanCastleConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Defence.json");
            defenseConfig = new DefenceConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "DefenceBase.json");
            defenceBaseConfig = new DefenceBaseConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Laboratory.json");
            laboratoryConfig = new LaboratoryConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Obstacle.json");
            obstacleConfig = new ObstacleConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Resource.json");
            resourceConfig = new ResourceConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Storage.json");
            storageConfig = new StorageConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "TownHall.json");
            townHallConfig = new TownHallConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Troop.json");
            troopConfig = new TroopConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "TroopBase.json");
            troopBaseConfig = new TroopBaseConfig(reader, objectMapper, gson);

            reader = new FileReader(configPath + "Wall.json");
            wallConfig = new WallConfig(reader, objectMapper, gson);
//            JsonNode b1 = node.get("BAR_1");
//
//
//            Type t = new TypeToken<HashMap<Integer, BarrackLevelInfo>>() {
//            }.getType();
//
//            HashMap<Integer, BarrackLevelInfo> m = gson.fromJson(b1.toString(), t);
//
//            Bar1Config b = new Bar1Config(m);
//            mapConfigForAll.put(keyBarConfig, b);

//            Bar1Config tt = mapConfigForAll.get(keyBarConfig);

            System.out.println();
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }
}
