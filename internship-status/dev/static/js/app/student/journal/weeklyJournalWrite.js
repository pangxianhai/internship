define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/validator.js');
    var dialog = require('../../../lib/dialog.js');
    var string = require('../../../lib/strings.js');
    var WdatePicker = require('../../../lib/My97DatePicker/WdatePicker.js');
    var weekly_journal_write = {
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
        bind_weekly_day_action: function () {
            $('[name="beginDay"]').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});
            });
            $('[name="endDay"]').click(function () {
                WdatePicker.WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    minDate: $('[name="beginDay"]').val(),
                    maxDate: '%y-%M-%d'
                });
            });
        },
        bind_write_journal_btn: function () {
            $('#weekly-journal-write-form').validator({
                fields: {
                    '[name="beginDay"]': '实习日期:required',
                    '[name="endDay"]': '实习日期:required'
                },
                ok: function (node) {
                    node.removeClass("error");
                },
                error: function (node, msg) {
                    node.addClass("error");
                },
                submit_target: '#weekly-journal-write-btn-a',
                valid: function (form) {
                    $.ajax({
                        url: '/student/weeklyJournal/write.json',
                        type: 'POST',
                        dataType: 'json',
                        data: string.getFormData(form),
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '提交成功');
                                setTimeout(function () {
                                    window.location.href = '/student/weeklyJournal/list.htm';
                                }, 1000);
                            } else {
                                dialog.openSample('提交失败', result.message);
                            }
                        }
                    });
                }
            });
        },
        init: function () {
            this.load_journal_content_editor();
            this.bind_weekly_day_action();
            this.bind_write_journal_btn();
        }
    };
    weekly_journal_write.init();
})