-- 启动mysql sudo mysqld_safe
-- 创建数据库 CREATE DATABASE internship DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci
-- 创建用户 CREATE USER 'internship-user'@'%' IDENTIFIED BY 'internship';
-- 给用户分配权限 GRANT ALL ON internship.* TO 'internship-user'@'%';

drop table  if exists user_info;
create table user_info(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  user_name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '用户名',
  password varchar(64) NOT NULL COLLATE utf8_bin COMMENT '密码',
  name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '姓名',
  sex TINYINT  NOT NULL COMMENT '性别',
  user_type TINYINT NOT NULL COMMENT '用户类型',
  phone varchar(64)  COLLATE utf8_bin COMMENT '联系方式',
  status TINYINT  NOT NULL COMMENT '用户状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20)  COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(user_name)
 )ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

insert into user_info(user_name,password,name,sex,user_type,status,created_by,created_time,updated_by,updated_time)
values('sys_admin','DW/zh+/euHMWZDrhMfcrHLKETjmcHXvgk71ub/X+bqE=','系统管理员',100,100,100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000);

drop table  if exists company;
create table company(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  company_name varchar(128) NOT NULL COLLATE utf8_bin COMMENT '单位名称',
  company_address varchar(256) NOT NULL COLLATE utf8_bin COMMENT '单位地址',
  phone varchar(64) NOT NULL COLLATE utf8_bin COMMENT '单位联系方式',
  internship_number int(4) NOT NULL COMMENT '单位能容纳的实习人数',
  student_number int(4)  COMMENT '正在实习的学生人数',
  internship_desc text COLLATE utf8_bin  COMMENT '实习描述(实习管理办法)',
  status TINYINT  NOT NULL COMMENT '公司状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20)  COMMENT '更新时间',
  PRIMARY KEY (id)
 )ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='实习单位(基地)基础信息表';

drop table  if exists company_tutor;
create table company_tutor(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '姓名',
  department varchar(64) NOT NULL COLLATE utf8_bin COMMENT '单位导师所在部门',
  company_id bigint(20) NOT NULL COMMENT '所在单位',
  company_name varchar(128) NOT NULL COLLATE utf8_bin COMMENT '单位名称',
  user_id bigint(20) NOT NULL COMMENT '用户信息',
  tutor_type TINYINT  NOT NULL COMMENT '导师类型 普通/单位负责人',
  student_number int(4)  COMMENT '正在实习的学生人数',
  status TINYINT  NOT NULL COMMENT '导师状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20)  COMMENT '更新时间',
  PRIMARY KEY (id)
 )ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='单位实习导师信息表';

drop table  if exists teacher;
create table teacher(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '姓名',
  university_id bigint(20) NOT NULL COMMENT '所属学校',
  college_id bigint(20) not null comment '所属学院',
  department_id bigint(20) comment '所属系',
  speciality_id bigint(20) comment '所属专业',
  rank varchar(64) NOT NULL COLLATE utf8_bin COMMENT '带队老师职称',
  user_id bigint(20) NOT NULL COMMENT '用户信息',
  teacher_type TINYINT  NOT NULL COMMENT '带队老师类型 普通/学院负责人',
  student_number int(4)  COMMENT '正在实习的学生人数',
  status TINYINT  NOT NULL COMMENT '带队老师状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20)  COMMENT '更新时间',
  PRIMARY KEY (id)
 )ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学院带队老师信息表';

