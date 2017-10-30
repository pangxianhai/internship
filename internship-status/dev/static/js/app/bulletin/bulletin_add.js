define(function (require) {
    require('../../lib/jquery-1.11.3.js');
    require('../../lib/ueditor/umeditor.config.js');
    require('../../lib/ueditor/umeditor.js');
    require('../../lib/validator.js');
    require('../menu.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var add_bulletin = {
        init_edit: function () {
            um = UM.getEditor('content', {
                imagePath: '',
                initialFrameHeight: 200,
                initialFrameWidth: 600,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });

        },
        bind_add_bulletin_action: function () {
            $('#add_bulletin_form').validator({
                fields: {
                    '#title': '标题:required;length[~500]'
                },
                submit_target: '#add_bulletin_btn',
                valid: function (form) {
                    add_bulletin.add_bulletin(form);
                }
            });
        },
        add_bulletin: function (form) {
            $.ajax({
                url: '/bulletin/add.json',
                type: 'POST',
                dataType: 'json',
                data: string.getFormData(form),
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('系统提示', '添加公告成功');
                        setTimeout(function () {
                            window.location.href = '/bulletin/myBulletin.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('系统提示', result.message);
                    }
                }
            });
        },
        init: function () {
            this.init_edit();
            this.bind_add_bulletin_action();
        }
    };
    add_bulletin.init();
});