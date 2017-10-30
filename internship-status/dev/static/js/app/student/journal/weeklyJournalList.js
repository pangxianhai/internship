define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/date.js');
    var string = require('../../../lib/strings.js');
    var page = require('../../../lib/page.js');
    var WdatePicker = require('../../../lib/My97DatePicker/WdatePicker.js');
    var weekly_journal_list = {
        data: {
            is_teacher: false,
            is_tutor: false
        },
        push_notes_info: function (internshipNotes) {
            var html = [];
            for (var i = 0; i < internshipNotes.length; ++i) {
                var notes = internshipNotes[i];
                html.push('<tr>');
                html.push('<td>' + new Date(notes.beginDay).format('yyyy-MM-dd') + '至' + new Date(notes.endDay).format('yyyy-MM-dd') + '</td>');
                html.push('<td>' + notes.studentName + '</td>');
                html.push('<td>' + notes.student.studentNumber + '</td>');
                if (weekly_journal_list.data.is_teacher || weekly_journal_list.data.is_tutor) {
                    html.push('<td>');
                    if (weekly_journal_list.data.is_teacher && string.isEmpty(notes.teacherRemark)) {
                        html.push('未考评');
                    } else if (weekly_journal_list.data.is_tutor && string.isEmpty(notes.tutorRemark)) {
                        html.push('未考评');
                    } else {
                        html.push('-');
                    }
                    html.push('</td>');
                }
                html.push('<td><a href="/student/weeklyJournal/detail/' + notes.desId + '.htm">[查看详情]</a></td>')
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(weekly_journal_list.push_notes_info(result.data.internshipNotes));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(weekly_journal_list.load_journal_info);
            } else {

            }
        },
        load_journal_info: function (currentPage) {
            $.ajax({
                url: '/student/weeklyJournal/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: weekly_journal_list.push_page_data
            });
        },
        bind_search_action: function () {
            $('#search_btn').click(function () {
                $('#search_form input[type="hidden"]').val(''); //清除影藏域的搜索条件
                weekly_journal_list.load_journal_info(1);
            });
        },
        init: function () {
            this.data.is_teacher = $('#is_teacher').val() == 'true';
            this.data.is_tutor = $('#is_tutor').val() == 'true';
            this.load_journal_info(1);
            this.bind_search_action();
        }
    };
    weekly_journal_list.init();
});