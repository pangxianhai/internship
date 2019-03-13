define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../../../lib/webApp/panel');
    require('../header.js');
    var dialog = require('../../../lib/webApp/dialog');
    var strings = require('../../../lib/strings');
    var SysadminInfo = {
        updateSysadminInfo: function (data, success) {
            $.ajax({
                url: '/sysadmin/updateInfo.json',
                type: 'POST',
                data: $.extend({}, data),
                dataType: 'json',
                success: function (result) {
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
        bindUpdateSysadminPanelAction: function () {
            this.updateSexPanel = $('#updateSysadminSexPanel').panel({
                contentWrap: $('.sysadmin_info_panel'),
                openNode: $('#sysadminSexNode')
            });
            this.updatePhonePanel = $('#updateSysadminPhonePanel').panel({
                contentWrap: $('.sysadmin_info_panel'),
                openNode: $('#sysadminPhoneNode')
            });
        },

        initUpdateSexPanel: function () {
            var $sexCode = $('#sysadminSexNode').attr('sexCode');
            $('#updateSysadminSexPanel ._node').each(function () {
                if ($(this).attr('sexCode') == $sexCode) {
                    $(this).find('._icon').addClass('show');
                } else {
                    $(this).find('._icon').removeClass('show');
                }
            });
        },

        bindUpdateSysadminSexAction: function () {
            $('#updateSysadminSexPanel ._node').click(function () {
                var $this = $(this);
                var $sexCode = $this.attr('sexCode');
                if (strings.isEmpty($sexCode) || $('#sysadminSexNode').attr('sexCode') == $sexCode) {
                    return;
                } else {
                    SysadminInfo.updateSysadminInfo({sexCode: $sexCode}, function () {
                        $('#sysadminSexNode').attr('sexCode', $sexCode);
                        $('#sysadminSexNode ._content').html($this.find('._tips').html());
                        SysadminInfo.initUpdateSexPanel();
                        SysadminInfo.updateSexPanel.closePanel();
                    });
                }
            });
            $('#updateSysadminSexPanel button[item="cancelBtn"]').click(function () {
                SysadminInfo.updateSexPanel.closePanel();
            });
        },

        bindUpdateSysadminPhoneAction: function () {
            $('#updateSysadminPhonePanel button[item="saveBtn"]').click(function () {
                var phoneInputValue = $('#updateSysadminPhoneInput').val();
                if (strings.isEmpty(phoneInputValue)) {
                    dialog.push_error_message('联系方式不能为空');
                    return;
                }
                if (phoneInputValue.length >= 50) {
                    dialog.push_error_message('请输入50个以内的字符');
                    return;
                }
                SysadminInfo.updateSysadminInfo({phone: phoneInputValue}, function () {
                    $('#sysadminPhoneNode ._content').html(phoneInputValue);
                    SysadminInfo.updatePhonePanel.closePanel();
                });
            });
            $('#updateSysadminPhonePanel button[item="cancelBtn"]').click(function () {
                $('#updateSysadminPhoneInput').val($('#sysadminPhoneNode ._content').html());
                SysadminInfo.updatePhonePanel.closePanel();
            });
        },
        init: function () {
            this.bindUpdateSysadminPanelAction();
            this.initUpdateSexPanel();
            this.bindUpdateSysadminSexAction();
            this.bindUpdateSysadminPhoneAction();
        }
    };
    SysadminInfo.init();
});