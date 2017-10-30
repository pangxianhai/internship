define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/validator.js');
    var strings = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var send_assessment = {
        init_edit: function () {
            UM.getEditor('tutor-remark', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
        },
        send_assessment_info: function (form) {
            $.ajax({
                url: '/student/assessment/send.json',
                type: 'POST',
                data: strings.getFormData(form),
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '发放成功');
                        setTimeout(function () {
                            window.location.href = '/student/internship/info.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            })
        },
        bind_send_assessment_action: function () {
            $('#send-assessment-form').validator({
                rules: {
                    check_score: function () {
                        return strings.check_score($('#tutor_score').val());
                    }
                },
                fields: {
                    '#tutor_score': '建议成绩:required;check_score',
                },
                submit_target: '#assessment-info-a-btn',
                valid: function (form) {
                    send_assessment.send_assessment_info(form);
                }
            });
        },
        init: function () {
            this.init_edit();
            this.bind_send_assessment_action();
        }
    };
    send_assessment.init();
});