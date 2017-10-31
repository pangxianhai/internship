define(function (require) {
    require('../../lib/jquery-1.11.3.js');
    require('../../lib/ueditor/umeditor.config.js');
    require('../../lib/ueditor/umeditor.js');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var update = {
        init_edit: function () {
            um = UM.getEditor('internshipDesc', {
                imagePath: '',
                initialFrameHeight: 200,
                initialFrameWidth: 600,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });

        },
        bind_update_company_action: function () {
            $('#update_company_form').validator({
                fields: {
                    '[name="companyAddress"]': '公司名地址:required;length[5~100]',
                    '[name="phone"]': '联系方式:required;length[5~15]',
                    '[name="internshipNumber"]': '实习人数:required;positive_integer'
                },
                submit_target: '#update_company_btn',
                valid: function (form) {
                    $.ajax({
                        url: '/company/update.json',
                        type: 'POST',
                        data: string.getFormData(form),
                        dataType: 'json',
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('系统提示', '更新成功');
                                setTimeout(function () {
                                    window.location.href = '/company/detail/' + result.data + '.htm';
                                }, 1000);
                            } else {
                                dialog.openSample('系统提示', result.message);
                            }
                        }
                    });
                }
            });
        },
        init: function () {
            this.init_edit();
            this.bind_update_company_action();
        }
    };
    update.init();
});