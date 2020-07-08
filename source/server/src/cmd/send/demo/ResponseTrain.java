package cmd.send.demo;

import bitzero.server.extensions.data.BaseMsg;

import cmd.CmdDefine;
import cmd.obj.demo.Trooper;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseTrain extends BaseMsg {
    private final ConcurrentHashMap<Short, Long> res;
    public ResponseTrain(short error, ConcurrentHashMap<Short, Long> res) {
        super(CmdDefine.TRAINING, error);
        this.res = res;
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        bf.putShort((short)res.size());
        res.forEach((k, v) ->{
            bf.putShort(k);
            bf.putLong(v);
        });
        return packBuffer(bf);
    }

    public ConcurrentHashMap<Short, Long> getRes(){
        return res;
    }
}
