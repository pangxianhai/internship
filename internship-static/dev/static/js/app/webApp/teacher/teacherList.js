define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../header.js');
    var Scroll = require('../../../lib/webApp/scroll');
    var Dialog = require('../../../lib/webApp/dialog');
    var Page = require('../../../lib/webApp/page/page');
    var Strings = require('../../../lib/strings.js');

    var teacherList = {

        data: {
            action: '',
            studentDesId: ''
        },

        bindScrollAction: function () {
            $('#teacherList [item="teacherShow"]').each(function () {
                var $this = $(this);
                if ($this.attr('has_operate') != 'true') {
                    return;
                }
                var operate_node = $this.parent().find('[item="operate"]');
                var operate_node_width = operate_node.width();
                new Scroll({
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

        buildTeacherInfoHtml: function (teachers) {
            var html = [];
            for (var i = 0; i < teachers.length; ++i) {
                var teacher = teachers[i];
                var operateHtml = [];
                if (this.data.action == 'addStudent') {
                    operateHtml.push('<li class="tutor" item="assignTeacher"><div class="assign">分配带队老师</div></li>');
                }
                if (this.data.isSysadmin) {
                    operateHtml.push('<li item="deleteTeacher" class="_delete">删除</li>');
                }
                html.push('<li  class="user_node" item="teacherNode" teacherName="' + teacher.name + '" teacherDesId="' + teacher.desId + '">');
                html.push('<div class="operate" item="operate">');
                html.push('<ul class="operate_list">' + operateHtml.join('') + '</ul>');
                html.push('</div>');
                html.push('<div class="user_show" item="teacherShow" has_operate="' + (operateHtml.length > 0) + '">');
                html.push('<ul class="user_info">');
                html.push('<li>' + teacher.name + '</li>');
                html.push('<li>' + teacher.teacherType.desc + '</li>');
                html.push('<li><span>' + Strings.filterString(teacher.rank, ' ') + '</span><i>&gt;</i></li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">实习人数：</li>');
                html.push('<li class="_right">' + Strings.filterString(teacher.studentNumber, '0') + '</li>');
                html.push('<li class="_left">联系方式：</li>');
                html.push('<li class="_right">' + Strings.filterString(teacher.userInfo.phone, '-') + '</li>');
                html.push('</ul>');
                html.push('</div>');
                html.push('</li>');
                html.push('<hr/>');
            }
            return html.join('');
        },

        bindToTeacherDetailAction: function () {
            var studentNode = $('#teacherList [item="teacherNode"]');
            studentNode.off('click');
            studentNode.on('click', function () {
                window.location.href = '/webApp/teacher/detail/' + $(this).attr('teacherDesId') + '.htm?returnUrl='
                    + encodeURIComponent(location.href);
            });
        },

        bindAssignTeacherAction: function () {
            var assignTeacherNode = $('#teacherList [item="assignTeacher"]');
            assignTeacherNode.off('click');
            assignTeacherNode.on('click', function (e) {
                e.stopPropagation();
                var thisTeacher = $(this).parents('.user_node');
                Dialog.yes_no('你确定将该学生分配给' + thisTeacher.attr('teacherName') + '吗?', function () {
                    $.ajax({
                        url: '/student/updateTeacher.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            teacherDestId: thisTeacher.attr('teacherDesId'),
                            studentDestId: $('#studentDesId').val()
                        },
                        success: function (result) {
                            Dialog.close_dialog();
                            if (result.ret) {
                                if (result.data == $('#studentDesId').val().split(',').length) {
                                    Dialog.push_ok_message('分配成功');
                                } else {
                                    Dialog.push_ok_message('部分学生操作成功，您这个成功操作了' + result.data + '个学生');
                                }
                                setTimeout(function () {
                                    window.location.href = $('#returnUrl').val();
                                }, 2000);
                            } else {
                                Dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },

        bindDeleteTeacherAction: function () {
            var assignTeacherNode = $('#teacherList [item="deleteTeacher"]');
            assignTeacherNode.off('click');
            assignTeacherNode.on('click', function (e) {
                e.stopPropagation();
                var thisTeacher = $(this).parents('.user_node');
                Dialog.yes_no('你确定要删除该带队老师吗?', function () {
                    $.ajax({
                        url: '/teacher/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            destId: thisTeacher.attr('teacherDesId')
                        },
                        success: function (result) {
                            if (result.ret) {
                                Dialog.push_ok_message('删除成功!');
                                setTimeout(function () {
                                    window.location.reload(true);
                                }, 2000);
                            } else {
                                Dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },

        loadTeacherInfo: function (currentPage) {
            $.ajax({
                url: '/teacher/list.json',
                type: 'POST',
                data: {
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    Dialog.dialog_load('加载中...');
                },
                success: function (result) {
                    Dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#teacherList').html('');
                        }
                        var teacherHtmlInfo = teacherList.buildTeacherInfoHtml(result.data.teachers);
                        $('#teacherList').append(teacherHtmlInfo);
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#teacherList'), teacherList.loadTeacherInfo)
                        teacherList.bindScrollAction();
                        teacherList.bindToTeacherDetailAction();
                        teacherList.bindAssignTeacherAction();
                        teacherList.bindDeleteTeacherAction();
                    } else {
                        Dialog.push_error_message(result.message);
                    }
                }
            });
        },
        init: function () {
            this.data.action = $('#action').val();
            this.data.studentDesId = $('#studentDesId').val();
            this.data.isSysadmin = $('#isSysadmin').val();
            this.loadTeacherInfo(1);

        }
    };
    teacherList.init();
});