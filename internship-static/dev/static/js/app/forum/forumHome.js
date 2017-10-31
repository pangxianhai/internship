define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../../lib/date');
    require('../menu.js');
    var strings = require('../../lib/strings.js');
    var forum_home = {
        build_bulletin_list: function (bulletin_list) {
            var html = [];
            for (var i = 0; i < bulletin_list.length; ++i) {
                var bulletin = bulletin_list[i];
                html.push('<li>');
                html.push('<p class="_bulletin_title"><a href="/bulletin/detail/' + bulletin.desId + '.htm">' + strings.substring(bulletin.title, 76) + '</a></p>');
                html.push('<p class="_iss_time">发布日期:' + new Date(bulletin.createdTime).format('yyyy-MM-dd') + '</p>')
                if (i < bulletin_list.length - 1) {
                    html.push('<hr/>');
                }
                html.push('</li>');
            }
            return html.join('');
        },
        load_bulletin: function () {
            $.ajax({
                url: '/bulletin/all.json',
                type: 'POST',
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        var html = forum_home.build_bulletin_list(result.data);
                        $('.forum_list ul').html(html);
                    }
                }
            });
        },
        init: function () {
            this.load_bulletin();
        }
    };
    forum_home.init();
});