drop table  if exists student;
create table student(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  student_number varchar(64) NOT NULL COLLATE utf8_bin COMMENT '学号',
  university_id bigint(20) NOT NULL COMMENT '所属学校',
  college_id bigint(20) not null comment '所属学院',
  department_id bigint(20) comment '所属系',
  speciality_id bigint(20) comment '所属专业',
  name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '姓名',
  grade varchar(64) NOT NULL COLLATE utf8_bin COMMENT '年级',
  academician_type TINYINT  NOT NULL COMMENT '学位',
  tutor_id bigint(20)  COMMENT '学生的导师',
  tutor_name varchar(64)   COLLATE utf8_bin COMMENT '导师姓名',
  teacher_id bigint(20) COMMENT '学生的带队老师',
  teacher_name varchar(64)  COLLATE utf8_bin COMMENT '带队姓名',
  company_id bigint(20) COMMENT '学生的实习单位',
  company_name  varchar(128) COLLATE utf8_bin  COMMENT '实习单位姓名',
  expect_company varchar(128) COLLATE utf8_bin COMMENT '期望实习单位',
  remark varchar(1024) COLLATE utf8_bin COMMENT '备注',
  user_id bigint(20) NOT NULL COMMENT '用户信息',
  status TINYINT  NOT NULL COMMENT '学生状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
 )ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学院带队老师信息表';

drop table if exists attend_record;
create table attend_record(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  user_id  bigint(20) NOT NULL COMMENT '用户ID',
  attend_day bigint(20)  NOT NULL COMMENT '签到日期',
  time_interval TINYINT  NOT NULL COMMENT '签到日期上午或下午',
  status TINYINT  NOT NULL COMMENT '签到状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
 )ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生签到表';

