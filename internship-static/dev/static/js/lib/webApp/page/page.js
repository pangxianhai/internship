define(function (require, exports, module) {
    require('../zepto');
    var scroll = require('../scroll');
    var strings = require('../../strings');
    var dialog = require('../dialog');
    var css = require('page.css', 'css');

    function Page (current_page, total_page, target_node, turning_page_action) {
        this.current_page = current_page;
        this.total_page = total_page;
        this.target_node = target_node;
        this.turning_page_action = turning_page_action;
        this.init();
    };

    Page.prototype = {
        constructor: Page,
        push_load_btn: function () {
            var parent_node = this.target_node.parent();
            if (parent_node.find('.ui_page_load_more_btn').length <= 0) {
                parent_node.append('<div class="ui_page_load_more_panel"><button class="ui_page_load_more_btn"></button></div>');
            }
            var page_more_btn = parent_node.find('.ui_page_load_more_panel .ui_page_load_more_btn');
            page_more_btn.off('click');
            var $page = this;
            if (this.current_page < this.total_page) {
                page_more_btn.html('点击加载更多');
                page_more_btn.on('click', function () {
                    $page.turning_page($(this));
                });
            } else {
                page_more_btn.html('暂无更多内容');
            }
        },

        turning_page: function (page_more_btn) {
            if (this.current_page < this.total_page) {
                this.current_page++;
                this.turning_page_action(this.current_page);
            } else {
                page_more_btn.html('暂无更多内容');
                page_more_btn.off('click');
            }
        },

        bind_scroll: function () {
            var $page = this;
            new scroll({
                target_node: this.target_node,
                v_up_scroll: true,
                max_scroll: 200,
                can_scroll_with_window: false,
                end_callback: function (e) {
                    if (e.swipe_direction(e.range) == '_up') {
                        e.clean_target_transform();
                        var parent_node = $page.target_node.parent();
                        var page_more_btn = parent_node.find('.ui_page_load_more_panel .ui_page_load_more_btn');
                        $page.turning_page(page_more_btn);
                    }
                }
            });
        },

        init: function () {
            this.push_load_btn();
            this.bind_scroll();
        }
    };

    Page.loadSinglePage = function (current_page, total_page, target_node, turning_page_action) {
        if (typeof  Page._pageInfo == 'undefined' || current_page == 1) {
            Page._pageInfo = new Page(current_page, total_page, target_node, turning_page_action)
        } else {
            Page._pageInfo.current_page = current_page;
            Page._pageInfo.total_page = total_page;
        }
    }

    Page.load_css = function () {
        $('head').append('<style type="text/css">' + css + '</style>');
    };

    Page.load_css();
    return Page;
});