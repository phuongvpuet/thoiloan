const BuildingType = {
    TownHall: "TOW_1",
    ArmyCamp: "AMC_1",
    Barrack: "BAR_1",
    XMen: "BAR_2",
    BuilderHut: "BDH_1",
    GoldWareHouse: "STO_1",
    ElixirWareHouse: "STO_2",
    DarkElixirWareHouse: "STO_3",
    GoldMine: "RES_1",
    ClanCastle: "CLC_1",
    Default: -1,
}

const ResourceType = {
    Gold: 1,
    Elixir: 2,
    DarkElixir: 3,
}


const sourceFolderName = {}
sourceFolderName[BuildingType.TownHall] = 'townhall'
sourceFolderName[BuildingType.ArmyCamp] = 'army camp'
sourceFolderName[BuildingType.BuilderHut] = 'builder hut'
sourceFolderName[BuildingType.Barrack] = 'barrack'
sourceFolderName[BuildingType.GoldMine] = 'gold mine'
sourceFolderName[BuildingType.ClanCastle] = 'clan_castle'

const ObstacleTypePrefix = "OBS_"

let SingleInstance = {}
const MAP_ORDER = 0;
const SHOP_ORDER = 1;
const INFO_ORDER = 2;

const MAP_SIZE = 40
const HALF_SIZE = MAP_SIZE / 2
const GIRD_WIDTH = res.map.girdRect.width //76
const GIRD_HEIGHT = res.map.girdRect.height //57
const GIRD_ORDER = 0
const MIN_BUILDING_ORDER = 20
const GRASS_ORDER = -1
const EDGE_ORDER = 1
const EMPTY_CELL = null
const CHOSEN_BUILDING_ORDER = 50
const MINUTE = 60;
const HOUR = 3600;
const DAY = 86400;

var barrackInfo = barrackInfo || {};

let testTrain = {
    timeLeft: 5,
    queue:[
        {
            trooperId: 1,
            length: 2
        },
        {
            trooperId: 3,
            length: 10,
        }
    ]
}

barrackInfo["2"] = testTrain;


const army = {
    WARRIOR: 0,
    ARCHER: 0,
    GIANT: 0,
    FLYING_BOMB: 0
}

