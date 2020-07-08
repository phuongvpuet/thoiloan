package cmd.send.map;

import bitzero.server.extensions.data.BaseMsg;
import cmd.ErrorConst;

/**
 * Created by Fresher_LOCAL on 7/3/2020.
 */
public class ResponseNoAvailableBuilder extends BaseMsg {
    public ResponseNoAvailableBuilder(short type) {
        super(type, ErrorConst.NO_AVAILABLE_BUILDER);
    }
}
