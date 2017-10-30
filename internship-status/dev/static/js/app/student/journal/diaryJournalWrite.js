define(function (require) {
    var $ = require('../../../lib/jquery-1.11.3.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/date.js');
    require('../../menu.js');
    require('../../../lib/validator.js');
    // var editor = require('../../../lib/peditor/themes/default/defaultEdtor.js');
    var string = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var WdatePicker = require('../../../lib/My97DatePicker/WdatePicker.js');
    var add_journal = {
        init_edit: function () {
            UM.getEditor('journal-content', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
            // editor.bind('journal-content', {
            //     width: '700px',
            //     height: '300px'
            // });
            UM.getEditor('journal-review', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
        },
        bind_journal_day_action: function () {
            var journalDayNote = $('#journal-day');
            journalDayNote.click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});
            });
            journalDayNote.blur(function (event) {
                var day_text = $(this).val();
                if (string.isNotEmpty(day_text)) {
                    $('#journal-week').html(string.week_show(new Date(day_text).getDay()));
                }
            });
        },
        init_journal_day: function () {
            if (string.isEmpty($('#journal-day').val())) {
                var d = new Date();
                $('#journal-day').val(d.format('yyyy-MM-dd'));
            }
            $('#journal-week').html(string.week_show(new Date($('#journal-day').val()).getDay()));
        },
        bind_add_journal_action: function () {
            $('#add-diary-journal-a-btn').click(function () {
                $.ajax({
                    url: '/student/diaryJournal/write.json',
                    type: 'POST',
                    dataType: 'json',
                    data: string.getFormData($('#add-diary-journal-form')),
                    success: function (result) {
                        if (result.ret) {
                            dialog.openSample('提示', '提交成功');
                            setTimeout(function () {
                                window.location.href = '/student/diaryJournal/list.htm';
                            }, 1000);
                        } else {
                            dialog.openSample('提交失败', result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            this.init_journal_day();
            this.init_edit();
            this.bind_journal_day_action();
            this.bind_add_journal_action();
        }
    };
    add_journal.init();
});