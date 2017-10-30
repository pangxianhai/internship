define(function (require, exports, module) {
    'use strict';
    var Strings = require('strings.js');

    function UniversityDepartment (options) {
        var node = {
            selectNode: null,
            type: '',
            departmentId: '',
            lock: false
        };
        if (typeof options.university != 'undefined') {
            this.universityNode = $.extend({}, node, {
                type: 'university'
            }, options.university);
        }

        if (typeof options.college != 'undefined') {
            this.collegeNode = $.extend({}, node, {
                type: 'college'
            }, options.college);
        }

        if (typeof options.department != 'undefined') {
            this.departmentNode = $.extend({}, node, {
                type: 'department'
            }, options.department);
        }

        if (typeof options.speciality != 'undefined') {
            this.specialityNode = $.extend({}, node, {
                type: 'speciality'
            }, options.speciality);
        }
        this.complete = options.complete;
        this.main();
    }

    UniversityDepartment.prototype = {
        constructor: UniversityDepartment,

        hideSelectNode: function (selectNode) {
            for (var i = 0; i < selectNode.length; ++i) {
                var node = selectNode[i];
                if (typeof  node != 'undefined') {
                    node.selectNode.html('');
                    node.selectNode.hide();
                }
            }
        },

        buildOptionHtml: function (departmentList, type) {
            var departmentNode;
            if (departmentList.length == 0 && type == 'load') {
                if (typeof this.complete == 'function') {
                    this.complete();
                }
                return departmentNode;
            }
            var html = [];
            html.push('<option value="">全部</option>')
            for (var i = 0; i < departmentList.length; ++i) {
                var department = departmentList[i];
                if (i == 0) {
                    if (department.isUniversity) {
                        departmentNode = this.universityNode;
                    } else if (department.isCollege) {
                        departmentNode = this.collegeNode;
                    } else if (department.isDepartment) {
                        departmentNode = this.departmentNode;
                    } else if (department.isSpeciality) {
                        departmentNode = this.specialityNode;
                    }
                }
                html.push('<option value="' + department.desId + '">' + department.departmentName + '</option>')
            }
            if (typeof departmentNode != 'undefined' && departmentNode != null) {
                departmentNode.selectNode.html(html.join(''));
                departmentNode.selectNode.show();
                if (Strings.isNotEmpty(departmentNode.departmentId)) {
                    departmentNode.selectNode.val(departmentNode.departmentId);
                    this.loadUniversityDepartment(departmentNode.departmentId, type);
                    departmentNode.departmentId = ''; //使用完成清除
                } else if (type == 'load') {
                    if (typeof this.complete == 'function') {
                        this.complete();
                    }
                }
                if (departmentNode.lock) {
                    departmentNode.selectNode.attr('disabled', 'disabled');
                }
            }
            return departmentNode;
        },

        bindSelectAction: function (departmentNode) {
            if (typeof departmentNode != 'undefined' && departmentNode != null && !departmentNode.lock) {
                var $this = this;
                departmentNode.selectNode.off('change');
                departmentNode.selectNode.on('change', '', departmentNode.type, function (event) {
                    if (event.data == 'university') {
                        $this.hideSelectNode([$this.collegeNode, $this.departmentNode, $this.specialityNode]);
                    } else if (event.data == 'college') {
                        $this.hideSelectNode([$this.departmentNode, $this.specialityNode]);
                    } else if (event.data == 'department') {
                        $this.hideSelectNode([$this.specialityNode]);
                    }
                    var thisDepartmentId = $(this).val();
                    if (Strings.isNotEmpty(thisDepartmentId)) {
                        $this.loadUniversityDepartment(thisDepartmentId, 'action');
                    }
                });
            }
        },

        loadUniversityDepartment: function (parentId, type) {
            var $this = this;
            $.ajax({
                url: '/university/list.json',
                type: 'POST',
                data: {
                    parentDesId: parentId
                },
                dataType: 'json',
                success: function (result) {
                    if (result.ret) {
                        var departmentNode = $this.buildOptionHtml(result.data, type);
                        $this.bindSelectAction(departmentNode);
                    } else {
                        console.log('load university department failed:' + result.message);
                    }
                }
            })
        },

        main: function () {
            this.hideSelectNode([this.collegeNode, this.departmentNode, this.specialityNode]);
            this.loadUniversityDepartment('', 'load');
        }
    };

    UniversityDepartment.initSelect = function (options) {
        new UniversityDepartment(options);
    }

    return UniversityDepartment;
});