define(function (require) {
    require('../../lib/jquery-1.11.3.js');
    require('../../lib/ueditor/umeditor.config.js');
    require('../../lib/ueditor/umeditor.js');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var add_company = {
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
        bind_add_company_action: function () {
            $('#add_company_form').validator({
                fields: {
                    '#companyName': '公司名:required;length[5~50]',
                    '#companyAddress': '公司名地址:required;length[5~100]',
                    '#phone': '联系方式:required;length[5~15]',
                    '#internshipNumber': '实习人数:required;positive_integer'
                },
                submit_target: '#add_company_btn',
                valid: function (form) {
                    add_company.add_company_submit(form);
                }
            });
        },
        add_company_submit: function (form) {
            $.ajax({
                url: '/company/add.json',
                type: 'POST',
                data: string.getFormData(form),
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        if (result.data) {
                            dialog.openSample('系统提示', '添加公司成功');
                            setTimeout(function () {
                                window.location.href = '/company/list.htm';
                            }, 2000);
                        }
                    } else {
                        dialog.openSample('系统提示', result.message);
                    }
                }
            });
        },
        init: function () {
            this.init_edit();
            this.bind_add_company_action();
        }
    };
    add_company.init();
});