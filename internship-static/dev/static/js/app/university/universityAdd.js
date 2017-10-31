define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var Strings = require('../../lib/strings.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var Dialog = require('../../lib/dialog.js');

    var UniversityAdd = {

        addUniversityDepartment: function (form) {
            $.ajax({
                url: '/university/add.json',
                type: 'POST',
                data: Strings.getFormData(form),
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        Dialog.openSample('系统提示', '添加成功');
                        setTimeout(function () {
                            window.location.href = '/university/list.htm';
                        }, 1000);
                    } else {
                        Dialog.openSample('添加失败', result.message);
                    }
                }
            })
        },

        buildParentSelectionPanel: function (departmentTypeCode, universityDesId, collegeDesId, departmentDesId) {
            if (Strings.isEmpty(departmentTypeCode)) {
                return;
            }
            if (departmentTypeCode == '100') {
                $('.parent_tips').hide();
                $('.parent_select').hide();
            } else {
                $('.parent_tips').show();
                $('.parent_select').show();
                if (departmentTypeCode == '101') {
                    UniversityDepartment.initSelect({
                        university: {
                            selectNode: $('#university'),
                            departmentId: universityDesId
                        }
                    });
                } else if (departmentTypeCode == '102') {
                    UniversityDepartment.initSelect({
                        university: {
                            selectNode: $('#university'),
                            departmentId: universityDesId
                        },
                        college: {
                            selectNode: $('#college'),
                            departmentId: collegeDesId
                        }
                    });
                } else if (departmentTypeCode == '103') {
                    UniversityDepartment.initSelect({
                        university: {
                            selectNode: $('#university'),
                            departmentId: universityDesId
                        },
                        college: {
                            selectNode: $('#college'),
                            departmentId: collegeDesId
                        },
                        department: {
                            selectNode: $('#department'),
                            departmentId: departmentDesId
                        }
                    });
                }
            }
        },

        bindDepartmentTypeAction: function () {
            $('#departmentType').change(function () {
                $('.parent_select select').html('');
                $('.parent_select select').hide();
                var type = $(this).val();
                UniversityAdd.buildParentSelectionPanel(type, $('#universityDesId').val(), $('#collegeDesId').val(), $('#departmentDesId').val());
            });
        },


        bindAddUniversityDepartmentAction: function () {
            $('#addUniversityForm').validator({
                fields: {
                    '#departmentName': '部门名称:required;length[1~50]'
                },
                submit_target: '#addUniversityBtn',
                valid: function (form) {
                    UniversityAdd.addUniversityDepartment(form);
                }
            });
        },

        initAddDepartmentPanel: function () {
            var departmentTypeCode = $('#departmentTypeCode').val();
            if (Strings.isNotEmpty(departmentTypeCode)) {
                $('#departmentType').val(departmentTypeCode);
                UniversityAdd.buildParentSelectionPanel(departmentTypeCode, $('#universityDesId').val(), $('#collegeDesId').val(), $('#departmentDesId').val());
            }
        },

        main: function () {
            this.initAddDepartmentPanel();
            this.bindDepartmentTypeAction();
            this.bindAddUniversityDepartmentAction();
        }
    };


    UniversityAdd.main();
});