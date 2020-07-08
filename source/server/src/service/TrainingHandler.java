package service;

import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.obj.demo.Army;
import cmd.obj.demo.Trooper;
import cmd.receive.demo.RequestTimeTrain;
import cmd.receive.demo.RequestTrain;
import cmd.send.demo.ResponseTimeTrain;
import cmd.send.demo.ResponseTrain;
import event.eventType.DemoEventType;
import model.PlayerInfo;
import model.UserProfile;
import model.UserTroopData;
import org.apache.commons.lang.exception.ExceptionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cmd.CmdDefine;
import util.server.ServerConstant;

import java.util.concurrent.ConcurrentHashMap;

public class TrainingHandler extends BaseClientRequestHandler {
    public static short TRAINING_MULTI_IDS = 3000;


    private final Logger logger = LoggerFactory.getLogger("TrainingHandler");

    public TrainingHandler() {super();}

    public void init(){
        getParentExtension().addEventListener(DemoEventType.LOGIN_SUCCESS, this);

    }

    public void handleClientRequest(User user, DataCmd dataCmd){
        logger.warn("handleClient: " + dataCmd.getId());
        try{
            switch (dataCmd.getId()){
                case CmdDefine.TRAINING:
                    logger.info("Client Request Train");
                    RequestTrain train = new RequestTrain(dataCmd);
                    processTrain(user, train);
                    break;
                default:
                    logger.info("Request unknown from client with CmdID = {}",  dataCmd.getId());
            }
        } catch (Exception e){
            logger.error("TRAINING HANDLER EXCEPTION ", e );
            //logger.warn(ExceptionUtils.getStackTrace(e)  );
        }

    }

    private void processTrain(User user, RequestTrain train){
        try {
            logger.info("Id user: {}", user.getId());
            UserTroopData userTroopData = (UserTroopData) user.getProperty(ServerConstant.USER_TROOP_DATA);
            if (userTroopData == null) logger.info("User Troop is null");
            userTroopData.processTrain(train.getBarrack());
            userTroopData.save();
            send(new ResponseTrain(DemoError.SUCCESS.getValue(), userTroopData.getRes()), user);
        } catch (Exception e){
            logger.info("Exception in Process Train");
            e.printStackTrace();
            //send(new ResponseTimeTrain(DemoError.EXCEPTION.getValue()), user);
        }
    }
    public enum DemoError{
        SUCCESS((short)8),
        ERROR((short)1),
        PLAYERINFO_NULL((short)2),
        EXCEPTION((short)3);

        private final short value;
        private DemoError(short value){
            this.value = value;
        }

        public short getValue(){
            return this.value;
        }
    }
}

