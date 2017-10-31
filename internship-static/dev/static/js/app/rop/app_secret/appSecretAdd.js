define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/validator.js');
    var dialog = require('../../../lib/dialog.js');
    var string = require('../../../lib/strings.js');
    var add_app_secret = {
        change_app_id: function () {
            $('#change_app_id_btn').click(function () {
                $.ajax({
                    url: '/app_secret/getDefaultAppId.json',
                    type: 'post',
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            $('#appId').val(result.data);
                        } else {
                            dialog.openSample('系统提示', result.message);
                        }
                    }
                })
            });

        },
        change_app_secret: function () {
            $('#change_app_secret_btn').click(function () {
                $.ajax({
                    url: '/app_secret/getDefaultAppSecret.json',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        appId: $('#appId').val()
                    },
                    success: function (result) {
                        if (result.ret) {
                            $('#appSecret').val(result.data);
                        } else {
                            dialog.openSample('系统提示', result.message);
                        }
                    }
                })
            });
        },

        add_app_secret: function (form) {
            $.ajax({
                url: '/app_secret/add.json',
                type: 'post',
                dataType: 'json',
                data: string.getFormData(form),
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('系统提示', '添加成功!');
                        setTimeout(function () {
                            dialog.close(1);
                            window.location.href = '/app_secret/list.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('系统提示', result.message);
                    }
                }
            })
        },

        bind_add_app_secret_action: function () {
            $('#add_app_secret_form').validator({
                rules: {
                    checkAppId: function (appId) {
                        var returnRet = {};
                        $.ajax({
                            url: '/app_secret/checkAppId.json',
                            async: false,
                            type: 'post',
                            data: {
                                appId: appId
                            },
                            dataType: 'json',
                            success: function (result) {
                                if (result.ret) {
                                    returnRet.ret = true;
                                } else {
                                    returnRet.message = '该AppId已经存在';
                                }
                            }
                        });
                        return returnRet;
                    }
                },
                fields: {
                    '#appId': 'AppId:required;length[1~20];checkAppId',
                    '#appSecret': 'AppSecret:required;length[1~50]',
                    '#description': '描述:required;length[1~50]',
                },
                submit_target: '#add_app_secret_btn',
                valid: function (form) {
                    add_app_secret.add_app_secret(form);
                }
            });
        },

        init: function () {
            this.change_app_id();
            this.change_app_secret();
            this.bind_add_app_secret_action();
        }
    };
    add_app_secret.init();
});