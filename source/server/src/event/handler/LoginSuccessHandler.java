package event.handler;

import bitzero.server.core.BZEvent;
import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseServerEventHandler;
import bitzero.server.extensions.ExtensionLogLevel;

import bitzero.util.ExtensionUtility;

import java.util.HashMap;
import java.util.Map;

import common.GameConfig;
import common.config.InitGameConfig;
import event.eventType.DemoEventParam;
import event.eventType.DemoEventType;
import model.*;
import model.building.Building;
import model.building.BuildingFactory;
import model.building.BuildingType;
import model.common.Obstacle;
import model.common.ObstacleType;
import util.server.ServerConstant;

public class LoginSuccessHandler extends BaseServerEventHandler {
    public LoginSuccessHandler() {
        super();
    }

    public void handleServerEvent(IBZEvent iBZEvent) {
        this.onLoginSuccess((User) iBZEvent.getParameter(BZEventParam.USER));
    }

    /**
     * @param user description: after login successful to server, core framework will dispatch this event
     */
    private void onLoginSuccess(User user) {
        trace(ExtensionLogLevel.DEBUG, "On Login Success ", user.getName());
        UserProfile pInfo = null;
        try {
            pInfo = UserProfile.getModel(user.getId(), UserProfile.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pInfo == null) {
            try {
                initNewUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            /**
             * cache playerinfo in RAM
             */
            user.setProperty(ServerConstant.USER_INFO, pInfo);
            try {
                UserMapData userMapData = UserMapData.getUserMapData(user.getId());
                user.setProperty(ServerConstant.USER_MAP_DATA, userMapData);
                UserTroopData userTroopData = UserTroopData.getModel(user.getId(), UserTroopData.class);
                user.setProperty(ServerConstant.USER_TROOP_DATA, userTroopData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        initLogicalMap(user);

        /**
         * send login success to client
         * after receive this message, client begin to send game logic packet to server
         */
        ExtensionUtility.instance().sendLoginOK(user);

        /**
         * dispatch event here
         */
        Map evtParams = new HashMap();
        evtParams.put(DemoEventParam.USER, user);
        evtParams.put(DemoEventParam.NAME, user.getName());
        ExtensionUtility.dispatchEvent(new BZEvent(DemoEventType.LOGIN_SUCCESS, evtParams));

    }

    private void initLogicalMap(User user) {
        UserLogicalMap userLogicalMap = new UserLogicalMap((UserMapData) user.getProperty(ServerConstant.USER_MAP_DATA));
        user.setProperty(ServerConstant.USER_LOGICAL_MAP, userLogicalMap);
    }

    private void initNewUser(User user) throws Exception {
        InitGameConfig initGameConfig = GameConfig.initGameConfig;
        // init user info
        UserProfile uInfo = new UserProfile(user.getId(),
                user.getName(),
                initGameConfig.player.gold,
                initGameConfig.player.elixir,
                initGameConfig.player.darkElixir,
                initGameConfig.player.builderNumber,
                initGameConfig.player.coin);
        uInfo.save();

        // init user map
        UserMapData userMapData = new UserMapData(user.getId());
        // init obstacles
        initGameConfig.obs.forEach((key, value) -> {
            Obstacle obstacle = new Obstacle(userMapData.getCurObstacleId(), ObstacleType.valueOf(value.type), value.posX, value.posY);
            userMapData.addObstacleToMap(obstacle);
        });
        // init buildings
        initGameConfig.map.forEach((key, value) -> {
            Building building = BuildingFactory.createNewBuilding(userMapData.getCurBuildingId(),
                    BuildingType.valueOf(key),
                    value.posX,
                    value.posY,
                    0);
            userMapData.addBuildingToMap(building);
        });

        userMapData.save();
        // init troops
        UserTroopData userTroopData = new UserTroopData(user.getId());
        userTroopData.save();

        /**
         * cache player info in RAM
         */
        user.setProperty(ServerConstant.USER_INFO, uInfo);
        user.setProperty(ServerConstant.USER_MAP_DATA, userMapData);
        user.setProperty(ServerConstant.USER_TROOP_DATA, userTroopData);
    }

}
