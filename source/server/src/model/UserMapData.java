package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.building.BuilderHut;
import model.building.Building;
import model.common.Obstacle;
import org.apache.commons.lang3.tuple.ImmutablePair;
import util.database.DataHandler;
import util.database.DataModel;
import util.database.UserDataModel;
import util.server.ServerUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class UserMapData extends UserDataModel {
    static final ObjectMapper objectMapper = new ObjectMapper();
    private int curObstacleId;
    private int curBuildingId;
    private Map<Integer, Obstacle> obstacles;
    private Map<Integer, ImmutablePair<String, Building>> buildings;

    public UserMapData(int userId) {
        super(userId);
        this.obstacles = new HashMap<>();
        this.buildings = new HashMap<>();
        curBuildingId = 0;
        curObstacleId = 0;
    }

    public void addObstacleToMap(Obstacle obstacle) {
        this.obstacles.put(obstacle.obstacleId, obstacle);
    }

    public void addBuildingToMap(Building building) {
        this.buildings.put(building.buildingId, new ImmutablePair<>(building.getClass().getName(), building));
    }

    public static UserMapData getUserMapData(int userId) throws Exception {
        String key = ServerUtil.getModelKeyName(UserMapData.class.getSimpleName(), userId);
        String jsonData = (String) DataHandler.get(key);
        return convertUserMapFromJson(jsonData);
    }

    private static UserMapData convertUserMapFromJson(String jsonData) throws ClassNotFoundException, IOException {
        JsonNode node = objectMapper.readTree(jsonData.getBytes(StandardCharsets.UTF_8));

        UserMapData result = new UserMapData(node.get("userId").asInt());
        result.curObstacleId = node.get("curObstacleId").asInt();
        result.curBuildingId = node.get("curBuildingId").asInt();

        JsonNode obstaclesNode = node.get("obstacles");
        Iterator<Map.Entry<String, JsonNode>> obstaclesFieldsIterator = obstaclesNode.fields();
        while (obstaclesFieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = obstaclesFieldsIterator.next();
            result.obstacles.put(Integer.parseInt(entry.getKey()), gson.fromJson(entry.getValue().toString(), Obstacle.class));
        }

        JsonNode buildingsNode = node.get("buildings");
        Iterator<Map.Entry<String, JsonNode>> buildingsFieldsIterator = buildingsNode.fields();

        while (buildingsFieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = buildingsFieldsIterator.next();
            int buildingId = Integer.parseInt(entry.getKey());
            JsonNode buildingNode = entry.getValue();
            String className = buildingNode.get("left").asText();
            String jsonBuilding = buildingNode.get("right").toString();
            Class clazz = Class.forName(className);
            Building building = (Building) gson.fromJson(jsonBuilding, clazz);

            result.buildings.put(buildingId, new ImmutablePair<>(clazz.getName(), building));
        }

        return result;
    }

    public int getCurObstacleId() {
        return curObstacleId++;
    }

    public int getCurBuildingId() {
        return curBuildingId++;
    }

    public Map<Integer, Obstacle> getObstacles() {
        return obstacles;
    }

    public Map<Integer, ImmutablePair<String, Building>> getBuildings() {
        return buildings;
    }

    public int getBuildingLevelById(int id) {
        if (buildings.containsKey(id)) {
            return buildings.get(id).getRight().getLevel();
        } else return -1;
    }

    public Building getBuildingById(int id) {
        return buildings.get(id).getRight();
    }
}
