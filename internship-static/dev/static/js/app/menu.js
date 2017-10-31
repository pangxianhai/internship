define(function (require, exports, module) {
    require('../lib/jquery-1.11.3');
    var menu = {
        init_choice: function () {
            var menu_value = $('#menu_id').val();
            $('.menu_list').find('A').each(function (menu_value) {
                var thisObj = $(this);
                if (this.getAttribute('data') == menu_value) {
                    $(this).addClass('current');
                    $(this).removeClass('no_current');
                } else {
                    $(this).addClass('no_current');
                    $(this).removeClass('current');
                }
            }, [menu_value]);
        }
    };

    var bulletin_scroll = {
        timer: null,
        top: 0,
        begin: function (node) {
            bulletin_scroll.timer = setInterval(function () {
                node.css('top', bulletin_scroll.top + 'px');
                if (bulletin_scroll.top < -11) {
                    bulletin_scroll.top = 12;
                } else {
                    bulletin_scroll.top -= 1;
                }
            }, 180);
        },
        end: function () {
            if (bulletin_scroll.timer != null) {
                clearInterval(bulletin_scroll.timer);
            }
        }
    };

    var bulletin = {
        tips_show: function () {
            bulletin_scroll.begin($('#bulletin_head_tips'));
            $('#bulletin_head_tips').hover(function () {
                bulletin_scroll.end();
            }, function () {
                bulletin_scroll.begin($('#bulletin_head_tips'));
            });
        }
    };
    $(function () {
        menu.init_choice();
        bulletin.tips_show();
    });
    exports.get_title = function () {
        $('.menu').find('.title').val();
    }
});