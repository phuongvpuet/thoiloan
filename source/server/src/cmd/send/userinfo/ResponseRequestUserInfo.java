package cmd.send.userinfo;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.UserProfile;

import java.nio.ByteBuffer;
import java.util.Map;

public class ResponseRequestUserInfo extends BaseMsg {
    public UserProfile info;

    public Map<Short, Integer> userResources;

    public ResponseRequestUserInfo(UserProfile _info) {
        super(CmdDefine.GET_USER_INFO);
        info = _info;
        userResources = info.getResources();
    }

    public void testUserInfoResponse() {
        StringBuilder response = new StringBuilder().append(info.getUsername()).append("\n")
                .append(info.getLevel()).append("\n")
                .append(info.getCurExp()).append("\n")
                .append(info.getFamePoint()).append("\n")
                .append(info.getVipLevel()).append("\n")
                .append(this.userResources.size()).append("\n");
        userResources.forEach((type, amount) -> {
            response.append(type).append("\t").append(amount).append("\n");
        });
        response.append(info.getCoin()).append("\n")
                .append(info.getBuilderNumber()).append("\n");
        System.out.println("testUserInfoResponse:");
        System.out.println(response.toString());
    }

    @Override
    public byte[] createData() {
        ByteBuffer bf = makeBuffer();
        putStr(bf, info.getUsername());
        bf.putInt(info.getLevel());
        bf.putInt(info.getCurExp());
        bf.putInt(info.getFamePoint());
        bf.putInt(info.getVipLevel());
        bf.putInt(userResources.size());
        userResources.forEach((type, amount) -> {
            bf.putShort(type);
            bf.putInt(amount);
        });
        bf.putInt(info.getCoin());
        bf.putInt(info.getBuilderNumber());
        return packBuffer(bf);
    }
}
