package cmd.receive.map;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.common.business.CommonHandle;

import java.nio.ByteBuffer;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class RequestBuildNewBuilding extends BaseCmd {
    private String buildingType;
    private int posX;
    private int posY;

    public RequestBuildNewBuilding(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }

    @Override
    public void unpackData() {
        try {
            ByteBuffer bf = makeBuffer();
            this.buildingType = readString(bf);
            this.posX = readInt(bf);
            this.posY = readInt(bf);
        } catch (Exception e) {
            this.buildingType = "";
            this.posX = 0;
            this.posY = 0;
            CommonHandle.writeErrLog(e);
        }
    }

    public String getBuildingType() {
        return buildingType;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
