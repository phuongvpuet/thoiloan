const TrainingQueue = cc.Layer.extend({
    current:null,
    count:null,
    ctor:function (trooperId, timeTrain, timeLeft, lenQueue) {
       this._super();
       this.trooperType = trooperId;
       this.timeTrain = timeTrain;
       this.init();
       if (timeLeft)  this.current = timeLeft;
       if (lenQueue) {
           this.count = lenQueue;
           this.countText.setString("x" + this.getLength());
           synchronizeNumberTrooperSignal.dispatch(this.trooperType, this.getLength());
           this.btnDecrease.setVisible(true);
           this.sendTime();
       }
       else this.increase();
    },

    init:function () {

       //GUI
       let json = ccs.load(trainingRes.trainingSlotSmall);
       let node = json.node;
       this.addChild(node);

       this.done = false;
       this.count = 0;

       this.btnDecrease = node.getChildByName("decreaseButton");
       this.btnDecrease.getChildByName("image").setTexture(getPathTrooperTrainImage(this.trooperType, true));

       this.btnDecrease = addEffectBtn(this.btnDecrease);
       this.btnDecrease.setVisible(false);
       //this.btnDecrease.addClickEventListener(this.decrease.bind(this));
       this.countText = this.btnDecrease.getChildByName("count");

       this.progressBar = this.btnDecrease.getChildByName("progressBar");
       this.progressBar.setVisible(false);
       this.progressBarBack = this.btnDecrease.getChildByName("image1");
       this.progressBarBack.setVisible(false);

       this.timeLeft = this.btnDecrease.getChildByName("timeLeft");
       this.timeLeft.setVisible(false);
       this.addTouchListener();

    },

    addTouchListener:function(){
        let self = this;
        cc.eventManager.addListener({
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            onTouchBegan:function (touch, event) {
                let target = event.getCurrentTarget();
                let pos = touch.getLocation();
                self.actionDec = cc.sequence(cc.callFunc(function () {
                    self.decrease();
                }, self), cc.delayTime(0.15)).repeatForever();
                if (pos.x >= target.x
                    && pos.x <= target.x + target.btnDecrease.width
                    && pos.y >= target.y
                    && pos.y <= target.y + target.btnDecrease.height){
                    this.current = self;
                    self.runAction(self.actionDec);
                }
                if (self.getLength()===0) self.stopAction(self.actionDec);
                return true;
            },
            onTouchEnded:function (touch, event) {
                if (event.getCurrentTarget()){
                    let target = event.getCurrentTarget();
                    let pos = touch.getLocation();
                    if (pos.x >= target.x
                        && pos.x <= target.x + target.btnDecrease.width
                        && pos.y >= target.y
                        && pos.y <= target.y + target.btnDecrease.height){
                        if (self === this.current) self.stopAction(self.actionDec);
                    }
                }
            }
        }, this);
    },

    isEmpty:function(){
      return this.getLength() <= 0;
    },

    isTraining:function(){
        return this.current != null;
    },

    getLength:function () {
       return this.count;
    },

    increase:function () {
        this.count ++;
        this.countText.setString("x" + this.getLength());
        synchronizeNumberTrooperSignal.dispatch(this.trooperType, this.getLength());
        this.btnDecrease.setVisible(true);
        this.sendTime();
        //cc.log("Total Training " + this.type + ": " + this.count);

    },

    clickEffect: function () {
        this.btnDecrease.runAction(cc.sequence(cc.scaleTo(0.05, 1.1), cc.scaleTo(0.05, 1)));

    },
    decrease:function () {
        this.clickEffect();
       this.count --;
       this.countText.setString("x" + this.getLength());
       synchronizeNumberTrooperSignal.dispatch(this.trooperType, this.getLength());
       updateBeginTrainTimeSignal.dispatch();
       if (!this.isEmpty()) {
           this.sendTime();
       }
       else this.finishTraining();
    },

    train:function () {
       if (!this.isTraining()){
           if (!this.isEmpty()) {
               this.current = this.timeTrain;
               this.runAction(cc.sequence(
                   cc.callFunc(this.processTrain, this),
                   cc.callFunc(this.checkQueue, this),
                   cc.delayTime(1)
               ).repeatForever());
           } else {
               this.finishTraining();
               this.btnDecrease.setVisible(false);
           }
       }
       else{
           this.runAction(cc.sequence(
               cc.callFunc(this.processTrain, this),
               cc.callFunc(this.checkQueue, this),
               cc.delayTime(1)
           ).repeatForever());
       }
    },

    processTrain:function () {
        this.timeLeft.setVisible(true);
        this.progressBar.setVisible(true);
        this.progressBarBack.setVisible(true);
       if (this.current <= 0){
           this.updateArmy();
       }
       else {
           this.timeLeft.setString(convertSec(this.getCurrentTime()));
           this.sendTime();
       }
       this.current --;
       this.progressBar.setPercent((this.timeTrain - this.current) / this.timeTrain * 100);
    },

    finishedTrainATrooper:function(){
      return this.current <= -1;
    },

    checkQueue:function () {
       if (this.finishedTrainATrooper()){
           this.count --;
           this.countText.setString("x" + this.getLength());
           synchronizeNumberTrooperSignal.dispatch(this.trooperType, this.getLength());
           if (this.isEmpty()){
               this.finishTraining();
           }
           else {
               this.continueTraining();
           }
       }
    },

    getDone:function () {
        return this.done;
    },

    getType:function(){
      return this.trooperType;
    },

    finishTraining:function () {
        this.btnDecrease.setVisible(false);
        this.timeLeft.setVisible(false);
        this.stopAllActions();
        this.current = null;
        this.done = true;
        this.count = 0;
        trainSingleArmyQueueSignal.dispatch(this);
        timeTrainingTotalSignal.dispatch(this.trooperType, 0);
        this.removeFromParent(true);
    },

    continueTraining:function () {
        this.current = null;
        this.stopAllActions();
        this.train();
    },

    updateArmy:function () {
        army[trooperUtils.fromIdToName(this.getType())] += 1;
        cc.log(JSON.stringify(army));
    },

    sendTime:function () {
        if (this.current != null) timeTrainingTotalSignal.dispatch(this.trooperType, this.getTotalTime());
        else timeTrainingTotalSignal.dispatch(this.trooperType, this.getTotalTime());
    },

    getTotalTime:function () {
        if (this.current != null) return this.current + this.timeTrain * (this.getLength()-1);
        return this.timeTrain * this.getLength()
    },
    getCurrentTime:function () {
        return this.current;
    },
    stop:function () {
        this.stopAllActions();
        this.timeLeft.setString("dừng");
    }
});