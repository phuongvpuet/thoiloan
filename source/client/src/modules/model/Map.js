let Map = cc.Class.extend({
    mapCoordinateAxis: [],
    buildings: [],
    obstacles: [],

    ctor: function () {
        for (let x = 0; x < MAP_SIZE; x++) {
            this.mapCoordinateAxis[x] = []
            for (let y = 0; y < MAP_SIZE; y++) {
                this.mapCoordinateAxis[x][y] = EMPTY_CELL
            }
        }
    },

    getBuildings: function () {
        return this.buildings
    },

    getObstacles: function () {
        return this.obstacles
    },

    getBlock: function (x, y) {
        if (x >= MAP_SIZE) return EMPTY_CELL
        if (x < 0) return EMPTY_CELL
        if (y >= MAP_SIZE) return EMPTY_CELL
        if (y < 0) return EMPTY_CELL
        return this.mapCoordinateAxis[x][y]
    },

    getMapCoordinateAxis: function () {
        return this.mapCoordinateAxis
    },

    initBuildings: function (buildings) {
        for (let buildingData of buildings) {
            let block;
            if (buildingData.type === BuildingType.BuilderHut) {
                block = new BuilderHut(
                    cc.p(buildingData.posX, buildingData.posY),
                    1
                )
            } else {
                block = Creator.makeBuildingSprite(
                    buildingData.type,
                    cc.p(buildingData.posX, buildingData.posY),
                    buildingData.level
                )
            }
            this.addBuilding(block)
        }
    },

    initObstacles: function (obstacles) {
        for (let key in obstacles) {
            let obstacleData = obstacles[key]
            let block = new Obstacle(
                obstacleData.type,
                cc.p(obstacleData.posX, obstacleData.posY)
            )
            this.obstacles.push(block)
            this._addBlock(block)
        }
    },

    _addBlock: function (block) {
        if (block.getInfo === undefined) throw "Block must have getInfo operation"
        if (block.originOztPoint === undefined) throw "Block must has originOztPoint property"
        this._addFlag(block, block.originOztPoint)
    },

    addBuilding: function (building) {
        this.buildings.push(building)
        this._addFlag(building, building.originOztPoint)
    },

    _addFlag: function (block, originOztPoint) {
        let width = block.getInfo()["width"]
        let height = block.getInfo()["height"]
        for (let x = 0; x < width; x++) {
            for (let y = 0; y < height; y++) {
                this.mapCoordinateAxis
                    [originOztPoint.x + x]
                    [originOztPoint.y + y] = block
            }
        }
    },

    // remove cac gia tri con tro cua building trong he toa do 40x40 cua map
    removeFlag: function (block, originOztPoint) {
        let width = block.getInfo()["width"]
        let height = block.getInfo()["height"]
        for (let x = 0; x < width; x++) {
            for (let y = 0; y < height; y++) {
                this.mapCoordinateAxis
                    [originOztPoint.x + x]
                    [originOztPoint.y + y] = EMPTY_CELL
            }
        }
    },
})

SingleInstance.map = new Map()