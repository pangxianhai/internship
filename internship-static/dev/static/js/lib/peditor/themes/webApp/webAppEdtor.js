define(function (require, exports, module) {
    var pEditor = require('../../peditor');
    var css = require('css/peditor.css', 'css');
    var modules = [];
    var pcEditor = {
        loadModule: function () {
            // modules.push(require('module/fontFamily/fontFamily.js'));
            // modules.push(require('module/fontSize/fontSize.js'));
        },
        bind: function (id, options) {
            pEditor.bind(id, options);
            pEditor.appendToolbarHtml(id, modules);
        },
        init: function () {
            $('head').append('<style type="text/css">' + css + '</style>');
            this.loadModule();
        }
    };
    pcEditor.init();
    return pcEditor;
});