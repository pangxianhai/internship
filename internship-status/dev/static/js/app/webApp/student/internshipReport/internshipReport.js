define(function (require, exports, module) {
    var $ = require('../../../../lib/jquery-1.11.3');
    // require('../../../../lib/date');
    require('../../../../lib/redactor.js');
    require('../../header.js');
    var strings = require('../../../../lib/strings');
    var Dialog = require('../../../../lib/webApp/dialog');

    var internshipReport = {
        initEditor: function () {
            $('#teacherReview').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },
        studentCreateInternshipReport: function () {
            var subjectValue = $('#subject_input').val();
            if (strings.isEmpty(subjectValue)) {
                dialog.push_error_message('实习报告题目不能为空!');
                return;
            } else if (subjectValue.length > 50) {
                dialog.push_error_message('题目太长!');
                return;
            }
            $.ajax({
                url: '/student/report/create.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    subject: subjectValue
                },
                beforeSend: function () {
                    Dialog.dialog_load('报告生成中...');
                },
                success: function (result) {
                    Dialog.close_dialog();
                    if (result.ret) {
                        Dialog.push_ok_message('生成成功');
                        setTimeout(function () {
                            window.location.href = $('#returnUrl').val();
                        }, 1000);
                    } else {
                        Dialog.push_error_message(result.message);
                    }
                }
            });
        },

        teacherReview: function () {
            $.ajax({
                url: '/student/report/teacher_review.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    desId: $('#desId').val(),
                    teacherReview: $('#teacherReview').html(),
                    reportScore: $('#reportScoreStr').val()
                },
                beforeSend: function () {
                    Dialog.dialog_load("提交中...")
                },
                success: function (result) {
                    Dialog.close_dialog();
                    if (result.ret) {
                        Dialog.push_ok_message('考评成功');
                        setTimeout(function () {
                            location.reload(true);
                        }, 1000);
                    } else {
                        Dialog.push_error_message(result.message);
                    }
                }
            });
        },

        bindSubmitAction: function () {
            var action = $('#action').val();
            $('#report_submit').click(function () {
                if (strings.isEmpty(action)) {
                    return;
                } else if (action == 'create') {
                    internshipReport.studentCreateInternshipReport();
                } else if (action == 'teacherReview') {
                    internshipReport.teacherReview();
                }
            });
        },
        init: function () {
            this.initEditor();
            this.bindSubmitAction();
        }
    }
    internshipReport.init();
});