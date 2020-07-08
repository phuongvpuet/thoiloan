package cmd.send.map;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.building.Building;

import java.nio.ByteBuffer;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class ResponseConstructSuccess extends BaseMsg {
    private Building building;
    public ResponseConstructSuccess(Building newBuilding) {
        super(CmdDefine.BUILD_NEW_BUILDING);
        this.building = newBuilding;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putInt(this.building.getBuildingId());
        putStr(bf, this.building.getBuildingType().toString());
        bf.putInt(this.building.getPosX());
        bf.putInt(this.building.getPosY());
        bf.putLong(this.building.getRemainingTime());
        return packBuffer(bf);
    }
}
