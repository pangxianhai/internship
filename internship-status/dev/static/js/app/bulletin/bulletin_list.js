define(function (require) {
    require('../../lib/jquery-1.11.3.js');
    require('../../lib/date.js');
    require('../menu.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var bulletin_list = {
        push_bulletin_info: function (bulletinInfoList) {
            var html = [];
            for (var i = 0; i < bulletinInfoList.length; i++) {
                var bulletin_info = bulletinInfoList[i];
                html.push('<tr>');
                html.push('<td><a target="_blank" href="/bulletin/detail/' + bulletin_info.desId + '.htm">' + bulletin_info.title + '</a></td>');
                html.push('<td>' + new Date(bulletin_info.createdTime).format('yyyy-MM-dd') + '</td>');
                html.push('<td><a item="delete" bulletinDesId="' + bulletin_info.desId + '" href="javascript:void(0)">[删除]</a></td>');
                html.push('</tr>');
            }
            return html.join('');
        },
        bind_page_action: function () {
            page.turning_page(bulletin_list.load_bulletin_info);
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(bulletin_list.push_bulletin_info(result.data.bulletinInfoList));
                $('.page_list').html(page.html(result.data.page));
                bulletin_list.bind_page_action();
                bulletin_list.bind_delete_action();
            } else {
            }
        },
        load_bulletin_info: function (currentPage) {
            $.ajax({
                url: '/bulletin/myBulletin.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: bulletin_list.push_page_data
            });
        },
        bind_delete_action: function () {
            $('a[item="delete"]').click(function () {
                var thisObj = $(this);
                dialog.yesNo('提示', '您确定删除该条公告吗？', function () {
                    dialog.close(1);
                    $.ajax({
                        url: '/bulletin/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            bulletinDesId: thisObj.attr('bulletinDesId')
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '删除成功!');
                                bulletin_list.load_bulletin_info(page.current_page());
                                dialog.close(2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                });
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                bulletin_list.load_bulletin_info(1);
            });
        },
        init: function () {
            this.load_bulletin_info(1);
            this.bind_search_action();
        }
    };
    bulletin_list.init();
});