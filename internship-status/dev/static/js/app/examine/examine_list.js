define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var string = require('../../lib/strings.js');
    var page = require('../../lib/page.js');
    var examine_list = {
        push_examine_info: function (flowRecords) {
            var html = [];
            for (var i = 0; i < flowRecords.length; ++i) {
                var record = flowRecords[i];
                html.push('<tr>');
                html.push('<td>' + record.studentName + '</td>');
                html.push('<td>' + record.flowType.desc + '</td>');
                html.push('<td>' + new Date(record.createdTime).format('yyyy-MM-dd') + '</td>');
                html.push('<td>');
                if (record.isAttend) {
                    html.push('<a href="/student/attend/list.htm?desId=' + record.targetDestId + '">[去审核]</a>');
                } else if (record.isLeave) {
                    html.push('<a href="/student/leave/detail/' + record.targetDestId + '.htm">[去审核]</a>');
                } else if (record.isWeeklyJournal) {
                    html.push('<a href="/student/weeklyJournal/detail/' + record.targetDestId + '.htm">[去考评]</a>');
                } else if (record.isAssessment) {
                    html.push('<a href="/student/assessment/detail/' + record.targetDestId + '.htm">[去查看]</a>');
                } else if (record.isReport) {
                    html.push('<a href="/student/report/detail/' + record.targetDestId + '.htm">[去考评]</a>');
                } else if (record.isDiaryJournal) {
                    html.push('<a href="/student/diaryJournal/detail/' + record.targetDestId + '.htm">[去考评]</a>');
                }
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(examine_list.push_examine_info(result.data.flowRecords));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(examine_list.load_examine_info);
            } else {

            }
        },
        load_examine_info: function (currentPage) {
            $.ajax({
                url: '/examine/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: examine_list.push_page_data
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                examine_list.load_examine_info(1);
            });
        },
        init: function () {
            this.load_examine_info(1);
            this.bind_search_action();
        }
    };
    examine_list.init();
});