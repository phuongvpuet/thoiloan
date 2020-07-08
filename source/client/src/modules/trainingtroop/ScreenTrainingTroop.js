const trooperUtils = trooperUtils || {};

trooperUtils.Trooper = {
    WARRIOR: 1,
    ARCHER: 2,
    GIANT: 3,
    FLYING_BOMB: 4
}

trooperUtils.trainingButtonPositionX = [78, 192, 306, 420, 534, 648];
trooperUtils.trainingButtonPositionYUp = 244;
trooperUtils.trainingButtonPositionYDown = 130;

trooperUtils.decreaseButtonPositionX = [490, 360, 290, 220];
trooperUtils.decreaseButtonPositionY = 404;

trooperUtils.orderButtonTrain = ["WARRIOR", "ARCHER", "GIANT", "FLYING_BOMB"]

trooperUtils.getTimeTrainOne = function(trooperId){
    let pathName = trooperUtils.trooperPath[trooperUtils.fromIdToName(trooperId)];
    return trooperBase[pathName].trainingTime;
};

trooperUtils.trooperPath = {
    WARRIOR: "ARM_1",
    ARCHER: "ARM_2",
    GIANT: "ARM_4",
    FLYING_BOMB: "ARM_6"
}

trooperUtils.fromIdToName = function(trooperID) {
    switch (trooperID) {
        case trooperUtils.Trooper.WARRIOR:
            return "WARRIOR"
        case trooperUtils.Trooper.ARCHER:
            return "ARCHER"
        case trooperUtils.Trooper.GIANT:
            return "GIANT"
        case trooperUtils.Trooper.FLYING_BOMB:
            return "FLYING_BOMB"
        default:
            cc.log("Cannot recognize TrooperID")
            return "Trooper ID is NULL"
    }
}

function getCostTrooper(name) {
    return trooperBase[trooperUtils.trooperPath[name]].cost;
}


function getPathTrooperTrainImage(trooperID, isSmall) {
    let name = trooperUtils.fromIdToName(trooperID)
    if (isSmall) return "content/Art/GUIs/train_troop_gui/small_icon/" + trooperUtils.trooperPath[name] + ".png"
    return "content/Art/GUIs/train_troop_gui/icon/" + trooperUtils.trooperPath[name] + ".png"
}


