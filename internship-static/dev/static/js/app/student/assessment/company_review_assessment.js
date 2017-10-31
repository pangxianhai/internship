define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    var strings = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var company_review_assessment = {
        init_edit: function () {
            UM.getEditor('company_remark', {
                imagePath: '',
                initialFrameHeight: 300,
                initialFrameWidth: 700,
                lang: 'zh-cn',
                langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                focus: true
            });
        },
        bind_assessment_review_action: function () {
            $('#assessment_review_btn').click(function () {
                $.ajax({
                    url: '/student/assessment/company_review/submit.json',
                    type: 'POST',
                    dataType: 'json',
                    data: strings.getFormData($('#assessment_form')),
                    success: function (result) {
                        if (result.ret) {
                            dialog.openSample('提示', '考评成功');
                            setTimeout(function () {
                                window.location.href = '/student/assessment/detail/' + $('#assessment_des_id').val() + '.htm';
                            }, 2000);
                        } else {
                            dialog.openSample('提示', result.message);
                        }
                    }
                });
            });
        },
        init: function () {
            this.init_edit();
            this.bind_assessment_review_action();
        }
    };
    company_review_assessment.init();
});