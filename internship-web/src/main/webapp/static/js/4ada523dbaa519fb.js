/*coolie built*/
define("0",["1","q","h","6","l"],function(e,t,i){var r,o,n,s=e("1");e("q");e("h");r=e("6");o=e("l");n={initEditor:function(){s("#teacherReview").redactor({buttons:["formatting","bold","underline","fontcolor","backcolor","|","alignleft","aligncenter","alignright","|","unorderedlist","orderedlist","html"],focus:!1})},studentCreateInternshipReport:function(){var e=s("#subject_input").val();r.isEmpty(e)?dialog.push_error_message("实习报告题目不能为空!"):e.length>50?dialog.push_error_message("题目太长!"):s.ajax({url:"/student/report/create.json",type:"POST",dataType:"json",data:{subject:e},beforeSend:function(){o.dialog_load("报告生成中...")},success:function(e){o.close_dialog();if(e.ret){o.push_ok_message("生成成功");setTimeout(function(){window.location.href=s("#returnUrl").val()},1e3)}else o.push_error_message(e.message)}})},teacherReview:function(){s.ajax({url:"/student/report/teacher_review.json",type:"POST",dataType:"json",data:{desId:s("#desId").val(),teacherReview:s("#teacherReview").html(),reportScore:s("#reportScoreStr").val()},beforeSend:function(){o.dialog_load("提交中...")},success:function(e){o.close_dialog();if(e.ret){o.push_ok_message("考评成功");setTimeout(function(){location.reload(!0)},1e3)}else o.push_error_message(e.message)}})},bindSubmitAction:function(){var e=s("#action").val();s("#report_submit").click(function(){r.isEmpty(e)||("create"==e?n.studentCreateInternshipReport():"teacherReview"==e&&n.teacherReview())})},init:function(){this.initEditor();this.bindSubmitAction()}};n.init()});
define("h",["g","6"],function(i,t,e){var n,o;i("g");n=i("6");o={initPanel:function(){"true"==$("#show_header_left").val()?$("header .header .left").css("visibility","visible"):$("header .header .left").css("visibility","hidden");var i=$("#headerRight").html();n.isNotEmpty(i)&&$("header .header .right").html(i)},initFooterMenu:function(){var i=$("#footerMenuId").val();$("footer .footer_menu").find("."+i).addClass("active")},bindLeftBtnAction:function(){$("header .header .left a").click(function(){var i=$("#returnUrl").val();n.isNotEmpty(i)?window.location.href=i:window.history.go(-1)})},init:function(){this.initPanel();this.bindLeftBtnAction();this.initFooterMenu();$("body").height($("body")[0].clientHeight)}};$(function(){o.init()})});
define("o",[],function(y,d,r){r.exports=".rDialog{position:fixed;_position:absolute}.rDialog-wrap{position:relative;background:#000;opacity:.7;background-clip:padding-box;border-radius:10px;-moz-border-radius:10px;-o-border-radius:10px;-webkit-border-radius:10px;box-shadow:1px 1px 1px #000;padding:.5em .5em}.rDialog-header-load{text-align:center;background:url(/static/images/0e6e0f46d7504242.gif);background-repeat:no-repeat;background-position:0 0;background-size:100%;width:30px;height:33px;margin:auto}.rDialog-header-alert,.rDialog-header-ok{text-align:center;background:url(/static/images/12dffd74007d31b6.png);background-repeat:no-repeat;background-size:100%;width:33px;height:36px;margin:auto}.rDialog-header-alert{background-position:0 -36px}.rDialog-header-ok{background-position:0 0}.rDialog-content{padding:1em 1em;font-size:1.1em;color:#FFF;overflow:hidden;text-align:center}.rDialog-footer{padding-left:15px;text-align:center}.rDialog-footer a{display:inline-block;margin:0 15px 15px 0;height:32px;line-height:32px;padding:0 30px;font-size:1rem;-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px}.rDialog-footer a:hover,.rDialog-footer a:link,.rDialog-footer a:visited{color:#fff;text-decoration:none}.rDialog-ok,.rDialog-ok:hover{background:#c70066;color:#fff}.rDialog-cancel,.rDialog-cancel:hover{background:#E6E6E6;color:#A7A7A7}.rDialog-mask{position:fixed;_position:absolute;left:0;top:0;width:100%;height:100%;background:#000;filter:alpha(opacity=30);opacity:.3}"});
coolie.chunk(["0"]);