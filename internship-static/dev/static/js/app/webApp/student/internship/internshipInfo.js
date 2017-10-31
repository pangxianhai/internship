define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    // require('../../../../lib/webApp/panel');
    require('../../header.js');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    var studentInternship = {

        pushStudentInfo: function (student) {
            var html = [];
            html.push('<li id="studentNameNode" studentDesId="' + student.desId + '" class="_node">');
            html.push('<p class="_tips">姓名</p>');
            html.push('<p class="_content"><a>' + student.name + '</a></p>');
            html.push('<i class="_icon">&gt;</i>');
            html.push('</li>');
            html.push('<li class="_node">');
            html.push('<p class="_tips">学号</p>');
            html.push('<p class="_content">' + student.studentNumber + '</p>');
            html.push('</li>');
            html.push('<li id="diaryJournalNode" class="_node">');
            html.push('<p class="_tips">日记统计</p>');
            html.push('<p class="_content"><a>' + student.diaryJournalCount + '</a></p>');
            html.push('<i class="_icon">&gt;</i>');
            html.push('</li>');
            html.push('<li id="weeklyJournalNode" class="_node">');
            html.push('<p class="_tips">周记统计</p>');
            html.push('<p class="_content"><a>' + student.weeklyJournalCount + '</a></p>');
            html.push('<i class="_icon">&gt;</i>');
            html.push('</li>');
            html.push('<li id="attendedNode" class="_node">');
            html.push('<p class="_tips">出勤统计</p>');
            html.push('<p class="_content"><a>' + student.attendedCount + '</a></p>');
            html.push('<i class="_icon">&gt;</i>');
            html.push('</li>');
            if (typeof student.assessment != 'undefined') {
                html.push('<li id="assessmentNode" assessmentDesId="' + student.assessment.desId + '" class="_node">');
                html.push('<p class="_tips">实习单位考评表</p>');
                html.push('<p class="_content"><a>查看</a></p>');
                html.push('<i class="_icon">&gt;</i>');
                html.push('</li>');
            } else {
                html.push('<li id="assessmentNode" class="_node">');
                html.push('<p class="_tips">实习单位考评表</p>');
                if (this.canSendAssessment) {
                    html.push('<p class="_content"><a>发放</a></p>');
                    html.push('<i class="_icon">&gt;</i>');
                } else {
                    html.push('<p class="_content">--</p>');
                }
                html.push('</li>');
            }
            if (typeof student.evaluation != 'undefined') {
                html.push('<li id="evaluationNode" evaluationDesId="' + student.evaluation.desId + '"  studentDesId="' + student.desId + '" class="_node">');
                html.push('<p class="_tips">质量控制表</p>');
                html.push('<p class="_content"><a>查看</a></p>');
                html.push('<i class="_icon">&gt;</i>');
                html.push('</li>');
            } else {
                html.push('<li id="evaluationNode"  studentDesId="' + student.desId + '" class="_node">');
                html.push('<p class="_tips">质量控制表</p>');
                if (this.canSendEvaluation) {
                    html.push('<p class="_content"><a>发放</a></p>');
                    html.push('<i class="_icon">&gt;</i>');
                } else {
                    html.push('<p class="_content">--</p>');
                }
                html.push('</li>');
            }

            var reportScore = '';
            if (typeof student.internshipReport != 'undefined') {
                reportScore = student.internshipReport.reportScoreStr;
                html.push('<li id="internshipReportNode" reportDesId="' + student.internshipReport.desId + '" class="_node">');
                html.push('<p class="_tips">实习报告</p>');
                html.push('<p class="_content"><a>查看</a></p>');
                html.push('<i class="_icon">&gt;</i>');
                html.push('</li>');
            } else {
                html.push('<li id="internshipReportNode" class="_node">');
                html.push('<p class="_tips">实习报告</p>');
                if (this.canCreateReport) {
                    html.push('<p class="_content"><a>生成</a></p>');
                    html.push('<i class="_icon">&gt;</i>');
                } else {
                    html.push('<p class="_content">--</p>');
                }
                html.push('</li>');
            }

            html.push('<li class="_node _last">');
            html.push('<p class="_tips">总成绩</p>');
            html.push('<p class="_content">' + strings.filterNull(reportScore) + '</p>');
            html.push('</li>');
            $('#studentInternship').html(html.join(''));

        },

        bindDiaryJournalNodeAction: function () {
            $('#diaryJournalNode').click(function () {
                window.location.href = '/webApp/student/diaryJournal/list.htm?student.desId=' + $('#studentDesId').val() + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindWeeklyJournalNodeAction: function () {
            $('#weeklyJournalNode').click(function () {
                window.location.href = '/webApp/student/weeklyJournal/list.htm?student.desId=' + $('#studentDesId').val() + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindAttendedNodeAction: function () {
            $('#attendedNode').click(function () {
                window.location.href = '/webApp/student/attend/list.htm?student.desId=' + $('#studentDesId').val() + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindAssessmentNodeAction: function () {
            $('#assessmentNode').click(function () {
                var assessmentDesId = $(this).attr('assessmentDesId');
                if (strings.isEmpty(assessmentDesId)) {
                    if (studentInternship.canSendAssessment) {
                        window.location.href = '/webApp/student/assessment/send.htm?studentDesId=' + $('#studentDesId').val() + '&returnUrl=' + encodeURIComponent(location.href);
                    }
                } else {
                    window.location.href = '/webApp/student/assessment/detail/' + assessmentDesId + '.htm?returnUrl=' + encodeURIComponent(location.href);
                }
            });
        },

        bindEvaluationNodeAction: function () {
            $('#evaluationNode').click(function () {
                var studentDesId = $(this).attr('studentDesId');
                var evaluationDesId = $(this).attr('evaluationDesId');
                if (strings.isEmpty(evaluationDesId)) {
                    if (studentInternship.canSendEvaluation) {
                        window.location.href = '/webApp/student/evaluation/send/' + studentDesId + '.htm?returnUrl=' + encodeURIComponent(location.href);
                    }
                } else {
                    window.location.href = '/webApp/student/evaluation/detail/studentId_' + studentDesId + '.htm?returnUrl=' + encodeURIComponent(location.href);
                }
            });
        },

        bindInternshipReportNodeAction: function () {
            $('#internshipReportNode').click(function () {
                var reportDesId = $(this).attr('reportDesId');
                if (strings.isEmpty(reportDesId)) {
                    if (studentInternship.canCreateReport) {
                        window.location.href = '/webApp/student/report/create.htm?returnUrl=' + encodeURIComponent(location.href);
                    }
                } else {
                    window.location.href = '/webApp/student/report/detail/' + reportDesId + '.htm?returnUrl=' + encodeURIComponent(location.href);
                }
            });
        },

        bindStudentNameNodeAction: function () {
            $('#studentNameNode').click(function () {
                window.location.href = '/webApp/student/detail/' + $(this).attr('studentDesId') + '.htm?returnUrl=' + encodeURIComponent(location.href);
            });
        },

        loadStudentInternship: function () {
            $.ajax({
                url: '/student/internship/internshipOfStudent.json',
                type: 'post',
                dataType: 'json',
                data: {
                    studentDesId: $('#studentDesId').val()
                },
                beforeSend: function () {
                    dialog.dialog_load('加载中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        studentInternship.pushStudentInfo(result.data);
                        studentInternship.bindDiaryJournalNodeAction();
                        studentInternship.bindWeeklyJournalNodeAction();
                        studentInternship.bindAttendedNodeAction();
                        studentInternship.bindAssessmentNodeAction();
                        studentInternship.bindEvaluationNodeAction();
                        studentInternship.bindInternshipReportNodeAction();
                        studentInternship.bindStudentNameNodeAction();
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            })
        },

        init: function () {
            this.canCreateReport = $('#canCreateReport').val() == 'true';
            this.canSendAssessment = $('#canSendAssessment').val() == 'true';
            this.canSendEvaluation = $('#canSendEvaluation').val() == 'true';
            this.loadStudentInternship();
        }
    };
    studentInternship.init();
});