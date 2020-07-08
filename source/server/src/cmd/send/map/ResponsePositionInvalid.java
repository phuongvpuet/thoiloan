package cmd.send.map;

import bitzero.server.extensions.data.BaseMsg;
import cmd.ErrorConst;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class ResponsePositionInvalid extends BaseMsg {
    public ResponsePositionInvalid(short type) {
        super(type, ErrorConst.POSITION_INVALID);
    }
}
