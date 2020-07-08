package cmd.send.userinfo;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.UserTroopData;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Fresher_LOCAL on 7/2/2020.
 */
public class ResponseRequestUserTroop extends BaseMsg {
    public UserTroopData troopData;
    private HashMap<Short, Integer> armyCamp;
    private ConcurrentHashMap<Short, LinkedHashMap<Short, Integer>> barrack;
    public ResponseRequestUserTroop(UserTroopData userTroopData) {
        super(CmdDefine.GET_USER_TROOP);
        this.troopData = userTroopData;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        //ArmyCamp
        armyCamp = troopData.getUserTroop();
        bf.putInt(armyCamp.size());
        armyCamp.forEach((trooperId, lenTroop) ->{
            bf.putShort(trooperId);
            bf.putInt(lenTroop);
        });

        //Barack
        barrack = troopData.getTrainQueue();
        bf.putInt(barrack.size());
        barrack.forEach((id, queue) ->{
            bf.putInt(id);
            bf.putInt(troopData.getTimeLeft(id));
            bf.putInt(queue.size());
            queue.forEach((trooperId, lenTroop) ->{
                bf.putShort(trooperId);
                bf.putInt(lenTroop);
            });
        });

        return packBuffer(bf);
    }
}
