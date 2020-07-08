package model;

import bitzero.core.I;
import cmd.obj.demo.Trooper;
import model.common.Troop;
import model.common.TroopType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.awt.image.ImageWatched;
import util.Common;
import util.database.UserDataModel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class UserTroopData extends UserDataModel {
    private final HashMap<Short, Long> timeStartTrain;
    private final HashMap<Short, Integer> timeLeft;
    private final ConcurrentHashMap<Short, LinkedHashMap<Short, Integer>> barrackTrainQueue;
    private static final Logger logger = LoggerFactory.getLogger("UserTroopData");
    private final HashMap<Short, Integer> userTroop;
    private final ConcurrentHashMap<Short, Long> userRes;

    public UserTroopData(int userId) {
        super(userId);
        logger.info("Create new user troop for user id: {}", userId);
        this.timeStartTrain = new HashMap<>();
        this.timeLeft = new HashMap<>();
        this.barrackTrainQueue = new ConcurrentHashMap<>();
        this.userTroop = new HashMap<>();
        this.userRes = new ConcurrentHashMap<>();
        this.userRes.put((short) 1, (long) 700);
        this.userRes.put((short) 2, (long) 800);
        this.userRes.put((short) 3, (long) 900);
    }

    private void createNewQueue(long startTime, short barrackId, LinkedHashMap<Short, Integer> requestTrainQueue) {
        setTimeStartTrain(barrackId, startTime);
        barrackTrainQueue.put(barrackId, requestTrainQueue);

    }

    public void processTrain(ConcurrentHashMap<Short, LinkedHashMap<Short, Integer>> barrack) {
        //Read Barrack Data

        Map.Entry<Short, LinkedHashMap<Short, Integer>> entry = barrack.entrySet().iterator().next(); //first pair
        //Barrack Id
        short barrackId = entry.getKey();
        //Barrack Train Queue
        LinkedHashMap<Short, Integer> requestTrainQueue = entry.getValue();

        //If already have training Queue in barrack Id then update army first
        if (this.barrackTrainQueue.get(barrackId) != null) {
            updateArmy(barrackId);
        }
        createNewQueue(Common.currentTimeInSecond() - getTimeLeft(barrackId), barrackId, requestTrainQueue);
    }

    private void updateArmy(short barrackId) {
        //Update Army and Barrack if Barrack's queue is available
        if (getTrainQueue(barrackId).size() > 0) {
            long currentTime = Common.currentTimeInSecond();
            long startTime = getTimeStartTrain(barrackId);
            for (Map.Entry<Short, Integer> entry : barrackTrainQueue.get(barrackId).entrySet()) {
                short trooperId = entry.getKey();
                int lenQueue = entry.getValue();
                if (!trainSingleQueue(barrackId, trooperId, lenQueue, startTime, currentTime)) {
                    break;
                }
                startTime += (long) lenQueue * getTimeTrainOne(trooperId);
            }
        }
    }

    private int getLimitSlot(){
        return 30;
    }

    private boolean trainSingleQueue(short barrackId, short trooperId, int lengthQueue, long startTime, long currentTime) {
        //If calculate train queue is done at current Time
        if (startTime + getTimeTrainOne(trooperId) * lengthQueue < currentTime) {
            int remain = getRemainTrooperAfterAdd(trooperId, lengthQueue);
            if (remain == 0)
            {
                removeQueue(barrackId, trooperId);
                return true;
            }
            else
            {
                logger.info("Limit Slot, remain {} troopers at barrack {}", trooperId, barrackId);

            }
        }
        //If it's not done, calculate how many trooper is done and timeLeft for that Barrack
        int trainDone = (int) (currentTime - startTime) / getTimeTrainOne(trooperId);
        int timeLeft = getTimeTrainOne(trooperId) - (int) (currentTime - startTime) % getTimeTrainOne(trooperId);
        setTimeLeft(barrackId, timeLeft);

        this.barrackTrainQueue.get(barrackId).put(trooperId, this.barrackTrainQueue.get(barrackId).get(trooperId) - trainDone);
        int numberTroop = userTroop.get(trooperId) == null ? trainDone : userTroop.get(trooperId) + trainDone;
        userTroop.put(trooperId, numberTroop);
        return false;
    }

    private void removeQueue(short barrackId, short trooperId) {
        LinkedHashMap<Short, Integer> barrack = getTrainQueue(barrackId);
        barrack.remove(trooperId);
        this.barrackTrainQueue.put(barrackId, barrack);
    }

    private int getRemainTrooperAfterAdd(short trooperId, int len) {
        int newLen = getLenTrooper(trooperId) + len;
        int totalTrooperSlotNumber = getTotalTrooperSlot();
        int limitTrooperSlot = getLimitSlot();
        if (totalTrooperSlotNumber + len * getSlotForOneTrooper(trooperId) <= limitTrooperSlot){
            this.userTroop.put(trooperId, newLen);
            return 0;
        }
        int remain = len - (limitTrooperSlot - totalTrooperSlotNumber) / getSlotForOneTrooper(trooperId);
        newLen = len - remain;
        this.userTroop.put(trooperId, newLen);
        return remain;
    }

    private int getTotalTrooperSlot(){
        int len = 0;
        if (this.userTroop.size() == 0) return len;
        for (Map.Entry<Short, Integer> entry: this.userTroop.entrySet()){
            short trooperId = entry.getKey();
            int lenTrooper = entry.getValue();
            len += lenTrooper * getSlotForOneTrooper(trooperId);
        }
        return len;
    }


    private int getLenTrooper(short trooperId) {
        if (this.userTroop.get(trooperId) == null) return 0;
        return this.userTroop.get(trooperId);
    }

    private int getTimeTrainOne(short TrooperId) {
        Trooper tr = Trooper.fromCode(TrooperId);
        int timeTrainOne = 0;
        switch (tr) {
            case WARRIOR:
                timeTrainOne = 20;
                break;
            case ARCHER:
                timeTrainOne = 25;
                break;
            case GIANT:
                timeTrainOne = 30;
                break;
            case FLYING_BOMB:
                timeTrainOne = 120;
                break;
            default:
                logger.info("UserTroopData: Cannot recognize Trooper ID {} in getTimeTrainOne", TrooperId);
                break;
        }
        return timeTrainOne;
    }

    private LinkedHashMap<Short, Integer> getTrainQueue(short barrackId) {
        return this.barrackTrainQueue.get(barrackId);
    }

    public ConcurrentHashMap<Short, Long> getRes() {
        return userRes;
    }

    public HashMap<Short, Integer> getUserTroop() {
        this.updateArmy();
        return userTroop;
    }

    public ConcurrentHashMap<Short, LinkedHashMap<Short, Integer>> getTrainQueue() {
        return barrackTrainQueue;
    }

    public int getTimeLeft(short barrackId) {
        return this.timeLeft.get(barrackId) != null ? this.timeLeft.get(barrackId) : 0;
    }

    private long getTimeStartTrain(short barrackId) {
        return this.timeStartTrain.get(barrackId);
    }

    private void setTimeLeft(short barrackId, int timeLeft) {
        this.timeLeft.put(barrackId, timeLeft);
    }

    private void setTimeStartTrain(short barrackId, long startTime) {
        this.timeStartTrain.put(barrackId, startTime);
    }

    private int getSlotForOneTrooper(short trooperId) {
        Trooper tr = Trooper.fromCode(trooperId);
        switch (tr) {
            case WARRIOR:
                return 1;
            case ARCHER:
                return 2;
            case FLYING_BOMB:
                return 4;
            case GIANT:
                return 5;
            default:
                logger.error("Cannot recognize this Trooper Id: {} in getSlotForOneTrooper", trooperId);
                return 0;
        }
    }
}
