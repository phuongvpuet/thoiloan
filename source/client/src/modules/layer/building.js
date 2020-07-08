let Building = cc.Sprite.extend({
    id: undefined,
    level: 0,
    buildingType: null,
    // originOztPoint: null,
    timeRemaining: 0,

    ctor: function (buildingType, oztPoint, level) {
        addGrassTrait(this)
        addChosenTrait(this)
        addOriginOztPointTrait(this)
        addMoveAroundInMapTrait(this)
        addCheckCollideTrait(this)
        this._super(getBuildingResource(buildingType, level))
        this.level = level
        this.buildingType = buildingType || BuildingType.Default
        this.addGrass()
        this.setOztPoint(oztPoint);
        this.updatePosition()
    },

    //tra lai config cua no
    getInfo: function () {
        return config[this.buildingType][this.level.toString()]
    }
})

let BuildingController = cc.Class.extend({})


// addMoveEvent: function () {
//     let event = cc.EventListener.create({
//         event: cc.EventListener.TOUCH_ONE_BY_ONE,
//         onTouchBegan: function (touch, event) {
//             let target = event.getCurrentTarget()
//             cc.log(target.buildingType)
//             return true
//         }
//     })
//     cc.eventManager.addListener(event, this)
// },

// setOztPoint: function (oztPoint) {
//     this.originOztPoint = oztPoint;
// },
//
// updatePosition: function () {
//     let midOztPoint = cc.p(
//         this.originOztPoint.x + this.getInfo()["width"] / 2,
//         this.originOztPoint.y + this.getInfo()["height"] / 2);
//     let oxyMidPoint = getOxyPointFromOzt(midOztPoint);
//     let position = oxyToMapPosition(oxyMidPoint);
//     this.setPosition(position);
// },