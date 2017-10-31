define(function (require, exports, module) {
    var $ = require('../../../lib/webApp/zepto');
    require('../header.js');
    var Scroll = require('../../../lib/webApp/scroll');
    var Dialog = require('../../../lib/webApp/dialog');
    var Page = require('../../../lib/webApp/page/page');
    var Strings = require('../../../lib/strings.js');

    var CompanyList = {

        data: {
            action: ''
        },

        bindCompanyInfoScroll: function () {
            $('#companyList [item="companyShow"]').each(function () {
                var $this = $(this);
                if ($this.attr('has_operate') != 'true') {
                    return;
                }
                var operate_node = $this.parent().find('[item="operate"]');
                var operate_node_width = operate_node.width();
                new Scroll({
                    target_node: $this,
                    h_scroll: true,
                    max_scroll: operate_node_width,
                    start_event_callback: function (e) {
                        operate_node.css('visibility', 'visible');
                    },
                    move_event_callback: function (e) {

                    },
                    end_callback: function (e) {
                        var range = e.range;
                        var swipe_type = e.swipe_direction(range);
                        if (swipe_type == '_left' && Math.abs(range.x1 - range.x2) >= operate_node_width / 2) {
                            $this.css({'transform': 'translate(-' + operate_node_width + 'px, 0px) scale(1) translateZ(0px)'});
                        } else {
                            $this.css({'transform': 'translate(0px, 0px) scale(1) translateZ(0px)'});
                            operate_node.css('visibility', 'hidden');
                        }
                    }
                });
            });
        },

        buildCompanyListHtml: function (companies) {
            var html = [];
            for (var i = 0; i < companies.length; ++i) {
                var company = companies[i];
                var operateHtml = [];
                if (this.data.action == 'addStudent') {
                    operateHtml.push('<li class="company" item="assignCompany"><div class="assign">分配实习单位</div></li>');
                }
                html.push('<li  class="user_node" item="companyNode" companyDesId="' + company.desId + '">');
                html.push('<div class="operate" item="operate">');
                html.push('<ul class="operate_list">' + operateHtml.join('') + '</ul>');
                html.push('</div>');
                html.push('<div class="user_show" item="companyShow" has_operate="' + (operateHtml.length > 0) + '">');
                html.push('<ul class="user_info">');
                html.push('<li class="company_name">');
                html.push('<span>' + Strings.substring(company.companyName, 13) + '</span>');
                html.push('</li>');
                html.push('<li class="student_number"><span>' + company.studentNumber + '人</span><i>&gt;</i></li>');
                html.push('</ul>');
                html.push('<ul class="user_message">');
                html.push('<li class="_left">单位地址：</li>');
                html.push('<li class="_right">' + Strings.substring(company.companyAddress, 6) + '</li>');
                html.push('<li class="_left">容纳人数：</li>');
                html.push('<li class="_right">' + Strings.filterString(company.internshipNumber, '0') + '</li>');
                html.push('</ul>');
                html.push('</div>');
                html.push('</li>');
                html.push('<hr/>');
            }
            return html.join('');
        },

        bindStudentAssignCompanyAction: function () {
            var assignCompanyNode = $('#companyList [item="assignCompany"]');
            assignCompanyNode.off('click');
            assignCompanyNode.on('click', function (e) {
                e.stopPropagation();
                var companyDestId = $(this).parents('[item="companyNode"]').attr('companyDesId');
                var studentDesId = $('#studentDesId').val();
                if (Strings.isEmpty(studentDesId) || Strings.isEmpty(companyDestId)) {
                    Dialog.push_error_message('单位或学生不能为空!');
                    return;
                }
                Dialog.yes_no('您确定要将该学生分配给该单位吗?', function () {
                    $.ajax({
                        url: '/student/updateCompany.json',
                        type: 'POST',
                        data: {
                            studentDestId: studentDesId,
                            companyDestId: companyDestId
                        },
                        dataType: 'json',
                        success: function (result) {
                            Dialog.close_dialog();
                            if (result.ret) {
                                if (result.data == studentDesId.split(',').length) {
                                    Dialog.push_ok_message('操作成功!');
                                } else {
                                    Dialog.push_ok_message('部分学生操作成功，您这个成功操作了' + result.data + '个学生');
                                }
                                setTimeout(function () {
                                    window.location.href = $('#returnUrl').val();
                                }, 2000);
                            } else {
                                Dialog.push_error_message(result.message);
                            }
                        }
                    })
                });
            });

        },

        loadCompanyListInfo: function (currentPage) {
            $.ajax({
                url: '/company/list.json',
                type: 'POST',
                data: {
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    Dialog.dialog_load('加载中...');
                },
                success: function (result) {
                    Dialog.close_dialog();
                    if (result.ret) {
                        if (currentPage <= 1) {
                            $('#companyList').html('');
                        }
                        var companyListHtml = CompanyList.buildCompanyListHtml(result.data.companies);
                        $('#companyList').append(companyListHtml);
                        var page_data = result.data.page;
                        Page.loadSinglePage(page_data.currentPage, page_data.totalPage, $('#companyList'), CompanyList.loadCompanyListInfo);
                        CompanyList.bindCompanyInfoScroll();
                        CompanyList.bindStudentAssignCompanyAction();
                    } else {
                        Dialog.push_error_message(result.message);
                    }
                }
            });
        },

        init: function () {
            this.data.action = $('#action').val();
            this.loadCompanyListInfo(1);
        }
    };

    CompanyList.init();

});