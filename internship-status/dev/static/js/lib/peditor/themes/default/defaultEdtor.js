define(function (require, exports, module) {
    var pEditor = require('../../peditor');
    var css = require('css/peditor.css', 'css');
    var modules = [];
    var boldModel = {
        buildHtml: function (id) {
            var html = [];
            html.push('<div class="ui_ed_toolbar_icon ui_ed_toolbar_item ui_ed_toolbar_bold">');
            html.push('</div>');
            return html.join('');
        }
    };
    var italicModel = {
        buildHtml: function (id) {
            var html = [];
            html.push('<div class="ui_ed_toolbar_icon ui_ed_toolbar_item ui_ed_toolbar_italic">');
            html.push('</div>');
            return html.join('');
        }
    };
    var underlineModel = {
        buildHtml: function (id) {
            var html = [];
            html.push('<div class="ui_ed_toolbar_icon ui_ed_toolbar_item ui_ed_toolbar_underline">');
            html.push('</div>');
            return html.join('');
        }
    };
    var foreColorModel = {
        buildHtml: function (id) {
            var html = [];
            html.push('<div class="ui_ed_toolbar_icon ui_ed_toolbar_item ui_ed_toolbar_fore_color">');
            html.push('</div>');
            return html.join('');
        }
    };
    var pcEditor = {
        loadModule: function () {
            modules.fontFamily = require('module/fontFamily/fontFamily.js');
            modules.fontSize = require('module/fontSize/fontSize.js');
            modules.bold = boldModel;
            modules.italic = italicModel;
            modules.underline = underlineModel;
            modules.foreColor = foreColorModel;
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