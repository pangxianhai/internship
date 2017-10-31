define(function (require, exports, module) {
    var $ = require('zepto');
    var touch = require('touch');

    var scrollPanelRight = {
        closePanel: function (options) {
            options.panelNode.css('transform', 'translate3d(' + window.screen.width + 'px, 0px, 0px)');
            options.contentWrap.css('transform', 'translate3d(0px, 0px, 0px)');
            setTimeout(function () {
                options.panelNode.css('visibility', 'hidden');
            }, options.transformSpeed);
            if (typeof options.closeFunction == 'function') {
                options.closeFunction();
            }
            //
        },
        openPanel: function (options) {
            options.panelNode.show();
            options.panelNode.css('visibility', 'visible');
            if (options.sourcePanelSwitch) {
                options.contentWrap.css('transform', 'translate3d(-' + options.panelNode.width() + 'px, 0px, 0px)');
            }
            options.panelNode.css('transform', 'translate3d(' + (window.screen.width - options.panelNode.width()) + 'px, 0px, 0px)');
        },
        scrollToClose: function (options) {
            new touch({
                target_node: options.panelNode,
                end_callback: function (e) {
                    if (Math.abs(e.range.x2 - e.range.x1) < 50) {
                        return;
                    }
                    var x_delta = Math.abs(e.range.x1 - e.range.x2);
                    var y_delta = Math.abs(e.range.y1 - e.range.y2);
                    if (y_delta != 0) {
                        if (x_delta / y_delta < 3) {
                            return;
                        }
                    }
                    if (e.swipe_direction(e.range) == '_right') {
                        scrollPanelRight.closePanel(options);
                    }
                }
            });
        }
    }

    var panel = {
        scrollTop: 0,
        options: {
            /**
             * @property {Dom | Zepto | selector} [contentWrap=''] 主体内容dom
             * @namespace options
             */
            contentWrap: '',
            /**
             * @property {String} [position='right'] 可选值：('left' | 'right'） 在右边或左边
             * @namespace options
             */
            position: 'right',

            /**
             * @property {Dom | Zepto | selector} [openNode=''] 打开panel的节点 如果openNode不传则直接打开面板
             * @namespace options
             */
            openNode: '',

            /**
             * @property {Boolean} [scrollClose=true] 可选值：(false | true） 是否滑动关闭panel
             * @namespace options
             */
            scrollClose: true,

            /**
             * @property {int} [transformSpeed=350] 面板切换速度
             * @namespace options
             */
            transformSpeed: 350,

            /**
             * @property {Boolean} [sourcePanelSwitch=true] 可选值：(false | true） 原面版是否切换
             * @namespace options
             */
            sourcePanelSwitch: true,

            /**
             * @property {function} 关闭面板时执行的函数
             * @namespace options
             */
            closeFunction: null,

            /**
             * panel node 无需传入
             */
            panelNode: ''
        },

        scrollPanel: function (options) {
            options.contentWrap.css('-webkit-transition', '-webkit-transform ' + options.transformSpeed + 'ms ease');
            options.panelNode.css('-webkit-transition', '-webkit-transform ' + options.transformSpeed + 'ms ease');
            options.panelNode.css('position', 'fixed');

            if (options.position == 'left') {
                this.scrollPanelLeft(options);

            } else if (options.position == 'right') {
                this._closePanel = scrollPanelRight.closePanel;
                this._opentPanel = scrollPanelRight.openPanel;
                this._scrollToClose = scrollPanelRight.scrollToClose;
            }

            if (typeof options.openNode == 'object') {
                this._closePanel(options);
                options.openNode.unbind('click');
                options.openNode.bind('click', function () {
                    panel.scrollTop = document.body.scrollTop;
                    document.body.scrollTop = 0;
                    panel._opentPanel(options);
                });
            } else {
                panel.scrollTop = document.body.scrollTop;
                document.body.scrollTop = 0;
                panel._opentPanel(options);
            }

            if (options.scrollClose) {
                this._scrollToClose(options);
            }
            this.closePanel = function () {
                document.body.scrollTop = panel.scrollTop;
                panel._closePanel(this.options);
            }
        },

        scrollPanelLeft: function (options) {

        },


        init: function () {
            $.fn['panel'] = function (options) {
                this.options = $.extend({}, panel.options, options);
                this.options.panelNode = $(this);
                panel.scrollPanel(this.options);
                this.closePanel = panel.closePanel;
                return this;
            }
        }
    }
    panel.init();
});