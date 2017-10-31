define(function (require, exports, module) {
    require('../../../lib/webApp/zepto');
    require('../header.js');
    var studentMain = {
        bindToAttendAction: function () {
            $('#attendButton').click(function () {
                window.location.href = '/webApp/student/attend/attend.htm?returnUrl='
                    + encodeURIComponent(location.href);
            });
        },
        bindToLeaveAction: function () {
            $('#leaveButton').click(function () {
                window.location.href = '/webApp/student/leave/leave.htm?returnUrl='
                    + encodeURIComponent(location.href);
            });
        },
        bindToMyInfoAction: function () {
            $('.student_main_head').click(function () {
                window.location.href = '/webApp/student/studentInfo.htm?returnUrl='
                    + encodeURIComponent(location.href);
            });
        },
        init: function () {
            this.bindToAttendAction();
            this.bindToLeaveAction();
            this.bindToMyInfoAction();
        }
    };
    studentMain.init();
});