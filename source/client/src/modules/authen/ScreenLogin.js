/**
 * Created by Fresher_LOCAL on 6/18/2020.
 */

LOGO_MARGIN = 10;
EDITBOX_WIDTH = 200;
EDITBOX_HEIGHT = 50;
MAX_USERNAME_LENGTH = 16;

BG_Z_ORDER = 0;
LOGO_Z_ORDER = 1;
EDITBOX_Z_ORDER = 10;

LOGIN_BTN_TITLE = "LOG IN";
LOGIN_BTN_FONT_SIZE = 26;
LOGIN_BTN_MARGIN_LEFT = 16;

var ScreenLogin = cc.Layer.extend({
    userIdEditbox: null,
    ctor:function () {
        this._super();

        var winSize = cc.director.getWinSize();
        this.initBackground(winSize);
        this.initUsernameEditBox(winSize);
    },
    initBackground: function(winSize) {
        var bg = new cc.Sprite(res.authen.loading);
        var bgSize = bg.getContentSize();
        bg.attr({
            anchorX: 0,
            anchorY: 0,
            scaleX: winSize.width/bgSize.width,
            scaleY: winSize.height/bgSize.height
        });
        this.addChild(bg, BG_Z_ORDER);

        var logo = new cc.Sprite(res.authen.logo);
        var logoSize = logo.getContentSize();
        logo.x = logoSize.width/2 + LOGO_MARGIN;
        logo.y = winSize.height - logoSize.height/2 - LOGO_MARGIN;
        this.addChild(logo, LOGO_Z_ORDER);
    },
    initUsernameEditBox: function(winSize) {
        var usernameEditBox = cc.EditBox.create(cc.size(EDITBOX_WIDTH, EDITBOX_HEIGHT), new cc.Scale9Sprite(res.authen.g_background));
        usernameEditBox.x = winSize.width/2;
        usernameEditBox.y = winSize.height/4;
        usernameEditBox.maxLength = MAX_USERNAME_LENGTH;
        this.addChild(usernameEditBox, EDITBOX_Z_ORDER);
        this.userIdEditbox = usernameEditBox;

        var loginButton = new ccui.Button(res.authen.ok_btn);
        loginButton.x = winSize.width/2 + loginButton.getContentSize().width/2 + usernameEditBox.getContentSize().width/2 + 16;
        loginButton.y = winSize.height/4;
        loginButton.setTitleText(LOGIN_BTN_TITLE);
        loginButton.setTitleFontSize(LOGIN_BTN_FONT_SIZE);
        loginButton.setPressedActionEnabled(true);

        loginButton.setScale9Enabled(true);
        loginButton.setUnifySizeEnabled(false);
        loginButton.ignoreContentAdaptWithSize(false);
        loginButton.addClickEventListener(this.onSelectLogin.bind(this));
        this.addChild(loginButton, EDITBOX_Z_ORDER);
    },
    onSelectLogin: function() {
        if (this.userIdEditbox.string.length > 0) {
            if (!isNaN(this.userIdEditbox.string)) {
                cc.log("login");
                gv.gameClient.connect();
            } else {
                cc.log("user id wrong format");
            }
        } else {
            cc.log("empty user id");
        }
    },
    onFinishLogin: function() {
        cc.log("login success");

    },
    onConnectFail: function() {
        cc.log("connect fail");
    },
    onConnectSuccess: function() {
        cc.log("connect success");
    },
    getUserIdInput: function() {
        return parseInt(this.userIdEditbox.string);
    },
    onGetUserInfoSuccess: function() {
        cc.log("get user info success");
        SingleInstance.map.initBuildings(initMap.map);
        SingleInstance.map.initObstacles(initMap.obs);
        SingleInstance.user.resources[ResourceType.Gold] = initMap.player.gold;
        SingleInstance.user.resources[ResourceType.Elixir] = initMap.player.elixir;
        SingleInstance.user.resources[ResourceType.Gold] = initMap.player.gold;
        SingleInstance.user.G = initMap.player.coin;
        fr.view(MapScene);
    }
});
