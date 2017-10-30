define(function (require, exports, module) {
    require('zepto');
    var strings = require('../strings');

    var touch = function (_options) {
        var options = {
            target_node: null,
            /**
             * touch开始 callback
             */
            start_event_callback: null,

            /**
             * touch中 callback
             */
            move_event_callback: null,

            /**
             * touch束 callback
             */
            end_callback: null
        };

        var _action = {
            touch_timeout: null,
            tap_timeout: null,
            swipe_timeout: null,
            long_tap_timeout: null,
            long_tap_delay: 750
        };

        var range = {
            x1: 0,
            y1: 0,
            x2: 0,
            y2: 0
        };

        var _touch = {
            clean_time_out: function (timeout) {
                if (timeout) {
                    clearTimeout(timeout);
                }
                timeout = null;
            },

            clean_long_tap_timeout: function () {
                this.clean_time_out(_action.long_tap_timeout);
                _action.long_tap_timeout = null;
            },

            clean_touch_timeout: function () {
                this.clean_time_out(_action.touch_timeout);
                _action.touch_timeout = null;
            },
            clean_tap_timeout: function () {
                this.clean_time_out(_action.tap_timeout);
                _action.tap_timeout = null;
            },

            clean_swipe_timeout: function () {
                this.clean_time_out(_action.swipe_timeout);
                _action.swipe_timeout = null;
            },

            clean_range: function () {
                range.x1 = 0;
                range.y1 = 0;
                range.x2 = 0;
                range.y2 = 0;
            },

            clean_all: function () {
                this.clean_long_tap_timeout();
                this.clean_touch_timeout();
                this.clean_tap_timeout();
                this.clean_swipe_timeout();
                this.clean_range();
            },

            swipe_direction: function (range) {
                var x_delta = Math.abs(range.x1 - range.x2);
                var y_delta = Math.abs(range.y1 - range.y2);
                return x_delta >= y_delta ? (range.x1 - range.x2 > 0 ? '_left' : '_right') : (range.y1 - range.y2 > 0 ? '_up' : '_down');
            },

            get_touch_point: function (e) {
                var point = {
                    x: 0,
                    y: 0
                };
                if (typeof e.clientX != 'undefined') {
                    point.x = e.clientX;
                    point.y = e.clientY;
                } else if (typeof  e.touches != 'undefined' && e.touches.length > 0) {
                    point.x = e.touches[0].pageX;
                    point.y = e.touches[0].pageY;
                } else if (typeof e.originalEvent != 'undefined' && typeof e.originalEvent.targetTouches != 'undefined' && e.originalEvent.targetTouches.length > 0) {
                    point.x = e.originalEvent.targetTouches[0].clientX;
                    point.y = e.originalEvent.targetTouches[0].clientY;
                }
                return point;
            },

            bind_touch_action: function () {
                var is_touch_pad = (/hp-tablet/gi).test(navigator.appVersion);
                var has_touch = 'ontouchstart' in window && !is_touch_pad;
                var RESIZE_EV = 'onorientationchange' in window ? 'orientationchange' : 'resize';
                var START_EV = has_touch ? 'touchstart' : 'mousedown';
                var MOVE_EV = has_touch ? 'touchmove' : 'mousemove';
                var END_EV = has_touch ? 'touchend' : 'mouseup';
                var CANCEL_EV = has_touch ? 'touchcancel' : 'mouseup';

                options.target_node.bind(START_EV, function (e) {
                    var point = _touch.get_touch_point(e);
                    range.x1 = point.x;
                    range.y1 = point.y;
                    range.x2 = range.x1;
                    range.y2 = range.y1;
                    if (options.type == 'long_tap' && typeof options.end_callback == 'function') {
                        _action.long_tap_timeout = setTimeout(options.end_callback, _action.long_tap_delay);
                    }
                    if (typeof options.start_event_callback == 'function') {
                        options.start_event_callback(e);
                    }
                }).bind(MOVE_EV, function (e) {
                    _touch.clean_long_tap_timeout();
                    var point = _touch.get_touch_point(e);
                    range.x2 = point.x;
                    range.y2 = point.y;
                    // if (Math.abs(range.x1 - range.x2) > 10) {
                    //     e.preventDefault();
                    // }
                    e.range = range;
                    if (typeof options.move_event_callback == 'function') {
                        options.move_event_callback(e);
                    }
                }).bind(END_EV, function (e) {
                    _touch.clean_long_tap_timeout();
                    e.range = range;
                    e.swipe_direction = _touch.swipe_direction;
                    _action.swipe_timeout = setTimeout(function () {
                        if (typeof options.end_callback == 'function') {
                            options.end_callback(e);
                        }
                        _touch.clean_range();
                    });
                }).bind('touchcancel', function (e) {
                    _touch.clean_all();
                });
            },

            init: function (_options) {
                $.extend(options, _options);
                this.bind_touch_action();
            }
        };
        _touch.init(_options);
        return _touch;
    }
    return touch;
});
