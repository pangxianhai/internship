define(function (require, exports, module) {
    var String = require('strings.js');
    var page_class = {
        current_page: 1,
        page_number: function (current, total) {
            var min = [];
            var mid = [];
            var max = [];
            for (var i = 1; i < 5; ++i) {
                if (i <= total) {
                    min.push(i);
                }
            }
            for (var i = current - 4; i <= current + 4; ++i) {
                if (i >= 1 && i <= total) {
                    mid.push(i);
                }
            }
            for (var i = total - 4; i <= total; ++i) {
                if (i >= 1 && i <= total) {
                    max.push(i);
                }
            }
            var res = [];
            var has = {};
            for (var i = 0; i < min.length; ++i) {
                res.push(min[i]);
                has[min[i]] = 1;
            }
            for (var i = 0; i < mid.length; ++i) {
                if (i == 0) {
                    if (mid[0] - res[res.length - 1] > 1) {
                        res.push('...');
                    }
                }
                if (!has[mid[i]]) {
                    res.push(mid[i]);
                    has[mid[i]] = 1;
                }
            }
            for (var i = 0; i < max.length; ++i) {
                if (i == 0) {
                    if (max[0] - res[res.length - 1] > 1) {
                        res.push('...');
                    }
                }
                if (!has[max[i]]) {
                    res.push(max[i]);
                    has[max[i]] = 1;
                }
            }
            return res;
        },
        html: function (page) {
            page_class.current_page = page.currentPage;
            var html = [];
            html.push('<div item="p-page-div">');
            html.push('<ul class="l">');
            html.push('当前' + page.currentPage + '/' + page.totalPage + '页');
            html.push(' 共有 ' + page.totalRecord + ' 条记录');
            html.push('</ul>');
            html.push('<ul class="r">');
            html.push('<ul class="r">');
            html.push('<li><a href="javascript:void(0)" data="1">首页</a></li>');
            if (page.currentPage == 1) {
                html.push('<li><a href="javascript:void(0)" data="1">上一页</a></li>');
            } else {
                html.push('<li><a href="javascript:void(0)" data="' + (page.currentPage - 1) + '">上一页</a></li>');
            }
            var page_numbers = this.page_number(page.currentPage, page.totalPage);
            for (var i = 0; i < page_numbers.length; ++i) {
                var num = page_numbers[i];
                if (num == '...') {
                    html.push('<li>...</li>');
                } else if (num == page.currentPage) {
                    html.push('<li><a href="javascript:void(0)" class="selected">' + num + '</a></li>');
                } else {
                    html.push('<li><a href="javascript:void(0)" data="' + num + '">' + num + '</a></li>');
                }
            }
            if (page.currentPage == page.totalPage) {
                html.push('<li><a href="javascript:void(0)" data="' + page.totalPage + '">下一页</a></li>');
            } else {
                html.push('<li><a href="javascript:void(0)" data="' + (page.currentPage + 1) + '">下一页</a></li>');
            }
            html.push('<li><a href="javascript:void(0)" data="' + page.totalPage + '">末页</a></li>');
            html.push('</ul>');
            html.push('</div>');
            return html.join('');
        },
        turning_page: function (callback) {
            var aNode = $('div[item="p-page-div"]').find('A');
            aNode.unbind('click');
            aNode.bind('click', function () {
                var p = this.getAttribute('data');
                if (String.isEmpty(p) || p == '...') {
                    return;
                } else {
                    callback(p);
                }
            });
        }
    };


    exports.html = function (page) {
        return page_class.html(page);
    };

    exports.turning_page = page_class.turning_page;

    exports.current_page = function () {
        if (typeof page_class.current_page == 'undefined' || page_class.current_page == null) {
            return 1;
        }
        return page_class.current_page;
    }
});