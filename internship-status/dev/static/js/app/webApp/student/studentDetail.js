define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../../../lib/webApp/panel');
    require('../header.js');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings');
    var studentDetail = {

        deleteStudent: function (destIds, total_count) {
            $.ajax({
                url: '/student/delete.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    destIds: destIds
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (result.data < total_count) {
                            dialog.push_ok_message('部分学生删除未成功，您这个只删除了' + result.data + '个学生');
                        } else {
                            dialog.push_ok_message('提示', '操作成功');
                        }
                        location.reload(true);
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        bindDeleteStudentAction: function () {
            var deleteNode = $('#deleteStudentBtn');
            deleteNode.click(function () {
                dialog.yes_no('您确定要删除该学生吗?', function () {
                    studentDetail.deleteStudent($('#studentDesId').val(), 1);
                });
            });
        },

        init: function () {
            this.bindDeleteStudentAction();
        }
    };
    studentDetail.init();
});