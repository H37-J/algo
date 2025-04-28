
(function (global, factory) {

    "use strict";

    if (typeof module === "object" && typeof module.exports === "object") {
        module.exports = global.document ?
            factory(global, true) :
            function (w) {
                if (!w.document) {
                    throw new Error("jQuery requires a window with a document");
                }
                return factory(w);
            };
    } else {
        factory(global);
    }

})(typeof window !== "undefined" ? window : this, function (window, noGlobal) {
    const document = window.document;

    sessionStorage.setItem('user', JSON.stringify({
        id: 1,
        name: 'hjk'
    }))


    const custom = {
        test: () => {
            console.log('test')
        },

        setSessionItem: (key, value) => {
            sessionStorage.setItem(key, JSON.stringify(value))
        },

        getSessionItem: (key) => {
            return JSON.parse(sessionStorage.getItem(key))
        }
    }

    const data = {
        'user' : 'name'
    }

    custom.setSessionItem('user', data)

    const item = custom.getSessionItem('user')
    console.log(item)


    window.custom = custom;

    return custom;
})
