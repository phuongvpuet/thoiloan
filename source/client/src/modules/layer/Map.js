let scale = {
    x: 1,
    y: 1,
}

let MapLayer = cc.Layer.extend({
    _winSize: null,
    SCALE_CHANGES_RATE: 1.25,
    MAX_SCALE: 1,
    MIN_SCALE: 0.25,
    maxVisibleWidth: 0,
    maxVisibleHeight: 0,
    oztIntCoordOfTouch: cc.p(0, 0),
    blockTouched: undefined,
    chosenBuilding: undefined,
    justMoved: false,
    chosenObstacle: undefined,

    ctor: function () {
        this._super()
        this._winSize = cc.director.getWinSize();
        this.setAnchorPoint(0, 0)
        this.setPosition(0, 0)
        this.setScale(scale.x, scale.y)
        this.addGirds()
        this.addEdges()
        this.addDetermineOztCoordAndBlockWhenTouchEvent()
        this.addKeyZoomEvent()
        this.addTouchMoveEvent()
        this.addMoveChosenBuildingEvent()
        this.addTouchToBuildingEvent()
        this.addTouchToObstacleEvent()
        this._initMap()
        //this.printOztCoord()
    },

    _initMap: function () {
        let buildings = SingleInstance.map.getBuildings()
        this._initBuildings(buildings)
        let obstacles = SingleInstance.map.getObstacles()
        this._initObstacles(obstacles)
    },

    _initBuildings: function (buildings) {
        cc.log("map: " + buildings.length);
        for (let building of buildings) {
            let oxyCoord = getOxyPointFromOzt(building.originOztPoint)
            this.addChild(building)
        }
    },

    _initObstacles: function (obstacles) {
        for (let obstacle of obstacles) {
            let oxyCoord = getOxyPointFromOzt(obstacle.originOztPoint)
            this.addChild(obstacle)
        }
    },

    addDetermineOztCoordAndBlockWhenTouchEvent: function () {

        let getMapLayerPosition = this.getMapLayerPosition.bind(this)
        let aboveEverythingNode = new cc.Node();
        let thisMap = this
        this.addChild(aboveEverythingNode, 10000);
        let determineOztIntCoordEvent = cc.EventListener.create({
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            swallowTouches: false,
            onTouchBegan: function (touch) {
                this.originPos = touch.getLocation()
                thisMap.justMoved = false;
                let inMapPosition = getMapLayerPosition(this.originPos)
                let oxyPosition = mapPositionToOxy(inMapPosition)
                let oztPosition = getOztPointFromOxy(oxyPosition)
                thisMap.oztIntCoordOfTouch = toIntegerPoint(oztPosition)
                thisMap.blockTouched = SingleInstance.map.getBlock(
                    thisMap.oztIntCoordOfTouch.x,
                    thisMap.oztIntCoordOfTouch.y
                )
                return false
            },
        })
        cc.eventManager.addListener(determineOztIntCoordEvent, aboveEverythingNode)
    },

    addKeyZoomEvent: function () {
        let getMapLayerPosition = this.getMapLayerPosition.bind(this)
        let increaseScaleFromPosition = this.increaseScaleFromPosition.bind(this)
        let decreaseScaleFromPosition = this.decreaseScaleFromPosition.bind(this)
        cc.eventManager.addListener(cc.EventListener.create({
            event: cc.EventListener.MOUSE,
            onMouseScroll: function (event) {
                let inMapPosition = getMapLayerPosition(event.getLocation())
                if (event.getScrollY() > 0) {
                    decreaseScaleFromPosition(inMapPosition)
                } else if (event.getScrollY() < 0) {
                    increaseScaleFromPosition(inMapPosition)
                }
                cc.log("Mouse Scroll", inMapPosition.x, inMapPosition.y)
            },
        }), this)
    },

    getMapLayerPosition: function (realPosition) {
        return new cc.p((realPosition.x - this.x) / scale.x,
            (realPosition.y - this.y) / scale.y);
    },

    increaseScaleFromPosition: function (inMapPosition) {
        let x = scale.x * this.SCALE_CHANGES_RATE
        let y = scale.y * this.SCALE_CHANGES_RATE
        let newScaleX = (x > this.MAX_SCALE) ? this.MAX_SCALE : x
        let newScaleY = (y > this.MAX_SCALE) ? this.MAX_SCALE : y
        this._adjustPositionAndScale(inMapPosition, newScaleX, newScaleY)
    },

    decreaseScaleFromPosition: function (inMapPosition) {
        let x = scale.x / this.SCALE_CHANGES_RATE
        let y = scale.y / this.SCALE_CHANGES_RATE
        let newScaleX = (x < this.MIN_SCALE) ? this.MIN_SCALE : x
        let newScaleY = (y < this.MIN_SCALE) ? this.MIN_SCALE : y
        this._adjustPositionAndScale(inMapPosition, newScaleX, newScaleY)
    },

    _adjustPositionAndScale: function (inMapPosition, newScaleX, newScaleY) {
        let xDistance = (newScaleX - scale.x) * inMapPosition.x
        let yDistance = (newScaleY - scale.y) * inMapPosition.y
        this.setPosition(cc.p(this.x - xDistance, this.y - yDistance))
        scale.x = newScaleX
        scale.y = newScaleY
        this._updateScale()
    },

    _updateScale: function () {
        this.setScale(scale.x, scale.y)
    },

    addTouchMoveEvent: function () {
        let thisMap = this
        let getMapLayerPosition = this.getMapLayerPosition.bind(this)
        const [maxVisibleX, minVisibleX, maxVisibleY, minVisibleY]
            = this._initVisibleXY()
        let posX, posY
        const moveEvent = cc.EventListener.create({
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            onTouchBegan: function (touch) {
                this.originPos = touch.getLocation()
                return thisMap.chosenBuilding !== thisMap.blockTouched;
            },
            onTouchMoved: function (touch, event) {
                thisMap._setJustMoved()
                let currentPos = touch.getLocation()
                posX = thisMap.getPositionX() + currentPos.x - this.originPos.x
                posY = thisMap.getPositionY() + currentPos.y - this.originPos.y
                //let [visiblePosX, visiblePosY] = this._adjustToVisiblePosition(posX, posY)
                // if (posX > maxVisibleX) posX = maxVisibleX
                // if (posX < minVisibleX) posX = minVisibleX
                // if (posY > maxVisibleY) posY = maxVisibleY
                // if (posY < minVisibleY) posY = minVisibleY
                this.originPos = currentPos;
                thisMap.setPosition(posX, posY)
                return false;
            }
        })
        cc.eventManager.addListener(moveEvent, this)
    },

    _setJustMoved: function () {
        this.justMoved = true
    },

    addTouchToBuildingEvent: function () {
        let thisMap = this
        let event = cc.EventListener.create({
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            onTouchBegan: function (touch, event) {
                return true
            },
            onTouchMoved: function () {
                return true
            },
            onTouchEnded: function (touch, event) {
                if (thisMap.justMoved) {
                    return false
                };
                if (SingleInstance.mapController.isBuilding(thisMap.blockTouched)) {
                    let building = thisMap.blockTouched
                    this.originPos = touch.getLocation()
                    this.originChosenBuildingOzt = building.originOztPoint;
                    thisMap._handleBuildingWasClicked(building)
                } else if (SingleInstance.mapController.isBuilderHut(thisMap.blockTouched)) {

                } else {
                    thisMap._removeChosenBuildingIfHas()
                }
            }
        })
        cc.eventManager.addListener(event, this)
    },

    addMoveChosenBuildingEvent: function () {
        let thisMap = this
        let event = cc.EventListener.create({
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            onTouchBegan: function (touch, event) {
                if (thisMap.blockTouched !== undefined &&
                    thisMap.blockTouched === thisMap.chosenBuilding) {
                    this.originPos = touch.getLocation()
                    this.originChosenBuildingOzt = thisMap.blockTouched.originOztPoint;
                    return true
                }
                return false
            },
            onTouchMoved: function (touch, event) {
                let building = thisMap.chosenBuilding
                // move building
                let currentPos = touch.getLocation()
                let oxyPoint = mapPositionToOxy(cc.p(
                    (currentPos.x - this.originPos.x) / scale.x,
                    (currentPos.y - this.originPos.y) / scale.y
                ))
                let oztPoint = getOztPointFromOxy(oxyPoint)
                let oztIntPoint = toIntegerPoint(oztPoint)
                building.setOztPoint(addPoints(
                    this.originChosenBuildingOzt,
                    oztIntPoint
                ))
                building.updatePosition(CHOSEN_BUILDING_ORDER)
                // show red or green
                if (SingleInstance.mapController.collideWithOtherBuildings(building)) {
                    building.removeGreenBgIfHas()
                    building.addRedBgIfNotHas()
                    building.isInWrongPosition = true
                } else {
                    building.removeRedBgIfHas()
                    building.addGreenBgIfNotHas()
                    building.isInWrongPosition = undefined
                }
                return true;
            },
            onTouchEnded: function () {
                if (thisMap.chosenBuilding.isInWrongPosition) {
                    thisMap.chosenBuilding.setOztPoint(this.originChosenBuildingOzt)
                    thisMap.chosenBuilding.removeRedBgIfHas()
                } else {
                    SingleInstance.map.removeFlag(
                        thisMap.chosenBuilding,
                        this.originChosenBuildingOzt
                    )
                    SingleInstance.map._addFlag(
                        thisMap.chosenBuilding,
                        thisMap.chosenBuilding.originOztPoint
                    )
                }
                thisMap.chosenBuilding.removeGreenBgIfHas()
                thisMap.chosenBuilding.updatePosition()
            }
        })
        cc.eventManager.addListener(event, this)
    },

    addTouchToObstacleEvent: function () {
        let thisMap = this
        let event = cc.EventListener.create({
            event: cc.EventListener.TOUCH_ONE_BY_ONE,
            onTouchBegan: function (touch, event) {
                return true
            },
            onTouchMoved: function (touch, event) {
                return true
            },
            onTouchEnded: function () {
                if (thisMap.justMoved) return false;
                if (SingleInstance.mapController.isObstacle(thisMap.blockTouched)) {
                    thisMap._handleObstacleWasClicked(thisMap.blockTouched)
                } else {
                    thisMap._removeChosenObstacleIfHas()
                }
            }
        })
        cc.eventManager.addListener(event, this)
    },


    _handleBuildingWasClicked: function (building) {
        if (this.chosenBuilding !== undefined && this.chosenBuilding === building) {
            return
        }
        this._removeChosenBuildingIfHas()
        this._addChosenBuilding(building)
    },

    _removeChosenBuildingIfHas: function () {
        if (this.chosenBuilding !== undefined) {
            this.chosenBuilding.removeArrow()
            this.chosenBuilding = undefined;
        }
    },

    _addChosenBuilding: function (building) {
        building.addArrow()
        this.chosenBuilding = building
    },

    _handleObstacleWasClicked: function (obstacle) {
        if (this.chosenObstacle !== undefined && this.chosenObstacle === obstacle) {
            return
        }
        this._removeChosenObstacleIfHas()
        obstacle.addArrow()
        this.chosenObstacle = obstacle
    },

    _removeChosenObstacleIfHas: function () {
        if (this.chosenObstacle !== undefined) {
            this.chosenObstacle.removeArrow()
            this.chosenObstacle = undefined;
        }
    },


    _adjustToVisiblePosition: function (posX, posY) {
        return [posX, posY]
    },

    _initVisibleXY: function () {
        return [
            MAP_SIZE * GIRD_WIDTH,
            0,
            HALF_SIZE * GIRD_HEIGHT,
            -HALF_SIZE * GIRD_HEIGHT
        ]
    },

    addGirds: function () {
        const MIN_X = 0;
        // Khoi tao phan tren phia trai
        for (let x = MIN_X; x < HALF_SIZE + MIN_X; x++) {
            for (let y = 1; y <= x + 1; y++) {
                this.addChild(new Gird(x, y), GIRD_ORDER)
            }
        }
        // Khoi tao phan tren phia phai
        for (let x = MIN_X; x < HALF_SIZE + MIN_X; x++) {
            for (let y = HALF_SIZE - x; y >= 1; y--) {
                this.addChild(new Gird(x + HALF_SIZE, y), GIRD_ORDER)
            }
        }
        // Khoi tao hang so 0
        for (let x = MIN_X; x < HALF_SIZE * 2 + MIN_X; x++) {
            this.addChild(new Gird(x, 0), GIRD_ORDER)
        }
        // Khoi tao phan duoi phia trai
        for (let x = MIN_X; x < HALF_SIZE + MIN_X; x++) {
            for (let y = 1; y <= x + 1; y++) {
                this.addChild(new Gird(x, -y), GIRD_ORDER)
            }
        }
        // Khoi tao phan duoi phia phai
        for (let x = MIN_X; x < HALF_SIZE + MIN_X; x++) {
            for (let y = HALF_SIZE - x; y >= 1; y--) {
                this.addChild(new Gird(x + HALF_SIZE, -y), GIRD_ORDER)
            }
        }
    },

    addEdges: function () {
        let edgeScale = {x: 2, y: 2}
        this.addTopLeftEdge(edgeScale, EDGE_ORDER)
        this.addTopRightEdge(edgeScale, EDGE_ORDER)
        this.addBottomLeftEdge(edgeScale, EDGE_ORDER)
        this.addBottomRightEdge(edgeScale, EDGE_ORDER)
    },

    addTopLeftEdge: function (scale, order) {
        let topLeftEdge = new cc.Sprite(res.map.topLeftEdge);
        topLeftEdge._setAnchorX(1)
        topLeftEdge._setAnchorY(0)
        topLeftEdge.setPosition(HALF_SIZE * GIRD_WIDTH, 0);
        topLeftEdge.setScale(scale.x, scale.y);
        this.addChild(topLeftEdge, order);
    },

    addTopRightEdge: function (scale, order) {
        let topRightEdge = new cc.Sprite(res.map.topRightEdge);
        topRightEdge._setAnchorX(0)
        topRightEdge._setAnchorY(0)
        topRightEdge.setPosition(HALF_SIZE * GIRD_WIDTH, -2);
        topRightEdge.setScale(scale.x, scale.y);
        this.addChild(topRightEdge, order);
    },

    addBottomLeftEdge: function (scale, order) {
        let bottomLeftEdge = new cc.Sprite(res.map.bottomLeftEdge);
        bottomLeftEdge._setAnchorX(1)
        bottomLeftEdge._setAnchorY(1)
        bottomLeftEdge.setPosition(HALF_SIZE * GIRD_WIDTH, 0);
        bottomLeftEdge.setScale(scale.x, scale.y);
        this.addChild(bottomLeftEdge, order);
    },

    addBottomRightEdge: function (scale, order) {
        let bottomRightEdge = new cc.Sprite(res.map.bottomRightEdge);
        bottomRightEdge._setAnchorX(0)
        bottomRightEdge._setAnchorY(1)
        bottomRightEdge.setPosition(HALF_SIZE * GIRD_WIDTH, -2);
        bottomRightEdge.setScale(scale.x, scale.y);
        this.addChild(bottomRightEdge, order);
    }
})


