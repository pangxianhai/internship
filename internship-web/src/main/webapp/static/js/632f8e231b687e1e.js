/*coolie built*/
define("0",["1","5","a","e","7","6","b"],function(t){var e,n,a,d,i;t("1");t("5");t("a");e=t("e");n=t("7");a=t("6");d=t("b");i={data:{is_tutor:!1,is_student:!1},bind_attend_day_action:function(){$("#attendDay").click(function(){e.WdatePicker({dateFmt:"yyyy-MM-dd",maxDate:"%y-%M-%d"})});$('[name="attendBeginDayStr"]').click(function(){e.WdatePicker({dateFmt:"yyyy-MM-dd",maxDate:"%y-%M-%d"})});$('[name="attendEndDayStr"]').click(function(){e.WdatePicker({dateFmt:"yyyy-MM-dd",minDate:$('[name="attendBeginDayStr"]').val(),maxDate:"%y-%M-%d"})})},attend_submit:function(t,e){$.ajax({url:"/student/attend/attend.json",type:"POST",dataType:"json",data:{attendDay:t,timeIntervalCode:e},success:function(t){if(t.ret){n.openSample("提示","签到成功");setTimeout(function(){n.close(1);i.load_student_attend_info(1)},1e3)}else n.openSample("签到失败",t.message)}})},bind_attend_action:function(){$(".today-attend").find(".btn45").click(function(){i.attend_submit("",this.getAttribute("timeInterval"))});$(".history-attend").find(".btn45").click(function(){var t=$("#attendDay").val();a.isEmpty(t)?n.openSample("提示","请选择补签的日期"):i.attend_submit(t,this.getAttribute("timeInterval"))})},push_student_attend:function(t){var e,n,a,d,i,s,o=[];for(e=0;e<t.length;++e){n=t[e];o.push("<tr>");o.push('<td item="student_name">'+n.student.name+"</td>");o.push("<td>"+n.student.studentNumber+"</td>");o.push('<td item="attendDay">'+new Date(n.attendDay).format("yyyy-MM-dd")+"</td>");o.push('<td item="timeInterval">'+n.timeInterval.desc+"</td>");o.push("<td>"+n.attendStatus.desc+"</td>");o.push("<td>");a=[];d=!1;for(i=0;i<n.flowRecords.length;++i){s=n.flowRecords[i];s.operateUserId==$("#userId").val()&&(d=!0);a.push(s.operateName)}o.push(a.join(","));o.push("</td>");o.push("<td>");this.data.is_tutor&&d&&n.isApply&&o.push('<a item="examine" attendRecordDestId="'+n.desId+'" href="javascript:void(0)">审核</a>');this.data.is_student&&n.isApply&&o.push('<a item="delete" attendRecordDestId="'+n.desId+'" href="javascript:void(0)">删除</a>');o.push("</td>");o.push("</tr>")}return o.join("")},push_page_data:function(t){if(t.ret){$("#u_data").html(i.push_student_attend(t.data.attendRecords));$(".page_list").html(d.html(t.data.page));d.turning_page(i.load_student_attend_info);i.bind_examine_action();i.bind_delete_attend_record_action()}else n.openSample("提示",t.message)},load_student_attend_info:function(t){$.ajax({url:"/student/attend/list.json",type:"POST",data:$.extend(a.getFormData($("#search_form")),{currentPage:t}),dataType:"json",beforeSend:function(){},success:i.push_page_data})},bind_examine_action:function(){$('a[item="examine"]').click(function(){var t=this.getAttribute("attendRecordDestId"),e=$(this).parent().parent();$("#examine_dialog").find(".notice").html("你正在审核"+e.find('td[item="student_name"]').html()+e.find('td[item="attendDay"]').html()+e.find('td[item="timeInterval"]').html()+"的签到结果");$("#examine_dialog").dialog({title:"",yesNo:!0,ok:function(){n.close(1);$.ajax({url:"/student/attend/examine.json",type:"POST",data:{attendRecordDestId:t,attendStatusCode:$("#attendStatusSelect").val()},dataType:"json",success:function(t){if(t.ret){n.openSample("系统提示","审核成功");n.close(2e3);i.load_student_attend_info(1)}else n.openSample("系统提示",t.message)}})}})})},bind_search_action:function(){$("#search_action").click(function(){$('[name="desId"]').val("");i.load_student_attend_info(1)})},bind_delete_attend_record_action:function(){$('a[item="delete"]').unbind("click");$('a[item="delete"]').bind("click",function(){var t=$(this).attr("attendRecordDestId");n.yesNo("提示","您确定要删除该签到信息吗?",function(){$.ajax({url:"/student/attend/delete.json",type:"POST",dataType:"json",data:{attendRecordDesId:t},success:function(t){if(t.ret){n.openSample("提示","删除成功!");i.load_student_attend_info(d.current_page());setTimeout(function(){n.close(1)},2e3)}else n.openSample("提示",t.message)}})})})},init:function(){this.data.is_tutor="true"==$("#is_tutor").val();this.data.is_student="true"==$("#is_student").val();this.load_student_attend_info(1);this.bind_attend_day_action();this.bind_attend_action();this.bind_search_action()}};i.init()});
define("5",["1"],function(t,i,n){var e,o,s;t("1");e={init_choice:function(){var t=$("#menu_id").val();$(".menu_list").find("A").each(function(t){$(this);if(this.getAttribute("data")==t){$(this).addClass("current");$(this).removeClass("no_current")}else{$(this).addClass("no_current");$(this).removeClass("current")}},[t])}};o={timer:null,top:0,begin:function(t){o.timer=setInterval(function(){t.css("top",o.top+"px");o.top<-11?o.top=12:o.top-=1},180)},end:function(){null!=o.timer&&clearInterval(o.timer)}};s={tips_show:function(){o.begin($("#bulletin_head_tips"));$("#bulletin_head_tips").hover(function(){o.end()},function(){o.begin($("#bulletin_head_tips"))})}};$(function(){e.init_choice();s.tips_show()});i.get_title=function(){$(".menu").find(".title").val()}});
define("9",[],function(y,d,r){r.exports=".dialog-dialog_div{overflow:hidden;display:block;position:fixed;border:1px solid #3f55a0;border-top-right-radius:4px;border-top-left-radius:4px;z-index:1001}.dialog-dialog_div table{border-collapse:collapse;width:100%}.dialog-dialog_div .title{background-color:#3f55a0;overflow:hidden;display:block;height:30px;color:#FFF}.dialog-dialog_div .title span{line-height:35px;margin-left:8px;font-size:14px}.dialog-dialog_div .title .close{float:right;line-height:30px;font-size:25px;margin-right:10px;color:#FFF}.dialog-dialog_div .dialog_content{overflow:hidden;background-color:#FFF;padding-left:10px;word-break:break-all}.dialog-dialog_div .bottom{height:38px;overflow:hidden;background-color:#ccc}.dialog-dialog_div .bottom .no{float:left;margin-left:8px;border-radius:5px}.dialog-dialog_div .bottom .yes{float:right;margin-right:8px;border-radius:5px}"});
coolie.chunk(["0"]);