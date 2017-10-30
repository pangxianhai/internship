define(function (require, exports, module) {
    var $ = require('../../../../lib/jquery-1.11.3');
    require('../../header.js');
    require('../../../../lib/redactor.js');
    var string = require('../../../../lib/strings.js');
    var dialog = require('../../../../lib/webApp/dialog.js');

    var weeklyJournalReview = {

        initEditor: function () {
            if (this.isTutor) {
                $('#tutorRemark').html('');
                $('#tutorRemark').redactor({
                    buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                    focus: false
                });
            } else if (this.isTeacher) {
                $('#teacherRemark').html('');
                $('#teacherRemark').redactor({
                    buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                    focus: false
                });
            }
        },

        bindReviewWeeklyJournalAction: function () {
            $('#reviewWeeklyJournalBtn').click(function () {
                $.ajax({
                    url: '/student/weeklyJournal/review.json',
                    type: 'POST',
                    data: string.getFormData($('#weeklyReviewForm')),
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            dialog.push_ok_message('提交成功');
                            setTimeout(function () {
                                window.location.href = $('#returnUrl').val();
                            }, 2000);
                        } else {
                            dialog.push_error_message(result.message);
                        }
                    }
                });
            });
        },

        init: function () {
            this.isTeacher = $('#isTeacher').val() == 'true';
            this.isTutor = $('#isTutor').val() == 'true';
            this.initEditor();
            this.bindReviewWeeklyJournalAction();
        }
    }

    weeklyJournalReview.init();
});