package service;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.receive.map.RequestBuildNewBuilding;
import cmd.send.map.*;
import common.GameConfig;
import model.UserLogicalMap;
import model.UserMapData;
import model.UserProfile;
import model.building.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.server.ServerConstant;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class UserMapHandler extends BaseClientRequestHandler {
    public static short USER_MAP_MULTI_IDS = 2000;
    private final Logger logger = LoggerFactory.getLogger("UserMapHandler");

    public UserMapHandler() {
        super();
    }

    @Override
    public void handleClientRequest(User user, DataCmd dataCmd) {
        try {
            switch (dataCmd.getId()) {
                case CmdDefine.BUILD_NEW_BUILDING: {
                    RequestBuildNewBuilding requestBuildNewBuilding = new RequestBuildNewBuilding(dataCmd);
                    processBuildNewBuilding(user, requestBuildNewBuilding);
                    break;
                }
                case CmdDefine.UPGRADE_BUILDING: {
                    break;
                }
            }
        } catch (Exception e) {
            logger.warn("USER MAP HANDLER EXCEPTION " + e.getMessage());
            logger.warn(ExceptionUtils.getStackTrace(e));
        }
    }

    private void processBuildNewBuilding(User user, RequestBuildNewBuilding requestBuildNewBuilding) {
        logger.info("processBuildNewBuilding: ");
        UserProfile userProfile = (UserProfile) user.getProperty(ServerConstant.USER_INFO);
        UserMapData userMapData = (UserMapData) user.getProperty(ServerConstant.USER_MAP_DATA);
        UserLogicalMap userLogicalMap = (UserLogicalMap) user.getProperty(ServerConstant.USER_LOGICAL_MAP);

        BuildingType type = BuildingType.valueOf(requestBuildNewBuilding.getBuildingType());
        int newPosX = requestBuildNewBuilding.getPosX();
        int newPosY = requestBuildNewBuilding.getPosY();

        // check building quality
        int userTownHallId = userLogicalMap.getTownHallId();
        if (userTownHallId != -1) {
            int userTownHallLevel = userMapData.getBuildingLevelById(userTownHallId);
            int maxBuildingQuantity = GameConfig.townHallConfig.getMaxBuildingQuantity(userTownHallLevel, type);
            int buildingQuantity = userLogicalMap.getBuildingQuantityByType(type);
            if (buildingQuantity >= maxBuildingQuantity) {
                // TODO: handle building quantity reaches max value
                send(new ResponseNoAvailableBuilding(CmdDefine.BUILD_NEW_BUILDING), user);
                return;
            }
        }

        // check available position
        if (!userLogicalMap.isAvailableArea(newPosX, newPosY, BuildingFactory.getAreaByType(type))) {
            // TODO: handle position is not available
            send(new ResponsePositionInvalid(CmdDefine.BUILD_NEW_BUILDING), user);
            return;
        }

        // check enough resources
        Map<Short, Integer> requiredResources = BuildingFactory.getRequiredResources(type, 1);
        if (requiredResources != null) {
            requiredResources.forEach((resType, amount) -> {
                int userResourceAmount = userProfile.getResourceByType(resType);
                if (amount > userResourceAmount) {
                    // TODO: handle lack of resource
                    send(new ResponseLackOfResources(CmdDefine.BUILD_NEW_BUILDING), user);
                    return;
                }
            });
        }

        // check available builder
        int availableBuilderHutId = -1;
        ArrayList<Integer> userBuilderHutIds = userLogicalMap.getBuildingsByType(BuildingType.BDH_1);
        for (int i = 0; i < userBuilderHutIds.size(); i++) {
            BuilderHut builderHut = (BuilderHut) userMapData.getBuildingById(userBuilderHutIds.get(i));
            if (builderHut.getDesBuildingId() == -1) {
                availableBuilderHutId = builderHut.getBuildingId();
                break;
            }
        }

        if (availableBuilderHutId != -1) {
            // TODO: assign destination building of this builder hut
        } else {
            // TODO: handle no available builder
            send(new ResponseNoAvailableBuilder(CmdDefine.BUILD_NEW_BUILDING), user);
            return;
        }

        // pass all the conditions
        // build a new building
        Building building = BuildingFactory.createNewBuilding(userMapData.getCurBuildingId(), type, newPosX, newPosY, System.currentTimeMillis());
        userMapData.addBuildingToMap(building);
        userMapData.save();
        // TODO: update logical map
        ResponseConstructSuccess responseConstructSuccess = new ResponseConstructSuccess(building);
        send(responseConstructSuccess, user);
    }
}
