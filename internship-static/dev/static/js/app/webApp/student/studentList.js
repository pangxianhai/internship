define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    var scroll = require('../../../lib/webApp/scroll');
    var dialog = require('../../../lib/webApp/dialog');
    var Page = require('../../../lib/webApp/page/page');
    var strings = require('../../../lib/strings.js');
    require('../header.js');

    var studentList = {
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

        bindStudentInfoScroll: function () {
            $('#studentList [item="studentShow"]').each(function () {
                var $this = $(this);
                if ($this.attr('has_operate') != 'true') {
                    return;
                }
                var operate_node = $this.parent().find('[item="operate"]');
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

        pushStudentInfo: function (studentInfoList) {
            var html = [];
            for (var i = 0; i < studentInfoList.length; ++i) {
                var studentInfo = studentInfoList[i];
                var operateHtml = [];
                if (this.canChangeCompany) {
                    operateHtml.push('<li class="company" item="assignCompany"><div class="assign">分配实习单位</div></li>');
                }
                if (this.canChangeTeacher) {
                    operateHtml.push('<li class="teacher" item="assignTeacher"><div class="assign">分配带队老师</div></li>');
                }
                if (this.canChangeTutor) {
                    operateHtml.push('<li class="tutor" item="assignTutor"><div class="assign">分配实习导师</div></li>');
                }
                if (this.canDeleteStudent) {
                    operateHtml.push('<li class="delete" item="delete">删除</li>');
                }
                html.push('<li class="user_node" item="studentNode" studentDesId="' + studentInfo.desId + '">');
                html.push('<div class="operate" item="operate">');
                html.push('<ul class="operate_list">' + operateHtml + '</ul>');
                html.push('</div>');
                html.push('<div class="user_show" item="studentShow"  has_operate="' + (operateHtml.length > 0) + '">');
                html.push('<ul class="user_info">');
                html.push('<li>' + studentInfo.name + '</li>');
                html.push('<li>' + studentInfo.studentNumber + '</li>');
                html.push('<li><span>' + studentInfo.userInfo.sex.desc + '</span><i>&gt;</i></li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">实习单位：</li>');
                html.push('<li class="_right">' + strings.filterString(strings.substring(studentInfo.companyName, 5), '-') + '</li>');
                html.push('<li class="_left">实习导师：</li>');
                html.push('<li class="_right">' + strings.filterString(studentInfo.tutorName, '-') + '</li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">带队老师：</li>');
                html.push('<li class="_right">' + strings.filterString(studentInfo.teacherName, '-') + '</li>');
                html.push('<li class="_left">联系方式：</li>');
                html.push('<li class="_right">' + strings.filterString(studentInfo.userInfo.phone, '-') + '</li>');
                html.push('</ul>');
                html.push('</div>');
                html.push('</li>');
                html.push('<hr/>');
            }
            return html.join('');
        },

        bindToStudentInfoDetailAction: function () {
            var studentNode = $('#studentList [item="studentNode"]');
            studentNode.off('click');
            studentNode.on('click', function () {
                window.location.href = '/webApp/student/detail/' + $(this).attr('studentDesId') + '.htm?returnUrl='
                    + encodeURIComponent(location.href);
            });
        },

        bindAssignTutorAction: function () {
            var assignTutorNode = $('#studentList [item="assignTutor"]');
            assignTutorNode.off('click');
            assignTutorNode.on('click', function (e) {
                e.stopPropagation();
                var studentNode = $(this).parents('[item="studentNode"]');
                window.location.href = '/webApp/tutor/list.htm?action=addStudent&studentDesId=' +
                    studentNode.attr('studentDesId') + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindAssignCompanyAction: function () {
            var assignTutorNode = $('#studentList [item="assignCompany"]');
            assignTutorNode.off('click');
            assignTutorNode.on('click', function (e) {
                e.stopPropagation();
                var studentNode = $(this).parents('[item="studentNode"]');
                window.location.href = '/webApp/company/list.htm?action=addStudent&studentDesId=' +
                    studentNode.attr('studentDesId') + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindAssignTeacherAction: function () {
            var assignTeacherNode = $('#studentList [item="assignTeacher"]');
            assignTeacherNode.off('click');
            assignTeacherNode.on('click', function (e) {
                e.stopPropagation();
                var studentNode = $(this).parents('[item="studentNode"]');
                window.location.href = '/webApp/teacher/list.htm?action=addStudent&studentDesId=' +
                    studentNode.attr('studentDesId') + '&returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindDeleteStudentAction: function () {
            var deleteNode = $('#studentList [item="delete"]');
            deleteNode.off('click');
            deleteNode.on('click', function (e) {
                e.stopPropagation();
                var studentDesId = $(this).parents('[item="studentNode"]').attr('studentDesId');
                dialog.yes_no('您确定要删除该学生吗?', function () {
                    studentList.deleteStudent(studentDesId, 1);
                });
            });
        },

        loadStudentInfo: function (currentPage) {
            $.ajax({
                url: '/student/list.json',
                type: 'POST',
                data: {
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('加载中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#studentList').html('');
                        }
                        var studentInfoHtml = studentList.pushStudentInfo(result.data.students);
                        $('#studentList').append(studentInfoHtml);
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#studentList'), studentList.loadStudentInfo)
                        studentList.bindStudentInfoScroll();
                        studentList.bindToStudentInfoDetailAction();
                        studentList.bindAssignTutorAction();
                        studentList.bindAssignCompanyAction();
                        studentList.bindAssignTeacherAction();
                        studentList.bindDeleteStudentAction();
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        init: function () {
            this.canChangeCompany = $('#canChangeCompany').val() == 'true';
            this.canChangeTutor = $('#canChangeTutor').val() == 'true';
            this.canChangeTeacher = $('#canChangeTeacher').val() == 'true';
            this.canDeleteStudent = $('#canDeleteStudent').val() == 'true';
            this.loadStudentInfo(1);
        }
    };
    studentList.init();
});