define(function (require, exports, module) {
    var pEditorConfig = require('../../../../peditorConfig');
    var strings = require('../../../../../strings');
    var fontFamily = {
        init: function () {
            var css = require('fontFamily.css', 'css');
            $('head').append('<style type="text/css">' + css + '</style>');
        },

        buildFontFamilySelectHtml: function (id) {
            var html = [];
            html.push('<ul class="ui_ed_toolbar_font_select">');
            var currentFontFamily;
            for (var i = 0; i < pEditorConfig.fontFamily.length; ++i) {
                var fontFamily = pEditorConfig.fontFamily[i];
                html.push('<li value="' + fontFamily.val + '"><p style="font-family: ' + fontFamily.val + '">' + fontFamily.name + '</p></li>');
            }
            html.push('</ul>');
            return {
                currentFontFamily: currentFontFamily,
                html: html.join('')
            };
        },

        buildHtml: function (id) {
            var selectHtml = this.buildFontFamilySelectHtml(id);
            var html = [];
            html.push('<div class="ui_ed_toolbar_font_family ui_ed_toolbar_item">');
            var currentFontFamilyName = '选择字体';
            if (typeof selectHtml.currentFontFamily != 'undefined' && selectHtml.currentFontFamily != null) {
                currentFontFamilyName = strings.substring(selectHtml.currentFontFamily.name, 4);
            }
            html.push('<a href="javascript:void(0)" class="ui_ed_toolbar_font_family_name">' + currentFontFamilyName + '</a>');
            html.push('<i></i>');
            html.push(selectHtml.html);
            html.push('</div>');
            return html.join('');
        },

        moduleSelectAction: function (id, edToolbarNode, edBodyNode) {
            this.bindFontFamilyPanelAction(edToolbarNode);
            this.bindFontFamilySelectAction(id, edToolbarNode, edBodyNode);
        },

        bindFontFamilySelectAction: function (id, edToolbarNode, edBodyNode) {
            var fontFamilyLiNode = edToolbarNode.find('.ui_ed_toolbar_font_family .ui_ed_toolbar_font_select li');
            fontFamilyLiNode.unbind('click');
            fontFamilyLiNode.bind('click', function () {
                // edBodyNode.focus();
                var $this = $(this);
                edToolbarNode.find('.ui_ed_toolbar_font_family .ui_ed_toolbar_font_family_name').html(strings.substring($this.find('p').html(), 5));
                // document.execCommand('FontName', false, fontFamily.val);
                // fontFamilyLiNode.trigger('comboboxselect', {
                //     label: $this.find('p').html(),
                //     value: $this.attr('value')
                // });
                var rng = edBodyNode.selection.getRange();
                if (rng.collapsed) {
                    var span = $('<span></span>').css('font-family', $this.attr('value'))[0];
                    rng.insertNode(span).setStart(span, 0).setCursor();
                }

                edToolbarNode.find('.ui_ed_toolbar_font_family .ui_ed_toolbar_font_select').hide();
                fontFamilyLiNode.each(function () {
                    var thisNode = $(this);
                    if (thisNode.attr('value') == $this.attr('value')) {
                        if (thisNode.hasClass('ui_ed_font_selected')) {
                            return;
                        } else {
                            thisNode.addClass('ui_ed_font_selected');
                        }
                    } else {
                        thisNode.removeClass('ui_ed_font_selected');
                    }
                });
            });
        },

        bindFontFamilyPanelAction: function (edToolbarNode) {
            var fontFamilyNode = edToolbarNode.find('.ui_ed_toolbar_font_family');
            var fontSelectNode = fontFamilyNode.find('.ui_ed_toolbar_font_select');
            fontFamilyNode.unbind('click');
            fontFamilyNode.bind('click', function () {
                fontSelectNode.show();
            });
            $("body>*").bind("click", function (e) {
                if (fontSelectNode.is(':hidden')) {
                    return;
                }
                var t = (e && e.target) || (event && event.srcElement);
                if (!$(t).hasClass('ui_ed_toolbar_font_family') && $(t).parents('.ui_ed_toolbar_font_family').length <= 0) {
                    fontSelectNode.hide();
                }
            });
            fontSelectNode.click(function (e) {
                e.stopPropagation();
            });
        },
    };
    fontFamily.init();
    return fontFamily;
});