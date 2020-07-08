/**
 * Created by GSN on 6/2/2015.
 */


var res = {
    //font
    FONT_BITMAP_NUMBER_1: "fonts/number_1.fnt",
    FONT_BITMAP_DICE_NUMBER: "fonts/diceNumber.fnt",
    //zcsd
    //screen
    ZCSD_SCREEN_MENU: "zcsd/screen_menu.json",
    ZCSD_SCREEN_NETWORK: "zcsd/screen_network.json",
    ZCSD_SCREEN_LOCALIZATION: "zcsd/screen_localize.json",
    ZCSD_SCREEN_DRAGON_BONES: "zcsd/screen_dragon_bones.json",
    ZCSD_SCREEN_DECRYPTION: "zcsd/screen_decryption.json",
    ZCSD_SCREEN_ZALO: "zcsd/screen_zalo.json",
    //popup
    ZCSD_POPUP_MINI_GAME: "zcsd/game/mini_game/PopupMiniGame.json",
    //images
    Slot1_png: "zcsd/slot1.png",
    //map
    map: {
        gird: "res/content/Art/Map/gird.png",
        girdRect: cc.rect(0, 0, 76, 57),
        topLeftEdge: "res/content/Art/Map/1_0000_Layer-3.png",
        topRightEdge: "res/content/Art/Map/1_0002_Layer-4.png",
        bottomLeftEdge: "res/content/Art/Map/1_0001_Layer-1.png",
        bottomRightEdge: "res/content/Art/Map/1_0003_Layer-2.png",
        grass_0_2_obs: "res/content/Art/Map/map_obj_bg/GRASS_0_2_OBS.png",
        grass_0_3_obs: "res/content/Art/Map/map_obj_bg/GRASS_0_3_OBS.png",
        getGrassBg: function (size) {
            return "res/content/Art/Map/map_obj_bg/BG_0/" + size + ".png";
        },
        getArrowBg: function (size) {
            return "res/content/Art/Map/map_obj_bg/BG/arrowmove" + size + ".png"
        },
        getGreenBg: function (size) {
            return "res/content/Art/Map/map_obj_bg/BG/GREEN_" + size + ".png"
        },
        getRedBg: function (size) {
            return "res/content/Art/Map/map_obj_bg/BG/RED_" + size + ".png"
        },
    },

    //images
    shopGui : "ccs/Shop/res/ShopGui.json",
    shopSlot : "ccs/Shop/res/shopSlot.json",
    builderHut: "res/content/Art/Buildings/builder hut/idle/image0000.png",
    shop_icon_g: "ccs/Shop/res/shop_gui/icon_g_bar.png",
    shop_icon_gold: "ccs/Shop/res/shop_gui/icon_gold_bar.png",
    shop_icon_elixir: "ccs/Shop/res/shop_gui/icon_elixir_bar.png",
    shop_icon_dElixir: "ccs/Shop/res/shop_gui/icon_dElixir_bar.png",


};


var g_resources = [
    //map
    res.map.gird,
    res.map.grass_0_3_obs,

];

const trainingRes = {
    //TrainingIcon
    trainingGui: "ccs/Training/TrainingScreen.json",

    //TrainingSlot
    trainingSlot: "ccs/Training/trooperTrainingSlot.json",

    //TrainingSlotSmall
    trainingSlotSmall: "ccs/Training/trooperTrainingSlotSmall.json",

};
