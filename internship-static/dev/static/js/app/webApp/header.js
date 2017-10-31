define(function (require, exports, module) {
    require('../../lib/webApp/zepto');
    var strings = require('../../lib/strings.js');

    var webAppHeader = {
        initPanel: function () {
            if ($('#show_header_left').val() == 'true') {
                $('header .header .left').css('visibility', 'visible');
            } else {
                $('header .header .left').css('visibility', 'hidden');
            }
            var rightHtml = $('#headerRight').html();
            if (strings.isNotEmpty(rightHtml)) {
                $('header .header .right').html(rightHtml);
            }
        },

        initFooterMenu: function () {
            var activeMenuId = $('#footerMenuId').val();
            $('footer .footer_menu').find('.' + activeMenuId).addClass('active');
        },

        bindLeftBtnAction: function () {
            $('header .header .left a').click(function () {
                var returnUrl = $('#returnUrl').val();
                if (strings.isNotEmpty(returnUrl)) {
                    window.location.href = returnUrl;
                } else {
                    window.history.go(-1);
                }
            });
        },

        init: function () {
            this.initPanel();
            this.bindLeftBtnAction();
            this.initFooterMenu();
            $('body').height($('body')[0].clientHeight);
        }
    };
    $(function () {
        webAppHeader.init();
    });
});