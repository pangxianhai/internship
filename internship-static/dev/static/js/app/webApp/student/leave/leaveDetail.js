define(function (require, exports, module) {
    var $ = require('../../../../lib/webApp/zepto');
    // require('../../../../lib/date');
    require('../../header.js');
    require('../../../../lib/webApp/panel');
    var dialog = require('../../../../lib/webApp/dialog');
    // var strings = require('../../../../lib/strings');
    // var calendar = require('../../../../lib/webApp/datePicker/calendar_picker');

    var leave_detail = {

        push_leave_flow_data: function (flowRecords) {
            var html = [];
            html.push('<li class="flow-node -passed">提交申请</li>');
            for (var i = 0; i < flowRecords.length; ++i) {
                var flow = flowRecords[i];
                if (i == 0) {
                    if (flow.isPass) {
                        html.push('<li class="flow-node -passed-line"></li>');
                    } else {
                        html.push('<li class="flow-node -passed-to-no-pass-line"></li>');
                    }
                }
                if (flow.isPass) {
                    html.push('<li class="flow-node -passed">' + flow.operateName + '</li>');
                } else if (flow.isNeedExamine || flow.isApply) {
                    html.push('<li class="flow-node -apply">' + flow.operateName + '</li>');
                } else if (flow.isNoPass) {
                    html.push('<li class="flow-node -no-pass">' + flow.operateName + '</li>');
                }
                if (i < flowRecords.length - 1) {
                    var nexFlow = flowRecords[i + 1];
                    if (nexFlow.isPass) {
                        html.push('<li class="flow-node -passed-line"></li>');
                    } else if (flow.pass && !nexFlow.pass) {
                        html.push('<li class="flow-node -passed-to-no-pass-line"></li>');
                    } else {
                        html.push('<li class="flow-node  -no-passed-line"></li>');
                    }
                }
                if (flow.operateUserId == $('#user_id').val() && flow.isNeedExamine) {
                    $('.examine').show();
                }
            }
            $('.leave_detail .leave_flow ul').html(html.join(''));
        },

        load_leave_flow: function () {
            $.ajax({
                url: '/student/leave/getFlow.json',
                type: 'POST',
                data: {
                    leaveId: $('#leave_id').val()
                },
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        leave_detail.push_leave_flow_data(result.data);
                    } else {
                    }
                }
            });
        },

        bindOpenExaminePanelAction: function () {
            $('#examineBtn').click(function () {
                leave_detail.examinePanel = $('#examinePanel').panel({
                    contentWrap: $('.leave_detail'),
                    sourcePanelSwitch: false,
                    closeFunction: function () {
                        $('.cover_panel').hide();
                    }
                });
                $('.cover_panel').show();
            });

        },

        bindExamineAction: function () {
            $('#examinePanel .examine_list_panel ._node').click(function () {
                var $thisNode = $(this);
                dialog.yes_no('您确认要提交审核信息吗?', function () {
                    $.ajax({
                        url: '/student/leave/examine.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            leaveId: $('#leave_id').val(),
                            statusCode: $thisNode.attr('examineStatusCode')
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.push_ok_message('审核成功');
                                setTimeout(function () {
                                    location.reload(true);
                                }, 2000);
                            } else {
                                dialog.push_error_message(result.message);
                            }
                        }
                    });
                })
            });
        },

        bindExamineCancelAction: function () {
            $('#examinePanel button[item="cancelBtn"]').click(function () {
                leave_detail.examinePanel.closePanel();
            });
        },

        init: function () {
            this.load_leave_flow();
            this.bindOpenExaminePanelAction();
            this.bindExamineAction();
            this.bindExamineCancelAction();
        }
    };
    leave_detail.init();
});