let BuilderHut = cc.Sprite.extend({
    id: undefined,
    builderHutOrder: 0,
    buildingType: BuildingType.BuilderHut,
    // originOztPoint: undefined,

    ctor: function (oztPoint, builderHutOrder) {
        addGrassTrait(this)
        addChosenTrait(this)
        addOriginOztPointTrait(this)
        addMoveAroundInMapTrait(this)
        addCheckCollideTrait(this)
        this._super(getSourcePath(sourceFolderName[BuildingType.BuilderHut]) + '/idle/image0000.png')
        this.builderHutOrder = builderHutOrder
        this.addGrass()
        this.setOztPoint(oztPoint);
        this.updatePosition()
    },

    //tra lai config cua no
    getInfo: function () {
        return config[this.buildingType][this.builderHutOrder.toString()]
    }
})

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