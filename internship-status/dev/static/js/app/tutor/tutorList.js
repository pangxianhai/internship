define(function (require) {
    require('../../lib/jquery-1.11.3');
    require('../menu.js');
    require('../../lib/date.js');
    var page = require('../../lib/page.js');
    var string = require('../../lib/strings.js');
    var dialog = require('../../lib/dialog.js');
    var tutor_list = {
        data: {
            action: '',
            is_sysadmin: false,
            is_leader: false
        },
        bind_page_action: function () {
            page.turning_page(tutor_list.load_tutor_info);
        },
        push_tutor_info: function (tutors) {
            var html = [];
            for (var i = 0; i < tutors.length; ++i) {
                var tutor = tutors[i];
                html.push('<tr>');
                html.push('<td>' + tutor.name + '</td>');
                html.push('<td>' + tutor.userInfo.userName + '</td>');
                html.push('<td>' + tutor.userInfo.phone + '</td>');
                html.push('<td>' + tutor.userInfo.sex.desc + '</td>');
                html.push('<td><a href="/company/detail/' + tutor.companyDestId + '.htm">' + tutor.companyName + '</a></td>');
                html.push('<td>' + tutor.department + '</td>');
                html.push('<td>' + tutor.tutorType.desc + '</td>');
                html.push('<td>' + string.filterString(tutor.studentNumber, '0') + '</td>');
                html.push('<td>' + new Date(tutor.createdTime).format('yyyy-MM-dd') + '</td>');
                if (this.data.action == 'addStudent' || this.data.is_sysadmin || this.data.is_leader) {
                    html.push('<td>');
                    if (this.data.action == 'addStudent') {
                        html.push('<a item="addStudent" tutorName="' + tutor.name + '"  tutorId="' + tutor.desId + '" href="javascript:void(0)">[分配]</a>');
                    } else if (this.data.is_sysadmin) {
                        if (!tutor.isLeader) {
                            html.push('<a item="setLeader" desId="' + tutor.desId + '" href="javascript:void(0)">[设为负责人]</a><br/>');
                        }
                        html.push('<a item="delete" desId="' + tutor.desId + '" href="javascript:void(0)">[删除]</a>');
                    } else if (this.data.is_leader && !tutor.leader) {
                        html.push('<a item="delete" desId="' + tutor.desId + '" href="javascript:void(0)">[删除]</a>');
                    }
                    html.push('</td>')
                }
                html.push('</tr>');
            }
            return html.join('');

        },
        push_page_data: function (result) {
            if (result.ret) {
                $('#u_data').html(tutor_list.push_tutor_info(result.data.tutors));
                $('.page_list').html(page.html(result.data.page));
                tutor_list.bind_page_action();
                tutor_list.bind_add_student_action();
                tutor_list.bind_set_leader_action();
                tutor_list.bind_delete_tutor_action();
            } else {

            }
        },
        set_leader: function (desId) {
            $.ajax({
                url: '/tutor/setLeader.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    tutorDesId: desId
                },
                success: function (result) {
                    if (result.ret) {
                        dialog.openSample('提示', '操作成功');
                        tutor_list.load_tutor_info(page.current_page());
                        dialog.close(2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            })
        },
        bind_set_leader_action: function () {
            $('a[item="setLeader"]').unbind('click');
            $('a[item="setLeader"]').bind('click', function () {
                var desId = this.getAttribute('desId');
                $.ajax({
                    url: '/tutor/checkCompanyHasLeader.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        tutorDesId: desId
                    },
                    success: function (result) {
                        if (result.ret) {
                            if (result.data) {
                                tutor_list.set_leader(desId);
                            } else {
                                dialog.yesNo('提示', '该单位已经有负责人了！你确定要更改负责人么？', function () {
                                    tutor_list.set_leader(desId);
                                });
                            }
                        } else {
                            dialog.openSample('提示', result.message);
                        }
                    }
                })
            });
        },
        bind_delete_tutor_action: function () {
            $('a[item="delete"]').unbind("click");
            $('a[item="delete"]').bind("click", function () {
                var destId = $(this).attr("desId");
                dialog.yesNo('提示', '您确定要删除该指导老师吗？', function () {
                    $.ajax({
                        url: '/tutor/delete.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            destId: destId
                        },
                        success: function (result) {
                            if (result.ret) {
                                dialog.openSample('提示', '删除成功!');
                                tutor_list.load_tutor_info(page.current_page());
                                dialog.close(2000);
                            } else {
                                dialog.openSample('提示', result.message);
                            }
                        }
                    });
                });
            });
        },
        bind_add_student_action: function () {
            var addBtn = $('a[item="addStudent"]');
            addBtn.unbind("click");
            addBtn.bind("click", function () {
                var thisObj = this;
                dialog.yesNo('提示', '您确定将该学生分配给' + this.getAttribute('tutorName') + '吗？', function () {
                    dialog.close(1);
                    tutor_list.add_student(thisObj.getAttribute('tutorId'));
                });
            });
        },
        add_student: function (tutorId) {
            $.ajax({
                url: '/student/updateTutor.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    tutorDestId: tutorId,
                    studentDestId: $('#studentId').val()
                },
                success: function (result) {
                    if (result.ret) {
                        if (result.data == $('#studentId').val().split(',').length) {
                            dialog.openSample('提示', '分配成功');
                        } else {
                            dialog.openSample('提示', '部分学生操作成功，您这个成功操作了' + result.data + '个学生');
                        }
                        setTimeout(function () {
                            dialog.close(1);
                            window.location.href = '/student/list.htm';
                        }, 2000);
                    } else {
                        dialog.openSample('提示', result.message);
                    }
                }
            });
        },
        load_tutor_info: function (currentPage) {
            $.ajax({
                url: '/tutor/list.json',
                type: 'POST',
                data: $.extend(string.getFormData($('#search_form')), {
                    currentPage: currentPage
                }),
                dataType: 'json',
                beforeSend: function () {
                },
                success: tutor_list.push_page_data
            });
        },
        bind_search_action: function () {
            $('#search_action').click(function () {
                tutor_list.load_tutor_info(1);
            });
        },
        init: function () {
            this.data.action = $('#action').val();
            this.data.is_sysadmin = $('#isSysadmin').val() == 'true';
            this.data.is_leader = $('#isLeader').val() == 'true';
            this.load_tutor_info(1);
            this.bind_search_action();
        }
    };
    tutor_list.init();
});