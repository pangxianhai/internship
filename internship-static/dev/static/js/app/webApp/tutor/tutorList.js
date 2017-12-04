define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings.js');
    var page = require('../../../lib/webApp/page/page');
    var scroll = require('../../../lib/webApp/scroll');
    require('../header.js');
    var tutorList = {
        data: {
            action: '',
            isSysadmin: false,
            isLeader: false
        },

        pushTutorInfo: function (tutors) {
            var html = [];
            for (var i = 0; i < tutors.length; ++i) {
                var tutor = tutors[i];
                var hasOperate = false;
                var operateHtml = [];
                if (this.data.action == 'addStudent' || this.data.isSysadmin || this.data.isLeader) {
                    if (this.data.action == 'addStudent') {
                        hasOperate = true;
                        operateHtml.push('<li class="_tutor" item="assign">');
                        operateHtml.push('<div class="_assign">分配实习导师</div>');
                        operateHtml.push('</li>');
                    } else if (this.data.isSysadmin) {
                        hasOperate = true;
                        if (!tutor.isLeader) {
                            operateHtml.push('<li class="_tutor" item="setLeader">');
                            operateHtml.push('<div class="_assign">设为负责人</div>');
                            operateHtml.push('</li>');
                        }
                        operateHtml.push('<li class="_delete">删除</li>');
                    } else if (this.data.isLeader && !tutor.isLeader) {
                        hasOperate = true;
                        operateHtml.push('<li class="_delete">删除</li>');
                    }
                }
                html.push('<li class="user_node" tutorDesId="' + tutor.desId + '" tutorName="' + tutor.name + '">');
                html.push('<div class="operate">');
                html.push('<ul class="operate_list">' + operateHtml.join('') + '</ul>');
                html.push('</div>');
                html.push('<div class="user_show" has_operate="' + hasOperate + '">');
                html.push('<ul class="user_info">');
                html.push('<li>' + tutor.name + '</li>');
                html.push('<li>' + tutor.department + '</li>');
                html.push('<li><span>' + tutor.tutorType.desc + '</span><i>&gt;</i></li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">实习单位：</li>');
                html.push('<li class="_right">' + tutor.companyName + '</li>');
                html.push('<li class="_left">实习人数：</li>');
                html.push('<li class="_right">' + strings.filterString(tutor.studentNumber, '0') + '人</li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">性别：</li>');
                html.push('<li class="_right">' + tutor.userInfo.sex.desc + '</li>');
                html.push('<li class="_left">联系方式：</li>');
                html.push('<li class="_right">' + tutor.userInfo.phone + '</li>');
                html.push('</ul>');
                html.push('</ul>');
                html.push('</div>');
                html.push('</li>');
                html.push('<hr/>');
            }
            return html.join('');
        },

        bindTutorInfoScrollAction: function () {
            $('#tutorList .user_show').each(function () {
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

        bindToTutorDetailAction: function () {
            var tutorNode = $('#tutorList .user_node');
            tutorNode.off('click');
            tutorNode.on('click', function () {
                window.location.href = '/webApp/tutor/detail/' + $(this).attr('tutorDesId') + '.htm?returnUrl=' + encodeURIComponent(location.href);
            });
        },

        bindAssignTutorAction: function () {
            var assignTutorNode = $('#tutorList .user_node .operate [item="assign"]');
            assignTutorNode.off('click');
            assignTutorNode.on('click', function (e) {
                e.stopPropagation();
                var thisTutor = $(this).parents('.user_node');
                dialog.yes_no('你确定将学生' + $('#studentName').val() + '分配到' + thisTutor.attr('tutorName') + '吗?', function () {
                    $.ajax({
                        url: '/student/updateTutor.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            tutorDestId: thisTutor.attr('tutorDesId'),
                            studentDestId: $('#studentDestId').val()
                        },
                        success: function (result) {
                            if (result.ret) {
                                if (result.data == $('#studentDestId').val().split(',').length) {
                                    dialog.push_ok_message('分配成功');
                                } else {
                                    dialog.push_ok_message('部分学生操作成功，您这个成功操作了' + result.data + '个学生');
                                }
                                setTimeout(function () {
                                    window.location.href = $('#returnUrl').val();
                                }, 2000);
                            } else {
                                dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },

        bindSetTutorLeaderAction: function () {
            var assignTutorNode = $('#tutorList .user_node .operate [item="setLeader"]');
            assignTutorNode.off('click');
            assignTutorNode.on('click', function (e) {
                e.stopPropagation();
                var thisTutor = $(this).parents('.user_node');
                var tutorDesId = thisTutor.attr('tutorDesId');
                dialog.yes_no('你确定将该导师设为负责人吗?', function () {
                    $.ajax({
                        url: '/tutor/setLeader.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            tutorDesId: tutorDesId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.push_ok_message('操作成功');
                                setTimeout(function () {
                                    window.location.reload(true);
                                }, 2000);
                            } else {
                                dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },

        bindDeleteTutorAction: function () {
            var assignTutorNode = $('#tutorList .user_node .operate ._delete');
            assignTutorNode.off('click');
            assignTutorNode.on('click', function (e) {
                e.stopPropagation();
                var thisTutor = $(this).parents('.user_node');
                var tutorDesId = thisTutor.attr('tutorDesId');
                dialog.yes_no('你确定删除该导师吗?', function () {
                    $.ajax({
                        url: '/tutor/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            destId: tutorDesId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.push_ok_message('操作成功');
                                setTimeout(function () {
                                    window.location.reload(true);
                                }, 2000);
                            } else {
                                dialog.push_error_message(result.message);
                            }
                        }
                    });
                });
            });
        },

        loadTutorInfo: function (currentPage) {
            $.ajax({
                url: '/tutor/list.json',
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
                            $('#tutorList').html('');
                        }
                        var tutorInfoHtml = tutorList.pushTutorInfo(result.data.tutors);
                        $('#tutorList').append(tutorInfoHtml);
                        var page_data = result.data.page;
                        if (page_data.totalPage > 1) {
                            new page(page_data.currentPage, page_data.totalPage, $('#tutorList'), tutorList.loadTutorInfo);
                        }
                        tutorList.bindTutorInfoScrollAction();
                        tutorList.bindToTutorDetailAction();
                        tutorList.bindAssignTutorAction();
                        tutorList.bindSetTutorLeaderAction();
                        tutorList.bindDeleteTutorAction();
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        }

        ,

        init: function () {
            this.data.isSysadmin = $('#isSysadmin').val() == 'true';
            this.data.isLeader = $('#isLeader').val() == 'true';
            this.data.action = $('#action').val();
            this.loadTutorInfo(1);

        }
    };
    tutorList.init();
});