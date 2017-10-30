define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    var dialog = require('../../../../lib/webApp/dialog');
    var page = require('../../../../lib/webApp/page/page');
    var strings = require('../../../../lib/strings');
    require('../../header.js');
    var internshipList = {
        pushInternshipInfo: function (students) {
            var html = [];
            for (var i = 0; i < students.length; i++) {
                var student = students[i];
                html.push('<li class="internship_node" studentDesId="' + student.desId + '">');
                html.push('<ul class="internship_record">');
                html.push('<li>' + student.name + '</li>');
                html.push('<li>' + student.studentNumber + '</li>');
                html.push('<li>');
                html.push('<span class="_score">');
                if (typeof student.internshipReport == 'undefined') {
                    html.push('--');
                } else {
                    html.push(strings.filterString(student.internshipReport.reportScoreStr, '--'));
                }
                html.push('</span>');
                html.push('<i>&gt;</i>');
                html.push('</li>');
                html.push('</ul>');
                html.push('<ul class="internship_message">');
                html.push('<li>日记:' + student.diaryJournalCount + '篇</li>');
                html.push('<li>周记:' + student.weeklyJournalCount + '篇</li>');
                html.push('<li>出勤:' + student.attendedCount + '天</li>');
                html.push('</ul>');
                html.push('</li>');
            }
            return html.join('');
        },

        bindInternshipToDetailAction: function () {
            var internshipNode = $('#studentInternship .internship_node');
            internshipNode.off('click');
            internshipNode.on('click', function () {
                window.location.href = ' /webApp/student/internship/info.htm?studentDesId=' +
                    $(this).attr('studentDesId') + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },
        loadInternshipInfoList: function (currentPage) {
            $.ajax({
                url: '/student/internship/info.json',
                type: 'POST',
                data: {
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('加载中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        var html = internshipList.pushInternshipInfo(result.data.students);
                        var internshipInfoNode = $('#studentInternship');
                        if (currentPage <= 1) {
                            internshipInfoNode.html('');
                        }
                        internshipInfoNode.append(html);
                        internshipList.bindInternshipToDetailAction();
                        var pageInfo = result.data.page;
                        if (pageInfo.totalPage > 1) {
                            new page(pageInfo.currentPage, pageInfo.totalPage, internshipInfoNode, internshipList.loadInternshipInfoList);
                        }
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },
        init: function () {
            this.loadInternshipInfoList(1);
        }
    };
    internshipList.init();
});