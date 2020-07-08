let User = cc.Class.extend({
    singleton: undefined,
    resources: {},
    G: 0,
    troops: {},

    getInstance: function () {
        if (this.singleton === undefined) {
            this.singleton = new User()
        }
        return this.singleton
    },

})

SingleInstance.user = new User()