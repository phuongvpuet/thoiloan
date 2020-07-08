let Obstacle = cc.Sprite.extend({
    id: undefined,
    obstacleType: undefined,
    // originOztPoint: undefined,
    destroyingTimeRemaining: -1,

    ctor: function (type, oztPoint) {
        this._super(getObstacleResource(type))
        addOriginOztPointTrait(this)
        addChosenTrait(this)
        this.obstacleType = type
        this.setOztPoint(oztPoint);
        this.updatePosition()
        this.addGrass()
    },

    addGrass: function () {
        let grass = new cc.Sprite(res.map.grass_0_3_obs)
        grass.setPosition(this.width / 2, this.height / 2)
        grass.setScaleX(this.getInfo()["width"] * GIRD_WIDTH / grass.width)
        grass.setScaleY(this.getInfo()["height"] * GIRD_HEIGHT / grass.height)
        this.addChild(grass, -1)
    },



    //tra lai config cua no
    getInfo: function () {
        return config[this.obstacleType]["1"]
    }
})


//
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