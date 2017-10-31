define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    require('../../lib/date.js');
    var string = require('../../lib/strings.js');
    var page = require('../../lib/page.js');
    var WdatePicker = require('../../lib/My97DatePicker/WdatePicker.js');
    var dialog = require('../../lib/dialog.js');
    var leave_detail = {
        push_examine_btn: function () {
            $('.examine').show();
        },
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
                    leave_detail.push_examine_btn();
                }
            }
            $('.leave-flow').find('ul').html(html.join(''));
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
        bind_examine_action: function () {
            $('#examine_btn').click(function () {
                $('#examine_dialog').dialog({
                    title: '审核请假',
                    yesNo: true,
                    ok: function () {
                        dialog.close(1);
                        $.ajax({
                            url: '/student/leave/examine.json',
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                leaveId: $('#leave_id').val(),
                                statusCode: $('#studentLeaveSelect').val()
                            },
                            success: function (result) {
                                if (result.ret) {
                                    dialog.openSample('审核结果', '审核成功');
                                    setTimeout(function () {
                                        location.reload(true);
                                    }, 2000);
                                } else {
                                    dialog.openSample('审核结果', result.message);
                                }
                            }
                        });
                    }
                });
            });
        },
        init: function () {
            this.load_leave_flow();
            this.bind_examine_action();
        }
    };
    leave_detail.init();
});