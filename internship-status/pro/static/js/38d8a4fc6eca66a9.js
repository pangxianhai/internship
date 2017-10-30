/*coolie built*/
define("0",["g","h","l","6"],function(e,s,n){var t,i,o,a=e("g");e("h");t=e("l");i=e("6");o={pushStudentInfo:function(e){var s,n=[];n.push('<li id="studentNameNode" studentDesId="'+e.desId+'" class="_node">');n.push('<p class="_tips">姓名</p>');n.push('<p class="_content"><a>'+e.name+"</a></p>");n.push('<i class="_icon">&gt;</i>');n.push("</li>");n.push('<li class="_node">');n.push('<p class="_tips">学号</p>');n.push('<p class="_content">'+e.studentNumber+"</p>");n.push("</li>");n.push('<li id="diaryJournalNode" class="_node">');n.push('<p class="_tips">日记统计</p>');n.push('<p class="_content"><a>'+e.diaryJournalCount+"</a></p>");n.push('<i class="_icon">&gt;</i>');n.push("</li>");n.push('<li id="weeklyJournalNode" class="_node">');n.push('<p class="_tips">周记统计</p>');n.push('<p class="_content"><a>'+e.weeklyJournalCount+"</a></p>");n.push('<i class="_icon">&gt;</i>');n.push("</li>");n.push('<li id="attendedNode" class="_node">');n.push('<p class="_tips">出勤统计</p>');n.push('<p class="_content"><a>'+e.attendedCount+"</a></p>");n.push('<i class="_icon">&gt;</i>');n.push("</li>");if("undefined"!=typeof e.assessment){n.push('<li id="assessmentNode" assessmentDesId="'+e.assessment.desId+'" class="_node">');n.push('<p class="_tips">实习单位考评表</p>');n.push('<p class="_content"><a>查看</a></p>');n.push('<i class="_icon">&gt;</i>');n.push("</li>")}else{n.push('<li id="assessmentNode" class="_node">');n.push('<p class="_tips">实习单位考评表</p>');if(this.canSendAssessment){n.push('<p class="_content"><a>发放</a></p>');n.push('<i class="_icon">&gt;</i>')}else n.push('<p class="_content">--</p>');n.push("</li>")}if("undefined"!=typeof e.evaluation){n.push('<li id="evaluationNode" evaluationDesId="'+e.evaluation.desId+'"  studentDesId="'+e.desId+'" class="_node">');n.push('<p class="_tips">质量控制表</p>');n.push('<p class="_content"><a>查看</a></p>');n.push('<i class="_icon">&gt;</i>');n.push("</li>")}else{n.push('<li id="evaluationNode"  studentDesId="'+e.desId+'" class="_node">');n.push('<p class="_tips">质量控制表</p>');if(this.canSendEvaluation){n.push('<p class="_content"><a>发放</a></p>');n.push('<i class="_icon">&gt;</i>')}else n.push('<p class="_content">--</p>');n.push("</li>")}s="";if("undefined"!=typeof e.internshipReport){s=e.internshipReport.reportScoreStr;n.push('<li id="internshipReportNode" reportDesId="'+e.internshipReport.desId+'" class="_node">');n.push('<p class="_tips">实习报告</p>');n.push('<p class="_content"><a>查看</a></p>');n.push('<i class="_icon">&gt;</i>');n.push("</li>")}else{n.push('<li id="internshipReportNode" class="_node">');n.push('<p class="_tips">实习报告</p>');if(this.canCreateReport){n.push('<p class="_content"><a>生成</a></p>');n.push('<i class="_icon">&gt;</i>')}else n.push('<p class="_content">--</p>');n.push("</li>")}n.push('<li class="_node _last">');n.push('<p class="_tips">总成绩</p>');n.push('<p class="_content">'+i.filterNull(s)+"</p>");n.push("</li>");a("#studentInternship").html(n.join(""))},bindDiaryJournalNodeAction:function(){a("#diaryJournalNode").click(function(){window.location.href="/webApp/student/diaryJournal/list.htm?student.desId="+a("#studentDesId").val()+"&returnUrl="+encodeURIComponent(location.href)})},bindWeeklyJournalNodeAction:function(){a("#weeklyJournalNode").click(function(){window.location.href="/webApp/student/weeklyJournal/list.htm?student.desId="+a("#studentDesId").val()+"&returnUrl="+encodeURIComponent(location.href)})},bindAttendedNodeAction:function(){a("#attendedNode").click(function(){window.location.href="/webApp/student/attend/list.htm?student.desId="+a("#studentDesId").val()+"&returnUrl="+encodeURIComponent(location.href)})},bindAssessmentNodeAction:function(){a("#assessmentNode").click(function(){var e=a(this).attr("assessmentDesId");i.isEmpty(e)?o.canSendAssessment&&(window.location.href="/webApp/student/assessment/send.htm?studentDesId="+a("#studentDesId").val()+"&returnUrl="+encodeURIComponent(location.href)):window.location.href="/webApp/student/assessment/detail/"+e+".htm?returnUrl="+encodeURIComponent(location.href)})},bindEvaluationNodeAction:function(){a("#evaluationNode").click(function(){var e=a(this).attr("studentDesId"),s=a(this).attr("evaluationDesId");i.isEmpty(s)?o.canSendEvaluation&&(window.location.href="/webApp/student/evaluation/send/"+e+".htm?returnUrl="+encodeURIComponent(location.href)):window.location.href="/webApp/student/evaluation/detail/studentId_"+e+".htm?returnUrl="+encodeURIComponent(location.href)})},bindInternshipReportNodeAction:function(){a("#internshipReportNode").click(function(){var e=a(this).attr("reportDesId");i.isEmpty(e)?o.canCreateReport&&(window.location.href="/webApp/student/report/create.htm?returnUrl="+encodeURIComponent(location.href)):window.location.href="/webApp/student/report/detail/"+e+".htm?returnUrl="+encodeURIComponent(location.href)})},bindStudentNameNodeAction:function(){a("#studentNameNode").click(function(){window.location.href="/webApp/student/detail/"+a(this).attr("studentDesId")+".htm?returnUrl="+encodeURIComponent(location.href)})},loadStudentInternship:function(){a.ajax({url:"/student/internship/internshipOfStudent.json",type:"post",dataType:"json",data:{studentDesId:a("#studentDesId").val()},beforeSend:function(){t.dialog_load("加载中...")},success:function(e){t.close_dialog();if(e.ret){o.pushStudentInfo(e.data);o.bindDiaryJournalNodeAction();o.bindWeeklyJournalNodeAction();o.bindAttendedNodeAction();o.bindAssessmentNodeAction();o.bindEvaluationNodeAction();o.bindInternshipReportNodeAction();o.bindStudentNameNodeAction()}else t.push_error_message(e.message)}})},init:function(){this.canCreateReport="true"==a("#canCreateReport").val();this.canSendAssessment="true"==a("#canSendAssessment").val();this.canSendEvaluation="true"==a("#canSendEvaluation").val();this.loadStudentInternship()}};o.init()});
define("h",["g","6"],function(i,t,e){var n,o;i("g");n=i("6");o={initPanel:function(){"true"==$("#show_header_left").val()?$("header .header .left").css("visibility","visible"):$("header .header .left").css("visibility","hidden");var i=$("#headerRight").html();n.isNotEmpty(i)&&$("header .header .right").html(i)},initFooterMenu:function(){var i=$("#footerMenuId").val();$("footer .footer_menu").find("."+i).addClass("active")},bindLeftBtnAction:function(){$("header .header .left a").click(function(){var i=$("#returnUrl").val();n.isNotEmpty(i)?window.location.href=i:window.history.go(-1)})},init:function(){this.initPanel();this.bindLeftBtnAction();this.initFooterMenu();$("body").height($("body")[0].clientHeight)}};$(function(){o.init()})});
define("o",[],function(y,d,r){r.exports=".rDialog{position:fixed;_position:absolute}.rDialog-wrap{position:relative;background:#000;opacity:.7;background-clip:padding-box;border-radius:10px;-moz-border-radius:10px;-o-border-radius:10px;-webkit-border-radius:10px;box-shadow:1px 1px 1px #000;padding:.5em .5em}.rDialog-header-load{text-align:center;background:url(/static/images/0e6e0f46d7504242.gif);background-repeat:no-repeat;background-position:0 0;background-size:100%;width:30px;height:33px;margin:auto}.rDialog-header-alert,.rDialog-header-ok{text-align:center;background:url(/static/images/12dffd74007d31b6.png);background-repeat:no-repeat;background-size:100%;width:33px;height:36px;margin:auto}.rDialog-header-alert{background-position:0 -36px}.rDialog-header-ok{background-position:0 0}.rDialog-content{padding:1em 1em;font-size:1.1em;color:#FFF;overflow:hidden;text-align:center}.rDialog-footer{padding-left:15px;text-align:center}.rDialog-footer a{display:inline-block;margin:0 15px 15px 0;height:32px;line-height:32px;padding:0 30px;font-size:1rem;-moz-border-radius:3px;-webkit-border-radius:3px;border-radius:3px}.rDialog-footer a:hover,.rDialog-footer a:link,.rDialog-footer a:visited{color:#fff;text-decoration:none}.rDialog-ok,.rDialog-ok:hover{background:#c70066;color:#fff}.rDialog-cancel,.rDialog-cancel:hover{background:#E6E6E6;color:#A7A7A7}.rDialog-mask{position:fixed;_position:absolute;left:0;top:0;width:100%;height:100%;background:#000;filter:alpha(opacity=30);opacity:.3}"});
coolie.chunk(["0"]);