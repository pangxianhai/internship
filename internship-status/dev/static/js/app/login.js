define(function (require) {
    require('../lib/jquery-1.11.3');
    require('../lib/validator.js');
    var strings = require('../lib/strings.js');
    var login = {
        init_error_msg: function () {
            var errMsg = $('#err_msg').val();
            if (strings.isNotEmpty(errMsg)) {
                $('.login_error').html(errMsg);
                $('.login_error').show();
            } else {
                $('.login_error').hide();
            }
        },
        bind_login_btn_action: function () {
            $('#login_form').validator({
                fields: {
                    '.user_name': '用户名:required;length[1~50]',
                    '.password': '密码:required;length[1~50]'
                },
                ok: function (node) {
                    node.removeClass("error");
                },
                error: function (node, msg) {
                    node.addClass("error");
                },
                submitAction: function (success) {
                    $('#login_btn').click(function () {
                        if (typeof success == 'function') {
                            success();
                        }
                    });
                    $('.password').on('keyup', function (event) {
                        if (event.keyCode == 13) {
                            if (typeof success == 'function') {
                                success();
                            }
                        }
                    })
                },
                valid: function (form) {
                    form.submit();
                }
            });
        },
        init: function () {
            this.bind_login_btn_action();
            this.init_error_msg();
        }
    };
    login.init();

});