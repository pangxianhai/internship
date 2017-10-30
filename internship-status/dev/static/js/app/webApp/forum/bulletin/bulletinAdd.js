define(function (require, exports, module) {
    var $ = require('../../../../lib/jquery-1.11.3');
    require('../../../../lib/redactor.js');
    var strings = require('../../../../lib/strings');
    var dialog = require('../../../../lib/webApp/dialog');
    require('../../../../lib/validator.js');
    require('../../header.js');
    var bulletinAdd = {
        initEditor: function () {
            $('#content').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },
        bindAddBulletinAction: function () {
            $('#bulletinAddForm').validator({
                fields: {
                    '#title': '标题:required;length[~500]'
                },
                submit_target: '#bulletinAddBtn',
                ok: function (node) {
                },
                error: function (node, msg) {
                    dialog.push_error_message(msg);
                },
                valid: function (form) {
                    $.ajax({
                        url: '/bulletin/add.json',
                        type: 'POST',
                        dataType: 'json',
                        data: strings.getFormData(form),
                        success: function (result) {
                            if (result.ret) {
                                dialog.push_ok_message('添加公告成功');
                                setTimeout(function () {
                                    window.location.href = $('#returnUrl').val();
                                }, 2000);
                            } else {
                                dialog.push_error_message(result.message);
                            }
                        }
                    });
                }
            });
        },
        init: function () {
            this.initEditor();
            this.bindAddBulletinAction();
        }
    };
    bulletinAdd.init();
});