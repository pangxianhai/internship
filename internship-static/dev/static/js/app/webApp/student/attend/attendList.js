define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    require('../../../../lib/date');
    var scroll = require('../../../../lib/webApp/scroll');
    require('../../header.js');
    require('../../../../lib/webApp/panel');
    var dialog = require('../../../../lib/webApp/dialog');
    var Page = require('../../../../lib/webApp/page/page');
    var strings = require('../../../../lib/strings');
    require('../../../../lib/webApp/datePicker/calendar_picker');

    var attendExamine = {
        openExaminePanel: function (targetNode) {
            var examinePanelDiv = $('#attendExaminePanel');
            this.attendExaminePanel = examinePanelDiv.panel({
                contentWrap: $('#attend_list'),
                sourcePanelSwitch: false
            });
            this.bindCloseExaminePanel(examinePanelDiv);
            this.bindExamineAction(examinePanelDiv, targetNode.attr('attendRecordDesId'));
        },

        bindExamineAction: function (examinePanelDiv, attendRecordDesId) {
            var statusList = examinePanelDiv.find('.attend_status_list ._node');
            statusList.unbind('click');
            statusList.bind('click', function () {
                var $this = $(this);
                dialog.yes_no('您确认要提交审核信息吗?', function () {
                    attendExamine.attendExamine(attendRecordDesId, $this.attr('attendStatusCode'));
                })
            });
        },

        bindCloseExaminePanel: function (examinePanelDiv) {
            var cancelBtn = examinePanelDiv.find('button[item="cancelBtn"]');
            cancelBtn.unbind('click');
            cancelBtn.bind('click', function () {
                attendExamine.attendExaminePanel.closePanel();
            });
        },
        attendExamine: function (attendRecordDesId, attendStatusCode) {
            $.ajax({
                url: '/student/attend/examine.json',
                type: 'POST',
                data: {
                    attendRecordDestId: attendRecordDesId,
                    attendStatusCode: attendStatusCode
                },
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        dialog.push_ok_message('审核成功');
                        location.reload(true);
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        }
    };

    var attendSelect = {

        openSelectPanel: function () {
            this.selectPanel = $('#attendSelectNode').panel({
                contentWrap: $('#attend_list'),
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
                attendSelect.openSelectPanel();
                $('#attendSelectForm input[type="hidden"]').val('');
                $('.cover_panel').show();
            });
        },

        bindConfirmSelectPanelAction: function () {
            $('#attendSelectNode button[item="confirm"]').click(function (e) {
                e.preventDefault();
                $('#attendSelectNode [name="desId"]').val('');
                attend_list.loadAttendList(1);
                attendSelect.selectPanel.closePanel();
            });
            $('#attendSelectNode button[item="reset"]').click(function (e) {
                e.preventDefault();
                $('#attendSelectNode').find('input').val('');
                $('#attendSelectNode').find('select').val('');
            });
        },

        bindAttendBeginDaySelectAction: function () {
            $('#attendBeginDayInput').click(function () {
                var endDayStr = $('#attendEndDayInput').val();
                var maxDate = null;
                if (strings.isNotEmpty(endDayStr)) {
                    maxDate = new Date(endDayStr);
                }
                var $this = $(this);
                $('#attendBeginSelectDatePicker').calendar({
                    min_date: null,
                    max_date: maxDate,
                    first_day: 0,
                    swipe_able: true,
                    select: function (date) {
                        $this.val(date.format('yyyy-MM-dd'));
                        $('#attendBeginSelectDatePicker').removeClass("active");
                    },
                    exceptHiddenNodes: ['attendBeginDayInput', 'attendBeginSelectDatePicker'],
                    hiddenFunction: function () {
                        $('#attendBeginSelectDatePicker').removeClass("active");
                    }
                });
                $('#attendBeginSelectDatePicker').addClass("active");
            });
        },

        bindAttendEndDaySelectAction: function () {
            $('#attendEndDayInput').click(function () {
                var beginDayStr = $('#attendBeginDayInput').val();
                var minDate = null;
                if (strings.isNotEmpty(beginDayStr)) {
                    minDate = new Date(beginDayStr);
                }
                var $this = $(this);
                $('#attendEndSelectDatePicker').calendar({
                    min_date: minDate,
                    max_date: null,
                    first_day: 0,
                    swipe_able: true,
                    select: function (date) {
                        $this.val(date.format('yyyy-MM-dd'));
                        $('#attendEndSelectDatePicker').removeClass("active");
                    },
                    exceptHiddenNodes: ['attendEndDayInput', 'attendEndSelectDatePicker'],
                    hiddenFunction: function () {
                        $('#attendEndSelectDatePicker').removeClass("active");
                    }
                });
                $('#attendEndSelectDatePicker').addClass("active");
            });
        },

        init: function () {
            this.bindOpenSelectPanelAction();
            this.bindConfirmSelectPanelAction();
            this.bindAttendBeginDaySelectAction();
            this.bindAttendEndDaySelectAction();
        }
    };

    var attend_list = {
        bindAttendRecordScroll: function () {
            $('#attend_list .attend_node .attend_record_show').each(function () {
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

        pushAttendList: function (attend_record_list) {
            var html = [];
            for (var i = 0; i < attend_record_list.length; ++i) {
                var attend = attend_record_list[i];
                var _h = [];
                var can_examine = false;
                for (var j = 0; j < attend.flowRecords.length; ++j) {
                    var flow = attend.flowRecords[j];
                    if (attend.isApply && flow.operateUserId == attend_list.user_id) {
                        can_examine = true;
                    }
                    _h.push(flow.operateName);
                }

                var can_delete = false;
                if (attend.isApply && attend.userId == attend_list.user_id) {
                    can_delete = true;
                }

                html.push('<li class="attend_node">');
                if (can_delete) {
                    html.push('<div class="operate operate_delete" can_delete="' + can_delete + '" attendRecordDesId="' + attend.desId + '">');
                    html.push('删除');
                    html.push('</div>');
                } else if (can_examine) {
                    html.push('<div class="operate operate_examine" can_examine="' + can_examine + '" attendRecordDesId="' + attend.desId + '">');
                    html.push('审核');
                    html.push('</div>');
                } else {
                    html.push('<div class="operate">');
                    html.push('</div>');
                }
                html.push('<div class="attend_record_show" has_operate="' + (can_delete || can_examine) + '">');
                html.push('<ul class="attend_record">');
                html.push('<li>' + new Date(attend.attendDay).format('yyyy-MM-dd') + '</li>');
                html.push('<li>' + attend.timeInterval.desc + '</li>');
                html.push('<li>' + attend.attendStatus.desc + '</li>');
                html.push('</ul>');
                html.push('<ul class="attend_message">');
                html.push('<li>' + attend.student.name + '</li>');
                html.push('<li>' + attend.student.studentNumber + '</li>');
                html.push('<li>' + _h.join(',') + '</li>');
                html.push('</ul>');
                html.push('</div>')
                html.push('</li>');
            }
            $('#attend_list').append(html.join(''));
            this.bindAttendRecordScroll();
            this.bindDeleteAction();
        },

        deleteAttendRecord: function (target_node) {
            dialog.yes_no('您确定要删除该签到信息吗?', function () {
                $.ajax({
                    url: '/student/attend/delete.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        attendRecordDesId: target_node.attr('attendRecordDesId')
                    },
                    beforeSend: function () {
                        dialog.dialog_load('删除中...');
                    },
                    success: function (result) {
                        dialog.close_dialog();
                        if (result.ret) {
                            dialog.push_ok_message('删除成功!');
                            target_node.parent().hide();
                        } else {
                            dialog.push_error_message(result.message);
                        }
                    }
                });
            });
        },

        bindDeleteAction: function () {
            var operateNode = $('#attend_list .attend_node .operate');
            operateNode.off('click');
            operateNode.on('click', function () {
                var $this = $(this);
                if ($this.attr('can_delete') == 'true') {
                    attend_list.deleteAttendRecord($this);
                } else if ($this.attr('can_examine') == 'true') {
                    attendExamine.openExaminePanel($this);
                }
            });
        },

        loadAttendList: function (currentPage) {
            $.ajax({
                url: '/student/attend/list.json',
                type: 'post',
                data: $.extend(strings.getFormData($('#attendSelectForm')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('正在加载签到记录...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#attend_list').html('');
                        }
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#attend_list'), attend_list.loadAttendList);
                        attend_list.pushAttendList(result.data.attendRecords);
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        init: function () {
            attend_list.user_id = $('#user_id').val();
            this.loadAttendList(1);
            attendSelect.init();
        }
    };

    attend_list.init();
});