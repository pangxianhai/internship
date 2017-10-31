define(function (require) {
    var $ = require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/validator.js');
    var Strings = require('../../lib/strings.js');
    var Dialog = require('../../lib/dialog.js');

    var UniversityList = {

        buildDepartmentShowHtml: function (departmentList) {
            var html = [];
            for (var i = 0; i < departmentList.length; ++i) {
                var department = departmentList[i];
                html.push('<tr level="' + department.level + '">');
                html.push('<td class="name">');
                for (var j = 0; j < department.level; ++j) {
                    html.push('<span class="_icon _line"></span>');
                }
                if (department.isLeafDepartment) {
                    if (i == departmentList.length - 1) {
                        html.push('<span class="_icon _join_bottom"></span>');
                    } else {
                        html.push('<span class="_icon _join"></span>');
                    }
                } else {
                    html.push('<span class="_icon _plus" departmentDesId="' + department.desId + '"></span>');
                }
                html.push('<span item="nameNode" class="_name">' + department.departmentName + '</span>');
                html.push('</td>');
                html.push('<td>' + department.departmentType.desc + '</td>');
                html.push('<td>' + Strings.filterString(department.departmentPath, '-') + '</td>');
                html.push('<td>');
                html.push('<a href="javascript:void(0)" item="update" departmentDesId="' + department.desId + '">[修改]</a>');
                if (!department.isSpeciality) {
                    html.push('<a href="/university/add.htm?parentDesId=' + department.desId + '" >[添加]</a>');
                }
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },

        buildDepartmentSearchResultHtml: function (departmentList) {
            var html = [];
            for (var i = 0; i < departmentList.length; ++i) {
                var department = departmentList[i];
                html.push('<tr>');
                html.push('<td class="name">');
                if (i == departmentList.length - 1) {
                    html.push('<span class="_icon _join_bottom"></span>');
                } else {
                    html.push('<span class="_icon _join"></span>');
                }
                html.push('<span item="nameNode" class="_name">' + department.departmentName + '</span>');
                html.push('</td>');
                html.push('<td>' + department.departmentType.desc + '</td>');
                html.push('<td>' + Strings.filterString(department.departmentPath, '-') + '</td>');
                html.push('<td>');
                html.push('<a href="javascript:void(0)" item="update" departmentDesId="' + department.desId + '">修改</a>')
                html.push('</td>');
                html.push('</tr>');
            }
            return html.join('');
        },

        bindDepartmentPlusAction: function () {
            var plusNode = $('#u_data ._plus');
            plusNode.off('click');
            plusNode.on('click', function () {
                var $this = $(this);
                var $thisTr = $this.parent().parent();
                if ($this.hasClass('_minus')) {
                    $this.removeClass('_minus');
                    UniversityList.eachDepartmentNextAllNode($thisTr, function (_departmentNode) {
                        _departmentNode.hide();
                    });
                } else {
                    $this.addClass('_minus');
                    var hasNext = false;
                    UniversityList.eachDepartmentNextAllNode($thisTr, function (_departmentNode, awayLevel) {
                        if (awayLevel == 1) {
                            hasNext = true;
                            _departmentNode.find('.name ._plus').removeClass('_minus');
                            _departmentNode.show();
                        }
                    });
                    if (!hasNext) {
                        var desId = $this.attr('departmentDesId');
                        UniversityList.loadUniversityDepartment(desId, function (departmentHtml) {
                            $this.parent().parent().after(departmentHtml);
                        });
                    }
                }
            });
        },

        bindUpdateAction: function () {
            var updateNode = $('#u_data A[item="update"]');
            updateNode.off('click');
            updateNode.on('click', function () {
                var $this = $(this);
                $this.find('input').val('');
                $('#updatePanel').dialog({
                    title: '您正在修改-' + $this.parent().parent().find('[item="nameNode"]').html(),
                    width: 320
                });
                $('#updateForm').validator({
                    fields: {
                        '[name="departmentName"]': '部门名称:required;length[1~50]'
                    },
                    submit_target: '#updateSubmit',
                    valid: function (form) {
                        $.ajax({
                            url: '/university/update.json',
                            type: 'POST',
                            data: $.extend(Strings.getFormData(form), {
                                desId: $this.attr('departmentDesId')
                            }),
                            dataType: 'json',
                            success: function (result) {
                                if (result.ret) {
                                    Dialog.openSample('系统提示', '修改成功');
                                    setTimeout(function () {
                                        window.location.reload(true);
                                    }, 1000);
                                } else {
                                    Dialog.openSample('系统提示', result.message);
                                }
                            }
                        });
                    }
                });
            });
        },

        eachDepartmentNextAllNode: function (departmentNode, callback) {
            departmentNode.nextAll().each(function (index, $this) {
                var thisLevel = parseInt($($this).attr('level'));
                var operateLevel = parseInt(departmentNode.attr('level'));
                if (thisLevel <= operateLevel) {
                    return false;
                } else {
                    callback($($this, thisLevel - operateLevel));
                }
            })
        },

        loadUniversityDepartment: function (parentDesId, callback) {
            $.ajax({
                url: '/university/search.json',
                type: 'POST',
                data: {
                    parentDesId: parentDesId
                },
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        var html = UniversityList.buildDepartmentShowHtml(result.data);
                        if (typeof callback == 'function') {
                            callback(html);
                        }
                        UniversityList.bindDepartmentPlusAction();
                        UniversityList.bindUpdateAction();
                    } else {
                        Dialog.openSample('系统提示', result.message);
                    }
                }
            });
        },

        bindSearchAction: function () {
            $('#searchBtn').click(function () {
                var searchData = Strings.getFormData($('#search_form'));
                var emptyData = true;
                for (var key in searchData) {
                    if (Strings.isNotEmpty(searchData[key])) {
                        emptyData = false;
                        break;
                    }
                }
                if (emptyData) {
                    UniversityList.loadUniversityDepartment('', function (departmentHtml) {
                        $('#u_data').html(departmentHtml);
                    });
                } else {
                    $.ajax({
                        url: '/university/search.json',
                        type: 'POST',
                        data: $.extend(Strings.getFormData($('#search_form')), {
                            action: 'search'
                        }),
                        dataType: 'json',
                        success: function (result) {
                            if (result.ret) {
                                var html = UniversityList.buildDepartmentSearchResultHtml(result.data);
                                $('#u_data').html(html);
                            } else {
                                Dialog.openSample('系统提示', result.message);
                            }
                        }
                    });
                }
            });
        },

        init: function () {
            this.loadUniversityDepartment('', function (departmentHtml) {
                $('#u_data').html(departmentHtml);
            });
            this.bindSearchAction();
        }
    };
    UniversityList.init();
});