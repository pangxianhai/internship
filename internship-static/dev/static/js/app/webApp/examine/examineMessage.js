define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../../../lib/date.js');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings');
    var page = require('../../../lib/webApp/page/page');
    require('../header.js');
    var examineMessage = {
        pushExamineInfo: function (flowRecords) {
            var html = [];
            for (var i = 0; i < flowRecords.length; ++i) {
                var flowRecord = flowRecords[i];
                var examineLink = '';
                if (flowRecord.isAttend) {
                    examineLink = '/webApp/student/attend/list.htm?desId=' + flowRecord.targetDestId
                        + '&returnUrl=' + encodeURIComponent(location.href);
                } else if (flowRecord.isLeave) {
                    examineLink = '/webApp/student/leave/detail/' + flowRecord.targetDestId + '.htm' +
                        '?returnUrl=' + encodeURIComponent(location.href);
                } else if (flowRecord.isWeeklyJournal) {
                    examineLink = '/webApp/student/weeklyJournal/detail/' + flowRecord.targetDestId + '.htm' +
                        '?returnUrl=' + encodeURIComponent(location.href);
                } else if (flowRecord.isAssessment) {
                    examineLink = '/webApp/student/assessment/detail/' + flowRecord.targetDestId + '.htm' +
                        '?returnUrl=' + encodeURIComponent(location.href);
                } else if (flowRecord.isReport) {
                    examineLink = '/webApp/student/report/detail/' + flowRecord.targetDestId + '.htm' +
                        '?returnUrl=' + encodeURIComponent(location.href);
                } else if (flowRecord.isDiaryJournal) {
                    examineLink = '/webApp/student/diaryJournal/detail/' + flowRecord.targetDestId + '.htm' +
                        '?returnUrl=' + encodeURIComponent(location.href);
                }
                html.push('<li class="examine_node" examineLink="' + examineLink + '">');
                html.push('<ul class="examine_node_ul">');
                html.push('<li>' + new Date(flowRecord.createdTime).format('yyyy-MM-dd') + '</li>');
                html.push('<li>' + flowRecord.studentName + '</li>');
                html.push('<li><span>' + flowRecord.flowType.desc + '</span><i>&gt;</i></li>');
                html.push('</ul>');
                html.push('</li>');
            }
            return html.join('');
        },
        bindToExamineAction: function () {
            var examineNode = $('#examine_list .examine_node');
            examineNode.off('click');
            examineNode.on('click', function () {
                var examineLink = $(this).attr('examineLink');
                if (strings.isNotEmpty(examineLink)) {
                    window.location.href = examineLink;
                }
            });
        },
        loadExamineInfo: function (currentPage) {
            $.ajax({
                url: '/examine/list.json',
                type: 'POST',
                data: {
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('正在加载待审核信息...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#examine_list').html('');
                        }
                        $('#examine_list').append(examineMessage.pushExamineInfo(result.data.flowRecords));
                        var pageInfo = result.data.page;
                        if (pageInfo.totalPage > 1) {
                            new page(pageInfo.currentPage, pageInfo.totalPage, $('#examine_list'), examineMessage.loadExamineInfo);
                        }
                        examineMessage.bindToExamineAction();
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },
        init: function () {
            this.loadExamineInfo(1);
        }
    };

    examineMessage.init();
});
