define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var strings = require('../../lib/strings.js');

    var company_list = {
        data: {
            action: '',
            is_sysadmin: false
        },
        bind_page_action: function () {
            page.turning_page(company_list.load_company_info);
        },
        push_user_info: function (companies) {
            var html = [];
            for (var i = 0; i < companies.length; ++i) {
                var company = companies[i];
                html.push('<tr>');
                html.push('<td><input item="select_company" companyDestId="' + company.desId + '" type="checkbox"/></td>');
                html.push('<td><a href="/company/detail/' + company.desId + '.htm">' + company.companyName + '</a></td>');
                html.push('<td>' + company.companyAddress + '</td>');
                html.push('<td>' + company.phone + '</td>');
                html.push('<td>' + company.internshipNumber + '</td>');
                html.push('<td>' + strings.filterNull(company.studentNumber) + '</td>');
                html.push('<td>' + new Date(company.createdTime).format('yyyy-MM-dd') + '</td>');
                if (this.data.is_sysadmin || this.data.action == 'addStudent') {
                    html.push('<td>');
                    if (this.data.action == 'addStudent') {
                        html.push('<a item="addStudent" companyName="' + company.companyName + '"  companyId="' + company.desId + '" href="javascript:void(0)">[分配]</a>');
                    } else if (this.data.is_sysadmin) {
                        html.push('<a href="/tutor/list.htm?companyName=' + encodeURIComponent(company.companyName) + '">[查看导师]</a>');
                        html.push('<br/>');
                        html.push('<a href="javascript:void(0)" item="delete" companyDestId="' + company.desId + '">[删除]</a>');
                    }
                    html.push('</td>');
                }
                html.push('</tr>');
            }
            return html.join('');
        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(company_list.push_user_info(result.data.companies));
                $('.page_list').html(page.html(result.data.page));
                company_list.bind_page_action();
                company_list.bind_add_student_action();
                company_list.bind_delete_company_action();
            } else {

            }
        },
        load_company_info: function (currentPage) {
            $.ajax({
                url: '/company/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: company_list.push_page_data
            });
        },
        delete_company: function (companyDestId, total_count) {
            $.ajax({
                url: '/company/delete.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    companyDestId: companyDestId
                },
                success: function (result) {
                    if (result.ret) {
                        if (result.data == total_count) {
                            dialog.openSample('提示', '删除成功');
                        } else {
                            dialog.openSample('提示', '部分单位操作成功，您这个成功操作了' + result.data + '个单位');
                        }
                        company_list.load_company_info(page.current_page());
                        dialog.close(2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                company_list.load_company_info(1);
            });
        },
        bind_add_student_action: function () {
            var addBtn = $('a[item="addStudent"]');
            addBtn.unbind("click");
            addBtn.bind("click", function () {
                var thisObj = this;
                dialog.yesNo('提示', '您确定将该学生分配到' + this.getAttribute('companyName') + '吗？', function () {
                    dialog.close(1);
                    company_list.add_student(thisObj.getAttribute('companyId'));
                });
            });
        },
        add_student: function (companyId) {
            var studentDestId = $('#studentId').val();
            $.ajax({
                url: '/student/updateCompany.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    companyDestId: companyId,
                    studentDestId: studentDestId
                },
                success: function (result) {
                    if (result.ret) {
                        if (result.data == studentDestId.split(',').length) {
                            dialog.openSample('提示', '分配成功');
                        } else {
                            dialog.openSample('提示', '部分学生操作成功，您这个成功操作了' + result.data + '个学生');
                        }
                        dialog.close(2000);
                        window.location.href = '/student/list.htm';
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },
        bind_delete_company_action: function () {
            $('a[item="delete"]').unbind('click');
            $('a[item="delete"]').bind('click', function () {
                var companyDestId = $(this).attr('companyDestId');
                dialog.yesNo('提示', '您确定要删除该实习单位吗？', function () {
                    company_list.delete_company(companyDestId, 1);
                });
            });
        },
        bind_select_all_action: function () {
            $('input[item="select_all"]').click(function () {
                $('input[item="select_company"]').prop('checked', $(this).is(':checked'));
            });
        },
        bind_delete_company_all_action: function () {
            $('#delete_company_all').click(function () {
                var companyDestIds = [];
                $('input[item="select_company"]:checked').each(function () {
                    companyDestIds.push($(this).attr('companyDestId'));
                });
                if (companyDestIds.length == 0) {
                    dialog.openSample('提示', '请选择要删除的单位!');
                    return;
                }
                dialog.yesNo('提示', '您确定要删除选中的单位吗？', function () {
                    company_list.delete_company(companyDestIds.join(','), companyDestIds.length);
                });
            });
        },
        init: function () {
            this.data.action = $('#action').val();
            this.data.is_sysadmin = $('#isSysadmin').val() == 'true';
            this.load_company_info(1);
            this.bind_search_action();
            this.bind_select_all_action();
            this.bind_delete_company_all_action();
        }
    };
    company_list.init();
});