const ScreenTrainingTroop = cc.Layer.extend({
    warriorQueue: null,
    archerQueue: null,
    giantQueue: null,
    flyingBombQueue: null,
    armyTotalQueue: null,
    onEnter:function(){
      this._super();
      openUI(this);
    },
    ctor: function (id) {
        this._super();
        this.id = id ? id : 1;
        timeTrainingTotalSignal.add(this.updateTotalTime, this);
        updateBeginTrainTimeSignal.add(this.updateBeginTrainTime, this);
        synchronizeNumberTrooperSignal.add(this.updateCount, this);
        this.initGuiTrain();
        /*
            trainingQueue = {
                timeLeft: xs,
                queue:[
                    {
                        trooperId: a,
                        length: b
                    },
                    {
                        trooperId: c,
                        length: d,
                     }
                ]
            }
         */
        /*
         */
        if (barrackInfo[this.id]){
            cc.log("Have barrack");
            let trainingQueue = barrackInfo[this.id];
            cc.log("Queue: ", JSON.stringify(trainingQueue));

            this.armyTotalQueue = new ArmyTotalQueue();
            this.addChild(this.armyTotalQueue);

            let timeLeft = trainingQueue.timeLeft;

            trainingQueue.queue.forEach((queue, idx) =>{
                cc.log("Single Queue: ", JSON.stringify(queue));
                switch (queue.trooperId) {
                    case trooperUtils.Trooper.WARRIOR:
                        this.warriorQueue = this.createSingleQueueFromData(idx, queue.trooperId, timeLeft, queue.length);
                        this.armyTotalQueue.addQueue(this.warriorQueue);
                        this.addChild(this.warriorQueue);
                        break;
                    case trooperUtils.Trooper.ARCHER:
                        this.archerQueue = this.createSingleQueueFromData(idx, queue.trooperId, timeLeft, queue.length);
                        this.armyTotalQueue.addQueue(this.archerQueue);
                        this.addChild(this.archerQueue);
                        break;
                    case trooperUtils.Trooper.GIANT:
                        this.giantQueue = this.createSingleQueueFromData(idx, queue.trooperId, timeLeft, queue.length);
                        this.armyTotalQueue.addQueue(this.giantQueue);
                        this.addChild(this.giantQueue);
                        break;
                    case trooperUtils.Trooper.FLYING_BOMB:
                        this.flyingBombQueue = this.createSingleQueueFromData(idx, queue.trooperId, timeLeft, queue.length);
                        this.armyTotalQueue.addQueue(this.flyingBombQueue);
                        this.addChild(this.flyingBombQueue);
                        break;
                    default:
                        cc.log("Cannot recognize TrooperID: ", queue.trooperId);
                        break;

                }
            });
            this.armyTotalQueue.setPositionQueue();
        }
    },

    createSingleQueueFromData:function(idx, trooperId, timeLeft, length){
        let timeTrainOne = trooperUtils.getTimeTrainOne(trooperId);
        cc.log("ID: ", idx);
        cc.log("Trooper Id: ", trooperId);
        cc.log("timeLeft : ", timeLeft);
        cc.log("length: ", length);
        cc.log("timeTrainOne: ", timeTrainOne);
        if (idx === 0) return new TrainingQueue(trooperId, timeTrainOne, timeLeft, length);
        else return new TrainingQueue(trooperId, timeTrainOne, 0, length);
    },

    initGuiTrain: function () {
        this.size = cc.director.getWinSize();

        //Training GUI
        const json = ccs.load(trainingRes.trainingGui);
        this.node = json.node;
        this.node.attr({
            x: this.size.width / 2,
            y: this.size.height / 2,
            anchorX: 0.5,
            anchorY: 0.5
        })

        let closeButton = this.node.getChildByName("close");
        closeButton = addEffectBtn(closeButton);
        closeButton.addClickEventListener(this.onSelectClose.bind(this));

        this.addChild(this.node);

        this.timePanel = this.node.getChildByName("timePanel");
        this.textTime = this.timePanel.getChildByName("timeCount");
        this.timePanel.setVisible(false);

        this.initTimeTrain();
        this.beginTrainTime = null;
        this.schedule(this.sendTrain, 0.1);
        trooperUtils.orderButtonTrain.forEach((btnName, idx) => {
            let btn = new createBtnTrain(btnName, idx, this);
            btn.setName(btnName)
            this.node.addChild(btn);
        })
        this.maxLen = 35;
        this.title = this.node.getChildByName("title");
        this.title.setString("Nhà Lính " + this.id + " (" + this.getLength() + "/" + this.maxLen + ")");
        this.addTouchListener();

    },

    getLength:function(){
      return 0;
    },

    initTimeTrain: function () {
        this.warTime = this.arcTime = this.giaTime = this.flyTime = 0;
    },

    sendTrain: function () {
        if (this.beginTrainTime != null) {
            if ((new Date().getTime() - this.beginTrainTime) / 1000 > 1) {
                testnetwork.connector.sendTrain(this.id, this.armyTotalQueue);
                this.beginTrainTime = null;
            }
        }
    },
    addTouchListener: function () {
        let self = this;
        cc.eventManager.addListener({
            swallowTouches:true,
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            onTouchBegan: function (touch, event) {
                let pos = touch.getLocation();

                trooperUtils.orderButtonTrain.forEach((btnName) => {
                   let target = event.getCurrentTarget().node.getChildByName(btnName).getChildByName("btn");
                    //Action train
                    this.action = cc.sequence(cc.callFunc(function () {
                        self.onSelectTrain(trooperUtils.Trooper[btnName]);
                    }, self), cc.delayTime(0.15)).repeatForever();
                    if (pos.x >= target.x && pos.x <= target.x + target.width && pos.y >= target.y && pos.y <= target.y + target.height) {
                        self.runAction(this.action);
                    }
                });
                return true;
            },
            onTouchMoved:function(){
                return true;
            },
            onTouchEnded: function (touch, event) {
                    self.stopAllActions();
            }
        }, this);
    },

    addClickEffect:function(obj){
        obj.runAction(cc.sequence(cc.scaleTo(0.05, 1.1), cc.scaleTo(0.05, 1)));
    },

    onSelectTrain: function (trooperId) {
        this.beginTrainTime = new Date().getTime();
        if (this.armyTotalQueue == null) {
            this.armyTotalQueue = new ArmyTotalQueue();
            this.addChild(this.armyTotalQueue);
        }
        this.addClickEffect(this.node.getChildByName(trooperUtils.fromIdToName(trooperId)).getChildByName("btn"));
        switch (trooperId) {
            case trooperUtils.Trooper.WARRIOR:
                if (this.warriorQueue == null || this.warriorQueue.getDone()) {
                    this.warriorQueue = new TrainingQueue(trooperId, trooperBase[trooperUtils.trooperPath.WARRIOR].trainingTime);
                    this.armyTotalQueue.addQueue(this.warriorQueue);
                    this.addChild(this.warriorQueue);
                } else this.warriorQueue.increase();
                break;
            case trooperUtils.Trooper.ARCHER:
                if (this.archerQueue == null || this.archerQueue.getDone()) {
                    this.archerQueue = new TrainingQueue(trooperId, trooperBase[trooperUtils.trooperPath.ARCHER].trainingTime);
                    this.armyTotalQueue.addQueue(this.archerQueue);
                    this.addChild(this.archerQueue);
                } else this.archerQueue.increase();
                break;
            case trooperUtils.Trooper.GIANT:
                if (this.giantQueue == null || this.giantQueue.getDone()) {
                    this.giantQueue = new TrainingQueue(trooperId, trooperBase[trooperUtils.trooperPath.GIANT].trainingTime);
                    this.armyTotalQueue.addQueue(this.giantQueue);
                    this.addChild(this.giantQueue);
                } else this.giantQueue.increase();
                break;
            case trooperUtils.Trooper.FLYING_BOMB:
                if (this.flyingBombQueue == null || this.flyingBombQueue.getDone()) {
                    this.flyingBombQueue = new TrainingQueue(trooperId, trooperBase[trooperUtils.trooperPath.FLYING_BOMB].trainingTime);
                    this.armyTotalQueue.addQueue(this.flyingBombQueue);
                    this.addChild(this.flyingBombQueue);
                } else this.flyingBombQueue.increase();
                break;
            default:
                break;
        }
        this.armyTotalQueue.setPositionQueue();
    },

    updateTotalTime: function (type, totalTime) {
        this.timePanel.setVisible(true);
        switch (type) {
            case trooperUtils.Trooper.WARRIOR:
                this.warTime = totalTime;
                break;
            case trooperUtils.Trooper.ARCHER:
                this.arcTime = totalTime;
                break;
            case trooperUtils.Trooper.GIANT:
                this.giaTime = totalTime;
                break;
            case trooperUtils.Trooper.FLYING_BOMB:
                this.flyTime = totalTime;
                break;
            default:
                break;
        }
        let totalTrainTime = this.warTime + this.arcTime + this.giaTime + this.flyTime;
        this.textTime.setString(convertSec(totalTrainTime));
        if (totalTrainTime < 1) {
            this.timePanel.setVisible(false);
        }
    },

    updateBeginTrainTime: function () {
        this.beginTrainTime = new Date().getTime();
    },

    saveState:function(){
        let trainingQueue = {};
        trainingQueue.timeLeft = this.armyTotalQueue.getQueue()[0].getCurrentTime();
        trainingQueue.queue = [];
        this.armyTotalQueue.getQueue().forEach(queue =>{
           let tmp = {};
           tmp.trooperId = queue.getType();
           tmp.length = queue.getLength();
           trainingQueue.queue.push(tmp);
        });
        BARRACKINFO[this.id] = trainingQueue;
    },
    onSelectClose: function () {
        if (this.armyTotalQueue) {
            this.saveState();
            //barrackTrain(this.id);
        }
        closeUI(this);
    },

    updateCount: function (trooperID, cnt) {
        switch (trooperID) {
            case trooperUtils.Trooper.WARRIOR:
                this.setCount("WARRIOR", cnt);
                break;
            case trooperUtils.Trooper.ARCHER:
                this.setCount("ARCHER", cnt);
                break;
            case trooperUtils.Trooper.GIANT:
                this.setCount("GIANT", cnt);
                break;
            case trooperUtils.Trooper.FLYING_BOMB:
                this.setCount("FLYING_BOMB", cnt);
                break;
            default:
                cc.log("Cannot recognize Trooper ID: ", trooperID);

        }
    },

    setCount: function (btnName, cnt) {
        this.node.getChildByName(btnName).getChildByName("btn").getChildByName("count").setVisible(true);
        this.node.getChildByName(btnName).getChildByName("btn").getChildByName("count").setString("x" + cnt);
        if (cnt < 1) this.node.getChildByName(btnName).getChildByName("btn").getChildByName("count").setVisible(false);
    }

});


function createBtnTrain(btnName, idx, obj) {
    let json = ccs.load(trainingRes.trainingSlot);
    let layer = json.node;
    let btn = layer.getChildByName("btn");
    btn.getChildByName("image").setTexture(getPathTrooperTrainImage(trooperUtils.Trooper[btnName]));
    btn.getChildByName("costText").setString(getCostTrooper(btnName));
    btn = addEffectBtn(btn);
    btn.attr({
        x: trooperUtils.trainingButtonPositionX[idx] + 60,
        y: idx < 6 ? trooperUtils.trainingButtonPositionYUp : trooperUtils.trainingButtonPositionYDown
    });
    btn.getChildByName("count").setVisible(false);
    return layer;
}
