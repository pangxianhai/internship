/*coolie built*/
define("0",["1","2","3","5","4","6","7"],function(i){var n,t,e;i("1");i("2");i("3");i("5");i("4");n=i("6");t=i("7");e={init_edit:function(){um=UM.getEditor("internshipDesc",{imagePath:"",initialFrameHeight:200,initialFrameWidth:600,lang:"zh-cn",langPath:UMEDITOR_CONFIG.UMEDITOR_HOME_URL+"lang/",focus:!0})},bind_add_company_action:function(){$("#add_company_form").validator({fields:{"#companyName":"公司名:required;length[5~50]","#companyAddress":"公司名地址:required;length[5~100]","#phone":"联系方式:required;length[5~15]","#internshipNumber":"实习人数:required;positive_integer"},submit_target:"#add_company_btn",valid:function(i){e.add_company_submit(i)}})},add_company_submit:function(i){$.ajax({url:"/company/add.json",type:"POST",data:n.getFormData(i),dataType:"json",success:function(i){if(i.ret){if(i.data){t.openSample("系统提示","添加公司成功");setTimeout(function(){window.location.href="/company/list.htm"},2e3)}}else t.openSample("系统提示",i.message)}})},init:function(){this.init_edit();this.bind_add_company_action()}};e.init()});
define("5",["1"],function(t,i,n){var e,o,s;t("1");e={init_choice:function(){var t=$("#menu_id").val();$(".menu_list").find("A").each(function(t){$(this);if(this.getAttribute("data")==t){$(this).addClass("current");$(this).removeClass("no_current")}else{$(this).addClass("no_current");$(this).removeClass("current")}},[t])}};o={timer:null,top:0,begin:function(t){o.timer=setInterval(function(){t.css("top",o.top+"px");o.top<-11?o.top=12:o.top-=1},180)},end:function(){null!=o.timer&&clearInterval(o.timer)}};s={tips_show:function(){o.begin($("#bulletin_head_tips"));$("#bulletin_head_tips").hover(function(){o.end()},function(){o.begin($("#bulletin_head_tips"))})}};$(function(){e.init_choice();s.tips_show()});i.get_title=function(){$(".menu").find(".title").val()}});
define("8",[],function(y,d,r){r.exports="@charset \"UTF-8\";.n-error{overflow:hidden;color:red;font-size:12px;display:inline-block}.n-error .n-icon{background:url(/static/images/f66d41752c6b2b20.png) no-repeat;width:16px;height:16px;overflow:hidden;float:left}.n-error .n-msg{line-height:16px}.n-ok{overflow:hidden;background:url(/static/images/f66d41752c6b2b20.png) no-repeat;background-position-x:-16px;color:#390;width:18px;height:16px;display:inline-block;margin-left:5px;font-size:12px}"});
define("9",[],function(y,d,r){r.exports=".dialog-dialog_div{overflow:hidden;display:block;position:fixed;border:1px solid #3f55a0;border-top-right-radius:4px;border-top-left-radius:4px;z-index:1001}.dialog-dialog_div table{border-collapse:collapse;width:100%}.dialog-dialog_div .title{background-color:#3f55a0;overflow:hidden;display:block;height:30px;color:#FFF}.dialog-dialog_div .title span{line-height:35px;margin-left:8px;font-size:14px}.dialog-dialog_div .title .close{float:right;line-height:30px;font-size:25px;margin-right:10px;color:#FFF}.dialog-dialog_div .dialog_content{overflow:hidden;background-color:#FFF;padding-left:10px;word-break:break-all}.dialog-dialog_div .bottom{height:38px;overflow:hidden;background-color:#ccc}.dialog-dialog_div .bottom .no{float:left;margin-left:8px;border-radius:5px}.dialog-dialog_div .bottom .yes{float:right;margin-right:8px;border-radius:5px}"});
coolie.chunk(["0"]);