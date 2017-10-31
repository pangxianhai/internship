define(function (require, exports, module) {
    require('../zepto');
    var touch = require('../touch');
    var strings = require('../../strings');
    require('../../date');
    var css = require('calendar_picker.css', 'css');

    var picker = {
        /**
         * 画面板年月日设置为1号
         */
        draw_date: null,
        /**
         * 选择最小日期 只取年月日
         */
        draw_min_date: null,
        /**
         * 选择最大日期 只取年月日
         */
        draw_max_date: null,
        options: {
            /**
             * @property {Date|String} [date=null] 初始化日期，默认今天
             * @namespace options
             */
            date: null,
            /**
             * @property {Number} [first_day=1] 设置新的一周从星期几开始，星期天用0表示, 星期一用1表示, 以此类推.
             * @namespace options
             */
            first_day: 1,
            /**
             * @property {Date|String} [maxDate=null] 设置可以选择的最大日期
             * @namespace options
             */
            max_date: null,
            /**
             * @property {Date|String} [minDate=null] 设置可以选择的最小日期
             * @namespace options
             */
            min_date: null,
            /**
             * @property {Boolean} [swipeable=false] 设置是否可以通过左右滑动手势来切换日历
             * @namespace options
             */
            swipe_able: false,
            /**
             * @property {Boolean} [monthChangeable=false] 设置是否让月份可选择
             * @namespace options
             */
            month_changeable: false,
            /**
             * @property {Boolean} [yearChangeable=false] 设置是否让年份可选择
             * @namespace options
             */
            year_changeable: false,

            /**
             *不隐藏按钮
             */
            exceptHiddenNodes: [],

            /**
             * 影藏面饭的方法
             */
            hiddenFunction: null
        },
        load_css: function () {
            $('head').append('<style type="text/css">' + css + '</style>');
        },

        //获取月份的天数
        get_days_in_month: function (year, month) {
            return 32 - new Date(year, month, 32).getDate();
        },

        //获取月份中的第一天是所在星期的第几天
        get_first_day_of_month: function (year, month) {
            return new Date(year, month, 1).getDay();
        },

        append_calendar_frame: function (target_node) {
            var html = [];
            html.push('<ul class="ui_calendar_picker_head">');
            if (this.options.year_changeable) {
                html.push('<li class="ui_calendar_picker_head_move"><select></select></li>');
            } else {
                html.push('<li class="ui_calendar_picker_head_move"><a  item="left" href="javascript:void(0)">&lt;&lt;</a></li>');
            }
            html.push('<li class="ui_calendar_picker_head_year">' + this.draw_date.getFullYear() + '年</li>');
            html.push('<li class="ui_calendar_picker_head_month">' + strings.format_number((this.draw_date.getMonth() + 1), 2) + '月</li>');
            if (this.options.month_changeable) {
                html.push('<li class="ui_calendar_picker_head_move"><select></select></li>');
            } else {
                html.push('<li class="ui_calendar_picker_head_move"><a item="right" href="javascript:void(0)">&gt;&gt;</a></li>');
            }
            html.push('</ul>');
            html.push('<table class="ui_calendar_picker_table">');
            html.push('<thead class="ui_calendar_picker_table_head">');
            for (var i = 0; i < 7; ++i) {
                html.push('<th>' + strings.week_show((this.options.first_day + i) % 7) + '</th>');
            }
            html.push('</thead>');
            html.push('<tbody class="ui_calendar_picker_table_tbody"></tbody>');
            html.push('</table>');
            target_node.html(html.join(''));
        },

        append_calendar_days: function (target_node) {
            target_node.find('.ui_calendar_picker_head .ui_calendar_picker_head_year').html(this.draw_date.getFullYear() + '年');
            target_node.find('.ui_calendar_picker_head .ui_calendar_picker_head_month').html(strings.format_number((this.draw_date.getMonth() + 1), 2) + '月');

            var calendar_days_body = target_node.find('.ui_calendar_picker_table .ui_calendar_picker_table_tbody');
            var days_in_month = this.get_days_in_month(this.draw_date.getFullYear(), this.draw_date.getMonth());
            var first_day_of_month = this.get_first_day_of_month(this.draw_date.getFullYear(), this.draw_date.getMonth());
            var days_before_first = (first_day_of_month + 7 - this.options.first_day) % 7;
            var row = Math.ceil((days_in_month + days_before_first) / 7);
            var print_date = new Date(this.draw_date.getFullYear(), this.draw_date.getMonth(), 1 - days_before_first);
            var html = [];
            var default_date = new Date(this.options.date.getFullYear(), this.options.date.getMonth(), this.options.date.getDate());
            for (var i = 0; i < row; ++i) {
                html.push('<tr class="ui_calendar_picker_line">');
                for (var j = 0; j < 7; ++j) {
                    if (this.draw_date.getMonth() != print_date.getMonth()) {
                        html.push('<td></td>');
                    } else {
                        var showDate = strings.format_number(print_date.getDate(), 2);
                        var class_name = '';
                        if (this.draw_min_date != null) {
                            if (print_date.getTime() < this.draw_min_date.getTime()) {
                                class_name = 'ui_calendar_disable_day';
                            }
                        }

                        if (strings.isEmpty(class_name) && this.draw_max_date != null) {
                            if (print_date.getTime() > this.draw_max_date.getTime()) {
                                class_name = 'ui_calendar_disable_day';
                            }
                        }
                        var span_class_name = "";
                        if (strings.isEmpty(class_name) && default_date.getTime() == print_date.getTime()) {
                            span_class_name = 'ui_calendar_current_day';
                        }
                        html.push('<td class="' + class_name + '"><span class="' + span_class_name + '">' + showDate + '</span></td>');
                    }
                    print_date.setDate(print_date.getDate() + 1);
                }
                html.push('</tr>');
            }
            calendar_days_body.html(html.join(''));
            this.bind_select_date(target_node);
        },

        bind_move_month: function (target_node) {
            var head_move_node = target_node.find('.ui_calendar_picker_head .ui_calendar_picker_head_move a');
            head_move_node.unbind('click');
            head_move_node.bind('click', function () {
                if ($(this).attr('item') == 'left') {
                    picker.draw_date.setMonth(picker.draw_date.getMonth() - 1);
                } else {
                    picker.draw_date.setMonth(picker.draw_date.getMonth() + 1);
                }
                picker.append_calendar_days(target_node);
            });
        },

        bind_select_date: function (target_node) {
            var day_node = target_node.find('.ui_calendar_picker_table .ui_calendar_picker_table_tbody td');
            day_node.unbind('click');
            day_node.bind('click', function () {
                var $this = $(this);
                if ($this.hasClass('ui_calendar_disable_day')) {
                    return;
                }
                if (strings.isEmpty($this.html())) {
                    return;
                }
                day_node.find('span').removeClass('ui_calendar_current_day');
                $this.find('span').addClass('ui_calendar_current_day');
                picker.options.date = new Date(picker.draw_date.getFullYear(), picker.draw_date.getMonth(), parseInt($this.find('span').html()));
                if (typeof  picker.options.select == 'function') {
                    picker.options.select(picker.options.date);
                }
            });
        },

        bind_picker_swipe_action: function (target_node) {
            if (!this.options.swipe_able) {
                return;
            }
            var picker_body = target_node.find('.ui_calendar_picker_table .ui_calendar_picker_table_tbody');

            var _touch = new touch({
                target_node: picker_body,
                end_callback: function (e) {
                    var range = e.range;
                    if (Math.abs(range.x2 - range.x1) < 90) {
                        return;
                    }
                    var swipe_type = _touch.swipe_direction(range);
                    if (swipe_type == '_left') {
                        picker.draw_date.setMonth(picker.draw_date.getMonth() + 1);
                    } else if (swipe_type == '_right') {
                        picker.draw_date.setMonth(picker.draw_date.getMonth() - 1);
                    } else {
                        return;
                    }
                    picker.append_calendar_days(target_node);
                }
            });
        },

        append_calendar_panel: function (target_node) {
            if (typeof this.options.date == 'undefined' || this.options.date == null) {
                this.options.date = new Date();
            }
            this.draw_date = new Date(this.options.date.getFullYear(), this.options.date.getMonth(), 1);
            if (this.options.min_date != null) {
                this.draw_min_date = new Date(this.options.min_date.getFullYear(), this.options.min_date.getMonth(), this.options.min_date.getDate());
            } else {
                this.draw_min_date = null;
            }
            if (this.options.max_date != null) {
                this.draw_max_date = new Date(this.options.max_date.getFullYear(), this.options.max_date.getMonth(), this.options.max_date.getDate());
            } else {
                this.draw_max_date = null;
            }

            target_node.addClass('ui_calendar_picker');
            picker.append_calendar_frame(target_node);
            picker.append_calendar_days(target_node);
            picker.bind_move_month(target_node);
            picker.bind_picker_swipe_action(target_node);
        },

        hiddenCalendar: function (nodeIdList, hiddenFunction) {
            $("body>*").bind("click", function (e) {
                var t = (e && e.target) || (event && event.srcElement);
                var canHidden = true;
                for (var i = 0; i < nodeIdList.length; ++i) {
                    if ($(t).attr('id') == nodeIdList[i] || $(t).parents('#' + nodeIdList[i]).length > 0) {
                        canHidden = false;
                        break;
                    }
                }
                if (canHidden && typeof hiddenFunction == 'function') {
                    hiddenFunction();
                }
            })
        },

        initCalendar: function () {
            $.fn['calendar'] = function (options) {
                picker.options = $.extend(picker.options, options);
                picker.append_calendar_panel(this);
                picker.hiddenCalendar(picker.options.exceptHiddenNodes, picker.options.hiddenFunction);
                return picker;
            }
        },

        init: function () {
            this.load_css();
            this.initCalendar();
        }
    };
    picker.init();

    var calendar = {
        /**
         * 解析字符串成日期格式对象。目前支持yyyy-mm-dd格式和yyyy/mm/dd格式。
         */
        parseDate: function (obj) {
            var dateRE = /^(\d{4})(?:\-|\/)(\d{1,2})(?:\-|\/)(\d{1,2})$/;
            return Object.prototype.toString.call(obj) === '[object Date]' ? obj : dateRE.test(obj) ? new Date(parseInt(RegExp.$1, 10), parseInt(RegExp.$2, 10) - 1, parseInt(RegExp.$3, 10)) : null;
        }
    }
    return calendar;
});