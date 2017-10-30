define(function (require, exports, module) {
    // require('../../../../lib/webApp/zepto');
    var $ = require('../../../../lib/jquery-1.11.3');
    require('../../header.js');
    require('../../../../lib/date');
    require('../../../../lib/redactor.js');
    var string = require('../../../../lib/strings.js');
    var dialog = require('../../../../lib/webApp/dialog');

    var diaryJournalReview = {
        initEdit: function () {
            $('#teacherRemark').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },

        bindReviewDiaryJournalAction: function () {
            $('#reviewDiaryJournalBtn').click(function () {
                $.ajax({
                    url: '/student/diaryJournal/review.json',
                    type: 'POST',
                    data: string.getFormData($('#reviewDiaryJournalForm')),
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            dialog.push_ok_message('提交成功');
                            setTimeout(function () {
                                window.location.href = $('#returnUrl').val();
                            }, 1000);
                        } else {
                            dialog.push_error_message(result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            $('#journalWeek').html(string.week_show(new Date($('#journalDay').html()).getDay()));
            this.initEdit();
            this.bindReviewDiaryJournalAction();
        }
    };

    diaryJournalReview.init();
});