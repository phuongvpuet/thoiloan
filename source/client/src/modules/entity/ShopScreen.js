
const shop_icon = {
    gold: 0,
    elixir: 1,
    dElixir: 2,
    g: 3
}

const ShopScreen = cc.Layer.extend({
    itemTableView:null,
    ctor:function () {
        this._super();
        this.init();
    },

    onEnter:function(){
        this._super();
        openUI(this);
    },

    init:function () {
        const jsonShopGUI = ccs.load(res.shopGui);
        const shopNode = jsonShopGUI.node;
        this.addChild(shopNode);

        //categories screen
        this.categoricalPanel = shopNode.getChildByName("categorical_panel");

        //buy items screen
        this.itemPanel = shopNode.getChildByName("item_panel");
        this.itemPanel.setVisible(false);

        //Table view in Buy Items Screen
        this.tableViewContainer = shopNode.getChildByName("table_panel");
        this.tableViewContainer.setVisible(false);

        //Close button
        let close = shopNode.getChildByName("close");
        close = this.addEffectBtn(close);
        close.addClickEventListener(this.onSelectClose.bind(this));

        //Shop Title

        this.shopTitle = shopNode.getChildByName("shop_title");


        //Back button
        this.back = shopNode.getChildByName("back");
        this.back = this.addEffectBtn(this.back);
        this.back.setVisible(false);
        this.back.addClickEventListener(this.onSelectBack.bind(this));

        //Categories Screen

        let res_ = this.categoricalPanel.getChildByName("res")
        let res_button = res_.getChildByName("res_btn");
        res_button = this.addEffectBtn(res_button);
        res_button.addClickEventListener(this.onSelectRes.bind(this));

        let dc = this.categoricalPanel.getChildByName("dc");
        let dc_button = dc.getChildByName("dc_btn");
        dc_button = this.addEffectBtn(dc_button);
        dc_button.addClickEventListener(this.onSelectDc.bind(this));

        let army = this.categoricalPanel.getChildByName("army");
        let army_button = army.getChildByName("army_btn");
        army_button = this.addEffectBtn(army_button);
        army_button.addClickEventListener(this.onSelectArmy.bind(this));

        let defense = this.categoricalPanel.getChildByName("defense");
        let defense_button = defense.getChildByName("defense_btn");
        defense_button = this.addEffectBtn(defense_button);
        defense_button.addClickEventListener(this.onSelectDefense.bind(this));

        let shield = this.categoricalPanel.getChildByName("shield");
        let shield_button = shield.getChildByName("shield_btn");
        shield_button = this.addEffectBtn(shield_button);
        shield_button.addClickEventListener(this.onSelectShield.bind(this));

        let buy_res = this.categoricalPanel.getChildByName("buy_res");
        let buy_res_button = buy_res.getChildByName("buy_res_btn");
        buy_res_button = this.addEffectBtn(buy_res_button);
        buy_res_button.addClickEventListener(this.onSelectBuyRes.bind(this));



        let user_res = this.itemPanel.getChildByName("user_res");

        this.addTouchListener();
    },

    hide_categories:function(){
        this.categoricalPanel.setVisible(false);
        this.itemPanel.setVisible(true);
        this.back.setVisible(true)
        this.createTableView();
    },

    addTouchListener:function(){
        cc.eventManager.addListener({
            event:cc.EventListener.TOUCH_ONE_BY_ONE,
            swallowTouches: true,
            onTouchBegan: function () {
                return true;
            }
        }, this);
    },

    createTableView:function(){
        this.itemTableView = new ShopTableViewLayer(10, this.tableViewContainer);
        this.addChild(this.itemTableView);
    },

    onSelectRes:function(){
        this.shopTitle.setString("TÀI NGUYÊN");
        this.hide_categories();
        cc.log("Choose Res");
    },

    onSelectDc:function(){
        this.shopTitle.setString("TRANG TRÍ");
        this.hide_categories();
        cc.log("Choose Dc");
    },

    onSelectArmy:function(){
        this.shopTitle.setString("QUÂN ĐỘI");
        this.hide_categories();
        cc.log("Choose Army");
    },

    onSelectDefense:function(){
        this.shopTitle.setString("PHÒNG THỦ");
        this.hide_categories();
        cc.log("Choose Defense");
    },

    onSelectShield:function(){
        this.shopTitle.setString("BẢO VỆ");
        this.hide_categories();
        cc.log("Choose Shield");
    },

    onSelectBuyRes:function(){
        this.shopTitle.setString("NGÂN KHỐ");
        this.hide_categories();
        cc.log("Choose Buy Res");
    },

    addEffectBtn:function(btn){
        btn.setPressedActionEnabled(true);
        btn.setScale9Enabled(true);
        btn.setUnifySizeEnabled(false);
        btn.ignoreContentAdaptWithSize(false);
        return btn;
    },

    onSelectClose:function () {
        closeUI(this);
    },

    onSelectBack:function () {
        this.shopTitle.setString("CỬA HÀNG");
        this.itemPanel.setVisible(false);
        this.itemTableView.removeFromParent(true);
        this.back.setVisible(false);
        this.categoricalPanel.setVisible(true);
    }
});

