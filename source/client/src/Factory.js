let Creator = {
    makeBuildingSprite: function (type, oztPoint, level) {
        switch (type) {
            case BuildingType.TownHall:
                return new TownHall(oztPoint, level)
            case BuildingType.GoldMine:
                return new GoldMine(oztPoint,level)
            case BuildingType.ClanCastle:
                return new ClanCastle(oztPoint,level)
            case BuildingType.ArmyCamp:
                return new ArmyCamp(oztPoint, level)
            default:
                throw "Building Type was wrong: " + type
        }
    }
}