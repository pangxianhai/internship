define(function (require) {
    require('../../../lib/jquery-1.11.3');
    require('../../menu.js');
    require('../../../lib/date.js');
    var page = require('../../../lib/page.js');
    var string = require('../../../lib/strings.js');
    var dialog = require('../../../lib/dialog.js');
    var WdatePicker = require('../../../lib/My97DatePicker/WdatePicker.js');
    var journal_list = {
        data: {
            is_teacher: false
        },
        push_notes_info: function (internshipNotes) {
            var html = [];
            for (var i = 0; i < internshipNotes.length; ++i) {
                var notes = internshipNotes[i];
                html.push('<tr>');
                html.push('<td>' + new Date(notes.beginDay).format('yyyy-MM-dd') + '</td>');
                html.push('<td>' + notes.studentName + '</td>');
                html.push('<td>' + notes.student.studentNumber + '</td>');
                if (string.isEmpty(notes.teacherRemark)) {
                    html.push('<td>未考评</td>');
                } else {
                    html.push('<td class="has_examine">已考评</td>');
                }
                html.push('<td><a href="/student/diaryJournal/detail/' + notes.desId + '.htm">[查看详情]</a></td>')
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(journal_list.push_notes_info(result.data.internshipNotes));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(journal_list.load_journal_info);
            } else {

            }
        },
        load_journal_info: function (currentPage) {
            $.ajax({
                url: '/student/diaryJournal/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: journal_list.push_page_data
            });
        },
        bind_day_action: function () {
            $('[name="beginDayStr"]').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});
            });
        },
        bind_search_action: function () {
            $('#search_btn').click(function () {
                $('#search_form input[type="hidden"]').val(''); //清除影藏域的搜索条件
                journal_list.load_journal_info(1);
            });
        },
        init: function () {
            this.data.is_teacher = $('#is_teacher').val() == 'true';
            this.load_journal_info(1);
            this.bind_day_action();
            this.bind_search_action();
        }
    };
    journal_list.init();
});