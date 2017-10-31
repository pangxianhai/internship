define(function (require, exports, module) {
    require('../../../../lib/webApp/zepto');
    // require('../../../../lib/date');
    require('../../header.js');
    var Dialog = require('../../../../lib/webApp/dialog');

    var AssessmentDetail = {
        bindTeacherConfirmAction: function () {
            $('#teacherConfirmBtn').click(function () {
                Dialog.yes_no('你确定要执行此操作吗？', function () {
                    $.ajax({
                        url: '/student/assessment/teacher_confirm.json',
                        type: 'POST',
                        data: {
                            desId: $('#assessmentDesId').val()
                        },
                        beforeSend: function () {
                            Dialog.dialog_load("提交中...")
                        },
                        dataType: 'json',
                        success: function (result) {
                            Dialog.close_dialog();
                            if (result.ret) {
                                Dialog.push_ok_message('操作成功');
                                setTimeout(function () {
                                    location.reload(true);
                                }, 2000);
                            } else {
                                Dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },
        main: function () {
            this.bindTeacherConfirmAction();
        }
    }

    AssessmentDetail.main();
});