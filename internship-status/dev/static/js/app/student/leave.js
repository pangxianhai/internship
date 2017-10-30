define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    require('../../lib/date.js');
    var string = require('../../lib/strings.js');
    var page = require('../../lib/page.js');
    var WdatePicker = require('../../lib/My97DatePicker/WdatePicker.js');
    var dialog = require('../../lib/dialog.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var leave = {
        data: {
            is_student: false
        },
        bind_leave_day_action: function () {
            $('#beginDay').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $('#endDay').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', minDate: $('#beginDay').val()});
            });
        },
        bind_ask_for_leave_action: function () {
            $('.student-leave').validator({
                fields: {
                    '#beginDay': '开始时间:required',
                    '#endDay': '结束时间:required',
                    '#reason': '请假原因:required'
                },
                ok: function (node) {
                    node.removeClass("error");
                },
                error: function (node, msg) {
                    node.addClass("error");
                },
                submit_target: '#student-leave-btn',
                valid: function (form) {
                    $.ajax({
                        url: '/student/leave/ask_for_leave.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            beginDay: $('#beginDay').val(),
                            beginTime: $('#beginTime').val(),
                            endDay: $('#endDay').val(),
                            endTime: $('#endTime').val(),
                            leaveType: $('#leaveType').val(),
                            reason: $('#reason').val()
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '提交成功');
                                leave.load_student_leave_info(page.current_page());
                                dialog.close(2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                }
            });
        },
        push_student_leave: function (leaveRecords) {
            var html = [];
            for (var i = 0; i < leaveRecords.length; ++i) {
                var leave_record = leaveRecords[i];
                html.push('<tr>');
                html.push('<td>' + leave_record.student.name + '</td>');
                html.push('<td>' + leave_record.student.studentNumber + '</td>');
                html.push('<td>' + new Date(leave_record.beginDay).format('yyyy-MM-dd') + leave_record.beginTimeInterval.desc + '</td>');
                html.push('<td>' + new Date(leave_record.endDay).format('yyyy-MM-dd') + leave_record.endTimeInterval.desc + '</td>');
                html.push('<td>' + leave_record.leaveType.desc + '</td>');
                html.push('<td title="' + string.filterNull(leave.reason) + '">' + string.substring(leave_record.reason, 5) + '</td>');
                html.push('<td>' + leave_record.leaveStatus.desc + '</td>');
                html.push('<td>');
                html.push('<a href="/student/leave/detail/' + leave_record.desId + '.htm">[查看详情]</a>');
                if (leave.data.is_student && leave_record.isApplying) {
                    html.push('<br/>');
                    html.push('<a item="delete" leaveDesId="' + leave_record.desId + '" href="javascript:void(0)">[删除]</a>');
                }
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(leave.push_student_leave(result.data.leaveRecords));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(leave.load_student_leave_info);
                leave.bind_delete_leave_action();
            } else {

            }
        },
        load_student_leave_info: function (currentPage) {
            $.ajax({
                url: '/student/leave/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: leave.push_page_data
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                leave.load_student_leave_info(1);
            });
        },
        bind_delete_leave_action: function () {
            $('a[item="delete"]').unbind('click');
            $('a[item="delete"]').bind('click', function () {
                var leaveDesId = $(this).attr('leaveDesId');
                dialog.yesNo('提示', '您确定要删除该条请假记录吗?', function () {
                    $.ajax({
                        url: '/student/leave/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            leaveDesId: leaveDesId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '操作成功');
                                leave.load_student_leave_info(page.current_page());
                                dialog.close(2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                });
            });
        },
        init: function () {
            this.data.is_student = $('#isStudent').val() == 'true';
            this.bind_leave_day_action();
            this.bind_ask_for_leave_action();
            this.load_student_leave_info(1);
            this.bind_search_action();
        }
    };
    leave.init();
});