define(function (require, exports, module) {
    var $ = require('../../../../lib/jquery-1.11.3');
    require('../../../../lib/redactor.js');
    var Strings = require('../../../../lib/strings');
    var Dialog = require('../../../../lib/webApp/dialog');
    require('../../../../lib/validator.js');
    require('../../header.js');

    var SendAssessment = {
        initEditor: function () {
            $('#tutorRemark').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },
        sendAssessmentInfo: function (form) {
            $.ajax({
                url: '/student/assessment/send.json',
                type: 'POST',
                data: Strings.getFormData(form),
                dataType: 'json',
                beforeSend: function () {
                    Dialog.dialog_load("提交中...")
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
            })
        },
        bindSendAssessmentAction: function () {
            $('#sendAssessmentForm').validator({
                rules: {
                    checkScore: function () {
                        return Strings.check_score($('[name="tutorScore"]').val());
                    }
                },
                ok: function (node) {

                },
                error: function (node, msg) {
                    Dialog.push_error_message(msg);
                },
                fields: {
                    '[name="tutorScore"]': '建议成绩:required;checkScore',
                },
                submit_target: '#assessmentSubmitBtn',
                valid: function (form) {
                    SendAssessment.sendAssessmentInfo(form);
                }
            });
        },
        main: function () {
            this.initEditor();
            this.bindSendAssessmentAction();
        }
    };
    SendAssessment.main();
});