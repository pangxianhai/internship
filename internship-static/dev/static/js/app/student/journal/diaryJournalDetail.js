define(function (require) {
    require('../../../lib/jquery-1.11.3.js');
    require('../../../lib/date.js');
    require('../../menu.js');
    var string = require('../../../lib/strings.js');
    var print = require('../../../lib/print.js');
    var journal_detail = {
        print: function () {
            $('#print').unbind('click');
            $('#print').bind('click', function () {
                print.print($(this), $('.diary-journal-info'), function () {
                    journal_detail.print();
                });
            });
        },
        init_week: function () {
            $('#journal-week').html(string.week_show(new Date($('#journal-day').html()).getDay()));
        },
        init: function () {
            this.init_week();
            this.print();
        }
    };
    journal_detail.init();
});