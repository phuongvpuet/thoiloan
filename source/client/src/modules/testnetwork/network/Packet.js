/**
 * Created by KienVN on 10/2/2017.
 */

gv.CMD = gv.CMD || {};
gv.CMD.HAND_SHAKE = 0;
gv.CMD.USER_LOGIN = 1;

gv.CMD.USER_INFO = 1001;
gv.CMD.TROOPDATA = 1003;

gv.CMD.MOVE = 2001;
gv.CMD.TRAINING = 3001;

testnetwork = testnetwork || {};
testnetwork.packetMap = {};

/** Outpacket */

//Handshake
CmdSendHandshake = fr.OutPacket.extend(
    {
        ctor: function () {
            this._super();
            this.initData(100);
            this.setControllerId(gv.CONTROLLER_ID.SPECIAL_CONTROLLER);
            this.setCmdId(gv.CMD.HAND_SHAKE);
        },
        putData: function () {
            //pack
            this.packHeader();
            //update
            this.updateSize();
        }
    }
)
CmdSendUserInfo = fr.OutPacket.extend(
    {
        ctor: function () {
            this._super();
            this.initData(100);
            this.setCmdId(gv.CMD.USER_INFO);
        },
        pack: function () {
            this.packHeader();
            this.updateSize();
        }
    }
)

CmdSendLogin = fr.OutPacket.extend(
    {
        ctor: function () {
            this._super();
            this.initData(100);
            this.setCmdId(gv.CMD.USER_LOGIN);
        },
        pack: function (user) {
            this.packHeader();
            this.putInt(user);
            this.updateSize();
        }
    }
)

CmdSendMove = fr.OutPacket.extend(
    {
        ctor: function () {
            this._super();
            this.initData(100);
            this.setCmdId(gv.CMD.MOVE);
        },
        pack: function (direction) {
            this.packHeader();
            this.putShort(direction);
            this.updateSize();
        }
    }
)


CmdSendTrain = fr.OutPacket.extend(
    {
        ctor: function () {
            this._super();
            this.initData(100);
            this.setCmdId(gv.CMD.TRAINING);
        },
        pack: function (barrackId, totalQueue) {
            this.packHeader();
            this.putShort(barrackId);
            this.putInt(totalQueue.getLength());
            cc.log("Length Queue: ", totalQueue.getLength());
            totalQueue.getQueue().forEach(queue => {
                this.putShort(queue.getType());
                cc.log("Trooper: ", queue.getType());
                this.putInt(queue.getLength());
                cc.log("Length: ", queue.getLength());
            });
            this.updateSize();
        }
    }
)

CmdSendGetUserTroopData = fr.OutPacket.extend(
    {
        ctor: function () {
            this._super();
            this.initData(100);
            this.setCmdId(gv.CMD.TROOPDATA);
        },
        pack: function () {
            this.packHeader();
            this.updateSize();
        }
    }
)

/**
 * InPacket
 */

//Handshake
testnetwork.packetMap[gv.CMD.HAND_SHAKE] = fr.InPacket.extend(
    {
        ctor: function () {
            this._super();
        },
        readData: function () {
            this.token = this.getString();
        }
    }
);

testnetwork.packetMap[gv.CMD.USER_LOGIN] = fr.InPacket.extend(
    {
        ctor: function () {
            this._super();
        },
        readData: function () {
        }
    }
);


testnetwork.packetMap[gv.CMD.USER_INFO] = fr.InPacket.extend(
    {
        ctor: function () {
            this._super();
        },
        readData: function () {

        }
    }
);

testnetwork.packetMap[gv.CMD.MOVE] = fr.InPacket.extend(
    {
        ctor: function () {
            this._super();
        },
        readData: function () {
            this.x = this.getInt();
            this.y = this.getInt();
        }
    }
);
testnetwork.packetMap[gv.CMD.TRAINING] = fr.InPacket.extend(
    {
        ctor: function () {
            this._super();
        },
        readData: function () {
            this.data = {};
            this.data.lenRes = this.getShort();
            this.data.res = {};
            for (let tmp = 0; tmp < this.data.lenRes; tmp++) {
                let type = this.getShort();
                this.data.res[type] = this.getLong();
            }
        }
    }
);

testnetwork.packetMap[gv.CMD.TROOPDATA] = fr.InPacket.extend(
    {
        ctor:function () {
            this._super();
        },
        readData: function () {
            this.armyCamp = {};
            //ArmyCamp
            this.armyCamp.lenTrooperType = this.getInt();
            this.armyCamp.army = {};
            //cc.log("Len Trooper: ", this.armyCamp.lenTrooperType);
            for (let tmp = 0; tmp < this.armyCamp.lenTrooperType; tmp ++){
                let trooperId = this.getShort();
                this.armyCamp.army[trooperId] = this.getInt();
            }

            //Barracks
            this.barrack = {};
            this.barrack.lenBarrack = this.getInt();
            this.barrack.queue = {};
            for (let tmp = 0; tmp < this.barrack.lenBarrack; tmp ++){
                let trainingQueue = {};
                let barrackId = this.getInt();
                trainingQueue.timeLeft = this.getInt();
                trainingQueue.queue = [];
                let lenTrooperType = this.getInt();
                //cc.log("Barrack Id: ", barrackId, " TimeLeft: ", trainingQueue.timeLeft);
                for(let tmp2 = 0; tmp2 < lenTrooperType; tmp2 ++){
                    let tmp3 = {};
                    tmp3.trooperId = this.getShort();
                    tmp3.length = this.getInt();
                    trainingQueue.queue.push(tmp3);
                }
                this.barrack.queue[barrackId] = trainingQueue;
            }
        }
    }
);