let Gird = cc.Sprite.extend({
    ctor: function (x, y) {
        this._super(res.map.gird, res.map.girdRect)
        this._setAnchorY(0.5);
        this._setAnchorX(0);
        // Toa do tren truc Oxy
        this.setOxyCoordinate(x, y)
    },

    setOxyCoordinate: function (x, y) {
        let position = oxyToMapPosition(cc.p(x, y))
        this.setPosition(position);
    }
})


let MapScene = cc.Scene.extend({
    ctor: function () {
        this._super()
        let mapLayer = new MapLayer()
        this.addChild(mapLayer)
    }
})

let MapTestScene = cc.Scene.extend({
    ctor: function () {
        this._super()
        SingleInstance.map.initBuildings(initMap.map);
        SingleInstance.map.initObstacles(initMap.obs);
        SingleInstance.user.resources[ResourceType.Gold] = initMap.player.gold;
        SingleInstance.user.resources[ResourceType.Elixir] = initMap.player.elixir;
        SingleInstance.user.resources[ResourceType.Gold] = initMap.player.gold;
        SingleInstance.user.G = initMap.player.coin;
        let mapLayer = new MapLayer()
        this.addChild(mapLayer, MAP_ORDER)

        let size = cc.director.getVisibleSize();
        var btnShop = gv.commonButton(200, 64, size.width - 120, 50, "Shop");
        this.addChild(btnShop);
        btnShop.addClickEventListener(this.onSelectShop.bind(this));

        let btnTrain = gv.commonButton(200, 64, 120, 50, "Train");
        this.addChild(btnTrain);
        btnTrain.addClickEventListener(this.onSelectTrain.bind(this));
    },
    onSelectShop: function () {
        let shop = new ShopScreen();
        this.addChild(shop, SHOP_ORDER);
    },
    onSelectTrain:function () {
        this.trainScreen = new ScreenTrainingTroop(2);
        this.addChild(this.trainScreen);
    }
})


// printOztCoord: function () {
//     let getMapLayerPosition = this.getMapLayerPosition.bind(this)
//     cc.eventManager.addListener(cc.EventListener.create({
//         event: cc.EventListener.TOUCH_ONE_BY_ONE,
//         onTouchBegan: function (touch, event) {
//             let inMapPosition = getMapLayerPosition(touch.getLocation())
//             cc.log('MapLayer: '
//                 + inMapPosition.x
//                 + ' '
//                 + inMapPosition.y)
//             let oxyPosition = mapPositionToOxy(inMapPosition)
//             let oztPosition = getOztPointFromOxy(oxyPosition)
//             cc.log('Ozt: '
//                 + Math.floor(oztPosition.x)
//                 + ' '
//                 + Math.floor(oztPosition.y));
//             return true
//         }
//     }), this)
// },