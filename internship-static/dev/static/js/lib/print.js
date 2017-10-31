define(function (require, exports, module) {
    'use strict';
    exports.print = function (print_node, print_content, callback) {
        var _page_content = document.body.innerHTML;
        print_node.hide();
        document.body.innerHTML = print_content.prop("outerHTML");
        window.print();
        document.body.innerHTML = _page_content;
        callback();
    };
});