define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    require('../../header.js');
    require('../../../../lib/date');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    var scroll = require('../../../../lib/webApp/scroll');
    // var page = require('../../../../lib/webApp/page/page');

    var bulletinList = {
        bindBulletinNodeAction: function () {
            var bulletinNode = $('#bulletinList .bulletin_node');
            bulletinNode.off('click');
            bulletinNode.on('click', function () {
                window.location.href = '/webApp/bulletin/detail/' + $(this).attr('bulletinDesId') + '.htm' +
                    '?returnUrl=' + encodeURIComponent(location.href);
            });
        },
        bindBulletinScrollAction: function () {
            $('#bulletinList .bulletin_node .bulletin_info').each(function () {
                var $this = $(this);
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
        bindDeleteBulletinAction: function () {
            var deleteNode = $('#bulletinList .bulletin_node .operate');
            deleteNode.off('click');
            deleteNode.on('click', function (e) {
                e.stopPropagation();
                var $this = $(this);
                var bulletinDesId = $this.parent().attr('bulletinDesId');
                var thisObj = $(this);
                dialog.yes_no('您确定删除该条公告吗？', function () {
                    dialog.close_dialog();
                    $.ajax({
                        url: '/bulletin/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            bulletinDesId: bulletinDesId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.push_ok_message('删除成功!');
                                location.reload(true);
                            } else {
                                dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },
        pushBulletinInfo: function (bulletinList) {
            var html = [];
            for (var i = 0; i < bulletinList.length; ++i) {
                var bulletinInfo = bulletinList[i];
                html.push('<li class="bulletin_node" bulletinDesId="' + bulletinInfo.desId + '">');
                html.push('<div class="operate">删除</div>');
                html.push('<div class="bulletin_info">');
                html.push('<p class="_bulletin_title">' + strings.substring(bulletinInfo.title, 20) + '</p>');
                html.push('<i class="_icon">&gt;</i>');
                html.push('<p class="_iss_time">发布日期：' + new Date(bulletinInfo.createdTime).format('yyyy-MM-dd')
                    + '；发布者：' + bulletinInfo.createdBy + '</p>');
                html.push('</div>');
                html.push('</li>');
            }
            $('#bulletinList').html(html.join(''));
            this.bindBulletinNodeAction();
            this.bindBulletinScrollAction();
            this.bindDeleteBulletinAction();
        },
        loadBulletinInfo: function () {
            $.ajax({
                url: '/bulletin/myBulletin.json',
                type: 'POST',
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        bulletinList.pushBulletinInfo(result.data.bulletinInfoList);
                    }
                }
            });
        },

        bindIssueBulletinAction: function () {
            $('#issueBulletinBtn').click(function () {
                window.location.href = '/webApp/bulletin/add.htm' + '?returnUrl=' + encodeURIComponent(location.href);
            });
        },

        init: function () {
            this.loadBulletinInfo();
            this.bindIssueBulletinAction();
        }
    };

    bulletinList.init();

});