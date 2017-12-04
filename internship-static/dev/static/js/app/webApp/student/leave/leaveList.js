define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    require('../../../../lib/date');
    require('../../header.js');
    var dialog = require('../../../../lib/webApp/dialog');
    var strings = require('../../../../lib/strings');
    require('../../../../lib/webApp/datePicker/calendar_picker');
    var scroll = require('../../../../lib/webApp/scroll');
    var Page = require('../../../../lib/webApp/page/page');
    require('../../../../lib/webApp/panel');

    var leave_list = {

        push_leave_list_data: function (leave_records) {
            var html = [];
            for (var i = 0; i < leave_records.length; ++i) {
                var leave = leave_records[i];
                var can_delete = false;
                if (leave_list.user_id == leave.userId && leave.isApplying) {
                    can_delete = true;
                }
                html.push('<li class="leave_node"  leave_record_des_id="' + leave.desId + '">');
                if (can_delete) {
                    html.push('<div class="operate operate_delete" leave_record_des_id="' + leave.desId + '">删除</div>');
                } else {
                    html.push('<div class="operate"></div>');
                }
                html.push('<div class="leave_node_show" has_operate="' + can_delete + '" >');
                html.push('<ul class="leave_record">');
                html.push('<li>' + new Date(leave.beginDay).format('yyyy-MM-dd') + leave.beginTimeInterval.desc + '</li>');
                html.push('<li>' + new Date(leave.endDay).format('yyyy-MM-dd') + leave.endTimeInterval.desc + '</li>');
                html.push('<li>');
                html.push(leave.leaveStatus.desc);
                html.push('</li>');
                html.push('</ul>');
                html.push('<ul class="leave_message">');
                html.push('<li>' + leave.student.name + '</li>');
                html.push('<li>' + leave.leaveType.desc + '</li>');
                html.push('<li>' + strings.substring(leave.reason, 8) + '</li>');
                html.push('</ul>');
                html.push('<i></i>');
                html.push('</div>')
                html.push('</li>');
            }
            $('#leave_list').append(html.join(''));
            this.bind_scroll_action();
            this.bind_delete_action();
            this.bind_leave_to_detail();
        },

        bind_delete_action: function () {
            $('#leave_list .leave_node .operate').unbind('click');
            $('#leave_list .leave_node .operate').bind('click', function () {
                var $this = $(this);
                if ($this.hasClass('operate_delete')) {
                    leave_list.delete_leave_record($this);
                }
            });
        },

        delete_leave_record: function (target_node) {
            dialog.yes_no('您确定要删除该请假信息吗?', function () {
                $.ajax({
                    url: '/student/leave/delete.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        leaveDesId: target_node.attr('leave_record_des_id')
                    },
                    success: function (result) {
                        if (result.ret) {
                            dialog.push_ok_message('操作成功');
                            target_node.parent().hide();
                        } else {
                            dialog.push_error_message(result.message);
                        }
                    }
                });
            });
        },

        bind_scroll_action: function () {
            $('#leave_list .leave_node .leave_node_show').each(function () {
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

        bind_leave_to_detail: function () {
            $('#leave_list .leave_node').off('click');
            $('#leave_list .leave_node').on('click', function () {
                window.location.href = '/webApp/student/leave/detail/' + $(this).attr('leave_record_des_id')
                    + '.htm?returnUrl=' + encodeURIComponent(location.href);
            });
        },

        load_leave_list: function (current_page) {
            $.ajax({
                url: '/student/leave/list.json',
                type: 'POST',
                data: $.extend(strings.getFormData($('#leaveSelectForm')), {
                    currentPage: current_page
                }),
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('正在加载请假记录...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (current_page <= 1) {
                            $('#leave_list').html('');
                        }
                        leave_list.push_leave_list_data(result.data.leaveRecords);
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#leave_list'), leave_list.load_leave_list);
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        openSelectPanel: function () {
            this.selectPanel = $('#leaveSelectNode').panel({
                contentWrap: $('#leave_list_panel'),
                sourcePanelSwitch: false,
                closeFunction: function () {
                    $('.cover_panel').hide();
                }
            });
        },

        bindOpenSelectPanelAction: function () {
            
            var selectBtn = $('#selectBtn');
            selectBtn.off('click');
            selectBtn.on('click', function () {
                leave_list.openSelectPanel();
                $('.cover_panel').show();
            });
        },

        bindConfirmSelectPanelAction: function () {
            $('#leaveSelectNode button[item="confirm"]').click(function (e) {
                e.preventDefault();
                leave_list.load_leave_list(1);
                leave_list.selectPanel.closePanel();
            });
            $('#leaveSelectNode button[item="reset"]').click(function (e) {
                e.preventDefault();
                $('#leaveSelectNode').find('input').val('');
                $('#leaveSelectNode').find('select').val('');
            });
        },

        init: function () {
            this.user_id = $('#user_id').val();
            this.load_leave_list(1);
            this.bindOpenSelectPanelAction();
            this.bindConfirmSelectPanelAction();
        }
    };

    leave_list.init();
});