define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    require('../../../../lib/date');
    require('../../header.js');
    var dialog = require('../../../../lib/webApp/dialog');
    var Strings = require('../../../../lib/strings');
    var Page = require('../../../../lib/webApp/page/page');

    var weeklyJournalList = {
        pushWeeklyJournalInfo: function (internshipNotes) {
            var html = [];
            for (var i = 0; i < internshipNotes.length; ++i) {
                var notes = internshipNotes[i];
                html.push('<li class="weekly_journal_node" weeklyJournalDesId="' + notes.desId + '">');
                html.push('<div class="operate">删除</div>');
                html.push('<div class="weekly_journal_show" has_operate="false">');
                html.push('<p class="weekly_journal_title">' + notes.studentName + '的' + new Date(notes.beginDay).format('yyyy-MM-dd') + '至' + new Date(notes.endDay).format('yyyy-MM-dd') + '的实习周记</p>');
                html.push('<i>&gt;</i>');
                html.push('<div class="weekly_journal_content">' + notes.notesContent + '</div>');
                html.push('</div>')
                html.push('</li>');
            }
            $('#weeklyJournalList').append(html.join(''));
            this.bindWeeklyJournalNode();
        },

        bindWeeklyJournalNode: function () {
            var weeklyJournalNode = $('#weeklyJournalList .weekly_journal_node');
            weeklyJournalNode.off('click');
            weeklyJournalNode.on('click', function () {
                window.location.href = '/webApp/student/weeklyJournal/detail/' + $(this).attr('weeklyJournalDesId')
                    + '.htm?returnUrl=' + encodeURIComponent(location.href);
            });
        },

        loadWeeklyJournalInfo: function (currentPage) {
            $.ajax({
                url: '/student/weeklyJournal/list.json',
                type: 'POST',
                data: $.extend(Strings.getFormData($('#weeklyJournalSearchForm')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('周记志加载中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#weeklyJournalList').html('');
                        }
                        weeklyJournalList.pushWeeklyJournalInfo(result.data.internshipNotes);
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#weeklyJournalList'), weeklyJournalList.loadWeeklyJournalInfo);
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },
        init: function () {
            this.loadWeeklyJournalInfo(1);
        }
    };
    weeklyJournalList.init();
});