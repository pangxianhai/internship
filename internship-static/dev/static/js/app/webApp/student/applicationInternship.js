define(function (require, exports, module) {
    require('../../../lib/webApp/zepto');
    require('../header.js');
    var Dialog = require('../../../lib/webApp/dialog');
    var Strings = require('../../../lib/strings');
    var UniversityDepartment = require('../../../lib/universityDepartment.js');
    require('../../../lib/validator.js');
    var ApplicationInternship = {

        applicationInternship: function (form) {
            $.ajax({
                url: '/student/application-inter.json',
                type: 'POST',
                data: Strings.getFormData(form),
                dataType: 'json',
                beforeSend: function () {
                    Dialog.dialog_load('提交中...');
                },
                success: function (result) {
                    Dialog.close_dialog();
                    if (result.ret) {
                        Dialog.push_ok_message('申请成功，请登录系统');
                        setTimeout(function () {
                            window.location.href = $('#returnUrl').val();
                        }, 2000);
                    } else {
                        Dialog.push_error_message(result.message);
                    }
                }
            });
        },

        bindApplicationInternshipAction: function () {
            $('#applicationForm').validator({
                fields: {
                    '[name=studentNumber]': '学号:required;length[1~50];checkUserName',
                    '[name=password]': '密码:required;length[6~18]',
                    '[name=passwordAgain]': '确认密码:required;length[6~18];checkPasswordAgain',
                    '[name=name]': '姓名:required;length[2~18]',
                    '[name=phone]': '联系方式:required;length[3~20]',
                    '[name=grade]': '年级:required;length[1~50]',
                    '[name=college]': '院系:required;length[1~50]',
                    '[name=major]': '专业:required;length[1~50]',
                    '[name=remark]': '备注:length[~100]',
                    '[name="universityDesId"]': '学校:required',
                    '[name="collegeDesId"]': '学院:required'
                },
                rules: {
                    checkPasswordAgain: function () {
                        var result = {};
                        if ($('[name=password]').val() == $('[name=passwordAgain]').val()) {
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
                ok: function (node) {
                },
                error: function (node, msg) {
                    Dialog.push_error_message(msg);
                },
                submit_target: '#submitBtn',
                valid: function (form) {
                    ApplicationInternship.applicationInternship(form);
                }
            });
        },

        initUpdateUniversityPanel: function () {
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

        main: function () {
            this.initUpdateUniversityPanel();
            this.bindApplicationInternshipAction();
        }
    };
    ApplicationInternship.main();
});