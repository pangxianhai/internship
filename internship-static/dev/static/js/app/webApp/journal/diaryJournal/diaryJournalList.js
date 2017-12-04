define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    require('../../../../lib/date');
    require('../../header.js');
    require('../../../../lib/webApp/panel');
    require('../../../../lib/webApp/datePicker/calendar_picker');
    var scroll = require('../../../../lib/webApp/scroll');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    var Page = require('../../../../lib/webApp/page/page');
    var diaryJournalList = {
        bindScrollAction: function () {
            $('#diary_journal_list .diary_journal_node .diary_journal_show').each(function () {
                var $this = $(this);
                if ($this.attr('has_operate') != 'true') {
                    return;
                }
                var operate_node = $this.parent().find('.operate');
                var operate_node_width = operate_node.width();
                new scroll({
                    target_node: $this,
                    h_scroll: true,
                    max_scroll: operate_node_width,
                    start_event_callback: function (e) {
                        operate_node.css('visibility', 'visible');
                    },
                    move_event_callback: function (e) {
                        var range = e.range;
                        var transX = parseFloat(document.defaultView.getComputedStyle($this.get(0)).transform.substring(7).split(",")[4]);
                        var rX = range.x1 - range.x2;
                        if (transX <= 0 && rX < 0) {
                            $this.css({'transform': 'translate(0px, 0px) scale(1) translateZ(0px)'});
                        }
                    },
                    end_callback: function (e) {
                        var range = e.range;
                        var swipe_type = e.swipe_direction(range);
                        if (swipe_type == '_left' && Math.abs(range.x1 - range.x2) >= operate_node_width / 2) {
                            $this.css({'transform': 'translate(-' + operate_node_width + 'px, 0px) scale(1) translateZ(0px)'});
                        } else {
                            $this.css({'transform': 'translate(0px, 0px) scale(1) translateZ(0px)'});
                            operate_node.css('visibility', 'hidden');
                        }
                    }
                });
            });
        },

        openSelectPanel: function () {
            this.selectPanel = $('#diaryJournalSelectNode').panel({
                contentWrap: $('#diary_journal_list'),
                sourcePanelSwitch: false,
                closeFunction: function () {
                    $('.cover_panel').hide();
                }
            });
        },

        pushStudentDiaryJournal: function (internshipNotes) {
            var html = [];
            for (var i = 0; i < internshipNotes.length; ++i) {
                var internshipNote = internshipNotes[i];
                html.push('<li class="diary_journal_node" diaryJournalDesId="' + internshipNote.desId + '" >');
                html.push('<div class="operate">删除</div>');
                // html.push('<div class="diary_journal_show" has_operate="true">');
                html.push('<div class="diary_journal_show" has_operate="false">');
                html.push('<p class="diary_journal_title">' + internshipNote.studentName + '的' + new Date(internshipNote.beginDay).format('yyyy-MM-dd') + '的实习日记</p>');
                if (strings.isEmpty(internshipNote.teacherRemark)) {
                    html.push('<i>未考评&nbsp;&gt;</i>');
                } else {
                    html.push('<i>已考评&nbsp;&gt;</i>');
                }
                html.push('<div class="diary_journal_content">' + internshipNote.notesContent + '</div>');
                html.push('</div>');
                html.push('</li>');
            }
            $('#diary_journal_list').append(html.join(''));
            this.bindDiaryJournalNode();
        },

        loadStudentDiaryJournal: function (currentPage) {
            $.ajax({
                url: '/student/diaryJournal/list.json',
                type: 'POST',
                data: $.extend(strings.getFormData($('#diaryJournalSelectForm')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('日志加载中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#diary_journal_list').html('');
                        }
                        diaryJournalList.pushStudentDiaryJournal(result.data.internshipNotes);
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#diary_journal_list'), diaryJournalList.loadStudentDiaryJournal)
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        bindDiaryJournalNode: function () {
            var node = $('#diary_journal_list .diary_journal_node');
            node.unbind('click');
            node.bind('click', function () {
                window.location.href = '/webApp/student/diaryJournal/detail/' +
                    $(this).attr('diaryJournalDesId') + '.htm?returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindOpenSelectPanelAction: function () {
            var selectBtn = $('#selectBtn');
            selectBtn.off('click');
            selectBtn.on('click', function () {
                $('#diaryJournalSelectForm input[type="hidden"]').val('');
                diaryJournalList.openSelectPanel();
                $('.cover_panel').show();
            });
        },

        bindConfirmSelectPanelAction: function () {
            $('#diaryJournalSelectNode button[item="confirm"]').click(function (e) {
                e.preventDefault();
                diaryJournalList.loadStudentDiaryJournal(1);
                diaryJournalList.selectPanel.closePanel();
            });
            $('#diaryJournalSelectNode button[item="reset"]').click(function (e) {
                e.preventDefault();
                $('#diaryJournalSelectNode').find('input').val('');
            });
        },

        bindDiaryJournalDaySelectAction: function () {
            $('#beginDayInput').click(function () {
                var $this = $(this);
                $('#diaryJournalBeginDaySelectDatePicker').calendar({
                    first_day: 0,
                    swipe_able: true,
                    select: function (date) {
                        $this.val(date.format('yyyy-MM-dd'));
                        $('#diaryJournalBeginDaySelectDatePicker').removeClass("active");
                    },
                    exceptHiddenNodes: ['beginDayInput', 'diaryJournalBeginDaySelectDatePicker'],
                    hiddenFunction: function () {
                        $('#diaryJournalBeginDaySelectDatePicker').removeClass("active");
                    }
                });
                $('#diaryJournalBeginDaySelectDatePicker').addClass("active");
            });
        },

        init: function () {
            this.loadStudentDiaryJournal(1);
            this.bindOpenSelectPanelAction();
            this.bindConfirmSelectPanelAction();
            this.bindDiaryJournalDaySelectAction();
        }
    };
    diaryJournalList.init();
});