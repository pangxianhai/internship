define(function (require, exports, module) {
    var $ = require('../../../../lib/jquery-1.11.3');
    // require('../../../../lib/date');
    require('../../header.js');
    require('../../../../lib/redactor.js');
    var Dialog = require('../../../../lib/webApp/dialog');
    var Strings = require('../../../../lib/strings');

    var CompanyReviewAssessment = {
        initEditor: function () {
            $('#companyRemark').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },

        bindAssessmentReviewAction: function () {
            $('#assessmentSubmitBtn').click(function () {
                $.ajax({
                    url: '/student/assessment/company_review/submit.json',
                    type: 'POST',
                    dataType: 'json',
                    beforeSend: function () {
                        Dialog.dialog_load("提交中...")
                    },
                    data: Strings.getFormData($('#companyReviewAssessmentForm')),
                    success: function (result) {
                        Dialog.close_dialog();
                        if (result.ret) {
                            Dialog.push_ok_message('考评成功');
                            setTimeout(function () {
                                window.location.href = $('#returnUrl').val();
                            }, 2000);
                        } else {
                            Dialog.push_error_message(result.message);
                        }
                    }
                });
            });
        },

        main: function () {
            this.initEditor();
            this.bindAssessmentReviewAction();
        }
    };
    CompanyReviewAssessment.main();
});