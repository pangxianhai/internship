define(function (require, exports, module) {
    require('../../../../lib/webApp/zepto');
    require('../../../../lib/date');
    require('../../header.js');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    var calendar = require('../../../../lib/webApp/datePicker/calendar_picker');

    var attend = {
        student_attend: function (attend_day, time_interval_code) {
            $.ajax({
                url: '/student/attend/attend.json',
                type: 'post',
                data: {
                    attendDay: attend_day,
                    timeIntervalCode: time_interval_code
                },
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load("签到中...")
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        dialog.push_ok_message('签到成功');
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        /**
         * 初始化补签到日历框
         */
        init_attend_day_picker: function () {
            var max_date = new Date();
            max_date.setDate(max_date.getDate() - 1);
            var default_date = calendar.parseDate($('#attend_day').text());
            if (default_date == null) {
                default_date = max_date;
            }
            $('#attend_day_picker').calendar({
                date: default_date,
                max_date: max_date,
                first_day: 0,
                swipe_able: true,
                select: function (date) {
                    $('#attend_day').val(date.format('yyyy-MM-dd'));
                    $('#attend_day_picker').removeClass("active");
                },
                exceptHiddenNodes: ['attend_day', 'attend_day_picker'],
                hiddenFunction: function () {
                    $('#attend_day_picker').removeClass("active");
                }
            });
        },
        bind_select_attend_day_action: function () {
            $('#attend_day').click(function (e) {
                $('#attend_day_picker').addClass("active");
                e.stopPropagation();
            });
        },
        bind_attend_action: function () {
            $('.attend_btn').unbind('click');
            $('.attend_btn').bind('click', function () {
                var $this = $(this);
                if ($this.parents('.today_attend_ul').length == 0) {
                    var attend_day = $('#attend_day').val();
                    if (strings.isEmpty(attend_day) || attend_day == '选择补签日期') {
                        dialog.push_error_message('请选择补签日期');
                    } else {
                        attend.student_attend(attend_day, $this.attr('timeInterval'));
                    }
                } else {
                    attend.student_attend('', $this.attr('timeInterval'));
                }
            });
        },
        init: function () {
            this.init_attend_day_picker();
            this.bind_select_attend_day_action();
            this.bind_attend_action();
        }
    };
    attend.init();
});