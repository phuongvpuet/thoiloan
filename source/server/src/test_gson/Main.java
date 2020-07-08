package test_gson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import model.UserMapData;
import model.building.BuilderHut;
import model.building.Building;
import model.building.ClanCastle;
import model.common.Obstacle;
import model.common.ObstacleType;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class Main {

    public static void testJackson() {

    }
    public static final String SEPARATOR = File.separator;
    public static void main(String[] args) throws Exception {
//        String configPath = new StringBuilder().append("conf").append(SEPARATOR).append("config_json").append(SEPARATOR).toString();

        Gson gson = new Gson();

//        MyDataModel model = new MyDataModel();
//        ChildB childB = new ChildB();
//        childB.childB = 1000000000;
//        model.data = childB;
//
//
//
//        model.dd = new ImmutablePair<>(childB.getClass().getName(), childB);
//
//        String parentJson = gson.toJson(model);
//        System.out.println(parentJson);

        /*MyDataModel model2 = gson.fromJson(parentJson, MyDataModel.class);

        System.out.println(model2.data.getClass());*/

        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode node = objectMapper.readTree(parentJson.getBytes(StandardCharsets.UTF_8));
//
//        JsonNode dd = node.get("dd");
//
//        String className = dd.get("left").asText();
//
//        String jsonRight = dd.get("right").toString();
//        System.out.println(jsonRight);
//
//        Class childBclass = Class.forName(className);
//        Object b = gson.fromJson(jsonRight, childBclass);


//        FileReader reader = new FileReader(configPath + "InitGame.json");
//        JsonNode node = objectMapper.readTree(reader);
//        Iterator<String> iterator = node.fieldNames();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
//        InitGameConfig initGameConfig = gson.fromJson(reader, InitGameConfig.class);

//        UserMapData userMapData = new UserMapData(1234);
//
//        Obstacle obs1 = new Obstacle(userMapData.curObstacleId++, ObstacleType.OBS_1, 1, 1);
//        Obstacle obs2 = new Obstacle(userMapData.curObstacleId++, ObstacleType.OBS_10, 2, 2);
//        userMapData.addObstacleToMap(obs1);
//        userMapData.addObstacleToMap(obs2);
//
//        BuilderHut builderHut = new BuilderHut(userMapData.curBuildingId++, 3, 3, System.currentTimeMillis());
//        ClanCastle clanCastle = new ClanCastle(userMapData.curBuildingId++, 4, 4);
//        userMapData.addBuildingToMap(builderHut);
//        userMapData.addBuildingToMap(clanCastle);
//
//
//        String jsonData = gson.toJson(userMapData);
//
//        JsonNode node = objectMapper.readTree(jsonData.getBytes(StandardCharsets.UTF_8));
//
//        UserMapData result = new UserMapData(node.get("userId").asInt());
//        result.curObstacleId = node.get("curObstacleId").asInt();
//        result.curBuildingId = node.get("curBuildingId").asInt();
//
//        JsonNode obstaclesNode = node.get("obstacles");
//        Iterator<Map.Entry<String, JsonNode>> obstaclesFieldsIterator = obstaclesNode.fields();
//        while (obstaclesFieldsIterator.hasNext()) {
//            Map.Entry<String, JsonNode> entry = obstaclesFieldsIterator.next();
//            result.obstacles.put(Integer.parseInt(entry.getKey()), gson.fromJson(entry.getValue().toString(), Obstacle.class));
//        }
//
//        JsonNode buildingsNode = node.get("buildings");
//        Iterator<Map.Entry<String, JsonNode>> buildingsFieldsIterator = buildingsNode.fields();
//
//        while (buildingsFieldsIterator.hasNext()) {
//            Map.Entry<String, JsonNode> entry = buildingsFieldsIterator.next();
//            int buildingId = Integer.parseInt(entry.getKey());
//            JsonNode buildingNode = entry.getValue();
//            String className = buildingNode.get("left").asText();
//            String jsonBuilding = buildingNode.get("right").toString();
//            Class clazz = Class.forName(className);
//            Building building = (Building) gson.fromJson(jsonBuilding, clazz);
//
//            result.buildings.put(buildingId, new ImmutablePair<>(clazz.getName(), building));
//        }

        System.out.println(ObstacleType.valueOf("OBS_"));
        System.out.println();
    }
}
