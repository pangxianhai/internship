define(function (require, exports, module) {
    require('../../../../lib/webApp/zepto');
    // require('../../../../lib/date');
    require('../../header.js');
    var evaluationUtil = require('../../../../lib/util/evaluation_util.js');

    var evaluationDetail = {
        resetEvaluationTable: function () {
            evaluationUtil.reset_evaluation_table($('#evaluation_rules').find('tr'));
        },
        init: function () {
            this.resetEvaluationTable();
        }
    }
    evaluationDetail.init();
});