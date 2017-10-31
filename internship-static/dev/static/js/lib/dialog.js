define(function (require, exports, module) {
    var $ = require('../lib/jquery-1.11.3.js');
    var strings = require('../lib/strings.js');
    var css = require('../../css/dialog/dialog.css', 'css');
    var drag = {
        params: {
            left: 0,
            top: 0,
            currentX: 0,
            currentY: 0,
            move: false,
            yesNo: false,
            ok: ''
        },
        onmousedown: function (bar, target) {
            bar.mousedown(function (event) {
                drag.params.move = true;
                if (!event) {
                    event = window.event;
                    //防止IE文字选中
                    bar.onselectstart(function () {
                        return false;
                    });
                }
                var e = event;
                drag.params.currentX = e.clientX;
                drag.params.currentY = e.clientY;
                drag.params.left = target.css('left');
                drag.params.top = target.css('top');
            });
        },
        onmouseup: function (target) {
            document.onmouseup = function () {
                drag.params.move = false;
                if (target.css('left') !== "auto") {
                    drag.params.left = target.css('left');
                }
                if (target.css('top') !== "auto") {
                    drag.params.top = target.css('top');
                }
            };
        },
        onmousemove: function (target) {
            document.onmousemove = function (event) {
                var e = event ? event : window.event;
                if (drag.params.move) {
                    var nowX = e.clientX, nowY = e.clientY;
                    var disX = nowX - drag.params.currentX, disY = nowY - drag.params.currentY;
                    target.css('left', parseInt(drag.params.left) + disX + "px");
                    target.css('top', parseInt(drag.params.top) + disY + "px");
                }
            }
        },
        drag_target: function (bar, target) {
            this.onmousedown(bar, target);
            this.onmousemove(target);
            this.onmouseup(target);
        }
    };


    var dialog = {
        _config: {
            width: '300px',
            height: '175px',
            left: '40%',
            top: '20%',
            title: '系统提示',
            content: '',
            only_text: false,
            query_dialog: false,
            dome_node: null
        },
        load_css: function () {
            $('head').append('<style type="text/css">' + css + '</style>');
        },
        _dialog: function (config) {
            var dialogNode = $('.dialog-dialog_div');
            if (dialogNode.length == 0) {
                $('body').prepend('<div class="dialog-dialog_div"></div>');
                dialogNode = $('.dialog-dialog_div');
            }
            dialogNode.css('width', config.width);
            dialogNode.css('height', config.height);
            dialogNode.css('left', config.left);
            dialogNode.css('top', config.top);
            dialogNode.html(this._build_dialog_html(config));
            dialogNode.show();
            this._dialog_only_text(dialogNode, config);
            this._bind_yes_no_btn(dialogNode, config);
            drag.drag_target(dialogNode.find('.title'), dialogNode);
            this._bind_close_action(dialogNode);
        },
        _dialog_only_text: function (dialogNode, config) {
            var contentNode = dialogNode.find('.dialog_content');
            var line = dialogNode.height() - dialogNode.find('.title').height() - dialogNode.find('.bottom').height();
            contentNode.css('height', line + 'px');
        },
        _build_dialog_html: function (config) {
            var html = [];
            html.push('<table>');
            html.push('<tr>');
            html.push('<td class="title">');
            html.push('<span>' + config.title + '</span>');
            html.push('<a href="javascript:void(0)" class="close"><strong>x</strong></a>');
            html.push('</td>');
            html.push('</tr>');
            html.push('<tr>');
            html.push('<td class="dialog_content">');
            html.push(config.content);
            html.push('</td>');
            html.push('</tr>');
            if (config.yesNo) {
                html.push('<tr>');
                html.push('<td class="bottom">');
                html.push('<a class="no btn45" href="javascript:void(0)">取消</a>');
                html.push('<a class="yes btn45" href="javascript:void(0)">确认</a>');
                html.push('</td>');
                html.push('</tr>');
            }
            html.push('</table>');
            return html.join('');
        },
        _bind_close_action: function (dialogNode) {
            var closeNode = dialogNode.find('.close');
            closeNode.unbind('click');
            closeNode.bind('click', function () {
                dialog.close_drag();
            });
        },
        _bind_yes_no_btn: function (dialogNode, config) {
            var noBtn = dialogNode.find('.bottom .no');
            noBtn.unbind('click');
            noBtn.bind('click', function () {
                dialog.close_drag();
            });

            var yesBtn = dialogNode.find('.bottom .yes');
            yesBtn.unbind('click');
            yesBtn.bind('click', function () {
                if (typeof config.ok == 'function') {
                    config.ok();
                }
            });
        },
        close_drag: function () {
            var dialogNode = $('.dialog-dialog_div');
            dialogNode.hide();
            if (this._config.query_dialog) {
                this._config.dome_node.html(this._config.content);
            }
        },
        init_config: function (config) {
            if (typeof config !== 'undefined') {
                var _config = {};
                $.extend(_config, this._config);
                return $.extend(_config, config);
            }
            return this._config;
        },
        extend_jquery_dialog: function () {
            $.fn['dialog'] = function (config) {
                dialog._config.content = this.html();
                dialog._dialog(dialog.init_config(config));
                dialog._config.query_dialog = true;
                dialog._config.dome_node = this;
                this.html('');
            }
        },
        open_dialog: function (config) {
            dialog._dialog(dialog.init_config(config));
        },
        init: function () {
            this.load_css();
            this.extend_jquery_dialog();
        }
    };
    dialog.init();
    exports.open = dialog.open_dialog;

    exports.openSample = function (title, content) {
        dialog.open_dialog({
            title: title,
            content: content,
            yesNo: false
        });
    };

    exports.yesNo = function (title, content, yesFunc) {
        dialog.open_dialog({
            title: title,
            content: content,
            height: '190px',
            yesNo: true,
            ok: yesFunc
        });
    };


    exports.close = function (time) {
        setTimeout(function () {
            dialog.close_drag();
        }, time);
    }
});