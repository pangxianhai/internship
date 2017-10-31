define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var teacherList = {
        data: {
            action: '',
            isSysadmin: false,
            isLeader: false
        },
        pushTeacherInfo: function (teachers) {
            var html = [];
            for (var i = 0; i < teachers.length; ++i) {
                var teacher = teachers[i];
                html.push('<tr>');
                html.push('<td>' + teacher.name + '</td>');
                html.push('<td>' + teacher.userInfo.userName + '</td>');
                html.push('<td>' + teacher.userInfo.phone + '</td>');
                html.push('<td>' + teacher.userInfo.sex.desc + '</td>');
                html.push('<td>' + string.filterString(teacher.universityDepartmentInfo, '—') + '</td>');
                html.push('<td>' + teacher.rank + '</td>');
                html.push('<td>' + teacher.teacherType.desc + '</td>');
                html.push('<td>');
                if (typeof teacher.studentNumber == 'undefined') {
                    html.push(0);
                } else {
                    html.push(teacher.studentNumber);
                }
                html.push('</td>');
                html.push('<td>');
                if (this.data.action == 'addStudent') {
                    html.push('<a item="addStudent" teacherName="' + teacher.name + '"  teacherId="' + teacher.desId + '" href="javascript:void(0)">[分配]</a>');
                } else if (this.data.isSysadmin) {
                    if (!teacher.leader) {
                        html.push('<a item="setLeader" desId="' + teacher.desId + '" href="javascript:void(0)">[设为负责人]</a>');
                    }
                    html.push('<a item="delete" desId="' + teacher.desId + '" href="javascript:void(0)">[删除]</a>');
                } else if (this.data.isLeader && !teacher.leader) {
                    html.push('<a item="delete" desId="' + teacher.desId + '" href="javascript:void(0)">[删除]</a>');
                }
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },
        pushPageData: function (result) {
            if (result.ret) {
                $('#u_data').html(teacherList.pushTeacherInfo(result.data.teachers));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(teacherList.loadTeacherInfo);
                teacherList.bindAddStudentAction();
                teacherList.bindSetLeaderAction();
                teacherList.bindDeleteTeacherAction();
            } else {

            }
        },

        setLeader: function (teacherDesId, leaderType) {
            $.ajax({
                url: '/teacher/setLeader.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    teacherDesId: teacherDesId,
                    teacherTypeCode: leaderType
                },
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '操作成功');
                        teacherList.loadTeacherInfo(page.current_page());
                        dialog.close(2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            })
        },

        checkHasLeader: function (leaderType, teacherDesId, success) {
            $.ajax({
                url: '/teacher/checkTeacherHasLeader.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    teacherTypeCode: leaderType,
                    teacherDesId: teacherDesId
                },
                success: function (result) {
                    if (result.ret) {
                        if (result.data) {
                            success(teacherDesId, leaderType);
                        } else {
                            dialog.yesNo('提示', '该单位已经有负责人了！你确定要更改负责人么？', function () {
                                success(teacherDesId, leaderType);
                            });
                        }
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            })
        },

        bindSetLeaderAction: function () {
            var setLeaderNode = $('a[item="setLeader"]');
            setLeaderNode.off('click');
            setLeaderNode.on('click', function () {
                var $thisNode = $(this);
                $('.teacher_type_select_panel').dialog({
                    title: '请选择负责人类型',
                    yesNo: true,
                    ok: function () {
                        var leaderType = $('#teacherType').val();
                        var teacherDesId = $thisNode.attr('desId');
                        teacherList.checkHasLeader(leaderType, teacherDesId, function (teacherDesId, leaderType) {
                            teacherList.setLeader(teacherDesId, leaderType);
                        });
                    }
                });
            });
        },

        bindDeleteTeacherAction: function () {
            $('a[item="delete"]').unbind("click");
            $('a[item="delete"]').bind("click", function () {
                var destId = $(this).attr("desId");
                dialog.yesNo('提示', '您确定要删除该带队老师吗？', function () {
                    $.ajax({
                        url: '/teacher/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            destId: destId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '删除成功!');
                                teacherList.loadTeacherInfo(page.current_page());
                                dialog.close(2000);
                            } else {
                                dialog.openSample('提示', result.message);
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
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: teacherList.pushPageData
            });
        },
        bindAddStudentAction: function () {
            var addBtn = $('a[item="addStudent"]');
            addBtn.unbind("click");
            addBtn.bind("click", function () {
                var thisObj = this;
                dialog.yesNo('提示', '您确定将该学生分配给' + this.getAttribute('teacherName') + '吗？', function () {
                    dialog.close(1);
                    teacherList.addStudent(thisObj.getAttribute('teacherId'));
                });
            });
        },
        addStudent: function (teacherId) {
            var studentDestId = $('#studentId').val();
            $.ajax({
                url: '/student/updateTeacher.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    teacherDestId: teacherId,
                    studentDestId: studentDestId
                },
                success: function (result) {
                    if (result.ret) {
                        if (result.data == studentDestId.split(',').length) {
                            dialog.openSample('提示', '分配成功');
                        } else {
                            dialog.openSample('提示', '部分学生操作成功，您这个成功操作了' + result.data + '个学生');
                        }
                        dialog.close(2000);
                        setTimeout(function () {
                            window.location.href = '/student/list.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },

        bindSearchAction: function () {
            var isUniversityLeader = $('#isUniversityLeader').val() === 'true';
            var isCollegeLeader = $('#isCollegeLeader').val() === 'true';
            var isDepartmentLeader = $('#isDepartmentLeader').val() === 'true';
            var isSpecialityLeader = $('#isSpecialityLeader').val() === 'true';
            UniversityDepartment.initSelect({
                university: {
                    selectNode: $('[name="universityDesId"]'),
                    departmentId: $('#universityDesId').val(),
                    lock: isUniversityLeader || isCollegeLeader || isDepartmentLeader || isSpecialityLeader
                },
                college: {
                    selectNode: $('[name="collegeDesId"]'),
                    departmentId: $('#collegeDesId').val(),
                    lock: isCollegeLeader || isDepartmentLeader || isSpecialityLeader
                },
                department: {
                    selectNode: $('[name="departmentDesId"]'),
                    departmentId: $('#departmentDesId').val(),
                    lock: isDepartmentLeader || isSpecialityLeader
                },
                speciality: {
                    selectNode: $('[name="specialityDesId"]'),
                    departmentId: $('#specialityDesId').val(),
                    lock: isSpecialityLeader
                },
                complete: function () {
                    teacherList.loadTeacherInfo(1);
                }
            });
            $('#search_action').click(function () {
                teacherList.loadTeacherInfo(1);
            });
        },

        init: function () {
            this.data.action = $('#action').val();
            this.data.isSysadmin = $('#isSysadmin').val() == 'true';
            this.data.isLeader = $('#isLeader').val() == 'true';
            this.bindSearchAction();
        }
    };
    teacherList.init()
});