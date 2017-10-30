package cn.edu.swufe.lawschool.internship.journal.model;

import cn.edu.swufe.lawschool.common.base.BaseType;

/**
 * Created on 2015年11月23
 * <p>Title:      笔记类型</p>
 * <p>Description: 笔记类型定义</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class NotesType extends BaseType {

    public final static NotesType DIARY_JOURNAL = new NotesType(100, "日记");
    public final static NotesType WEEKLY_JOURNAL = new NotesType(101, "周记");

    protected NotesType(Integer code, String desc) {
        super(code, desc);
    }
}
