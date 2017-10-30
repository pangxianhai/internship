define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/validator.js');
    var string = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var weekly_journal_review = {
        data: {
            is_teacher: false,
            is_tutor: false
        },
        load_journal_content_editor: function () {
            if (weekly_journal_review.data.is_tutor) {
                $('#tutor_remark').html('');
                UM.getEditor('tutor_remark', {
                    imagePath: '',
                    initialFrameHeight: 300,
                    initialFrameWidth: 900,
                    lang: 'zh-cn',
                    langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                    focus: true
                });
            } else if (weekly_journal_review.data.is_teacher) {
                $('#teacher_remark').html('');
                UM.getEditor('teacher_remark', {
                    imagePath: '',
                    initialFrameHeight: 300,
                    initialFrameWidth: 900,
                    lang: 'zh-cn',
                    langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                    focus: true
                });
            }
        },
        bind_review_action: function () {
            $('#weekly_review_btn').click(function () {
                $.ajax({
                    url: '/student/weeklyJournal/review.json',
                    type: 'POST',
                    data: string.getFormData($('#weekly_review_form')),
                    dataType: 'json',
                    success: function (result) {
                        if (result.ret) {
                            dialog.openSample('提示', '提交成功');
                            setTimeout(function () {
                                window.location.href = '/student/weeklyJournal/detail/' + $('[name="desId"]').val() + '.htm';
                            }, 2000);
                        } else {
                            dialog.openSample('提交失败', result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            this.data.is_teacher = $('#is_teacher').val() == 'true';
            this.data.is_tutor = $('#is_tutor').val() == 'true';
            this.load_journal_content_editor();
            this.bind_review_action();
        }
    };
    weekly_journal_review.init();
});