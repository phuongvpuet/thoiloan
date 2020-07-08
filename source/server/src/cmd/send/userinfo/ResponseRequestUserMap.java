package cmd.send.userinfo;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.obj.userinfo.BuildingResponseData;
import cmd.obj.userinfo.ObstacleResponseData;
import com.google.gson.Gson;
import model.UserMapData;
import model.building.Building;

import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class ResponseRequestUserMap extends BaseMsg {
    public UserMapData mapData;
//    public ArrayList<ObstacleResponseData> obstacles;

    public ResponseRequestUserMap(UserMapData userMapData) {
        super(CmdDefine.GET_USER_MAP);
        mapData = userMapData;
    }

    public void testUserMapResponse() {
        ArrayList<BuildingResponseData> buildings = new ArrayList<>();
        ArrayList<ObstacleResponseData> obstacles = new ArrayList<>();
        mapData.getBuildings().forEach((id, pair) -> {
            BuildingResponseData buildingResponseData = new BuildingResponseData();
            buildingResponseData.setBuildingId(id);
            Building building = pair.getRight();
            buildingResponseData.setBuildingType(building.getBuildingType().toString());
            buildingResponseData.setPosX(building.getPosX());
            buildingResponseData.setPosY(building.getPosY());
            buildingResponseData.setLevel(building.getLevel());
            buildingResponseData.setTimeRemaining(building.getRemainingTime());
            buildings.add(buildingResponseData);
        });
        mapData.getObstacles().forEach((id, obs) -> {
            ObstacleResponseData obstacleResponseData = new ObstacleResponseData();
            obstacleResponseData.setObstacleId(id);
            obstacleResponseData.setObstacleType(obs.getObstacleType().toString());
            obstacleResponseData.setPosX(obs.getPosX());
            obstacleResponseData.setPosY(obs.getPosY());
            obstacleResponseData.setTimeRemaining(obs.getRemainingTime());
            obstacles.add(obstacleResponseData);
        });

        Gson gson = new Gson();
        String buildingsJson = gson.toJson(buildings);
        String obstaclesJson = gson.toJson(obstacles);
        System.out.println("testUserMapResponse");
        System.out.println("Building:\n" + buildingsJson);
        System.out.println("Obstacle:\n" + obstaclesJson);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        // create buildings data
        bf.putInt(mapData.getBuildings().size());
        mapData.getBuildings().forEach((id, pair) -> {
            bf.putInt(id);
            Building building = pair.getRight();
            putStr(bf, building.getBuildingType().toString());
            bf.putInt(building.getPosX());
            bf.putInt(building.getPosY());
            bf.putInt(building.getLevel());
            bf.putLong(building.getRemainingTime());
        });
        // create obstacles data
        bf.putInt(mapData.getObstacles().size());
        mapData.getObstacles().forEach((id, obs) -> {
            bf.putInt(id);
            putStr(bf, obs.getObstacleType().toString());
            bf.putInt(obs.getPosX());
            bf.putInt(obs.getPosY());
            bf.putLong(obs.getRemainingTime());
        });
//        bf.putInt(obstacles.size());
//        obstacles.forEach(obs -> {
//            bf.putInt(obs.getObstacleId());
//            putStr(bf, obs.getObstacleType());
//            bf.putInt(obs.getPosX());
//            bf.putInt(obs.getPosY());
//            bf.putLong(obs.getTimeRemaining());
//        });

        return packBuffer(bf);
    }
}
