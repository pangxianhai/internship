/*coolie built*/
define("0",["1","a","h","q","r","l","6"],function(e,i,a){var n,t,d,r=e("1");e("a");e("h");e("q");e("r");n=e("l");t=e("6");d={bindDatePickerAction:function(){r("#beginDay").click(function(){r("#beginDayPicker").addClass("date_picker_activity");r("#beginDayPicker").calendar({date:t.isNotEmpty(r("#beginDay").val())?new Date(r("#beginDay").val()):new Date,first_day:0,swipe_able:!0,max_date:new Date(r("#endDay").val()),select:function(e){console.log("a");r("#beginDay").val(e.format("yyyy-MM-dd"));r("#beginDayPicker").removeClass("date_picker_activity")},exceptHiddenNodes:["beginDayPicker","beginDay"],hiddenFunction:function(){r("#beginDayPicker").removeClass("date_picker_activity")}})});r("#endDay").click(function(){r("#endDayPicker").addClass("date_picker_activity");r("#endDayPicker").calendar({date:t.isNotEmpty(r("#beginDay").val())?new Date(r("#beginDay").val()):new Date,first_day:0,swipe_able:!0,min_date:new Date(r("#beginDay").val()),select:function(e){r("#endDay").val(e.format("yyyy-MM-dd"));r("#endDayPicker").removeClass("date_picker_activity")},exceptHiddenNodes:["endDayPicker","endDay"],hiddenFunction:function(){r("#endDayPicker").removeClass("date_picker_activity")}})})},initEditor:function(){r("#journalContent").redactor({buttons:["formatting","bold","underline","fontcolor","backcolor","|","alignleft","aligncenter","alignright","|","unorderedlist","orderedlist","html"],focus:!1})},bindAddWeeklyJournalAction:function(){r("#addWeeklyJournalBtn").click(function(){t.isEmpty(r("#beginDay").val())||t.isEmpty(r("#endDay").val())?n.push_error_message("请选择实习日期"):r.ajax({url:"/student/weeklyJournal/write.json",type:"POST",dataType:"json",data:t.getFormData(r("#weeklyJournalWriteForm")),beforeSend:function(){n.dialog_load("提交中,请稍后...")},success:function(e){n.close_dialog();if(e.ret){n.push_ok_message("提交成功");setTimeout(function(){window.location.href=r("#returnUrl").val()},1e3)}else n.push_error_message(e.message)}})})},init:function(){this.initEditor();this.bindDatePickerAction();this.bindAddWeeklyJournalAction()}};d.init()});
define("h",["g","6"],function(i,t,e){var n,o;i("g");n=i("6");o={initPanel:function(){"true"==$("#show_header_left").val()?$("header .header .left").css("visibility","visible"):$("header .header .left").css("visibility","hidden");var i=$("#headerRight").html();n.isNotEmpty(i)&&$("header .header .right").html(i)},initFooterMenu:function(){var i=$("#footerMenuId").val();$("footer .footer_menu").find("."+i).addClass("active")},bindLeftBtnAction:function(){$("header .header .left a").click(function(){var i=$("#returnUrl").val();n.isNotEmpty(i)?window.location.href=i:window.history.go(-1)})},init:function(){this.initPanel();this.bindLeftBtnAction();this.initFooterMenu();$("body").height($("body")[0].clientHeight)}};$(function(){o.init()})});
define("o",[],function(y,d,r){r.exports=".rDialog{position:fixed;_position:absolute}.rDialog-wrap{position:relative;background:#000;opacity:.7;background-clip:padding-box;border-radius:10px;-moz-border-radius:10px;-o-border-radius:10px;-webkit-border-radius:10px;box-shadow:1px 1px 1px #000;padding:.5em .5em}.rDialog-header-load{text-align:center;background:url(/static/images/0e6e0f46d7504242.gif);background-repeat:no-repeat;background-position:0 0;background-size:100%;width:30px;height:33px;margin:auto}.rDialog-header-alert,.rDialog-header-ok{text-align:center;background:url(/static/images/12dffd74007d31b6.png);background-repeat:no-repeat;background-size:100%;width:33px;height:36px;margin:auto}.rDialog-header-alert{background-position:0 -36px}.rDialog-header-ok{background-position:0 0}.rDialog-content{padding:1em 1em;font-size:1.1em;color:#FFF;overflow:hidden;text-align:center}.rDialog-footer{padding-left:15px;text-align:center}.rDialog-footer a{display:inline-block;margin:0 15px 15px 0;height:32px;line-height:32px;padding:0 30px;font-size:1rem;-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px}.rDialog-footer a:hover,.rDialog-footer a:link,.rDialog-footer a:visited{color:#fff;text-decoration:none}.rDialog-ok,.rDialog-ok:hover{background:#c70066;color:#fff}.rDialog-cancel,.rDialog-cancel:hover{background:#E6E6E6;color:#A7A7A7}.rDialog-mask{position:fixed;_position:absolute;left:0;top:0;width:100%;height:100%;background:#000;filter:alpha(opacity=30);opacity:.3}"});
coolie.chunk(["0"]);