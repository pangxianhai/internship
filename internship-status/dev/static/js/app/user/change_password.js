define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var change_password = {
        bind_change_password: function () {
            $.ajax({
                url: '/user/change_password.json',
                type: 'POST',
                data: string.getFormData($('#chang_password_form')),
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('系统提示', '修改成功,请重新登录');
                        setTimeout(function () {
                            dialog.close(1);
                            window.location.href = '/login/logout.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('系统提示', result.message);
                    }
                }
            });
        },
        bind_change_password_action: function () {
            $('#chang_password_form').validator({
                rules: {
                    checkPasswordAgain: function () {
                        var result = {};
                        if ($('#password').val() == $('#passwordAgain').val()) {
                            result.ret = true;
                        } else {
                            result.ret = false;
                            result.message = '两次密码输入不相等';
                        }
                        return result;
                    }
                },
                fields: {
                    '#sourcePassword': '原密码:required',
                    '#password': '密码:required;length[6~18]',
                    '#passwordAgain': '确认密码:required;length[6~18];checkPasswordAgain'
                },
                submit_target: '#chang_password_btn',
                valid: function (form) {
                    change_password.bind_change_password(form);
                }
            });
        },
        init: function () {
            this.bind_change_password_action();
        }
    };
    change_password.init();
});