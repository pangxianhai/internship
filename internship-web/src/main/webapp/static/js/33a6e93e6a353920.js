/*coolie built*/
define("0",["1","5","a","b","6","7"],function(t){var e,a,n,i,d;t("1");t("5");t("a");e=t("b");a=t("6");n=t("7");i=t("6");d={data:{action:"",is_sysadmin:!1},bind_page_action:function(){e.turning_page(d.load_company_info)},push_user_info:function(t){var e,a,n=[];for(e=0;e<t.length;++e){a=t[e];n.push("<tr>");n.push('<td><input item="select_company" companyDestId="'+a.desId+'" type="checkbox"/></td>');n.push('<td><a href="/company/detail/'+a.desId+'.htm">'+a.companyName+"</a></td>");n.push("<td>"+a.companyAddress+"</td>");n.push("<td>"+a.phone+"</td>");n.push("<td>"+a.internshipNumber+"</td>");n.push("<td>"+i.filterNull(a.studentNumber)+"</td>");n.push("<td>"+new Date(a.createdTime).format("yyyy-MM-dd")+"</td>");if(this.data.is_sysadmin||"addStudent"==this.data.action){n.push("<td>");if("addStudent"==this.data.action)n.push('<a item="addStudent" companyName="'+a.companyName+'"  companyId="'+a.desId+'" href="javascript:void(0)">[分配]</a>');else if(this.data.is_sysadmin){n.push('<a href="/tutor/list.htm?companyName='+encodeURIComponent(a.companyName)+'">[查看导师]</a>');n.push("<br/>");n.push('<a href="javascript:void(0)" item="delete" companyDestId="'+a.desId+'">[删除]</a>')}n.push("</td>")}n.push("</tr>")}return n.join("")},push_page_data:function(t){if(t.ret){$("#u_data").html(d.push_user_info(t.data.companies));$(".page_list").html(e.html(t.data.page));d.bind_page_action();d.bind_add_student_action();d.bind_delete_company_action()}},load_company_info:function(t){$.ajax({url:"/company/list.json",type:"POST",data:$.extend(a.getFormData($("#search_form")),{currentPage:t}),dataType:"json",beforeSend:function(){},success:d.push_page_data})},delete_company:function(t,a){$.ajax({url:"/company/delete.json",type:"POST",dataType:"json",data:{companyDestId:t},success:function(t){if(t.ret){t.data==a?n.openSample("提示","删除成功"):n.openSample("提示","部分单位操作成功，您这个成功操作了"+t.data+"个单位");d.load_company_info(e.current_page());n.close(2e3)}else n.openSample("提示",t.message)}})},bind_search_action:function(){$("#search_action").click(function(){d.load_company_info(1)})},bind_add_student_action:function(){var t=$('a[item="addStudent"]');t.unbind("click");t.bind("click",function(){var t=this;n.yesNo("提示","您确定将该学生分配到"+this.getAttribute("companyName")+"吗？",function(){n.close(1);d.add_student(t.getAttribute("companyId"))})})},add_student:function(t){var e=$("#studentId").val();$.ajax({url:"/student/updateCompany.json",type:"POST",dataType:"json",data:{companyDestId:t,studentDestId:e},success:function(t){if(t.ret){t.data==e.split(",").length?n.openSample("提示","分配成功"):n.openSample("提示","部分学生操作成功，您这个成功操作了"+t.data+"个学生");n.close(2e3);window.location.href="/student/list.htm"}else n.openSample("提示",t.message)}})},bind_delete_company_action:function(){$('a[item="delete"]').unbind("click");$('a[item="delete"]').bind("click",function(){var t=$(this).attr("companyDestId");n.yesNo("提示","您确定要删除该实习单位吗？",function(){d.delete_company(t,1)})})},bind_select_all_action:function(){$('input[item="select_all"]').click(function(){$('input[item="select_company"]').prop("checked",$(this).is(":checked"))})},bind_delete_company_all_action:function(){$("#delete_company_all").click(function(){var t=[];$('input[item="select_company"]:checked').each(function(){t.push($(this).attr("companyDestId"))});0!=t.length?n.yesNo("提示","您确定要删除选中的单位吗？",function(){d.delete_company(t.join(","),t.length)}):n.openSample("提示","请选择要删除的单位!")})},init:function(){this.data.action=$("#action").val();this.data.is_sysadmin="true"==$("#isSysadmin").val();this.load_company_info(1);this.bind_search_action();this.bind_select_all_action();this.bind_delete_company_all_action()}};d.init()});
define("5",["1"],function(t,i,n){var e,o,s;t("1");e={init_choice:function(){var t=$("#menu_id").val();$(".menu_list").find("A").each(function(t){$(this);if(this.getAttribute("data")==t){$(this).addClass("current");$(this).removeClass("no_current")}else{$(this).addClass("no_current");$(this).removeClass("current")}},[t])}};o={timer:null,top:0,begin:function(t){o.timer=setInterval(function(){t.css("top",o.top+"px");o.top<-11?o.top=12:o.top-=1},180)},end:function(){null!=o.timer&&clearInterval(o.timer)}};s={tips_show:function(){o.begin($("#bulletin_head_tips"));$("#bulletin_head_tips").hover(function(){o.end()},function(){o.begin($("#bulletin_head_tips"))})}};$(function(){e.init_choice();s.tips_show()});i.get_title=function(){$(".menu").find(".title").val()}});
define("9",[],function(y,d,r){r.exports=".dialog-dialog_div{overflow:hidden;display:block;position:fixed;border:1px solid #3f55a0;border-top-right-radius:4px;border-top-left-radius:4px;z-index:1001}.dialog-dialog_div table{border-collapse:collapse;width:100%}.dialog-dialog_div .title{background-color:#3f55a0;overflow:hidden;display:block;height:30px;color:#FFF}.dialog-dialog_div .title span{line-height:35px;margin-left:8px;font-size:14px}.dialog-dialog_div .title .close{float:right;line-height:30px;font-size:25px;margin-right:10px;color:#FFF}.dialog-dialog_div .dialog_content{overflow:hidden;background-color:#FFF;padding-left:10px;word-break:break-all}.dialog-dialog_div .bottom{height:38px;overflow:hidden;background-color:#ccc}.dialog-dialog_div .bottom .no{float:left;margin-left:8px;border-radius:5px}.dialog-dialog_div .bottom .yes{float:right;margin-right:8px;border-radius:5px}"});
coolie.chunk(["0"]);