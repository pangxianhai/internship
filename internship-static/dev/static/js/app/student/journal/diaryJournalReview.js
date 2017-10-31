define(function (require) {
    var $ = require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    var string = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var diary_journal_review = {
        load_journal_content_editor: function () {
            UM.getEditor('teacher_remark', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 900,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
        },
        bind_review_diary_journal_action: function () {
            $('#teacher_review').click(function () {
                $.ajax({
                    url: '/student/diaryJournal/review.json',
                    type: 'POST',
                    data: string.getFormData($('#diary_journal_form')),
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            dialog.openSample('提示', '提交成功');
                            setTimeout(function () {
                                window.location.href = '/student/diaryJournal/detail/' + $('[name="desId"]').val() + '.htm';
                            }, 2000);
                        } else {
                            dialog.openSample('提示', result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            this.load_journal_content_editor();
            this.bind_review_diary_journal_action();
        }
    };
    diary_journal_review.init();
});