drop table if exists leave_record;
create table leave_record(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  user_id  bigint(20) NOT NULL COMMENT '用户ID',
  begin_day bigint(20) NOT NULL COMMENT '开始时间',
  begin_time_interval TINYINT NOT NULL COMMENT '开始上午或下午',
  end_day bigint(20) NOT NULL COMMENT '开始时间',
  end_time_interval TINYINT NOT NULL COMMENT '开始上午或下午',
  leave_type TINYINT NOT NULL COMMENT '病假或事假',
  reason varchar(1024) COLLATE utf8_bin  COMMENT '请假原因',
  status TINYINT  NOT NULL COMMENT '请假状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='请假表';

drop table if exists flow_record;
create table flow_record(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  student_id bigint(20) NOT NULL COMMENT '学生Id',
  student_name varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '学生姓名',
  flow_type TINYINT  NOT NULL COMMENT '类型，如请假流程 签到流程',
  flow_order TINYINT  NOT NULL COMMENT '流程顺序 从小到大依次审核',
  target_id bigint(20) NOT NULL COMMENT '目标Id 请假表Id，签到表Id',
  operate_user_id bigint(20) NOT NULL comment'审核者用户ID',
  operate_name varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '审核者姓名',
  operate_user_type TINYINT  NOT NULL  COMMENT '操作者用户类型',
  status TINYINT  NOT NULL COMMENT '审核状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='审核流程表';

drop table if exists internship_notes;
create table internship_notes(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  student_id bigint(20) NOT NULL comment '学生ID',
  student_name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '学生姓名',
  begin_day bigint(20)  NOT NULL COMMENT '开始日期',
  end_day bigint(20)  NOT NULL COMMENT '结束日期',
  notes_content text COLLATE utf8_bin  COMMENT '笔记内容',
  notes_review text COLLATE utf8_bin  COMMENT '笔记心得',
  notes_type  TINYINT  NOT NULL COMMENT '笔记类型',
  tutor_reviewer_id bigint(20) comment '导师评价者用户Id',
  tutor_reviewer  varchar(64) COLLATE utf8_bin COMMENT '导师评价者姓名',
  tutor_remark  text COLLATE utf8_bin  COMMENT '导师评语',
  tutor_remark_time bigint(20) COMMENT '导师评价时间',
  teacher_reviewer_id bigint(20) comment '带队老师评价者用户Id',
  teacher_reviewer varchar(64) COLLATE utf8_bin COMMENT '带队老师评价者姓名',
  teacher_remark text COLLATE utf8_bin  COMMENT '带队老师评语',
  teacher_remark_time bigint(20) comment '带队老师评价时间',
  status TINYINT  NOT NULL COMMENT '笔记状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='实习笔记表(日记/周记)';

drop table if exists internship_assessment;
create table internship_assessment(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  student_id bigint(20) NOT NULL comment '学生ID',
  student_name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '学生姓名',
  tutor_reviewer_id bigint(20) comment '导师考评者用户Id',
  tutor_reviewer varchar(64) COLLATE utf8_bin COMMENT '导师考评者姓名',
  tutor_remark  text COLLATE utf8_bin  COMMENT '导师指导考评意见',
  tutor_score int(4)  COMMENT '导师指导建议分数',
  tutor_remark_time bigint(20) COMMENT '导师考评时间',
  company_reviewer_id bigint(20)  comment '实习单位考评者用户Id',
  company_reviewer  varchar(64) COLLATE utf8_bin  COMMENT '实习单位考评者姓名',
  company_remark text COLLATE utf8_bin  COMMENT '实习单位考评意见',
  company_remark_time bigint(20) COMMENT '实习单位考评时间',
  teacher_confirm_user_id bigint(20) COMMENT '带队老师确认者用户ID',
  teacher_confirm_user varchar(64) COLLATE utf8_bin COMMENT '带队老师确认者姓名',
  teacher_confirm TINYINT  NOT NULL COMMENT '带队老师确认',
  teacher_confirm_time bigint(20)  comment '带队老师确认时间',
  status TINYINT  NOT NULL COMMENT '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(student_id)
)ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生实习考评表';

drop table if exists internship_evaluation_rule;
create table internship_evaluation_rule(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  subject varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '项目',
  evaluation_rule varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '评价准则',
  university_id bigint(20) NOT NULL COMMENT '所属学校',
  college_id bigint(20) not null comment '所属学院',
  department_id bigint(20) comment '所属系',
  speciality_id bigint(20) comment '所属专业',
  status TINYINT  NOT NULL COMMENT '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生实习质量评价表模板';

insert into internship_evaluation_rule(subject,evaluation_rule,status,created_by,created_time,updated_by,updated_time)
values
('实习过程','学生在整个毕业实习中积极主动，认真负责，遵守实习守则和劳动纪律',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习过程','实习安排合理，各项实习工作循序渐进，符合实习程序和工作流程的要求',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习过程','全面地，独立地完成毕业实习教学要求的各项任务',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习过程','实习内容记录完整、全面，实习内容能全面反映专业培养目标要求和学科、专业的特点',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习过程','实习单位指导教师和实习单位给予的评价',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习作业、实习报告质量','整体思路清晰，结构完整，问题相符',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习作业、实习报告质量','论点鲜明，观点正确，条理分明，语言流畅',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习作业、实习报告质量','用语格式、图表、数据、各种资料、标准的引用符合科学论文的写作规范',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('实习作业、实习报告质量','部分成果在理论上具有新意，应用性研究对与实际工作具有一定意义',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('能力培养','能了解社会，接触实际，巩固专业思想，培养良好的职业道德和工作作风',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('能力培养','能综合运用所学专业业务知识和专业技能，观察问题、分析问题和解决问题',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000),
('能力培养','能较熟练地运用本学科的常规科学方法，能适当运用相关研究手段进行资料收集、加工、处理',100,'sys_tem',UNIX_TIMESTAMP()*1000,'sys_tem',UNIX_TIMESTAMP()*1000);

drop table if exists internship_evaluation;
create table internship_evaluation(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  student_id bigint(20) NOT NULL comment '学生ID',
  student_name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '学生姓名',
  evaluation_user_id bigint(20) NOT NULL comment '评价者用户Id',
  evaluation_name varchar(64) NOT NULL COLLATE utf8_bin COMMENT '评价者姓名',
  evaluation_time bigint(20) NOT NULL comment '评价时间',
  status TINYINT  NOT NULL COMMENT '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(student_id)
)ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生实习质量评价表';

drop table if exists internship_evaluation_result;
create table internship_evaluation_result (
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  evaluation_id bigint(20) NOT NULL comment '学生实习质量评价ID',
  subject varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '项目',
  evaluation_rule varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '评价准则',
  grade TINYINT  NOT NULL COMMENT '评分',
  status TINYINT  NOT NULL COMMENT '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生实习质量评价结果表';

drop table if exists internship_report;
create table internship_report(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  subject varchar(64)  NOT NULL COLLATE utf8_bin comment '题目',
  student_id bigint(20) NOT NULL comment '学生ID',
  student_name varchar(64) NOT NULL COLLATE utf8_bin comment '学生姓名',
  report_score int(4) comment '实习报告成绩',
  reviewer_id bigint(20) comment '评语者用户ID',
  reviewer varchar(64) COLLATE utf8_bin COMMENT '评语者姓名',
  remark text COLLATE utf8_bin  comment '评语',
  remark_time bigint(20) comment '评阅时间',
  status TINYINT  NOT NULL comment '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin comment '创建者',
  created_time bigint(20)  NOT NULL comment '创建时间',
  updated_by varchar(64) COLLATE utf8_bin comment '更新者',
  updated_time bigint(20) comment '更新时间',
  PRIMARY KEY (id),
  UNIQUE(student_id)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='学生实习报告';

drop table if exists file_record;
create table file_record(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  file_name varchar(64) NOT NULL COLLATE utf8_bin comment '文件名',
  file_content text COLLATE utf8_bin  comment '文件内容',
  status TINYINT  NOT NULL comment '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin comment '创建者',
  created_time bigint(20)  NOT NULL comment '创建时间',
  updated_by varchar(64) COLLATE utf8_bin comment '更新者',
  updated_time bigint(20) comment '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='文件存储';

/**
公告sql
 */
drop table if exists bulletin;
create table bulletin(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  title varchar(64) NOT NULL COLLATE utf8_bin COMMENT '公告标题',
  content text NOT NULL COLLATE utf8_bin COMMENT '公告内容',
  user_id bigint(20) NOT NULL COMMENT '用户信息',
  status TINYINT  NOT NULL COMMENT '公告状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id)
 )ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公告信息表';

drop table if exists app_secret;
create table app_secret(
  id bigint(20) NOT NULL AUTO_INCREMENT comment '主键ID',
  app_id varchar(128) NOT NULL COLLATE utf8_bin COMMENT 'app id',
  app_secret varchar(1024) NOT NULL COLLATE utf8_bin COMMENT 'app 密钥',
  description text NOT NULL COLLATE utf8_bin COMMENT '描述',
  status TINYINT  NOT NULL COMMENT '密钥状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(app_id)
 )ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='app密钥管理';

 drop table if exists university_department;

create table university_department (
  id bigint(20) NOT NULL comment '主键ID',
  parent_id bigint(20) NOT NULL comment '父ID',
  department_name  varchar(256) NOT NULL COLLATE utf8_bin COMMENT '部门名称',
  department_type  TINYINT  NOT NULL COMMENT '部门类型: 100-大学、101-学院、102-系、103-专业',
  status TINYINT  NOT NULL COMMENT '状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64) COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20) COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(id)
)ENGINE=InnoDB CHARSET=utf8 COLLATE=utf8_bin COMMENT='大学部门表';


insert into university_department(id,parent_id,department_name,department_type,status,created_by,created_time) values(10000,-1,'西南财经大学',100,100,'system',UNIX_TIMESTAMP()*1000);
insert into university_department(id,parent_id,department_name,department_type,status,created_by,created_time) values(10000100,10000,'法学院',101,100,'system',UNIX_TIMESTAMP()*1000);
insert into university_department(id,parent_id,department_name,department_type,status,created_by,created_time) values(10000100100,10000100,'法学系',102,100,'system',UNIX_TIMESTAMP()*1000);
insert into university_department(id,parent_id,department_name,department_type,status,created_by,created_time) values(10000100100100,10000100100,'法学',103,100,'system',UNIX_TIMESTAMP()*1000);
