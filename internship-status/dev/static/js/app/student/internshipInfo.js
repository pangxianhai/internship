define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var internship_info = {
        push_student_internship_info: function (students) {
            var html = [];
            for (var i = 0; i < students.length; ++i) {
                var student = students[i];
                html.push('<tr>');
                html.push('<td>' + student.name + '</td>');
                html.push('<td>' + student.studentNumber + '</td>');
                html.push('<td><a href="/student/diaryJournal/list.htm?studentName=' + encodeURIComponent(student.name) + '&studentId=' + student.id + '">' + student.diaryJournalCount + '</a></td>');
                html.push('<td><a href="/student/weeklyJournal/list.htm?studentName=' + encodeURIComponent(student.name) + '&studentId=' + student.id + '">' + student.weeklyJournalCount + '</a></td>');
                html.push('<td>');
                if (typeof student.assessment == 'undefined') {
                    if ($('#isTutor').val()) {
                        html.push('<a href="/student/assessment/send.htm?studentId=' + student.id + '">[发放]</a>');
                    } else {
                        html.push('');
                    }
                } else {
                    html.push('<a href="/student/assessment/detail/' + student.assessment.desId + '.htm">[查看]</a>');
                }
                html.push('</td>');
                html.push('<td>');
                if (typeof student.evaluation == 'undefined') {
                    if ($('#isTeacher').val()) {
                        html.push('<a href="/student/evaluation/send/' + student.desId + '.htm">发放</a>');
                    } else {
                        html.push('');
                    }
                } else {
                    html.push('<a href="/student/evaluation/detail/studentId_' + student.desId + '.htm">[查看]</a>');
                }
                html.push('</td>');
                html.push('<td>' + student.attendedCount + '</td>');
                if (typeof student.internshipReport == 'undefined') {
                    if ($('#isStudent').val()) {
                        html.push('<td><a href="/student/report/create.htm">[生成]</a></td>');
                    } else {
                        html.push('<td></td>');
                    }
                    html.push('<td></td>');
                } else {
                    html.push('<td><a href="/student/report/detail/' + student.internshipReport.desId + '.htm">[查看]</a></td>');
                    html.push('<td>' + string.filterString(student.internshipReport.reportScoreStr, '--') + '</td>');
                }
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(internship_info.push_student_internship_info(result.data.students));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(internship_info.load_student_internship_info);
            } else {
                dialog.openSample('签到失败', result.message);
            }
        },
        load_student_internship_info: function (currentPage) {
            $.ajax({
                url: '/student/internship/info.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: internship_info.push_page_data
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                internship_info.load_student_internship_info(1);
            });
        },
        init: function () {
            this.load_student_internship_info(1);
            this.bind_search_action();
        }
    };
    internship_info.init();
});