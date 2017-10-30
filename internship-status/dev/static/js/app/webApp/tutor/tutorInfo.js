define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../../../lib/webApp/panel');
    require('../header.js');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings');
    var tutorInfo = {
        updateTutorInfo: function (data, success) {
            $.ajax({
                url: '/tutor/updateInfo.json',
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
        bindUpdateTutorPanelAction: function () {
            this.updateSexPanel = $('#updateTutorSexPanel').panel({
                contentWrap: $('.tutor_info_panel'),
                openNode: $('#tutorSexNode')
            });
            this.updatePhonePanel = $('#updateTutorPhonePanel').panel({
                contentWrap: $('.tutor_info_panel'),
                openNode: $('#tutorPhoneNode')
            });
            this.updateDepartmentPanel = $('#updateTutorDepartmentPanel').panel({
                contentWrap: $('.tutor_info_panel'),
                openNode: $('#tutorDepartmentNode')
            });
        },
        initUpdateSexPanel: function () {
            var $sexCode = $('#tutorSexNode').attr('sexCode');
            $('#updateTutorSexPanel ._node').each(function () {
                if ($(this).attr('sexCode') == $sexCode) {
                    $(this).find('._icon').addClass('show');
                } else {
                    $(this).find('._icon').removeClass('show');
                }
            });
        },
        bindUpdateTutorSexAction: function () {
            $('#updateTutorSexPanel ._node').click(function () {
                var $this = $(this);
                var $sexCode = $this.attr('sexCode');
                if (strings.isEmpty($sexCode) || $('#tutorSexNode').attr('sexCode') == $sexCode) {
                    return;
                } else {
                    tutorInfo.updateTutorInfo({sexCode: $sexCode}, function () {
                        $('#tutorSexNode').attr('sexCode', $sexCode);
                        $('#tutorSexNode ._content').html($this.find('._tips').html());
                        tutorInfo.initUpdateSexPanel();
                        tutorInfo.updateSexPanel.closePanel();
                    });
                }
            });
            $('#updateTutorSexPanel button[item="cancelBtn"]').click(function () {
                tutorInfo.updateSexPanel.closePanel();
            });
        },
        bindUpdateTutorPhoneAction: function () {
            $('#updateTutorPhonePanel button[item="saveBtn"]').click(function () {
                var phoneInputValue = $('#updateTutorPhoneInput').val();
                if (strings.isEmpty(phoneInputValue)) {
                    dialog.push_error_message('联系方式不能为空');
                    return;
                }
                if (phoneInputValue.length >= 50) {
                    dialog.push_error_message('请输入50个以内的字符');
                    return;
                }
                tutorInfo.updateTutorInfo({phone: phoneInputValue}, function () {
                    $('#tutorPhoneNode ._content').html(phoneInputValue);
                    tutorInfo.updatePhonePanel.closePanel();
                });
            });
            $('#updateTutorPhonePanel button[item="cancelBtn"]').click(function () {
                $('#updateTutorPhoneInput').val($('#tutorPhoneNode ._content').html());
                tutorInfo.updatePhonePanel.closePanel();
            });
        },
        bindUpdateTutorDepartmentAction: function () {
            $('#updateTutorDepartmentPanel button[item="saveBtn"]').click(function () {
                var departmentInputValue = $('#updateTutorDepartmentInput').val();
                if (strings.isEmpty(departmentInputValue)) {
                    dialog.push_error_message('部门不能为空');
                    return;
                }
                if (departmentInputValue.length >= 50) {
                    dialog.push_error_message('请输入50个以内的字符');
                    return;
                }
                tutorInfo.updateTutorInfo({department: departmentInputValue}, function () {
                    $('#tutorDepartmentNode ._content').html(departmentInputValue);
                    tutorInfo.updateDepartmentPanel.closePanel();
                });
            });
            $('#updateTutorDepartmentPanel button[item="cancelBtn"]').click(function () {
                $('#updateTutorDepartmentInput').val($('#tutorDepartmentNode ._content').html());
                tutorInfo.updateDepartmentPanel.closePanel();
            });
        },
        init: function () {
            this.bindUpdateTutorPanelAction();
            this.initUpdateSexPanel();
            this.bindUpdateTutorSexAction();
            this.bindUpdateTutorPhoneAction();
            this.bindUpdateTutorDepartmentAction();
        }
    };
    tutorInfo.init();
});