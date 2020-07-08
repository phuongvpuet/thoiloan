package cmd.send.map;

import bitzero.server.extensions.data.BaseMsg;
import cmd.ErrorConst;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class ResponseLackOfResources extends BaseMsg {
    public ResponseLackOfResources(short type) {
        super(type, ErrorConst.LACK_OF_RESOURCES);
    }
}
