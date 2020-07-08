package model;

import model.common.Resource;
import util.database.UserDataModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class UserProfile extends UserDataModel {
    private String username;
    private int level;
    private int curExp;
    private int famePoint;
    private int vipLevel;
    private int builderNumber;
    private Map<Short, Integer> resources;
    private int coin;

    public UserProfile(int userId, String username, int gold, int elixir, int darkElixir, int builderNumber, int coin) {
        super(userId);
        this.username = username;
        this.level = 1;
        this.curExp = 0;
        this.famePoint = 0;
        this.vipLevel = 0;
        resources = new HashMap<>();
        resources.put(Resource.GOLD_TYPE, gold);
        resources.put(Resource.ELIXIR_TYPE, elixir);
        resources.put(Resource.DARK_ELIXIR_TYPE, darkElixir);
        this.builderNumber = builderNumber;
        this.coin = coin;
    }

    public UserProfile(int userId, String username) {
        super(userId);
        this.username = username;
        this.level = 1;
        this.curExp = 0;
        this.famePoint = 0;
        this.vipLevel = 0;
        resources = new HashMap<>();
        resources.put(Resource.GOLD_TYPE, 0);
        resources.put(Resource.ELIXIR_TYPE, 0);
        resources.put(Resource.DARK_ELIXIR_TYPE, 0);
        this.builderNumber = 0;
        this.coin = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurExp() {
        return curExp;
    }

    public void setCurExp(int curExp) {
        this.curExp = curExp;
    }

    public int getFamePoint() {
        return famePoint;
    }

    public void setFamePoint(int famePoint) {
        this.famePoint = famePoint;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getBuilderNumber() {
        return builderNumber;
    }

    public void setBuilderNumber(int builderNumber) {
        this.builderNumber = builderNumber;
    }

    public int getResourceByType(short type) {
        // TODO: check valid type
        return resources.get(type);
    }

    public void setResourceByType(short type, int amount) {
        // TODO: check valid type
        resources.put(type, amount);
    }

    public Map<Short, Integer> getResources() {
        return this.resources;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
