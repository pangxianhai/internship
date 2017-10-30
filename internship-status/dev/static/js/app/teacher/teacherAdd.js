define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var teacher_add = {
        add_teacher: function (form) {
            $.ajax({
                url: '/teacher/add.json',
                type: 'POST',
                dataType: 'json',
                data: string.getFormData(form),
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '添加成功');
                        setTimeout(function () {
                            window.location.href = '/teacher/list.htm';
                        }, 1000);
                    } else {
                        dialog.openSample('添加失败', result.message);
                    }
                }
            });
        },
        bind_add_teacher_action: function () {
            $('#add_teacher_form').validator({
                fields: {
                    '[name="userName"]': '用户名:required;length[1~50];checkUserName',
                    '[name="password"]': '密码:required;length[6~18]',
                    '[name="passwordAgain"]': '确认密码:required;length[6~18];checkPasswordAgain',
                    '[name="name"]': '姓名:required;length[2~18]',
                    '[name="phone"]': '联系方式:required;length[3~20]',
                    '[name="rank"]': '职称:required;length[1~20]'
                },
                rules: {
                    checkPasswordAgain: function (value) {
                        var result = {};
                        if ($('[name="password"]').val() == value) {
                            result.ret = true;
                        } else {
                            result.ret = false;
                            result.message = '两次密码输入不相等';
                        }
                        return result;
                    },
                    checkUserName: function (value) {
                        var returnRet = {};
                        $.ajax({
                            url: '/user/checkUserName.json',
                            async: false,
                            data: {
                                userName: value
                            },
                            dataType: 'json',
                            success: function (result) {
                                returnRet.ret = false;
                                if (result.ret) {
                                    if (result.data) {
                                        returnRet.ret = true;
                                    } else {
                                        returnRet.message = '该用户名已注册';
                                    }
                                }
                            }
                        })
                        return returnRet;
                    }
                },
                submit_target: '#add_btn',
                valid: function (form) {
                    teacher_add.add_teacher(form);
                }
            });
        },

        initUniversityDepartmentSelect: function () {
            UniversityDepartment.initSelect({
                university: {
                    selectNode: $('[name="universityDesId"]')
                },
                college: {
                    selectNode: $('[name="collegeDesId"]')
                },
                department: {
                    selectNode: $('[name="departmentDesId"]')
                },
                speciality: {
                    selectNode: $('[name="specialityDesId"]')
                }
            });
        },

        init: function () {
            this.bind_add_teacher_action();
            this.initUniversityDepartmentSelect();
        }
    };
    teacher_add.init();
})