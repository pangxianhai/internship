define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    var dialog = require('../../../lib/dialog.js');
    var print = require('../../../lib/print.js');
    var assessment_detail = {
        bind_teacher_confirm_action: function () {
            $('#teacher_confirm_btn').click(function () {
                dialog.yesNo('提示', '你确定要执行此操作吗？', function () {
                    $.ajax({
                        url: '/student/assessment/teacher_confirm.json',
                        type: 'POST',
                        data: {
                            desId: $('#desId').val()
                        },
                        dataType: 'json',
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '操作成功');
                                setTimeout(function () {
                                    location.reload(true);
                                }, 2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                });
            });
        },
        bind_print_action: function () {
            $('#print').unbind('click');
            $('#print').bind('click', function () {
                print.print($(this), $('.assessment-info'), function () {
                    assessment_detail.init();
                });
            });
        },
        init: function () {
            this.bind_teacher_confirm_action();
            this.bind_print_action()
        }
    };
    assessment_detail.init();
});