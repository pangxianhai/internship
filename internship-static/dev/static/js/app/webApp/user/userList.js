define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings.js');
    var page = require('../../../lib/webApp/page/page');
    var scroll = require('../../../lib/webApp/scroll');
    require('../header.js');
    var userList = {
        bindUserInfoScrollAction: function () {
            $('#userList .user_show').each(function () {
                var $this = $(this);
                if ($this.attr('has_operate') != 'true') {
                    return;
                }
                var operate_node = $this.parent().find('.operate');
                var operate_node_width = operate_node.width();
                new scroll({
                    target_node: $this,
                    h_scroll: true,
                    max_scroll: operate_node_width,
                    start_event_callback: function (e) {
                        operate_node.css('visibility', 'visible');
                    },
                    move_event_callback: function (e) {
                        var range = e.range;
                        var transX = parseFloat(document.defaultView.getComputedStyle($this.get(0)).transform.substring(7).split(",")[4]);
                        var rX = range.x1 - range.x2;
                        if (transX <= 0 && rX < 0) {
                            $this.css({'transform': 'translate(0px, 0px) scale(1) translateZ(0px)'});
                        }
                    },
                    end_callback: function (e) {
                        var range = e.range;
                        var swipe_type = e.swipe_direction(range);
                        if (swipe_type == '_left' && Math.abs(range.x1 - range.x2) >= operate_node_width / 2) {
                            $this.css({'transform': 'translate(-' + operate_node_width + 'px, 0px) scale(1) translateZ(0px)'});
                        } else {
                            $this.css({'transform': 'translate(0px, 0px) scale(1) translateZ(0px)'});
                            operate_node.css('visibility', 'hidden');
                        }
                    }
                });
            });
        },

        pushUserInfo: function (userInfoList) {
            var html = [];
            for (var i = 0; i < userInfoList.length; ++i) {
                var userInfo = userInfoList[i];
                html.push('<li class="user_node">');
                html.push('<div class="operate">');
                html.push('<ul class="operate_list">');
                html.push('<li class="reset_password" userDesId="' + userInfo.desId + '">');
                html.push('<div class="assign">重置密码</div>');
                html.push('</li>');
                html.push('</ul>');
                html.push('</div>');
                html.push('<div class="user_show" item="userInfoShow" has_operate="true">');
                html.push('<ul class="user_info">');
                html.push('<li>' + userInfo.name + '</li>');
                html.push('<li>' + userInfo.userName + '</li>');
                html.push('<li>' + userInfo.sex.desc + '</li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">联系方式：</li>');
                html.push('<li class="_right">' + userInfo.phone + '</li>');
                html.push('<li class="_left">用户类型：</li>');
                html.push('<li class="_right">' + userInfo.userType.desc + '</li>');
                html.push('</ul>');
                html.push('</div>');
                html.push('</li>');
                html.push('<hr/>');
            }
            return html.join('');
        },

        bindResetPassword: function () {
            var resetPasswordNode = $('#userList .user_node .operate .reset_password');
            resetPasswordNode.off('click');
            resetPasswordNode.on('click', function () {
                var userDesId = $(this).attr('userDesId');
                $.ajax({
                    url: '/user/reset_password.json',
                    type: 'POST',
                    data: {
                        userDesId: userDesId
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            var user = result.data;
                            dialog.yes_no('操作成功,新密码为：' + user.password, function () {

                            });
                        }
                    }
                });
            });
        },

        loadUserInfo: function (currentPage) {
            $.ajax({
                url: '/user/list.json',
                type: 'POST',
                data: {
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('加载中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#userList').html('');
                        }
                        var userInfoHtml = userList.pushUserInfo(result.data.userInfoList);
                        $('#userList').append(userInfoHtml);
                        var page_data = result.data.page;
                        if (page_data.totalPage > 1) {
                            new page(page_data.currentPage, page_data.totalPage, $('#userList'), userList.loadUserInfo);
                        }
                        userList.bindUserInfoScrollAction();
                        userList.bindResetPassword();
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        init: function () {
            this.loadUserInfo(1);
        }
    };
    userList.init();
});