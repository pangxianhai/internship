define(function (require, exports, module) {
    require('../date');
    var defaultOptions = require('peditorConfig');
    var _editor = {};
    var pEditor = {
        bind: function (id, options) {
            _editor[id] = $.extend(defaultOptions, options);
            this.appendHtml(id);
        },
        appendHtml: function (id) {
            var edNode = $('#' + id);
            var html = [];
            html.push('<div class="ui_ed_toolbar"></div>');
            html.push('<div class="ui_ed_body"></div>');
            edNode.html(html.join(''));
            this.initStyle(edNode);
            this.bindEditorAction(edNode);
        },
        appendToolbarHtml: function (id, modules) {
            var edToolbarNode = $('#' + id).find('.ui_ed_toolbar');
            var options = _editor[id];
            for (var i = 0; i < options.toolbar.length; ++i) {
                var toolbarMode = modules[options.toolbar[i]];
                if (typeof toolbarMode != 'undefined') {
                    edToolbarNode.append(toolbarMode.buildHtml(id));
                    if (typeof  toolbarMode.moduleSelectAction == 'function') {
                        toolbarMode.moduleSelectAction(id, edToolbarNode, $('#' + id).find('.ui_ed_body'));
                    }
                }
            }
        },
        initStyle: function (edNode) {
            edNode.addClass('ui_ed_container');
            var options = _editor[edNode.attr('id')];
            edNode.css('width', options.width);
            edNode.css('min-height', options.height);
            var edBodyNode = edNode.find('.ui_ed_body');
            var edToolbarNode = edNode.find('.ui_ed_toolbar');
            edBodyNode.css('min-height', (edNode.height() - edToolbarNode.height()) + 'px');
        },
        bindEditorAction: function (edNode) {
            var edBodyNode = edNode.find('.ui_ed_body');
            // edBodyNode[0].attr('contentEditable', 'true');
            var ed = edBodyNode[0];//.contentWindow.document; // IE、FF都兼容
            ed.designMode = "on";
            ed.contentEditable = true;
            // ed.open();
            // ed.close();
            edBodyNode.unbind('click');
            edBodyNode.bind('click', function () {
                // document.execCommand('formatblock', false, '<p>');
            })
        }
    };
    return pEditor;
});