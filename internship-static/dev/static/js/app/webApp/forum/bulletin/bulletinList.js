define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    require('../../header.js');
    require('../../../../lib/date');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    // var Page = require('../../../../lib/webApp/page/page');

    var bulletinList = {
        bindBulletinNodeAction: function () {
            var bulletinNode = $('#bulletinList .bulletin_node');
            bulletinNode.unbind('click');
            bulletinNode.bind('click', function () {
                window.location.href = '/webApp/bulletin/detail/' + $(this).attr('bulletinDesId') + '.htm'
                    + '?returnUrl=' + encodeURIComponent(location.href);
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
        },
        loadBulletinInfo: function () {
            $.ajax({
                url: '/bulletin/all.json',
                type: 'POST',
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        bulletinList.pushBulletinInfo(result.data);
                    }
                }
            });
        },
        init: function () {
            this.loadBulletinInfo();
        }
    };

    bulletinList.init();

});