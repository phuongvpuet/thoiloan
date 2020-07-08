let MapHelper = {};

let oxyToMapPosition = function (oxyPoint) {
    return cc.p(oxyPoint.x * GIRD_WIDTH, oxyPoint.y * GIRD_HEIGHT)
}

let mapPositionToOxy = function (inMapPosition) {
    return cc.p((inMapPosition.x / GIRD_WIDTH),
        (inMapPosition.y / GIRD_HEIGHT))
}

let getOxyPointFromOzt = function (oztPoint) {
    return cc.p((oztPoint.x + oztPoint.y) / 2,
        (oztPoint.y - oztPoint.x) / 2
    );
}

let getOztPointFromOxy = function (oxyPoint) {
    return cc.p(oxyPoint.x - oxyPoint.y,
        oxyPoint.x + oxyPoint.y)
}

let toIntegerPoint = function (point) {
    return cc.p(Math.floor(point.x), Math.floor(point.y))
}

// sourcePath la duong dan den thu muc me chua file hinh anh cua cong trinh */
let getBuildingResource = function (type, level) {
    let sourcePath = getSourcePath(sourceFolderName[type]);
    return (sourcePath + "/" + type + "_" + level + '/idle/image0000.png')
}

let getObstacleResource = function (type) {
    let sourcePath = getSourcePath('obstacle');
    return (sourcePath + "/" + type + '/idle/image0000.png')
}

// buildingFolderName: ten folder me cua cong trinh
let getSourcePath = function (buildingFolderName) {
    return 'res/content/Art/Buildings/' + buildingFolderName
}

let dump_log = function (object) {
    let string = ''
    for (let key in object) {
        string += key + ": " + object[key] + '\n'
    }
    cc.log(string)
}

let equalPoints = function (point1, point2) {
    return (
        point1.x === point2.x && point1.y === point2.y
    )
}

let addPoints = function (point1, point2) {
    return cc.p(point1.x + point2.x, point1.y + point2.y)
}

function addEffectBtn(btn){
    btn.setPressedActionEnabled(true);
    btn.setScale9Enabled(true);
    btn.setUnifySizeEnabled(false);
    btn.ignoreContentAdaptWithSize(false);
    return btn;
}

function convertSec(sec) {
    if (sec < MINUTE) return sec + "s";
    if (sec < HOUR) return Math.floor(sec / MINUTE) + "m" + this.convertSec(sec % MINUTE);
    if (sec < DAY) return Math.floor(sec / HOUR) + "h" + this.convertSec(sec % HOUR).slice(0, this.convertSec(sec % HOUR).indexOf("m") + 1);
    return Math.floor(sec / DAY) + "d" + this.convertSec(sec % DAY).slice(0, this.convertSec(sec % HOUR).indexOf("h") + 1);
}

function closeUI(obj) {
    obj.runAction(cc.sequence(
        cc.scaleTo(0.1, 1.25),
        cc.scaleTo(0.1, 1),
        cc.callFunc(()=>{obj.removeFromParent(true); obj.retain();}, obj)
    ));
}

function openUI(obj) {
    obj.runAction(cc.sequence(
        cc.scaleTo(0.2, 1.25),
        cc.scaleTo(0.1, 1)
    ));
}

function updateArmy(receive) {
    if (receive){
        for (let id in receive.army){
            army[trooperUtils.fromIdToName(Number(id))] = receive.army[id];
        }
        cc.log("ARMY from server: ", JSON.stringify(army));
    }
    else cc.log("ARMY: ", JSON.stringify(army));
}

function updateBarrack(barrack) {
    if (barrack){
        for (let id in barrack.queue){
            barrackInfo[id] = barrack.queue[id];
        }
        cc.log("Barrack Updated");
    }
}