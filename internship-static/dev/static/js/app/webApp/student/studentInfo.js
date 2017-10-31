define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../../../lib/webApp/panel');
    require('../header.js');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings');
    var UniversityDepartment = require('../../../lib/universityDepartment.js');
    var studentInfo = {
        data: {
            teacherId: ''
        },

        updateStudent: function (data, success) {
            $.ajax({
                url: '/student/updateInfo.json',
                type: 'POST',
                data: $.extend({}, data),
                dataType: 'json',
                beforeSend: function () {
                    dialog.dialog_load('提交中...');
                },
                success: function (result) {
                    dialog.close_dialog();
                    if (result.ret) {
                        dialog.push_ok_message('更新成功');
                        setTimeout(function () {
                            success();
                        }, 1000);
                    } else {
                        dialog.push_error_message(result.message);
                    }
                }
            });
        },

        initUpdateSexPanel: function () {
            var $sexCode = $('#studentSexNode').attr('sexCode');
            $('#updateStudentSexPanel ._node').each(function () {
                if ($(this).attr('sexCode') == $sexCode) {
                    $(this).find('._icon').addClass('show');
                } else {
                    $(this).find('._icon').removeClass('show');
                }
            });
        },

        initUpdateAcademicianPanel: function () {
            var academicianCode = $('#studentAcademicianNode').attr('academicianCode');
            $('#updateStudentAcademicianPanel ._node').each(function () {
                if ($(this).attr('academicianCode') == academicianCode) {
                    $(this).find('._icon').addClass('show');
                } else {
                    $(this).find('._icon').removeClass('show');
                }
            });
        },

        initUpdateStudentExpectCompanyPanel: function () {
            $('#updateStudentExpectCompanySelect').val($('#updateStudentExpectCompanyNode ._content').html());
        },

        initUpdateUniversityPanel: function () {
            UniversityDepartment.initSelect({
                university: {
                    selectNode: $('[name="universityDesId"]'),
                    departmentId: $('#universityDesId').val()
                },
                college: {
                    selectNode: $('[name="collegeDesId"]'),
                    departmentId: $('#collegeDesId').val()
                },
                department: {
                    selectNode: $('[name="departmentDesId"]'),
                    departmentId: $('#departmentDesId').val()
                },
                speciality: {
                    selectNode: $('[name="specialityDesId"]'),
                    departmentId: $('#specialityDesId').val()
                }
            });
        },

        bindUpdateStudentAction: function () {
            this.sexPanel = $('#updateStudentSexPanel').panel({
                contentWrap: $('.student_info_panel'),
                openNode: $('#studentSexNode')
            });
            this.gradePanel = $('#updateStudentGradePanel').panel({
                contentWrap: $('.student_info_panel'),
                openNode: $('#studentGradeNode')
            });
            if (strings.isEmpty(this.data.teacherId)) {
                this.universityPanel = $('#updateStudentUniversityPanel').panel({
                    contentWrap: $('.student_info_panel'),
                    openNode: $('[item="studentUniversityNode"]')
                });
            }
            this.phonePanel = $('#updateStudentPhonePanel').panel({
                contentWrap: $('.student_info_panel'),
                openNode: $('#studentPhoneNode')
            });
            this.remarkPanel = $('#updateStudentRemarkPanel').panel({
                contentWrap: $('.student_info_panel'),
                openNode: $('#studentRemarkNode')
            });
            this.expectCompanyPanel = $('#updateStudentExpectCompanyPanel').panel({
                contentWrap: $('.student_info_panel'),
                openNode: $('#updateStudentExpectCompanyNode')
            });
            this.studentAcademicianPanel = $('#updateStudentAcademicianPanel').panel({
                contentWrap: $('.student_info_panel'),
                openNode: $('#studentAcademicianNode')
            });
        },

        bindUpdateStudentSexAction: function () {
            $('#updateStudentSexPanel ._node').click(function () {
                var $this = $(this);
                var $sexCode = $this.attr('sexCode');
                if (strings.isEmpty($sexCode) || $('#studentSexNode').attr('sexCode') == $sexCode) {
                    return;
                } else {
                    studentInfo.updateStudent({sexCode: $sexCode}, function () {
                        $('#studentSexNode').attr('sexCode', $sexCode);
                        $('#studentSexNode ._content').html($this.find('._tips').html());
                        studentInfo.initUpdateSexPanel();
                        studentInfo.sexPanel.closePanel();
                    });
                }
            });
            $('#updateStudentSexPanel button[item="cancelBtn"]').click(function () {
                studentInfo.sexPanel.closePanel();
            });
        },

        bindUpdateStudentGradeAction: function () {
            $('#updateStudentGradePanel button[item="saveBtn"]').click(function () {
                var gradeInput = $('#updateStudentGradeInput').val();
                if (strings.isEmpty(gradeInput)) {
                    dialog.push_error_message('年级不能为空');
                    return;
                }
                if (gradeInput.length >= 50) {
                    dialog.push_error_message('请输入50个以内的字符');
                    return;
                }
                studentInfo.updateStudent({grade: gradeInput}, function () {
                    $('#studentGradeNode ._content').html(gradeInput);
                    studentInfo.gradePanel.closePanel();
                });
            });
            $('#updateStudentGradePanel button[item="cancelBtn"]').click(function () {
                $('#updateStudentGradeInput').val($('#studentGradeNode ._content').html());
                studentInfo.gradePanel.closePanel();
            });
        },

        bindUpdateStudentUniversityAction: function () {
            $('#updateStudentUniversityPanel button[item="saveBtn"]').click(function () {
                var universityDesId = $('#updateStudentUniversityPanel [name="universityDesId"]').val();
                var collegeDesId = $('#updateStudentUniversityPanel [name="collegeDesId"]').val();
                var departmentDesId = $('#updateStudentUniversityPanel [name="departmentDesId"]').val();
                var specialityDesId = $('#updateStudentUniversityPanel [name="specialityDesId"]').val();
                studentInfo.updateStudent({
                    universityDesId: universityDesId,
                    collegeDesId: collegeDesId,
                    departmentDesId: departmentDesId,
                    specialityDesId: specialityDesId,
                    updateUniversity: true
                }, function () {
                    setTimeout(function () {
                        location.reload(true);
                    }, 2000);
                });
            });
            $('#updateStudentUniversityPanel button[item="cancelBtn"]').click(function () {
                studentInfo.initUpdateUniversityPanel();
                studentInfo.universityPanel.closePanel();
            });
        },

        bindUpdateStudentPhoneAction: function () {
            $('#updateStudentPhonePanel button[item="saveBtn"]').click(function () {
                var phoneInput = $('#updateStudentPhoneInput').val();
                if (strings.isEmpty(phoneInput)) {
                    dialog.push_error_message('联系方式不能为空');
                    return;
                }
                if (phoneInput.length >= 50) {
                    dialog.push_error_message('请输入50个以内的字符');
                    return;
                }
                studentInfo.updateStudent({phone: phoneInput}, function () {
                    $('#studentPhoneNode ._content').html(phoneInput);
                    studentInfo.phonePanel.closePanel();
                });
            });
            $('#updateStudentPhonePanel button[item="cancelBtn"]').click(function () {
                $('#updateStudentPhoneInput').val($('#studentPhoneNode ._content').html());
                studentInfo.phonePanel.closePanel();
            });
        },

        bindUpdateStudentRemarkAction: function () {
            $('#updateStudentRemarkPanel button[item="saveBtn"]').click(function () {
                var remarkInput = $('#updateStudentRemarkInput').val();
                studentInfo.updateStudent({remark: remarkInput}, function () {
                    $('#studentRemarkNode ._content').html(remarkInput);
                    studentInfo.remarkPanel.closePanel();
                });
            });
            $('#updateStudentRemarkPanel button[item="cancelBtn"]').click(function () {
                $('#updateStudentRemarkInput').val($('#studentRemarkNode ._content').html());
                studentInfo.remarkPanel.closePanel();
            });
        },

        bindUpdateStudentExpectCompanyAction: function () {
            $('#updateStudentExpectCompanyPanel button[item="saveBtn"]').click(function () {
                var expectCompany = $('#updateStudentExpectCompanySelect').val();
                if (strings.isEmpty(expectCompany) || expectCompany == '-') {
                    dialog.push_ok_message('请选择你期望的实习单位');
                    return;
                }
                studentInfo.updateStudent({expectCompany: expectCompany}, function () {
                    $('#updateStudentExpectCompanySelect').val(expectCompany);
                    $('#updateStudentExpectCompanyNode ._content').html($('#updateStudentExpectCompanySelect').val());
                    studentInfo.expectCompanyPanel.closePanel();
                });
            });
            $('#updateStudentExpectCompanyPanel button[item="cancelBtn"]').click(function () {
                studentInfo.initUpdateStudentExpectCompanyPanel();
                studentInfo.expectCompanyPanel.closePanel();
            });
        },

        bindUpdateStudentAcademicianAction: function () {
            $('#updateStudentAcademicianPanel ._node').click(function () {
                var $this = $(this);
                var academicianCode = $this.attr('academicianCode');
                if (strings.isEmpty(academicianCode) || $('#studentAcademicianNode').attr('academicianCode') == academicianCode) {
                    return;
                } else {
                    studentInfo.updateStudent({academicianTypeCode: academicianCode}, function () {
                        $('#studentAcademicianNode').attr('academicianCode', academicianCode);
                        $('#studentAcademicianNode ._content').html($this.find('._tips').html());
                        studentInfo.initUpdateAcademicianPanel();
                        studentInfo.studentAcademicianPanel.closePanel();
                    });
                }
            });
            $('#updateStudentAcademicianPanel button[item="cancelBtn"]').click(function () {
                studentInfo.studentAcademicianPanel.closePanel();
            });
        },

        init: function () {
            this.data.teacherId = $('#teacherId').val();
            this.initUpdateSexPanel();
            this.initUpdateStudentExpectCompanyPanel();
            this.initUpdateAcademicianPanel();
            if (strings.isEmpty(this.data.teacherId)) {
                this.initUpdateUniversityPanel();
                this.bindUpdateStudentUniversityAction();
            }
            this.bindUpdateStudentAction();
            this.bindUpdateStudentSexAction();
            this.bindUpdateStudentGradeAction();
            this.bindUpdateStudentPhoneAction();
            this.bindUpdateStudentRemarkAction();
            this.bindUpdateStudentExpectCompanyAction();
            this.bindUpdateStudentAcademicianAction();
        }
    };
    studentInfo.init();
});