define(function (require, exports, module) {
    exports.reset_evaluation_table = function (trs) {
        var post = 1;
        for (var i = 0; i < trs.length; ++i) {
            var this_subject = $(trs[i]).find('.subject');
            if (i == 0) {
                this_subject.attr("rowspan", 1);
                post = 1;
            } else {
                var subject_td = $(trs[i - post]).find('.subject');
                if (this_subject.html() == subject_td.html()) {
                    this_subject.hide();
                    subject_td.attr("rowspan", parseInt(subject_td.attr("rowspan")) + 1);
                    post++;
                } else {
                    this_subject.attr("rowspan", 1);
                    post = 1;
                }
            }
        }
    }
})