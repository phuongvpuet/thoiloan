let MapController = cc.Class.extend({
    lastOztPointRequest: cc.p(0, 0),
    lastBuildingResult: null,

    ctor: function () {

    },

    hasBuilding: function (oztIntPoint) {
        let building = this.getBuilding(oztIntPoint)
        return (building != null)
    },

    isBuilding: function (block) {
        return block && block.buildingType !== undefined
    },

    isBuilderHut: function (block) {
        return (this.isBuilding(block) && block.buildingType === BuildingType.BuilderHut)
    },

    isObstacle: function (block) {
        return block && block.obstacleType !== undefined
    },

    getBuilding: function (oztIntPoint) {
        if (equalPoints(this.lastOztPointRequest, oztIntPoint)) {
            return this.lastBuildingResult
        } else {
            this.lastOztPointRequest = oztIntPoint
            let result = SingleInstance.map.mapCoordinateAxis[oztIntPoint.x][oztIntPoint.y]
            if (result === EMPTY_CELL || result.buildingType === undefined/*obstacle*/) {
                result = null
            }
            this.lastBuildingResult = result
            return result
        }
    },

    collideWithOtherBuildings: function (building) {
        let width = building.getInfo()["width"]
        let height = building.getInfo()["height"]
        for (let x = 0; x < width; x++) {
            for (let y = 0; y < height; y++) {
                let cellValue = SingleInstance.map.mapCoordinateAxis
                    [building.originOztPoint.x + x]
                    [building.originOztPoint.y + y]
                if (cellValue !== EMPTY_CELL && cellValue !== building)
                    return true
            }
        }
        return false;
    }
})

SingleInstance.mapController = new MapController();