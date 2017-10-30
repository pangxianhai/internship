/*coolie built*/
define("0",["g","h","k","l","m","6"],function(e,t,a){var s,i,n,l,r,c=e("g");e("h");s=e("k");i=e("l");n=e("m");l=e("6");r={data:{action:"",studentDesId:""},bindScrollAction:function(){c('#teacherList [item="teacherShow"]').each(function(){var e,t,a=c(this);if("true"==a.attr("has_operate")){e=a.parent().find('[item="operate"]');t=e.width();new s({target_node:a,h_scroll:!0,max_scroll:t,start_event_callback:function(t){e.css("visibility","visible")},move_event_callback:function(e){},end_callback:function(s){var i=s.range,n=s.swipe_direction(i);if("_left"==n&&Math.abs(i.x1-i.x2)>=t/2)a.css({transform:"translate(-"+t+"px, 0px) scale(1) translateZ(0px)"});else{a.css({transform:"translate(0px, 0px) scale(1) translateZ(0px)"});e.css("visibility","hidden")}}})}})},buildTeacherInfoHtml:function(e){var t,a,s,i=[];for(t=0;t<e.length;++t){a=e[t];s=[];"addStudent"==this.data.action&&s.push('<li class="tutor" item="assignTeacher"><div class="assign">分配带队老师</div></li>');i.push('<li  class="user_node" item="teacherNode" teacherName="'+a.name+'" teacherDesId="'+a.desId+'">');i.push('<div class="operate" item="operate">');i.push('<ul class="operate_list">'+s.join("")+"</ul>");i.push("</div>");i.push('<div class="user_show" item="teacherShow" has_operate="'+(s.length>0)+'">');i.push('<ul class="user_info">');i.push("<li>"+a.name+"</li>");i.push("<li>"+a.teacherType.desc+"</li>");i.push("<li><span>"+l.filterString(a.rank," ")+"</span><i>&gt;</i></li>");i.push("</ul>");i.push('<ul class="user_message">');i.push('<li class="_left">实习人数：</li>');i.push('<li class="_right">'+l.filterString(a.studentNumber,"0")+"</li>");i.push('<li class="_left">联系方式：</li>');i.push('<li class="_right">'+l.filterString(a.userInfo.phone,"-")+"</li>");i.push("</ul>");i.push("</div>");i.push("</li>");i.push("<hr/>")}return i.join("")},bindToTeacherDetailAction:function(){var e=c('#teacherList [item="teacherNode"]');e.off("click");e.on("click",function(){window.location.href="/webApp/teacher/detail/"+c(this).attr("teacherDesId")+".htm?returnUrl="+encodeURIComponent(location.href)})},bindAssignTeacherAction:function(){var e=c('#teacherList [item="assignTeacher"]');e.off("click");e.on("click",function(e){e.stopPropagation();var t=c(this).parents(".user_node");i.yes_no("你确定将该学生分配给"+t.attr("teacherName")+"吗?",function(){c.ajax({url:"/student/updateTeacher.json",type:"POST",dataType:"json",data:{teacherDestId:t.attr("teacherDesId"),studentDestId:c("#studentDesId").val()},success:function(e){i.close_dialog();if(e.ret){e.data==c("#studentDesId").val().split(",").length?i.push_ok_message("分配成功"):i.push_ok_message("部分学生操作成功，您这个成功操作了"+e.data+"个学生");setTimeout(function(){window.location.href=c("#returnUrl").val()},2e3)}else i.push_error_message(e.message)}})})})},loadTeacherInfo:function(e){c.ajax({url:"/teacher/list.json",type:"POST",data:{currentPage:e},dataType:"json",beforeSend:function(){i.dialog_load("加载中...")},success:function(t){var a,s;i.close_dialog();if(t.ret){e<=1&&c("#teacherList").html("");a=r.buildTeacherInfoHtml(t.data.teachers);c("#teacherList").append(a);s=t.data.page;n.loadSinglePage(s.currentPage,s.totalPage,c("#teacherList"),r.loadTeacherInfo);r.bindScrollAction();r.bindToTeacherDetailAction();r.bindAssignTeacherAction()}else i.push_error_message(t.message)}})},init:function(){this.data.action=c("#action").val();this.data.studentDesId=c("#studentDesId").val();this.loadTeacherInfo(1)}};r.init()});
define("h",["g","6"],function(i,t,e){var n,o;i("g");n=i("6");o={initPanel:function(){"true"==$("#show_header_left").val()?$("header .header .left").css("visibility","visible"):$("header .header .left").css("visibility","hidden");var i=$("#headerRight").html();n.isNotEmpty(i)&&$("header .header .right").html(i)},initFooterMenu:function(){var i=$("#footerMenuId").val();$("footer .footer_menu").find("."+i).addClass("active")},bindLeftBtnAction:function(){$("header .header .left a").click(function(){var i=$("#returnUrl").val();n.isNotEmpty(i)?window.location.href=i:window.history.go(-1)})},init:function(){this.initPanel();this.bindLeftBtnAction();this.initFooterMenu();$("body").height($("body")[0].clientHeight)}};$(function(){o.init()})});
define("o",[],function(y,d,r){r.exports=".rDialog{position:fixed;_position:absolute}.rDialog-wrap{position:relative;background:#000;opacity:.7;background-clip:padding-box;border-radius:10px;-moz-border-radius:10px;-o-border-radius:10px;-webkit-border-radius:10px;box-shadow:1px 1px 1px #000;padding:.5em .5em}.rDialog-header-load{text-align:center;background:url(/static/images/0e6e0f46d7504242.gif);background-repeat:no-repeat;background-position:0 0;background-size:100%;width:30px;height:33px;margin:auto}.rDialog-header-alert,.rDialog-header-ok{text-align:center;background:url(/static/images/12dffd74007d31b6.png);background-repeat:no-repeat;background-size:100%;width:33px;height:36px;margin:auto}.rDialog-header-alert{background-position:0 -36px}.rDialog-header-ok{background-position:0 0}.rDialog-content{padding:1em 1em;font-size:1.1em;color:#FFF;overflow:hidden;text-align:center}.rDialog-footer{padding-left:15px;text-align:center}.rDialog-footer a{display:inline-block;margin:0 15px 15px 0;height:32px;line-height:32px;padding:0 30px;font-size:1rem;-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px}.rDialog-footer a:hover,.rDialog-footer a:link,.rDialog-footer a:visited{color:#fff;text-decoration:none}.rDialog-ok,.rDialog-ok:hover{background:#c70066;color:#fff}.rDialog-cancel,.rDialog-cancel:hover{background:#E6E6E6;color:#A7A7A7}.rDialog-mask{position:fixed;_position:absolute;left:0;top:0;width:100%;height:100%;background:#000;filter:alpha(opacity=30);opacity:.3}"});
coolie.chunk(["0"]);