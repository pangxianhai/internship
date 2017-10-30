define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/validator.js');
    var dialog = require('../../../lib/dialog.js');
    var evaluation_util = require('../../../lib/util/evaluation_util.js');
    var send_evaluation = {
        reset_evaluation_table: function () {
            evaluation_util.reset_evaluation_table($('#evaluation_rules').find('tr'));
        },
        bind_send_evaluation_submit: function () {
            $('#evaluation_rules').validator({
                fields: {
                    '[name="grade"]': '评价:required'
                },
                ok: function (node) {
                    node.parent().find('.n-error').hide();
                },
                submit_target: '#evaluation-info-submit',
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
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '发放成功！');
                                setTimeout(function () {
                                    window.location.href = '/student/internship/info.htm';
                                }, 2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });

                }
            });
        },
        init: function () {
            this.reset_evaluation_table();
            this.bind_send_evaluation_submit();
        }
    };
    send_evaluation.init();
});