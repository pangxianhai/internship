define(function (require, exports, module) {
    require('zepto');
    require('zepto.alert');
    var css = require('../../../css/webApp/zepto.alert.css', 'css');
    var dialog = {
        load_css: function () {
            $('head').append('<style type="text/css">' + css + '</style>');
        },

        has_dialog: function () {
            if (dialog.dialog_ == null || typeof dialog.dialog_ == 'undefined' || !dialog.dialog_.isLock()) {
                return false;
            } else {
                return true;
            }
        },

        push_error_message: function (text) {
            if (!this.has_dialog()) {
                dialog.dialog_ = $.dialog({
                    content: text,
                    title: "alert",
                    time: 2000
                });
            }
        },
        push_ok_message: function (text) {
            if (!this.has_dialog()) {
                dialog.dialog_ = $.dialog({
                    content: text,
                    title: "ok",
                    time: 2000
                });
            }
        },
        dialog_load: function (text) {
            //同一个页面应当仅有一个lading 对话框 打开对话框前先关闭以前的对话框
            dialog.close_dialog();
            dialog.dialog_ = $.dialog({
                content: text,
                title: "load"
            });
        },
        close_dialog: function () {
            if (dialog.dialog_ != null && typeof dialog.dialog_ != 'undefined') {
                dialog.dialog_.close();
            }
        },

        yes_no: function (text, callback) {
            if (!this.has_dialog()) {
                $.dialog({
                    content: text,
                    okText: '确定',
                    cancelText: '取消',
                    ok: callback,
                    cancel: function () {

                    },
                    title: ""
                });
            }
        },

        init: function () {
            this.load_css();
        }
    };
    dialog.init();
    return dialog;
    // exports.dialog = dialog;
});