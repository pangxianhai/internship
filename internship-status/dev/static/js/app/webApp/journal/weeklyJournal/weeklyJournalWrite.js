define(function (require, exports, module) {
    var $ = require('../../../../lib/jquery-1.11.3');
    require('../../../../lib/date');
    require('../../header.js');
    require('../../../../lib/redactor.js');
    require('../../../../lib/webApp/datePicker/calendar_picker');
    var dialog = require('../../../../lib/webApp/dialog.js');
    var string = require('../../../../lib/strings.js');

    var weeklyJournalWrite = {
        bindDatePickerAction: function () {
            $('#beginDay').click(function () {
                $('#beginDayPicker').addClass("date_picker_activity");
                $('#beginDayPicker').calendar({
                    date: string.isNotEmpty($('#beginDay').val()) ? new Date($('#beginDay').val()) : new Date(),
                    first_day: 0,
                    swipe_able: true,
                    max_date: new Date($('#endDay').val()),
                    select: function (date) {
                        console.log('a');
                        $('#beginDay').val(date.format('yyyy-MM-dd'));
                        $('#beginDayPicker').removeClass("date_picker_activity");
                    },
                    exceptHiddenNodes: ['beginDayPicker', 'beginDay'],
                    hiddenFunction: function () {
                        $('#beginDayPicker').removeClass("date_picker_activity");
                    }
                });
            });
            $('#endDay').click(function () {
                $('#endDayPicker').addClass("date_picker_activity");
                $('#endDayPicker').calendar({
                    date: string.isNotEmpty($('#beginDay').val()) ? new Date($('#beginDay').val()) : new Date(),
                    first_day: 0,
                    swipe_able: true,
                    min_date: new Date($('#beginDay').val()),
                    select: function (date) {
                        $('#endDay').val(date.format('yyyy-MM-dd'));
                        $('#endDayPicker').removeClass("date_picker_activity");
                    },
                    exceptHiddenNodes: ['endDayPicker', 'endDay'],
                    hiddenFunction: function () {
                        $('#endDayPicker').removeClass("date_picker_activity");
                    }
                });
            });
        },
        initEditor: function () {
            $('#journalContent').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },
        bindAddWeeklyJournalAction: function () {
            $('#addWeeklyJournalBtn').click(function () {
                if (string.isEmpty($('#beginDay').val()) || string.isEmpty($('#endDay').val())) {
                    dialog.push_error_message('请选择实习日期');
                    return;
                }
                $.ajax({
                    url: '/student/weeklyJournal/write.json',
                    type: 'POST',
                    dataType: 'json',
                    data: string.getFormData($('#weeklyJournalWriteForm')),
                    beforeSend: function () {
                        dialog.dialog_load('提交中,请稍后...');
                    },
                    success: function (result) {
                        dialog.close_dialog();
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
            this.initEditor();
            this.bindDatePickerAction();
            this.bindAddWeeklyJournalAction();
        }
    };
    weeklyJournalWrite.init();
});