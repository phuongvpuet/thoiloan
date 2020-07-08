package cmd.receive.demo;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.common.business.CommonHandle;
import cmd.obj.demo.Trooper;

import java.nio.ByteBuffer;

public class RequestTimeTrain extends BaseCmd {
    public RequestTimeTrain(DataCmd dataCmd) {
        super(dataCmd);
    }
}