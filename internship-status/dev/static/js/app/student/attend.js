define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var WdatePicker = require('../../lib/My97DatePicker/WdatePicker.js');
    var dialog = require('../../lib/dialog.js');
    var string = require('../../lib/strings.js');
    var page = require('../../lib/page.js');
    var attend = {
        data: {
            is_tutor: false,
            is_student: false
        },
        bind_attend_day_action: function () {
            $('#attendDay').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});
            });
            $('[name="attendBeginDayStr"]').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});
            });
            $('[name="attendEndDayStr"]').click(function () {
                WdatePicker.WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    minDate: $('[name="attendBeginDayStr"]').val(),
                    maxDate: '%y-%M-%d'
                });
            });
        },
        attend_submit: function (attendDay, timeIntervalCode) {
            $.ajax({
                url: '/student/attend/attend.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    attendDay: attendDay,
                    timeIntervalCode: timeIntervalCode
                },
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '签到成功');
                        setTimeout(function () {
                            dialog.close(1);
                            attend.load_student_attend_info(1);
                        }, 1000);
                    } else {
                        dialog.openSample('签到失败', result.message);
                    }
                }
            });
        },
        bind_attend_action: function () {
            $('.today-attend').find('.btn45').click(function () {
                attend.attend_submit('', this.getAttribute('timeInterval'));
            });
            $('.history-attend').find('.btn45').click(function () {
                var attendDay = $('#attendDay').val();
                if (string.isEmpty(attendDay)) {
                    dialog.openSample('提示', '请选择补签的日期');
                    return;
                }
                attend.attend_submit(attendDay, this.getAttribute('timeInterval'));
            });
        },
        push_student_attend: function (attendRecords) {
            var html = [];
            for (var i = 0; i < attendRecords.length; ++i) {
                var attend = attendRecords[i];
                html.push('<tr>');
                html.push('<td item="student_name">' + attend.student.name + '</td>');
                html.push('<td>' + attend.student.studentNumber + '</td>');
                html.push('<td item="attendDay">' + new Date(attend.attendDay).format('yyyy-MM-dd') + '</td>');
                html.push('<td item="timeInterval">' + attend.timeInterval.desc + '</td>');
                html.push('<td>' + attend.attendStatus.desc + '</td>');
                html.push('<td>');
                var _h = [];
                var can_operate = false;
                for (var j = 0; j < attend.flowRecords.length; ++j) {
                    var flow = attend.flowRecords[j];
                    if (flow.operateUserId == $('#userId').val()) {
                        can_operate = true;
                    }
                    _h.push(flow.operateName);
                }
                html.push(_h.join(','));
                html.push('</td>');
                html.push('<td>');
                if (this.data.is_tutor) {
                    if (can_operate && attend.isApply) {
                        html.push('<a item="examine" attendRecordDestId="' + attend.desId + '" href="javascript:void(0)">审核</a>');
                    }
                }
                if (this.data.is_student && attend.isApply) {
                    html.push('<a item="delete" attendRecordDestId="' + attend.desId + '" href="javascript:void(0)">删除</a>');
                }
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(attend.push_student_attend(result.data.attendRecords));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(attend.load_student_attend_info);
                attend.bind_examine_action();
                attend.bind_delete_attend_record_action();
            } else {
                dialog.openSample("提示", result.message);
            }
        },
        load_student_attend_info: function (currentPage) {
            $.ajax({
                url: '/student/attend/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: attend.push_page_data
            });
        },

        bind_examine_action: function () {
            $('a[item="examine"]').click(function () {
                var attendRecordDestId = this.getAttribute('attendRecordDestId');
                var tr = $(this).parent().parent();
                $('#examine_dialog').find('.notice').html('你正在审核' + tr.find('td[item="student_name"]').html() +
                    tr.find('td[item="attendDay"]').html() + tr.find('td[item="timeInterval"]').html() + '的签到结果');
                $('#examine_dialog').dialog({
                    title: '',
                    yesNo: true,
                    ok: function () {
                        dialog.close(1);
                        $.ajax({
                            url: '/student/attend/examine.json',
                            type: 'POST',
                            data: {
                                attendRecordDestId: attendRecordDestId,
                                attendStatusCode: $('#attendStatusSelect').val()
                            },
                            dataType: 'json',
                            success: function (result) {
                                if (result.ret) {
                                    dialog.openSample('系统提示', '审核成功');
                                    dialog.close(2000);
                                    attend.load_student_attend_info(1);
                                } else {
                                    dialog.openSample('系统提示', result.message);
                                }
                            }
                        });
                    }
                });
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                $('[name="desId"]').val("");
                attend.load_student_attend_info(1);
            });
        },
        bind_delete_attend_record_action: function () {
            $('a[item="delete"]').unbind('click');
            $('a[item="delete"]').bind('click', function () {
                var attendRecordDesId = $(this).attr('attendRecordDestId');
                dialog.yesNo('提示', '您确定要删除该签到信息吗?', function () {
                    $.ajax({
                        url: '/student/attend/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            attendRecordDesId: attendRecordDesId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '删除成功!');
                                attend.load_student_attend_info(page.current_page());
                                setTimeout(function () {
                                    dialog.close(1);
                                }, 2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    })
                });
            });
        },
        init: function () {
            this.data.is_tutor = $('#is_tutor').val() == 'true';
            this.data.is_student = $('#is_student').val() == 'true';
            this.load_student_attend_info(1);
            this.bind_attend_day_action();
            this.bind_attend_action();
            this.bind_search_action();
        }
    }
    attend.init();
});