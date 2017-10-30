define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    var string = require('../../../lib/strings.js');
    var page = require('../../../lib/page.js');
    var dialog = require('../../../lib/dialog.js');

    var app_secret_list = {

        push_app_secret_info: function (app_secrets) {
            var html = [];
            for (var i = 0; i < app_secrets.length; ++i) {
                var appSecret = app_secrets[i];
                html.push('<tr>');
                html.push('<input item="publicKey" type="hidden" value="' + appSecret.publicKey + '"/>')
                html.push('<td>' + appSecret.appId + '</td>');
                html.push('<td>' + appSecret.appSecret + '</td>');
                html.push('<td>' + appSecret.description + '</td>');
                html.push('<td>' + appSecret.status.desc + '</td>');
                html.push('<td>');
                if (appSecret.isValid) {
                    html.push('<a item="publicKey" href="javascript:void(0)">[查看公钥]</a>');
                    html.push('<a item="cancel" appId="' + appSecret.appId + '" href="javascript:void(0)">[注销]</a>');
                    html.push('<a item="freeze" appId="' + appSecret.appId + '" href="javascript:void(0)">[冻结]</a>');
                } else if (appSecret.isCancel) {
                    html.push('<a item="delete" appId="' + appSecret.appId + '" href="javascript:void(0)">[删除]</a>');
                } else if (appSecret.isFreeze) {
                    html.push('<a item="unfreeze" appId="' + appSecret.appId + '" href="javascript:void(0)">[解冻]</a>');
                    html.push('<a item="delete" appId="' + appSecret.appId + '" href="javascript:void(0)">[删除]</a>');
                } else if (appSecret.isInvalid) {
                    html.push('<a item="unfreeze" appId="' + appSecret.appId + '" href="javascript:void(0)">[激活]</a>');
                    html.push('<a item="delete" appId="' + appSecret.appId + '" href="javascript:void(0)">[删除]</a>');
                }
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },

        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(app_secret_list.push_app_secret_info(result.data.appSecrets));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(app_secret_list.load_app_secret);
                app_secret_list.bind_operate_action();
            } else {

            }
        },

        load_app_secret: function (currentPage) {
            $.ajax({
                url: '/app_secret/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: app_secret_list.push_page_data
            });
        },

        bind_search_action: function () {
            $('#search_action').click(function () {
                app_secret_list.load_app_secret(1);
            });
        },

        change_app_secret_status: function (url, node) {
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: {
                    appId: $(node).attr('appId')
                },
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('系统提示', '操作成功');
                        setTimeout(function () {
                            dialog.close(1);
                            app_secret_list.load_app_secret(page.current_page());
                        }, 2000);
                    } else {
                        dialog.openSample('系统提示', result.message);
                    }
                }
            })
        },

        bind_operate_action: function () {
            $('#u_data').find('a[item="cancel"]').click(function () {
                app_secret_list.change_app_secret_status('/app_secret/cancel.json', this);
            });
            $('#u_data').find('a[item="freeze"]').click(function () {
                app_secret_list.change_app_secret_status('/app_secret/freeze.json', this);
            });
            $('#u_data').find('a[item="delete"]').click(function () {
                app_secret_list.change_app_secret_status('/app_secret/delete.json', this);
            });
            $('#u_data').find('a[item="unfreeze"]').click(function () {
                app_secret_list.change_app_secret_status('/app_secret/valid.json', this);
            });
            $('#u_data').find('a[item="publicKey"]').click(function () {
                dialog.open({
                    title: '公钥',
                    content: $(this).parent().parent().find('input[item="publicKey"]').val(),
                    height: '220px',
                    yesNo: false
                });
            });
        },

        init: function () {
            this.load_app_secret(1);
            this.bind_search_action();
        }
    };
    app_secret_list.init();
});