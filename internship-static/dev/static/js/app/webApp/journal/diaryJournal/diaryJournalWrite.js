define(function (require, exports, module) {
    // require('../../../../lib/webApp/zepto');
    require('../../../../lib/jquery-1.11.3');
    require('../../header.js');
    require('../../../../lib/date');
    require('../../../../lib/redactor.js');
    var calendar = require('../../../../lib/webApp/datePicker/calendar_picker');
    var string = require('../../../../lib/strings.js');
    var dialog = require('../../../../lib/webApp/dialog');

    var diaryJournalWrite = {
        initEdit: function () {
            $('#journalContent').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
            $('#journalReview').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },
        initDatePicker: function () {
            var defaultDate = new Date();
            if (string.isNotEmpty($('#journal_day').val())) {
                defaultDate = new Date($('#journal_day').val());
            }
            $('#diary_journal_day_picker').calendar({
                date: defaultDate,
                first_day: 0,
                swipe_able: true,
                max_date: new Date(),
                select: function (date) {
                    $('#journal_day').val(date.format('yyyy-MM-dd'));
                    $('#journal_week').html(string.week_show(date.getDay()));
                    $('#diary_journal_day_picker').removeClass("active");
                    $('#journalDay').val(date.format('yyyy-MM-dd'));
                },
                exceptHiddenNodes: ['diary_journal_day_picker', 'journal_day'],
                hiddenFunction: function () {
                    $('#diary_journal_day_picker').removeClass("active");
                }
            });
        },
        bindSelectJournalDate: function () {
            $('#journal_day').click(function () {
                $('#diary_journal_day_picker').addClass("active");
            });
        },
        bindAddJournalAction: function () {
            $('#addDiaryJournalBtn').click(function () {
                $.ajax({
                    url: '/student/diaryJournal/write.json',
                    type: 'POST',
                    dataType: 'json',
                    data: string.getFormData($('#writeDiaryJournalForm')),
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
            $('#journal_week').html(string.week_show(new Date().getDay()));
            $('#journal_day').val(new Date().format('yyyy-MM-dd'));
            $('#journalDay').val(new Date().format('yyyy-MM-dd'));
            this.initEdit();
            this.initDatePicker();
            this.bindSelectJournalDate();
            this.bindAddJournalAction();
        }
    };

    diaryJournalWrite.init();

});