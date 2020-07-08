const ShopTableViewCellCustom = cc.TableViewCell.extend({
    info:null,
    infoBtn:null,
    require:null,
    cost:null,
    money_type:null,
    icon:null,
    buyBtn:null,
    ctor:function(){
        this._super();
        this.node = ccs.load(res.shopSlot).node;
        this.addChild(this.node);
        //Name item
        this.itemName = this.node.getChildByName("title");

        //Info Button
        this.infoBtn = this.node.getChildByName("info");
        this.infoBtn = this.addEffectBtn(this.infoBtn);
        this.infoBtn.addClickEventListener(this.onSelectInfo.bind(this));

        this.node = this.node.getChildByName("slot");
        this.require = this.node.getChildByName("require");
        this.cost = this.node.getChildByName("cost");
        this.icon = this.node.getChildByName("icon");
        this.buyBtn = this.node.getChildByName("buy_btn");
        this.buyBtn = this.addEffectBtn(this.buyBtn);
        this.buyBtn.addClickEventListener(this.onSelectBuy.bind(this));

    },
    setProperty:function (user_props) {
        let props = null;
        if (user_props !== undefined) props = user_props;
        else props = {info:"info", name: "Nhà thợ xây", img:res.builderHut, cost:0, type:BuildingType.BuilderHut, money_type: 0,state:1, require:"nothing", time: 0 };
        this.info = props.info;
        this.itemName.setString(props.name);
        let sprite = new cc.Sprite(props.img);
        sprite.attr({x: this.node.width / 2, y: this.node.height / 2});
        this.node.addChild(sprite);

        this.require.setString(props.require);
        this.cost.setString(props.cost + "");
        this.type = props.type;

        if (props.state){
            this.require.setVisible(false);
            this.cost.setColor(cc.color("#FFFFFF"));
            this.buyBtn.setVisible(true);
        }
        else{
            this.require.setVisible(true);
            this.cost.setColor(cc.color("#FF0000"));
            this.buyBtn.setVisible(false);
        }
        let icon = null;
        switch (props.money_type) {
            case shop_icon.gold:
                icon = new cc.Sprite(res.shop_icon_gold);
                break;
            case shop_icon.elixir:
                icon = new cc.Sprite(res.shop_icon_elixir);
                break;
            case shop_icon.dElixir:
                icon = new cc.Sprite(res.shop_icon_dElixir);
                break;
            case shop_icon.g:
                icon = new cc.Sprite(res.shop_icon_g);
                break;
            default:
                icon = new cc.Sprite(res.shop_icon_gold);
                break;
        }
        icon.attr({x: this.icon.width / 2, y: this.icon.height / 2});
        this.icon.addChild(icon);
    },
    onSelectInfo:function () {
        cc.log(this.info);
    },
    onSelectBuy:function () {
        cc.log(this.type);
    },
    addEffectBtn:function(btn){
        btn.setPressedActionEnabled(true);
        btn.setScale9Enabled(true);
        btn.setUnifySizeEnabled(false);
        btn.ignoreContentAdaptWithSize(false);
        return btn;
    },
});