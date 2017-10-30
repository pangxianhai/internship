define(function (require, exports, module) {
    require('../../../../lib/webApp/zepto');
    require('../../../../lib/date');
    require('../../header.js');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    var calendar = require('../../../../lib/webApp/datePicker/calendar_picker');

    var leave_list = {

        bind_time_picker_action: function () {
            $('#begin_time').click(function () {
                $('#leave_day_picker').addClass('active');
                $('#leave_day_picker').calendar({
                    date: calendar.parseDate($('#begin_time').val()),
                    first_day: 0,
                    min_date: null,
                    max_date: calendar.parseDate($('#end_time').val()),
                    swipe_able: true,
                    select: function (date) {
                        $('#begin_time').val(date.format('yyyy-MM-dd'));
                        $('#leave_day_picker').removeClass('active');
                    }
                });
            });

            $('#end_time').click(function () {
                $('#leave_day_picker').addClass('active');
                $('#leave_day_picker').calendar({
                    date: calendar.parseDate($('#end_time').val()),
                    min_date: calendar.parseDate($('#begin_time').val()),
                    max_date: null,
                    first_day: 0,
                    swipe_able: true,
                    select: function (date) {
                        $('#end_time').val(date.format('yyyy-MM-dd'));
                        $('#leave_day_picker').removeClass('active');
                    }
                });
            });
        },

        bind_leave_action: function () {
            $('.leave_btn').click(function () {
                if (strings.isEmpty($('#begin_time').val())) {
                    dialog.push_error_message('开始时间不能为空');
                    return;
                }
                if (strings.isEmpty($('#end_time').val())) {
                    dialog.push_error_message('结束时间不能为空');
                    return;
                }
                $.ajax({
                    url: '/student/leave/ask_for_leave.json',
                    type: 'POST',
                    dataType: 'json',
                    data: strings.getFormData($('#leave_form')),
                    success: function (result) {
                        if (result.ret) {
                            dialog.push_ok_message('提交成功');
                        } else {
                            dialog.push_error_message(result.message);
                        }
                    }
                });
            });
        },

        init: function () {
            this.bind_time_picker_action();
            this.bind_leave_action();
        }
    };
    leave_list.init();
});