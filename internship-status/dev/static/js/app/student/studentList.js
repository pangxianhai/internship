define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var WdatePicker = require('../../lib/My97DatePicker/WdatePicker.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var student_list = {
        data: {},
        push_student_info: function (students) {
            var html = [];
            for (var i = 0; i < students.length; ++i) {
                var student = students[i];
                html.push('<tr>');
                html.push('<td><input item="select_student"  type="checkbox" companyId="' + student.companyId + '" ' +
                    'destId="' + student.desId + '" universityDesId="' + student.universityDesId + '" ' +
                    'collegeDesId="' + student.collegeDesId + '" departmentDesId="' + student.departmentDesId + '" ' +
                    'specialityDesId="' + student.specialityDesId + '" /></td>');
                html.push('<td>' + student.studentNumber + '</td>');
                html.push('<td>' + student.name + '</td>');
                html.push('<td>' + student.userInfo.phone + '</td>');
                html.push('<td>' + student.userInfo.sex.desc + '</td>');
                html.push('<td>' + student.universityDepartmentInfo + '</td>');
                html.push('<td>' + student.grade + '</td>');
                html.push('<td>');
                if (string.isEmpty(student.teacherId)) {
                    html.push('-');
                } else {
                    html.push(student.teacherName);
                }
                html.push('</td>');
                html.push('<td title="' + string.filterNull(student.companyName) + '">');
                if (string.isEmpty(student.companyId)) {
                    html.push('-');
                } else {
                    html.push(string.substring(student.companyName, 5));
                }
                html.push('</td>');
                html.push('<td>');
                if (string.isEmpty(student.tutorId)) {
                    html.push('-');
                } else {
                    html.push(student.tutorName);
                }
                html.push('</td>');
                html.push('<td title="' + string.filterNull(student.expectCompany) + '">' + string.substring(student.expectCompany, 5) + '</td>');
                html.push('<td title="' + string.filterNull(student.remark) + '">' + string.substring(student.remark, 5) + '</td>');
                html.push('<td>' + new Date(student.createdTime).format('yyyy-MM-dd') + '</td>');
                html.push('<td class="operator">');
                if (this.data.can_change_company) {
                    html.push('<a href="/company/list.htm?action=addStudent&studentId=' + student.desId + '">');
                    if (string.isEmpty(student.companyId)) {
                        html.push('[分配实习单位]');
                    } else {
                        html.push('[修改实习单位]');
                    }
                    html.push('</a><br/>');
                }
                if (this.data.can_change_tutor) {
                    html.push('<a href="/tutor/list.htm?action=addStudent&studentId=' + student.desId + '&companyId=' + student.companyId + '">');
                    if (string.isEmpty(student.tutorId)) {
                        html.push('[分配导师]');
                    } else {
                        html.push('[修改导师]');
                    }
                    html.push('</a><br/>');
                }
                if (this.data.can_change_teacher) {
                    html.push('<a href="/teacher/list.htm?action=addStudent&studentId=' + student.desId + '">');
                    if (string.isEmpty(student.teacherId)) {
                        html.push('[分配带队老师]');
                    } else {
                        html.push('[修改带队老师]');
                    }
                    html.push('</a></br>');
                }
                if (this.data.can_delete_student) {
                    html.push('<a item="delete" destId="' + student.desId + '" href="javascript:void(0)" >[删除]</a><br/>');
                    // html.push('<a href="javascript:void(0)" >[实习结束]</a>');
                }
                html.push('</td');
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(student_list.push_student_info(result.data.students));
                $('.page_list').html(page.html(result.data.page));
                page.turning_page(student_list.load_student_info);
                student_list.bind_delete_action();
            } else {

            }
        },
        load_student_info: function (currentPage) {
            $.ajax({
                url: '/student/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: student_list.push_page_data
            });
        },
        delete_student: function (destIds, total_count) {
            $.ajax({
                url: '/student/delete.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    destIds: destIds
                },
                success: function (result) {
                    if (result.ret) {
                        if (result.data < total_count) {
                            dialog.openSample('提示', '部分学生删除未成功，您这个只删除了' + result.data + '个学生');
                        } else {
                            dialog.openSample('提示', '操作成功');
                        }
                        student_list.load_student_info(page.current_page());
                        dialog.close(2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },
        bind_search_register_timer_action: function () {
            $('[name="beginTimeStr"]').click(function () {
                WdatePicker.WdatePicker({dateFmt: 'yyyy-MM-dd', maxDate: '%y-%M-%d'});
            });
            $('[name="endTimeStr"]').click(function () {
                WdatePicker.WdatePicker({
                    dateFmt: 'yyyy-MM-dd',
                    minDate: $('[name="beginTimeStr"]').val(),
                    maxDate: '%y-%M-%d'
                });
            });
        },
        bind_search_action: function () {
            UniversityDepartment.initSelect({
                university: {
                    selectNode: $('[name="universityDesId"]'),
                    departmentId: ''
                },
                college: {
                    selectNode: $('[name="collegeDesId"]'),
                    departmentId: ''
                },
                department: {
                    selectNode: $('[name="departmentDesId"]'),
                    departmentId: ''
                },
                speciality: {
                    selectNode: $('[name="specialityDesId"]'),
                    departmentId: ''
                }
            });
            $('#search_action').click(function () {
                $('input[item="select_all"]').prop('checked', false);
                student_list.load_student_info(1);
            });
        },

        bind_delete_action: function () {
            $('a[item="delete"]').unbind("click");
            $('a[item="delete"]').bind("click", function () {
                var destId = $(this).attr('destId');
                dialog.yesNo('提示', '您确定要删除该学生吗？', function () {
                    student_list.delete_student(destId, 1);
                });
            });
        },
        bind_select_all_action: function () {
            $('input[item="select_all"]').click(function () {
                $('input[item="select_student"]').prop('checked', $(this).is(':checked'));
            });
        },
        bind_delete_all_action: function () {
            $('#delete_all').click(function () {
                var destIds = [];
                $('input[item="select_student"]:checked').each(function () {
                    destIds.push($(this).attr('destId'));
                });
                if (destIds.length == 0) {
                    dialog.openSample('提示', '请选择要删除的学生');
                    return;
                }
                dialog.yesNo('提示', '您确定要删除选中的学生吗？', function () {
                    student_list.delete_student(destIds.join(','), destIds.length);
                });
            });
        },
        bind_change_company_all_action: function () {
            $('#change_company_all').click(function () {
                var destIds = [];
                $('input[item="select_student"]:checked').each(function () {
                    destIds.push($(this).attr('destId'));
                });
                if (destIds.length == 0) {
                    dialog.openSample('提示', '请选择要分配（或修改）的学生');
                    return;
                }
                dialog.yesNo('提示', '批量分配，将所选学生全部分配（或修改）到同一个单位，您确定要执行次操作吗？', function () {
                    window.location.href = '/company/list.htm?action=addStudent&studentId=' + destIds.join(',');
                });
            });
        },
        bind_change_teacher_all_action: function () {
            $('#change_teacher_all').click(function () {
                var destIds = [];
                var universityDesId;
                var collegeDesId;
                var departmentDesId;
                var specialityDesId;
                var sameUniversityDepartment = true;
                $('input[item="select_student"]:checked').each(function (index) {
                    var $this = $(this);
                    destIds.push($this.attr('destId'));
                    if (index == 0) {
                        universityDesId = $this.attr('universityDesId');
                        collegeDesId = $this.attr('collegeDesId');
                        departmentDesId = $this.attr('departmentDesId');
                        specialityDesId = $this.attr('specialityDesId');
                    } else {
                        var _universityDesId = $this.attr('universityDesId');
                        var _collegeDesId = $this.attr('collegeDesId');
                        var _departmentDesId = $this.attr('departmentDesId');
                        var _specialityDesId = $this.attr('specialityDesId');
                        if (universityDesId !== _universityDesId || collegeDesId !== _collegeDesId ||
                            departmentDesId !== _departmentDesId || specialityDesId !== _specialityDesId) {
                            sameUniversityDepartment = false;
                            return false;
                        }
                    }
                });
                if (!sameUniversityDepartment) {
                    dialog.openSample('提示', '请选择同学校同院系同专业的学生进行批量操作!');
                }
                if (destIds.length == 0) {
                    dialog.openSample('提示', '请选择要分配（或修改）的学生');
                    return;
                }
                dialog.yesNo('提示', '批量分配，将所选学生全部分配（或修改）到同一个带队老师，您确定要执行次操作吗？', function () {
                    window.location.href = '/teacher/list.htm?action=addStudent&studentId=' + destIds.join(',')
                        + '&universityDesId=' + string.filterNull(universityDesId) + '&collegeDesId=' + string.filterNull(collegeDesId)
                        + '&departmentDesId=' + string.filterNull(departmentDesId) + '&specialityDesId=' + string.filterNull(specialityDesId);
                });
            });
        },
        bind_change_tutor_all_action: function () {
            $('#change_tutor_all').click(function () {
                var destIds = [];
                var companyIds = [];
                $('input[item="select_student"]:checked').each(function () {
                    destIds.push($(this).attr('destId'));
                    companyIds.push($(this).attr('companyId'));
                });
                if (destIds.length == 0) {
                    dialog.openSample('提示', '请选择要分配（或修改）的学生');
                    return;
                }
                for (var i = 0; i < companyIds.length; ++i) {
                    if (string.isEmpty(companyIds[i])) {
                        dialog.openSample('提示', '您所选的学生有的未分配单位，请先为他们分配单位再执行该操作!');
                        return;
                    }
                    if (!(companyIds[i] == companyIds[0])) {
                        dialog.openSample('提示', '您所选的学生不属于同一单位，请选择同一单位的学生再执行该操作!');
                        return;
                    }
                }
                dialog.yesNo('提示', '批量分配，将所选学生全部分配（或修改）到同一个指导老师，您确定要执行次操作吗？', function () {
                    window.location.href = '/tutor/list.htm?action=addStudent&studentId=' + destIds.join(',')
                        + '&companyId=' + companyIds[0];
                });
            });
        },

        init: function () {
            this.data.can_change_company = $('#can_change_company').val();
            this.data.can_change_tutor = $('#can_change_tutor').val();
            this.data.can_change_teacher = $('#can_change_teacher').val();
            this.data.can_delete_student = $('#can_delete_student').val();
            this.load_student_info(1);
            this.bind_search_action();
            this.bind_select_all_action();
            this.bind_delete_all_action();
            this.bind_change_company_all_action();
            this.bind_search_register_timer_action();
            this.bind_change_teacher_all_action();
            this.bind_change_tutor_all_action();
        }
    }
    student_list.init();
});