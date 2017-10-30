define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var user_list = {
        push_user_info: function (userInfoList) {
            var html = [];
            for (var i = 0; i < userInfoList.length; ++i) {
                var user = userInfoList[i];
                html.push('<tr>');
                html.push('<td>' + user.userName + '</td>');
                html.push('<td>' + user.name + '</td>');
                html.push('<td>' + user.sex.desc + '</td>');
                html.push('<td>' + string.filterNull(user.phone) + '</td>');
                html.push('<td>' + user.userType.desc + '</td>');
                html.push('<td>' + new Date(user.createdTime).format('yyyy-MM-dd') + '</td>');
                html.push('<td><a item="reset_password" userId="' + user.desId + '" class="operator" href="javascript:void(0)">[重置密码]</a></td>');
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(user_list.push_user_info(result.data.userInfoList));
                $('.page_list').html(page.html(result.data.page));
                user_list.bind_page_action();
                user_list.bind_reset_password_action();
            } else {

            }
        },
        load_user_info: function (currentPage) {
            $.ajax({
                url: '/user/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: user_list.push_page_data
            });
        },
        bind_page_action: function () {
            page.turning_page(user_list.load_user_info);
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                user_list.load_user_info(1);
            });
        },
        bind_reset_password_action: function () {
            $('a[item="reset_password"]').click(function () {
                var desId = this.getAttribute('userId');
                $.ajax({
                    url: '/user/reset_password.json',
                    type: 'POST',
                    data: {
                        userDesId: desId
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            var user = result.data;
                            dialog.openSample(user.userName + ' 重置密码成功', '新密码为：' + user.password);
                        }
                    }
                });
            });
        },
        init: function () {
            this.load_user_info(1);
            this.bind_search_action();
        }
    };
    user_list.init();
});