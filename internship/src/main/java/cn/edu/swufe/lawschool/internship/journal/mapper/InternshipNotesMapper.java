package cn.edu.swufe.lawschool.internship.journal.mapper;

import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月23
 * <p>Title:       日记Mapper</p>
 * <p>Description: 日记Mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface InternshipNotesMapper {

    /**
     * Created on 2015年11月23
     * <p>Description:根据条件分页查询笔记信息</p>
     * @author 庞先海
     */
    List<InternshipNotes> select(InternshipNotes notes, Page page);

    /**
     * Created on 2015年11月23
     * <p>Description:根据条件查询笔记信息</p>
     * @author 庞先海
     */
    List<InternshipNotes> select(InternshipNotes notes);

    /**
     * Created on 2015年11月23
     * <p>Description:更新笔记</p>
     * @author 庞先海
     */
    int update(InternshipNotes notes);

    /**
     * Created on 2015年11月23
     * <p>Description:插入笔记</p>
     * @author 庞先海
     */
    int insert(InternshipNotes notes);
}
