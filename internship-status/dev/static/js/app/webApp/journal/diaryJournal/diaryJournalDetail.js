define(function (require, exports, module) {
    require('../../../../lib/webApp/zepto');
    require('../../header.js');
    // require('../../../../lib/date');
    // var touch = require('../../../../lib/webApp/touch');
    // var dialog = require('../../../../lib/webApp/dialog');
    // var strings = require('../../../../lib/strings');
    // var page = require('../../../../lib/webApp/page/page');

    var diaryJournalDetail = {
        rewriteReturnUrl: function () {
            //如果直接从日志列表过来 可能带有参数 防止参数丢失直接取referrer
            if (document.referrer.indexOf('/webApp/student/diaryJournal/list.htm') != -1) {
                $('#returnUrl').val(document.referrer);
            }
        },
        init: function () {
            this.rewriteReturnUrl();
        }
    };

    diaryJournalDetail.init();

});