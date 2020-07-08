const ArmyTotalQueue = cc.Layer.extend({
    queue: null,
    ctor: function () {
        this._super();
        trainSingleArmyQueueSignal.add(this.onFinishTrainSingleQueue, this);
        this.init();
    },
    init: function () {
        this.queue = [];
        this.currentQueue = null;
    },
    addQueue: function (armyQueue) {
        this.queue.push(armyQueue);
        if (this.currentQueue == null) {
            this.currentQueue = this.queue[0];
            this.currentQueue.train();
        }
    },

    getLength: function () {
        return this.queue.length;
    },

    getQueue: function () {
        return this.queue;
    },

    onFinishTrainSingleQueue: function (queue) {
        this.queue.splice(this.queue.indexOf(queue), 1);
        if (this.getLength() > 0) {
            this.currentQueue = this.queue[0];
            this.currentQueue.train();
        } else this.currentQueue = null;
        this.setPositionQueue();
    },

    setPositionQueue: function () {
        this.getQueue().forEach((queue, idx) => {
            queue.attr({
                x: trooperUtils.decreaseButtonPositionX[idx] + 60,
                y: trooperUtils.decreaseButtonPositionY
            });
        });
    }

});