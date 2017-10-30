define(function (require) {
    require('../../../lib/jquery-1.11.3.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/date.js');
    require('../../menu.js');
    var dialog = require('../../../lib/dialog.js');
    var string = require('../../../lib/strings.js');
    var update_journal = {
        init_edit: function () {
            UM.getEditor('journal-content', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
            UM.getEditor('journal-review', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
        },
        init_journal_day: function () {
            $('#journal-week').html(string.week_show(new Date($('#journal-day').html()).getDay()));
        },
        bind_add_journal_action: function () {
            $('#add-diary-journal-a-btn').click(function () {
                $.ajax({
                    url: '/student/diaryJournal/update.json',
                    type: 'POST',
                    dataType: 'json',
                    data: string.getFormData($('#add-diary-journal-form')),
                    success: function (result) {
                        if (result.ret) {
                            dialog.openSample('提示', '提交成功');
                            setTimeout(function () {
                                window.location.href = '/student/diaryJournal/detail/' + $('[name="journalDestId"]').val() + '.htm';
                            }, 2000);
                        } else {
                            dialog.openSample('提示', result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            this.init_journal_day();
            this.bind_add_journal_action();
            this.init_edit();
        }
    };
    update_journal.init();
});