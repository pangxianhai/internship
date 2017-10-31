define(function (require, exports, module) {
    var fontSize = {
        init: function () {
            var css = require('fontSize.css', 'css');
            $('head').append('<style type="text/css">' + css + '</style>');
        },
        buildHtml: function (id) {
            var html = [];
            html.push('<div class="ui_ed_toolbar_font_size ui_ed_toolbar_item">大小');
            html.push('</div>');
            return html.join('');
        }
    };
    fontSize.init();
    return fontSize;
});