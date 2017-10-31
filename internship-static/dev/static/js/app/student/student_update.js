define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var update = {
        bind_update_action: function () {
            $('#update_form').validator({
                fields: {
                    '[name="phone"]': '联系方式:required;length[3~20]',
                    '[name="grade"]': '年级:required;length[1~50]',
                    '[name="college"]': '院系:required;length[1~50]',
                    '[name="major"]': '专业:required;length[1~50]',
                    '[name="remark"]': '备注:length[~100]'

                },
                submit_target: '#update_btn',
                valid: function (form) {
                    $.ajax({
                        url: '/student/updateInfo.json',
                        type: 'POST',
                        data: string.getFormData(form),
                        dataType: 'json',
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '更新成功');
                                setTimeout(function () {
                                    window.location.href = '/student/my_info.htm';
                                }, 1000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                }
            });
        },
        initUniversitySelect: function () {
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
        init: function () {
            this.initUniversitySelect();
            this.bind_update_action();
        }
    };
    update.init();
});