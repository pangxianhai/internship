/*coolie built*/
define("0",["1","5","2","3","4","6","7"],function(e){var t,i,n;e("1");e("5");e("2");e("3");e("4");t=e("6");i=e("7");n={init_edit:function(){UM.getEditor("tutor-remark",{imagePath:"",initialFrameHeight:300,initialFrameWidth:700,lang:"zh-cn",langPath:UMEDITOR_CONFIG.UMEDITOR_HOME_URL+"lang/",focus:!0})},send_assessment_info:function(e){$.ajax({url:"/student/assessment/send.json",type:"POST",data:t.getFormData(e),dataType:"json",success:function(e){if(e.ret){i.openSample("提示","发放成功");setTimeout(function(){window.location.href="/student/internship/info.htm"},2e3)}else i.openSample("提示",e.message)}})},bind_send_assessment_action:function(){$("#send-assessment-form").validator({rules:{check_score:function(){return t.check_score($("#tutor_score").val())}},fields:{"#tutor_score":"建议成绩:required;check_score"},submit_target:"#assessment-info-a-btn",valid:function(e){n.send_assessment_info(e)}})},init:function(){this.init_edit();this.bind_send_assessment_action()}};n.init()});
define("5",["1"],function(t,i,n){var e,o,s;t("1");e={init_choice:function(){var t=$("#menu_id").val();$(".menu_list").find("A").each(function(t){$(this);if(this.getAttribute("data")==t){$(this).addClass("current");$(this).removeClass("no_current")}else{$(this).addClass("no_current");$(this).removeClass("current")}},[t])}};o={timer:null,top:0,begin:function(t){o.timer=setInterval(function(){t.css("top",o.top+"px");o.top<-11?o.top=12:o.top-=1},180)},end:function(){null!=o.timer&&clearInterval(o.timer)}};s={tips_show:function(){o.begin($("#bulletin_head_tips"));$("#bulletin_head_tips").hover(function(){o.end()},function(){o.begin($("#bulletin_head_tips"))})}};$(function(){e.init_choice();s.tips_show()});i.get_title=function(){$(".menu").find(".title").val()}});
define("8",[],function(y,d,r){r.exports="@charset \"UTF-8\";.n-error{overflow:hidden;color:red;font-size:12px;display:inline-block}.n-error .n-icon{background:url(/static/images/f66d41752c6b2b20.png) no-repeat;width:16px;height:16px;overflow:hidden;float:left}.n-error .n-msg{line-height:16px}.n-ok{overflow:hidden;background:url(/static/images/f66d41752c6b2b20.png) no-repeat;background-position-x:-16px;color:#390;width:18px;height:16px;display:inline-block;margin-left:5px;font-size:12px}"});
define("9",[],function(y,d,r){r.exports=".dialog-dialog_div{overflow:hidden;display:block;position:fixed;border:1px solid #3f55a0;border-top-right-radius:4px;border-top-left-radius:4px;z-index:1001}.dialog-dialog_div table{border-collapse:collapse;width:100%}.dialog-dialog_div .title{background-color:#3f55a0;overflow:hidden;display:block;height:30px;color:#FFF}.dialog-dialog_div .title span{line-height:35px;margin-left:8px;font-size:14px}.dialog-dialog_div .title .close{float:right;line-height:30px;font-size:25px;margin-right:10px;color:#FFF}.dialog-dialog_div .dialog_content{overflow:hidden;background-color:#FFF;padding-left:10px;word-break:break-all}.dialog-dialog_div .bottom{height:38px;overflow:hidden;background-color:#ccc}.dialog-dialog_div .bottom .no{float:left;margin-left:8px;border-radius:5px}.dialog-dialog_div .bottom .yes{float:right;margin-right:8px;border-radius:5px}"});
coolie.chunk(["0"]);