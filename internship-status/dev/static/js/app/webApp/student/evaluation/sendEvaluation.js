define(function (require, exports, module) {
    require('../../../../lib/webApp/zepto');
    require('../../header.js');
    require('../../../../lib/validator.js');
    var Dialog = require('../../../../lib/webApp/dialog');
    var evaluationUtil = require('../../../../lib/util/evaluation_util.js');

    var sendEvaluation = {
        resetEvaluationTable: function () {
            evaluationUtil.reset_evaluation_table($('#evaluation_rules').find('tr'));
        },
        bindSendEvaluationAction: function () {
            $('#evaluation_rules').validator({
                fields: {
                    '[name="grade"]': '评价:required'
                },
                ok: function (node) {
                },
                error: function (node, msg) {
                    Dialog.push_error_message(msg);
                },
                submit_target: '#evaluationSubmitBtn',
                valid: function (evaluation_rules) {
                    var data = [];
                    evaluation_rules.find('tr').each(function () {
                        data.push({
                            ruleId: $(this).find('[name="ruleId"]').val(),
                            grade: $(this).find('[name="grade"]').val()
                        });
                    });
                    $.ajax({
                        url: '/student/evaluation/send_evaluation_submit.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            evaluationResult: JSON.stringify(data),
                            studentDesId: $('#studentDesId').val()
                        },
                        beforeSend: function () {
                            Dialog.dialog_load('提交中...');
                        },
                        success: function (result) {
                            Dialog.close_dialog();
                            if (result.ret) {
                                Dialog.push_ok_message('发放成功');
                                setTimeout(function () {
                                    window.location.href = $('#returnUrl').val();
                                }, 2000);
                            } else {
                                Dialog.push_error_message(result.message);
                            }
                        }
                    });

                }
            });
        },
        init: function () {
            this.resetEvaluationTable();
            this.bindSendEvaluationAction();
        }
    }
    sendEvaluation.init();
});