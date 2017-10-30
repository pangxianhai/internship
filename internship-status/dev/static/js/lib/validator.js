define(function (require, exports, module) {
    var $ = require('../lib/jquery-1.11.3.js');
    var strings = require('../lib/strings.js');
    var css = require('../../css/validator/validator.css', 'css');
    var validator = {
        options: {},
        push_error_sign: function (node, msg) {
            if (typeof this.options.error == 'function') {
                this.options.error(node, msg);
            } else {
                var errHtml = [];
                errHtml.push('<span class="n-icon"></span>');
                errHtml.push('<span class="n-msg">' + msg + '</span>');
                var errorNode = node.parent().find('.n-error');
                if (errorNode.length > 0) {
                    errorNode.show();
                } else {
                    node.parent().append('<span class="n-error"></span>');
                    errorNode = node.parent().find('.n-error');
                }
                errorNode.html(errHtml.join(''));
                node.parent().find('.n-ok').hide();
            }
        },
        push_ok_sign: function (node) {
            if (typeof this.options.ok == 'function') {
                this.options.ok(node);
            } else {
                var okNode = node.parent().find('.n-ok');
                if (okNode.length > 0) {
                    okNode.show();
                } else {
                    node.parent().append('<span class="n-ok"></span>');
                }
                node.parent().find('.n-error').hide();
            }
        },

        check_number: function (node) {
            if (isNaN(node.val())) {
                this.push_error_sign(node, '请填入数字类型');
                return false;
            }
            return true;
        },

        positive_integer: function (node) {
            if (!this.check_number(node)) {
                return false;
            }
            if (parseInt(node.val()) != node.val()) {
                this.push_error_sign(node, '请填入整数');
                return false;
            }
            if (parseInt(node.val()) <= 0) {
                this.push_error_sign(node, '请填入不小于0的整数');
                return false;
            }
            return true;
        },

        required: function (node, name) {
            if (strings.isEmpty(node.val())) {
                this.push_error_sign(node, name + '不能为空');
                return false;
            } else {
                return true;
            }
        },
        check_length: function (node, value) {
            var range = value.replace('[', '').replace(']', '').split('~');
            if (range.length == 1) {
                if (node.val().length == parseInt(range[0])) {
                    return true;
                } else {
                    this.push_error_sign(node, '请输入' + range[0] + '个字符');
                }
            } else {
                if (strings.isEmpty(range[0]) && strings.isEmpty(range[1])) {
                    return true;
                } else if (strings.isEmpty(range[0])) {
                    if (node.val().length <= parseInt(range[1])) {
                        return true;
                    } else {
                        this.push_error_sign(node, '不能超过' + range[1] + '个字符');
                    }
                } else if (strings.isEmpty(range[1])) {
                    if (node.val().length >= parseInt(range[0])) {
                        return true;
                    } else {
                        this.push_error_sign(node, '不能少于' + range[0] + '个字符');
                    }
                } else {
                    if (node.val().length >= parseInt(range[0]) && node.val().length <= parseInt(range[1])) {
                        return true;
                    } else {
                        this.push_error_sign(node, '请输入' + range[0] + '到' + range[1] + '个字符');
                    }
                }
            }
            return false;
        },

        self_function: function (r, fieldNode) {
            if (typeof this.options.rules[r] == 'function') {
                var result = this.options.rules[r](fieldNode.val());
                if (result.ret) {
                    return true;
                } else {
                    this.push_error_sign(fieldNode, result.message);
                }
            } else {
                return true;
            }
        },

        validator: function (field, fieldValue) {
            var rule = fieldValue.split(":");
            var name = rule[0];
            var fieldNode = $(field);
            var ok = true;
            if (rule.length == 2) {
                var rules = rule[1].split(";");
                for (var i = 0; i < rules.length; ++i) {
                    var r = rules[i];
                    if (r == 'required') {
                        if (!validator.required(fieldNode, name)) {
                            ok = false;
                            break;
                        }
                    } else if (r.indexOf('length') >= 0) {
                        if (!validator.check_length(fieldNode, r.replace('length', ''))) {
                            ok = false;
                            break;
                        }
                    } else if (r === 'number') {
                        if (!validator.check_number(fieldNode)) {
                            ok = false;
                            break;
                        }
                    } else if (r === 'positive_integer') {
                        if (!validator.positive_integer(fieldNode)) {
                            ok = false;
                            break;
                        }
                    } else {
                        if (!validator.self_function(r, fieldNode)) {
                            ok = false;
                            break;
                        }
                    }
                }
            }
            if (ok) {
                validator.push_ok_sign(fieldNode);
            }
            return ok;
        },
        validator_fields: function (fields) {
            var ok = true;
            for (var field in fields) {
                $(field).each(function () {
                    if (!validator.validator(this, fields[field])) {
                        ok = false;
                    }
                });
            }
            return ok;
        },
        reset_sign: function (options) {
            var fields = options.fields;
            for (var field in fields) {
                if (typeof options.reset_sign == 'function') {
                    options.reset_sign($(field));
                } else {
                    $(field).parent().find('.n-error').hide();
                    $(field).parent().find('.n-ok').hide();
                }
            }
        },
        bind_validator_field_mouse_action: function (options) {
            var fields = options.fields;
            for (var field in fields) {
                $(field).each(function () {
                    $(this).blur({"field": field, "node": this}, function (event) {
                        validator.validator(event.data.node, fields[event.data.field]);
                    });
                });
            }
        },
        bind_submit_action: function (options, form) {
            $(options.submit_target).unbind('click');
            $(options.submit_target).bind('click', function () {
                if (validator.validator_fields(options.fields)) {
                    options.valid(form);
                }
            });
            if (typeof  options.submitAction == 'function') {
                options.submitAction(function () {
                    if (validator.validator_fields(options.fields)) {
                        options.valid(form);
                    }
                });
            }
        },

        init_css: function () {
            $('head').append('<style type="text/css">' + css + '</style>');
        },
        init: function () {
            $.fn['validator'] = function (options) {
                if ($(this).length == 0) {
                    return;
                }
                validator.options = options;
                validator.bind_submit_action(options, this);
                validator.bind_validator_field_mouse_action(options);
                validator.reset_sign(options);
            }
            this.init_css();
        }
    };
    validator.init();
});


