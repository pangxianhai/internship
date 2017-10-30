define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/validator.js');
    var string = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var weekly_journal_update = {
        load_journal_content_editor: function () {
            UM.getEditor('journal-content', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
        },
        bind_update_action: function () {
            $('#weekly-journal-update-btn-a').click(function () {
                $.ajax({
                    url: '/student/weeklyJournal/update.json',
                    type: 'POST',
                    data: string.getFormData($('#weekly-journal-update-form')),
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            dialog.openSample('提示', '提交成功');
                            setTimeout(function () {
                                window.location.href = '/student/weeklyJournal/detail/' + $('[name="desId"]').val() + '.htm';
                            }, 1000);
                        } else {
                            dialog.openSample('提交失败', result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            this.load_journal_content_editor();
            this.bind_update_action();
        }
    };
    weekly_journal_update.init();
});