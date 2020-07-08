package common.config;

import common.config.obj.BarrackLevelInfo;

import java.util.Map;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class Bar1Config {
    private Map<Integer, BarrackLevelInfo> configLevel;

    public Bar1Config(Map<Integer, BarrackLevelInfo> configLevel) {
        this.configLevel = configLevel;
    }
}
