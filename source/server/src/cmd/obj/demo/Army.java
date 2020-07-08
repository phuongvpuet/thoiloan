package cmd.obj.demo;

import java.util.concurrent.ConcurrentHashMap;

public class Army{
    private final ConcurrentHashMap<Short, Integer> army;
    public Army(){
        army = new ConcurrentHashMap<Short, Integer>();
        army.put(Trooper.WARRIOR.getValue(), 0);
        army.put(Trooper.ARCHER.getValue(), 0);
        army.put(Trooper.GIANT.getValue(), 0);
        army.put(Trooper.FLYING_BOMB.getValue(), 0);
    }
    public ConcurrentHashMap<Short, Integer> getValue(){
        return this.army;
    }
}