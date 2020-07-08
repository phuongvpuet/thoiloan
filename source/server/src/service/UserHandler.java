package service;

import bitzero.server.BitZeroServer;
import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;

import cmd.CmdDefine;

import cmd.send.userinfo.ResponseRequestUserInfo;

import cmd.send.userinfo.ResponseRequestUserMap;
import cmd.send.userinfo.ResponseRequestUserTroop;
import event.eventType.DemoEventParam;
import event.eventType.DemoEventType;
import extension.FresherExtension;

import model.UserMapData;
import model.UserProfile;
import model.UserTroopData;
import org.apache.commons.lang.exception.ExceptionUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.server.ServerConstant;

import java.util.ArrayList;
import java.util.List;

public class UserHandler extends BaseClientRequestHandler {
    public static short USER_MULTI_IDS = 1000;
    private final Logger logger = LoggerFactory.getLogger("UserHandler");

    public UserHandler() {
        super();
    }

    public void init() {
        getExtension().addEventListener(BZEventType.USER_DISCONNECT, this);
        getExtension().addEventListener(BZEventType.USER_RECONNECTION_SUCCESS, this);

        /**
         *  register new event, so the core will dispatch event type to this class
         */
        getExtension().addEventListener(DemoEventType.CHANGE_NAME, this);
    }

    private FresherExtension getExtension() {
        return (FresherExtension) getParentExtension();
    }

    public void handleServerEvent(IBZEvent ibzevent) {

        if (ibzevent.getType() == BZEventType.USER_DISCONNECT)
            this.userDisconnect((User) ibzevent.getParameter(BZEventParam.USER));
        else if (ibzevent.getType() == DemoEventType.CHANGE_NAME)
            this.userChangeName((User) ibzevent.getParameter(DemoEventParam.USER), (String) ibzevent.getParameter(DemoEventParam.NAME));
    }

    public void handleClientRequest(User user, DataCmd dataCmd) {
        try {
            switch (dataCmd.getId()) {
                case CmdDefine.GET_USER_INFO: {
                    logger.info("Get user info ");
                    //RequestUserInfo reqInfo = new RequestUserInfo(dataCmd);
                    getUserInfo(user);
//                    getUserMap(user);
//                    getUserTroop(user);
                    break;
                }
                case CmdDefine.GET_USER_MAP: {
                    logger.info("Get user map ");
                    getUserMap(user);
                    break;

                }
                case CmdDefine.GET_USER_TROOP: {
                    logger.info("Get user troop ");
                    getUserTroop(user);
                    break;
                }
            }

        } catch (Exception e) {
            logger.warn("USERHANDLER EXCEPTION " + e.getMessage());
            logger.warn(ExceptionUtils.getStackTrace(e));
        }

    }

    private void getUserTroop(User user) {
        UserTroopData userTroopData = (UserTroopData) user.getProperty(ServerConstant.USER_TROOP_DATA);
        if (userTroopData == null) {
            userTroopData = new UserTroopData(user.getId());
            userTroopData.save();
            logger.info("User id {} is null, create", user.getId());
        }
        send(new ResponseRequestUserTroop(userTroopData), user);
        logger.info("Send user troop ");
    }

    private void getUserInfo(User user) {
        try {
            UserProfile userInfo = (UserProfile) user.getProperty(ServerConstant.USER_INFO);
            if (userInfo == null) {
                userInfo = new UserProfile(user.getId(), user.getName());
                userInfo.saveModel(user.getId());
            }
            ResponseRequestUserInfo responseRequestUserInfo = new ResponseRequestUserInfo(userInfo);
//            responseRequestUserInfo.testUserInfoResponse();
            send(responseRequestUserInfo, user);
            logger.info("Send user info ");
        } catch (Exception e) {
            logger.info("EXCEPTION IN GET USER INFO");
        }
    }

    private void getUserMap(User user) {
        UserMapData userMapData = (UserMapData) user.getProperty(ServerConstant.USER_MAP_DATA);
        if (userMapData == null) {
            // TODO: Handle user map NULL
        }
        ResponseRequestUserMap responseRequestUserMap = new ResponseRequestUserMap(userMapData);
        responseRequestUserMap.testUserMapResponse();
        send(responseRequestUserMap, user);
        logger.info("Send user map ");
    }

    private void userDisconnect(User user) {
        // log user disconnect
    }

    private void userChangeName(User user, String name) {
        List<User> allUser = BitZeroServer.getInstance().getUserManager().getAllUsers();
        for (User aUser : allUser) {
            // notify user's change
        }
    }

}
