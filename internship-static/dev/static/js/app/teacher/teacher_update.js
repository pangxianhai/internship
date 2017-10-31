define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var update = {
        bind_update_action: function () {
            $('#update_form').validator({
                fields: {
                    '[name="phone"]': '联系方式:required;length[3~20]',
                    '[name="rank"]': '职称:required;length[1~20]'
                },
                submit_target: '#update_btn',
                valid: function (form) {
                    $.ajax({
                        url: '/teacher/updateInfo.json',
                        type: 'POST',
                        data: string.getFormData(form),
                        dataType: 'json',
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '更新成功');
                                setTimeout(function () {
                                    window.location.href = '/teacher/my_info.htm';
                                }, 1000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                }
            });
        },
        init: function () {
            this.bind_update_action();
        }
    };
    update.init();
});