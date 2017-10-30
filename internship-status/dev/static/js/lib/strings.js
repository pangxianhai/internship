define(function (require, exports, module) {
    'use strict';

    exports.isEmpty = function (text) {
        if (typeof text == 'undefined' || text == null || text.length == 0) {
            return true;
        }
        return false;
    };

    exports.isNotEmpty = function (text) {
        return !this.isEmpty(text);
    };

    exports.getFormData = function (frm) {
        var o = {};
        var a = frm.serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    exports.filterNull = function (text) {
        return this.filterString(text, '');
    };

    exports.filterString = function (text, defaultText) {
        if (this.isEmpty(text)) {
            return defaultText;
        } else {
            return text;
        }
    };

    exports.substring = function (text, len) {
        text = this.filterString(text, '');
        if (text.length <= len) {
            return text;
        } else {
            return text.substring(0, len) + '...';
        }
    }

    exports.week_show = function (w) {
        var map = {
            0: '日',
            1: '一',
            2: '二',
            3: '三',
            4: '四',
            5: '五',
            6: '六'
        }
        return map[w];
    }

    exports.check_score = function (score) {
        var result = {};
        result.ret = false;
        if (isNaN(score)) {
            result.message = '请填入数字';
            return result;
        }
        if (score < 0 || score > 100) {
            result.message = '请填入0~100之间的分数';
            return result;
        }
        var sar = score.split('.');
        if (sar.length == 2 && sar[1].length > 2) {
            result.message = '分数必须是两位以内的小数';
            return result;
        }
        result.ret = true;
        return result;
    }

    exports.format_number = function (val, len) {
        var num = "" + val;
        while (num.length < len) {
            num = "0" + num;
        }
        return num;
    }
});