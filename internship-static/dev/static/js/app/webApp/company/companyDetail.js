define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../header.js');
    require('../../../lib/webApp/panel');
    var companyDetail = {
        initCompanyDescPanel: function () {
            this.companDescPanel = $('#companyDescPanel').panel({
                contentWrap: $('.company_detail_panel'),
                openNode: $('#companyDescNode'),
                transformSpeed: 500,
                sourcePanelSwitch: false,
                scrollClose: true
            });
            $('#companyDescConfirmBtn').click(function () {
                companyDetail.companDescPanel.closePanel();
            });
        },
        init: function () {
            this.initCompanyDescPanel();
        }
    };
    companyDetail.init();
});