/*coolie built*/
define("0",["g","h","k","l","m","6"],function(s,a,t){var e,n,i,o,l,p=s("g");s("h");e=s("k");n=s("l");i=s("m");o=s("6");l={data:{action:""},bindCompanyInfoScroll:function(){p('#companyList [item="companyShow"]').each(function(){var s,a,t=p(this);if("true"==t.attr("has_operate")){s=t.parent().find('[item="operate"]');a=s.width();new e({target_node:t,h_scroll:!0,max_scroll:a,start_event_callback:function(a){s.css("visibility","visible")},move_event_callback:function(s){},end_callback:function(e){var n=e.range,i=e.swipe_direction(n);if("_left"==i&&Math.abs(n.x1-n.x2)>=a/2)t.css({transform:"translate(-"+a+"px, 0px) scale(1) translateZ(0px)"});else{t.css({transform:"translate(0px, 0px) scale(1) translateZ(0px)"});s.css("visibility","hidden")}}})}})},buildCompanyListHtml:function(s){var a,t,e,n=[];for(a=0;a<s.length;++a){t=s[a];e=[];"addStudent"==this.data.action&&e.push('<li class="company" item="assignCompany"><div class="assign">分配实习单位</div></li>');n.push('<li  class="user_node" item="companyNode" companyDesId="'+t.desId+'">');n.push('<div class="operate" item="operate">');n.push('<ul class="operate_list">'+e.join("")+"</ul>");n.push("</div>");n.push('<div class="user_show" item="companyShow" has_operate="'+(e.length>0)+'">');n.push('<ul class="user_info">');n.push('<li class="company_name">');n.push("<span>"+o.substring(t.companyName,13)+"</span>");n.push("</li>");n.push('<li class="student_number"><span>'+t.studentNumber+"人</span><i>&gt;</i></li>");n.push("</ul>");n.push('<ul class="user_message">');n.push('<li class="_left">单位地址：</li>');n.push('<li class="_right">'+o.substring(t.companyAddress,6)+"</li>");n.push('<li class="_left">容纳人数：</li>');n.push('<li class="_right">'+o.filterString(t.internshipNumber,"0")+"</li>");n.push("</ul>");n.push("</div>");n.push("</li>");n.push("<hr/>")}return n.join("")},bindStudentAssignCompanyAction:function(){var s=p('#companyList [item="assignCompany"]');s.off("click");s.on("click",function(s){var a,t;s.stopPropagation();a=p(this).parents('[item="companyNode"]').attr("companyDesId");t=p("#studentDesId").val();o.isEmpty(t)||o.isEmpty(a)?n.push_error_message("单位或学生不能为空!"):n.yes_no("您确定要将该学生分配给该单位吗?",function(){p.ajax({url:"/student/updateCompany.json",type:"POST",data:{studentDestId:t,companyDestId:a},dataType:"json",success:function(s){n.close_dialog();if(s.ret){s.data==t.split(",").length?n.push_ok_message("操作成功!"):n.push_ok_message("部分学生操作成功，您这个成功操作了"+s.data+"个学生");setTimeout(function(){window.location.href=p("#returnUrl").val()},2e3)}else n.push_error_message(s.message)}})})})},loadCompanyListInfo:function(s){p.ajax({url:"/company/list.json",type:"POST",data:{currentPage:s},dataType:"json",beforeSend:function(){n.dialog_load("加载中...")},success:function(a){var t,e;n.close_dialog();if(a.ret){s<=1&&p("#companyList").html("");t=l.buildCompanyListHtml(a.data.companies);p("#companyList").append(t);e=a.data.page;i.loadSinglePage(e.currentPage,e.totalPage,p("#companyList"),l.loadCompanyListInfo);l.bindCompanyInfoScroll();l.bindStudentAssignCompanyAction()}else n.push_error_message(a.message)}})},init:function(){this.data.action=p("#action").val();this.loadCompanyListInfo(1)}};l.init()});
define("h",["g","6"],function(i,t,e){var n,o;i("g");n=i("6");o={initPanel:function(){"true"==$("#show_header_left").val()?$("header .header .left").css("visibility","visible"):$("header .header .left").css("visibility","hidden");var i=$("#headerRight").html();n.isNotEmpty(i)&&$("header .header .right").html(i)},initFooterMenu:function(){var i=$("#footerMenuId").val();$("footer .footer_menu").find("."+i).addClass("active")},bindLeftBtnAction:function(){$("header .header .left a").click(function(){var i=$("#returnUrl").val();n.isNotEmpty(i)?window.location.href=i:window.history.go(-1)})},init:function(){this.initPanel();this.bindLeftBtnAction();this.initFooterMenu();$("body").height($("body")[0].clientHeight)}};$(function(){o.init()})});
define("o",[],function(y,d,r){r.exports=".rDialog{position:fixed;_position:absolute}.rDialog-wrap{position:relative;background:#000;opacity:.7;background-clip:padding-box;border-radius:10px;-moz-border-radius:10px;-o-border-radius:10px;-webkit-border-radius:10px;box-shadow:1px 1px 1px #000;padding:.5em .5em}.rDialog-header-load{text-align:center;background:url(/static/images/0e6e0f46d7504242.gif);background-repeat:no-repeat;background-position:0 0;background-size:100%;width:30px;height:33px;margin:auto}.rDialog-header-alert,.rDialog-header-ok{text-align:center;background:url(/static/images/12dffd74007d31b6.png);background-repeat:no-repeat;background-size:100%;width:33px;height:36px;margin:auto}.rDialog-header-alert{background-position:0 -36px}.rDialog-header-ok{background-position:0 0}.rDialog-content{padding:1em 1em;font-size:1.1em;color:#FFF;overflow:hidden;text-align:center}.rDialog-footer{padding-left:15px;text-align:center}.rDialog-footer a{display:inline-block;margin:0 15px 15px 0;height:32px;line-height:32px;padding:0 30px;font-size:1rem;-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px}.rDialog-footer a:hover,.rDialog-footer a:link,.rDialog-footer a:visited{color:#fff;text-decoration:none}.rDialog-ok,.rDialog-ok:hover{background:#c70066;color:#fff}.rDialog-cancel,.rDialog-cancel:hover{background:#E6E6E6;color:#A7A7A7}.rDialog-mask{position:fixed;_position:absolute;left:0;top:0;width:100%;height:100%;background:#000;filter:alpha(opacity=30);opacity:.3}"});
coolie.chunk(["0"]);