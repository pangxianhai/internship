define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/ueditor/umeditor.config.js');
    require('../../../lib/ueditor/umeditor.js');
    require('../../../lib/validator.js');
    var dialog = require('../../../lib/dialog.js');
    var strings = require('../../../lib/strings.js');
    var print = require('../../../lib/print.js');
    var internship_report = {
        action: '',
        load_umeditor: function () {
            if (this.action == 'teacherReview') {
                UM.getEditor('teacher_review_div', {
                    imagePath: '',
                    initialFrameHeight: 300,
                    initialFrameWidth: 648,
                    lang: 'zh-cn',
                    langPath: UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
                    focus: true
                });
            }
        },
        student_create_internship_report: function () {
            $.ajax({
                url: '/student/report/create.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    subject: $('#subject_input').val()
                },
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '创建成功');
                        setTimeout(function () {
                            window.location.href = '/student/report/detail/' + result.data + '.htm';
                        }, 1000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },
        teacher_review: function () {
            $.ajax({
                url: '/student/report/teacher_review.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    desId: $('#desId').val(),
                    teacherReview: $('#teacher_review_div').html(),
                    reportScore: $('#reportScoreStr').val()
                },
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '考评成功');
                        setTimeout(function () {
                            location.reload(true);
                        }, 1000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },
        load_fields: function () {
            if (internship_report.action == 'create') {
                return {
                    '#subject_input': '题目:required;length[1~50]'
                }
            } else if (internship_report.action == 'teacherReview') {
                return {
                    '#reportScoreStr': '成绩:required;check_score'
                }
            }
        },
        bind_report_submit: function () {
            $('.report-panel').validator({
                rules: {
                    check_score: function (reportScore) {
                        return strings.check_score(reportScore);
                    }
                },
                fields: internship_report.load_fields(),
                submit_target: '#report-submit',
                valid: function (report_panel) {
                    if (internship_report.action == 'create') {
                        internship_report.student_create_internship_report();
                    } else if (internship_report.action == 'teacherReview') {
                        internship_report.teacher_review();
                    }
                }
            });
        },
        bind_print_action: function () {
            $('#print').unbind('click');
            $('#print').bind('click', function () {
                print.print($(this), $('.report-panel'), function () {
                    internship_report.init();
                });
            });
        },
        init: function () {
            this.action = $('#action').val();
            this.bind_report_submit();
            this.load_umeditor();
            this.bind_print_action();
        }
    };
    internship_report.init();
});