define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    var print = require('../../../lib/print.js');
    var evaluationUtil = require('../../../lib/util/evaluation_util.js');
    var evaluationDetail = {
        resetEvaluationTable: function () {
            evaluationUtil.reset_evaluation_table($('#evaluation_rules').find('tr'));
        },
        bindPrintAction: function () {
            $('#print').unbind('click');
            $('#print').bind('click', function () {
                print.print($(this), $('.evaluation-info'), function () {
                    evaluationDetail.init();
                });
            });
        },
        init: function () {
            this.resetEvaluationTable();
            this.bindPrintAction();
        }
    }
    evaluationDetail.init();
});
