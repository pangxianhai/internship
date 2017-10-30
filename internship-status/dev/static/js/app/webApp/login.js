define(function (require, exports, module) {
    require('../../lib/webApp/zepto');
    require('../../lib/validator.js');
    var dialog = require('../../lib/webApp/dialog.js');
    var string = require('../../lib/strings.js');
    require('header.js');
    var webAppLogin = {
        login_action: function (form) {
            $.ajax({
                url: '/webApp/login/login.json',
                type: 'post',
                data: string.getFormData(form),
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('登录中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        window.location.href = result.data;
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            })
        },

        bind_login_action: function () {
            $('#login_form').validator({
                fields: {
                    '#userName': '用户名:required;length[1~50]',
                    '#password': '密码:required;length[1~50]'
                },
                submit_target: '#login_btn',
                ok: function (node) {
                },
                error: function (node, msg) {
                    dialog.push_error_message(msg);
                },
                valid: function (form) {
                    webAppLogin.login_action(form);
                }
            });
        },

        init: function () {
            this.bind_login_action();
        }
    };
    webAppLogin.init();
});