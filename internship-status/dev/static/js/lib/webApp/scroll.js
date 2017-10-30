define(function (require, exports, module) {
    require('zepto');
    var touch = require('touch');
    var strings = require('../strings');

    var scroll = function (_options) {
        var options = {

            target_node: null,

            /**
             * 垂直滑动
             */
            v_scroll: false,

            /**
             * 水平滑动
             */
            h_scroll: false,

            /**
             * 仅向左 滑与h_scroll同在时h_scroll无效
             */
            h_left_scroll: false,

            /**
             * 仅向右滑 与h_scroll同在时h_scroll无效
             */
            h_right_scroll: false,

            /**
             *仅向上滑 与v_scroll同在时v_scroll无效
             */
            v_up_scroll: false,

            /**
             * 仅向下滑 与v_scroll同在时v_scroll无效
             */
            v_down_scroll: false,

            /**
             * 最大滑动幅度
             */
            max_scroll: 99999,

            /**
             * 是否页面滑动就禁止滑动默认true
             */
            can_scroll_with_window: true,

            /**
             * 滑动开始
             */
            start_event_callback: null,

            /**
             * 滑动中
             */
            move_event_callback: null,

            /**
             * 滑动结束
             */
            end_callback: null
        };

        var status = {
            window_scroll_x: 0,
            window_scroll_y: 0,
            can_scroll: false,
            start_transform: ''
        };

        var targetScroller = function (distX, distY) {
            if (!matchAction(distX, distY)) {
                return;
            }

            if (options.h_left_scroll || options.h_right_scroll) {
                if ((options.h_left_scroll && !options.h_right_scroll && distX > 0) || (options.h_right_scroll && !options.h_left_scroll && distX < 0)) {
                    distX = 0;
                }
            } else if (!options.h_scroll) {
                distX = 0;
            }
            if (options.v_up_scroll || options.v_down_scroll) {
                if ((options.v_up_scroll && !options.v_down_scroll && distY > 0) || (options.v_down_scroll && !options.v_up_scroll && distY < 0)) {
                    distY = 0;
                }
            } else if (!options.v_scroll) {
                distY = 0;
            }

            if (options.max_scroll != null && !isNaN(options.max_scroll)) {
                if (Math.abs(distX) > options.max_scroll) {
                    if (distX > 0) {
                        distX = options.max_scroll;
                    } else {
                        distX = 0 - options.max_scroll;
                    }
                }
                if (Math.abs(distY) > options.max_scroll) {
                    if (distY > 0) {
                        distY = options.max_scroll;
                    } else {
                        distY = 0 - options.max_scroll;
                    }
                }
            }
            changeTargetTransform(distX, distY);
        };

        var matchAction = function (distX, distY) {
            if (!status.can_scroll) {
                return false;
            }
            if (options.can_scroll_with_window) {
                if (window.scrollX != status.window_scroll_x || window.scrollY != status.window_scroll_y) {
                    //页面滚动 不滑动
                    status.can_scroll = false;
                    return false;
                }
            }

            if (options.h_left_scroll || options.h_right_scroll || options.h_scroll) {
                if (Math.abs(distY) > Math.abs(distX)) {
                    return false;
                }
                if (Math.abs(distX) / Math.abs(distY) < 1.5) {
                    return false;
                }
            }
            if (options.v_up_scroll || options.v_down_scroll || options.v_scroll) {
                if (Math.abs(distX) > Math.abs(distY)) {
                    return false;
                }
                if (Math.abs(distY) / Math.abs(distX) < 1.5) {
                    return false;
                }
            }
            return true;
        };

        var changeTargetTransform = function (distX, distY) {
            options.target_node.css({
                'transition-property': '-webkit-transform',
                'transform-origin': '0px 0px 0px',
                'transform': 'translate(' + distX + 'px, ' + distY + 'px) scale(1) translateZ(0px)'
            });
        };

        var clean_target_transform = function () {
            if (strings.isNotEmpty(status.start_transform)) {
                options.target_node.css('transform', status.start_transform);
            } else {
                options.target_node.attr('translate-x', '0');
                options.target_node.attr('translate-y', '0');
                changeTargetTransform(0, 0);
            }
        };

        $.extend(options, _options);
        if (options.can_scroll_with_window) {
            $(window).scroll(function () {
                status.can_scroll = false;
            });
        }

        var _touch = new touch({
            target_node: options.target_node,
            start_event_callback: function (e) {
                if (typeof options.start_event_callback == 'function') {
                    options.start_event_callback(e);
                }
                status.can_scroll = true;//初始默认可以滑动
                status.window_scroll_x = window.scrollX;
                status.window_scroll_y = window.scrollY;
                var transform = options.target_node.css('transform');
                if (transform.indexOf('translate') >= 0) {
                    status.start_transform = transform;
                }
            },
            move_event_callback: function (e) {
                targetScroller(e.range.x2 - e.range.x1, e.range.y2 - e.range.y1);
                // if (Math.abs(e.range.x1 - e.range.x2) > 10) {
                //     e.preventDefault()
                // }
                if (typeof options.move_event_callback == 'function') {
                    options.move_event_callback(e);
                }
            },
            end_callback: function (e) {
                if (!matchAction(e.range.x2 - e.range.x1, e.range.y2 - e.range.y1)) {
                    clean_target_transform();
                    return;
                }
                e.clean_target_transform = clean_target_transform;
                if (typeof options.end_callback == 'function') {
                    options.end_callback(e);
                }
                // clean_range();
            }
        });

    };
    return scroll;
});
