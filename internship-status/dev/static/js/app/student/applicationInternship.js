define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var UniversityDepartment = require('../../lib/universityDepartment.js');
    var apply_for = {
        apply_for_submit: function (form) {
            $.ajax({
                url: '/student/application-inter.json',
                type: 'POST',
                data: string.getFormData(form),
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('系统提示', '申请成功，请登录系统');
                        setTimeout(function () {
                            dialog.close(1);
                            window.location.href = '/login/index.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('系统提示', result.message);
                    }
                }
            });
        },
        bind_apply_for_action: function () {
            $('#apply_for_form').validator({
                fields: {
                    '#studentNumber': '学号:required;length[1~50];checkUserName',
                    '#password': '密码:required;length[6~18]',
                    '#passwordAgain': '确认密码:required;length[6~18];checkPasswordAgain',
                    '#name': '姓名:required;length[2~18]',
                    '#phone': '联系方式:required;length[3~20]',
                    '#grade': '年级:required;length[1~50]',
                    '#college': '院系:required;length[1~50]',
                    '#major': '专业:required;length[1~50]',
                    '#remark': '备注:length[~100]',
                    '[name="universityDesId"]': '学校:required',
                    '[name="collegeDesId"]': '学院:required'
                },
                rules: {
                    checkPasswordAgain: function () {
                        var result = {};
                        if ($('#password').val() == $('#passwordAgain').val()) {
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
                                        returnRet.message = '该学号已注册';
                                    }
                                }
                            }
                        })
                        return returnRet;
                    }
                },
                submit_target: '#apply_for_btn',
                valid: function (form) {
                    apply_for.apply_for_submit(form);
                }
            });
        },
        init: function () {
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
            this.bind_apply_for_action();
        }
    };

    apply_for.init();
});