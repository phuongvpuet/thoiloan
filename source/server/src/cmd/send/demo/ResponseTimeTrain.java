package cmd.send.demo;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import cmd.obj.demo.Trooper;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseTimeTrain extends BaseMsg {
    public ResponseTimeTrain(short error) {
        super(CmdDefine.TIMETRAIN, error);
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putLong(System.currentTimeMillis());
        return packBuffer(bf);
    }

}