package common.config;

import java.util.Map;

/**
 * Created by Fresher_LOCAL on 6/25/2020.
 */
public class InitGameConfig {
    public class Position {
        public int posX;
        public int posY;
    }
    public class PlayerResource {
        public int gold;
        public int coin;
        public int elixir;
        public int darkElixir;
        public int builderNumber;
    }
    public class Obstacle {
        public String type;
        public int posX;
        public int posY;
    }
    public Map<String, Position> map;
    public PlayerResource player;
    public Map<Integer, Obstacle> obs;
}
