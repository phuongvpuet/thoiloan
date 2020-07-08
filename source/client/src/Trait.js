let addGrassTrait = function (target) {
    target.addGrass = CreateAddThingInBackGroundFunction(
        "GrassTrait",
        "grassBg",
        res.map.getGrassBg,
        GRASS_ORDER,
        {x: 2, y: 2}
    )[0]
}

let addOriginOztPointTrait = function (target) {

    target.originOztPoint = undefined
    target.setOztPoint = function (oztPoint) {
        this.originOztPoint = oztPoint;
    }
    target.updatePosition = function (zOrder) {
        if (this.getInfo === undefined) throw "Object doesnt have getInfo operation"
        if (this.setPosition === undefined) throw "Object doesnt have setPosition operation. Does it a Sprite ?"
        let midOztPoint = cc.p(
            this.originOztPoint.x + this.getInfo()["width"] / 2,
            this.originOztPoint.y + this.getInfo()["height"] / 2);
        let oxyMidPoint = getOxyPointFromOzt(midOztPoint);
        if (zOrder === undefined) {
            this.setLocalZOrder(HALF_SIZE - oxyMidPoint.y + MIN_BUILDING_ORDER)
        } else {
            this.setLocalZOrder(zOrder)
        }
        let position = oxyToMapPosition(oxyMidPoint);
        this.setPosition(position);
    }
}

let addMoveAroundInMapTrait = function (target) {

}

let addChosenTrait = function (target) {
    let [addArrow, removeArrow] = CreateAddThingInBackGroundFunction(
        "Chosen Trait",
        "arrowBg",
        res.map.getArrowBg,
        GRASS_ORDER
    )
    target.addArrow = addArrow
    target.removeArrow = removeArrow

    let greenBgName = "greenBg"
    let [addGreenBg, removeGreenBg] = CreateAddThingInBackGroundFunction(
        "Chosen Trait",
        greenBgName,
        res.map.getGreenBg,
        GRASS_ORDER,
        {x: 2, y: 2}
    )
    target.addGreenBgIfNotHas = function () {
        if (!this.getChildByName(greenBgName)) {
            addGreenBg.call(this)
        }
    }
    target.removeGreenBgIfHas = function () {
        if (this.getChildByName(greenBgName)) {
            removeGreenBg.call(this)
        }
    }

    let redBgName = "redBg"
    let [addRedBg, removeRedBg] = CreateAddThingInBackGroundFunction(
        "Chosen Trait",
        redBgName,
        res.map.getRedBg,
        GRASS_ORDER,
        {x: 2, y: 2}
    )
    target.addRedBgIfNotHas = function () {
        if (!this.getChildByName(redBgName)) {
            addRedBg.call(this)
        }
    }
    target.removeRedBgIfHas = function () {
        if (this.getChildByName(redBgName)) {
            removeRedBg.call(this)
        }
    }
}

let CreateAddThingInBackGroundFunction = function (traitName, thingName, getThingsResource, order, scale) {
    const DEFAULT_THING_SIZE = 3
    let thing;
    return [function () {
        if (this.getInfo === undefined) {
            cc.log(traitName, " need getInfo function");
            thing = new cc.Sprite(getThingsResource(DEFAULT_THING_SIZE))
        } else {
            thing = new cc.Sprite(getThingsResource(this.getInfo()["width"]))
        }
        thing.setPosition(this.width / 2, this.height / 2)
        if (scale !== undefined) {
            if (scale.x !== undefined) thing.setScaleX(scale.x)
            if (scale.y !== undefined) thing.setScaleY(scale.y)
        }
        this.addChild(thing, order, thingName)
    }, function () {
        this.removeChildByName(thingName)
    }
    ]
}

let addCheckCollideTrait = function (target) {
}