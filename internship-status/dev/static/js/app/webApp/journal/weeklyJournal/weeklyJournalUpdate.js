define(function (require, exports, module) {
    require('../../../../lib/jquery-1.11.3');
    require('../../header.js');
    require('../../../../lib/redactor.js');
    var dialog = require('../../../../lib/webApp/dialog.js');
    var string = require('../../../../lib/strings.js');

    var updateWeeklyJournal = {
        initEditor: function () {
            $('#weeklyContent').redactor({
                buttons: ['formatting', 'bold', 'underline', 'fontcolor', 'backcolor', '|', 'alignleft', 'aligncenter', 'alignright', '|', 'unorderedlist', 'orderedlist', 'html'],
                focus: false
            });
        },
        bindUpdateAction: function () {
            $('#updateWeeklyJournalBtn').click(function () {
                $.ajax({
                    url: '/student/weeklyJournal/update.json',
                    type: 'POST',
                    data: string.getFormData($('#weeklyJournalUpdateForm')),
                    dataType: 'json',
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
            this.bindUpdateAction();
        }
    };
    updateWeeklyJournal.init();
});