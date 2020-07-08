/**
 * Created by KienVN on 10/2/2017.
 */

var gv = gv||{};
var testnetwork = testnetwork||{};

testnetwork.Connector = cc.Class.extend({
    ctor:function(gameClient)
    {
        this.gameClient = gameClient;
        gameClient.packetFactory.addPacketMap(testnetwork.packetMap);
        gameClient.receivePacketSignal.add(this.onReceivedPacket, this);
        this._userName = "username";
    },
    onReceivedPacket:function(cmd, packet)
    {
        switch (cmd)
        {
            case gv.CMD.HAND_SHAKE:
                this.sendLoginRequest();
                break;
            case gv.CMD.USER_LOGIN:
                this.sendGetUserInfo();
                fr.getCurrentScreen().onFinishLogin();
                break;
            case gv.CMD.USER_INFO:
                cc.log("USER INFO");
                this.sendGetTroopInfo();
                fr.view(MapTestScene);
                break;
            case gv.CMD.MOVE:
                cc.log("MOVE:", packet.x, packet.y);
                fr.getCurrentScreen().updateMove(packet.x, packet.y);
                break;
            case gv.CMD.TRAINING:
                cc.log("Receive Training Data");
                cc.log(JSON.stringify(packet.data));
                break;
            case gv.CMD.TROOPDATA:
                cc.log("Receive Troop Data");
                cc.log(JSON.stringify(packet.armyCamp));
                cc.log(JSON.stringify(packet.barrack));
                updateArmy(packet.armyCamp);
                updateBarrack(packet.barrack);
                //army = packet.armyCamp.army;
                break;
        }
    },

    sendGetTroopInfo:function(){
      cc.log("SendGetTroopInfo");
      let pk = this.gameClient.getOutPacket(CmdSendGetUserTroopData);
      pk.pack();
      this.gameClient.sendPacket(pk);
    },
    sendGetUserInfo:function()
    {
        cc.log("sendGetUserInfo");
        var pk = this.gameClient.getOutPacket(CmdSendUserInfo);
        pk.pack();
        this.gameClient.sendPacket(pk);
    },
    sendLoginRequest: function () {
        cc.log("sendLoginRequest");
        var userId = fr.getCurrentScreen().getUserIdInput();
        if (userId === -1 || isNaN(userId)) {
            return;
        }
        var pk = this.gameClient.getOutPacket(CmdSendLogin);
        cc.log("input :" + userId);
        pk.pack(userId);
        this.gameClient.sendPacket(pk);
    },
    sendMove:function(direction){
        cc.log("SendMove:" + direction);
        var pk = this.gameClient.getOutPacket(CmdSendMove);
        pk.pack(direction);
        this.gameClient.sendPacket(pk);
    },
    sendTrain:function (barrackId, totalQueue) {
        cc.log("Send Train to Server");
        let pk = this.gameClient.getOutPacket(CmdSendTrain);
        pk.pack(barrackId, totalQueue);
        this.gameClient.sendPacket(pk);
    }
});



