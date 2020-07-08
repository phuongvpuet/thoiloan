
const ShopTableViewLayer = cc.Layer.extend({
    ctor:function (size, panel) {
        this._super();
        this.size = size;
        this.attr({
            x: panel.x,
            y: panel.y,
            width: panel.width,
            height: panel.height
        })
        this.init();
    },

    init:function () {
        const tableView = new cc.TableView(this, cc.size(this.width, this.height));
        tableView.setDirection(cc.SCROLLVIEW_DIRECTION_HORIZONTAL);
        this.cellWidth = 226;
        this.cellHeight = 325;
        tableView.setDelegate(this);
        this.addChild(tableView);
        tableView.reloadData();

        return true;
    },

    scrollViewDidScroll:function (view) {

    },
    scrollViewDidZoom:function (view) {

    },

    tableCellTouched:function (table, cell) {

        //cc.log("cell touched at index: " + cell.getIdx());
    },

    tableCellSizeForIndex:function (table, idx) {
        return cc.size(this.cellWidth, this.cellHeight);
    },

    tableCellAtIndex:function (table, idx) {
        let cell = table.dequeueCell();
        if (!cell) {
            cell = new ShopTableViewCellCustom();
            cell.setProperty();
        }
        return cell;
    },

    numberOfCellsInTableView:function (table) {
        return this.size;
    }
});