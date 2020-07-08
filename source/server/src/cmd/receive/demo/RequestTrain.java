package cmd.receive.demo;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import bitzero.util.common.business.CommonHandle;
import cmd.obj.demo.Trooper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;


public class RequestTrain extends BaseCmd{
    private final Logger logger = LoggerFactory.getLogger("RequestTrain");
    private LinkedHashMap<Short, Integer> trainQueue;
    private ConcurrentHashMap<Short, LinkedHashMap<Short, Integer>> barrack;

    public RequestTrain(DataCmd dataCmd) {
        super(dataCmd);
        trainQueue = new LinkedHashMap<Short, Integer>();
        barrack = new ConcurrentHashMap<>();
        unpackData();
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            short barrackId = readShort(bf);
            int lengthQueue = readInt(bf);
            logger.info("Length queue: {}", lengthQueue);
            for (int iter=0; iter < lengthQueue; ++iter){
                short trooperId = readShort(bf);
                logger.info("Tropper Id: {}", trooperId);
                int lenSingleQueue = readInt(bf);
                trainQueue.put(trooperId, lenSingleQueue);
                logger.info("LenSingleQueue: {}", lenSingleQueue);
            }
            barrack.put(barrackId, trainQueue);
        } catch (Exception e) {
            logger.error("Exception while read data from client: ", e);
            //CommonHandle.writeErrLog(e);
        }
    }

    public ConcurrentHashMap<Short, LinkedHashMap<Short, Integer>> getBarrack(){return barrack;}

}
