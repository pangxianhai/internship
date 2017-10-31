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
                print.print($(this), $('.weekly-journal-panel'), function () {
                    journal_detail.init();
                });
            });
        },
        init: function () {
            this.print();
        }
    }
    journal_detail.init();
});