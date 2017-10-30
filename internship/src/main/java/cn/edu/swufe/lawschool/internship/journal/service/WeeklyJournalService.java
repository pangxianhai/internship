package cn.edu.swufe.lawschool.internship.journal.service;

import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月23
 * <p>Title:       周记服务</p>
 * <p>Description: 周记服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface WeeklyJournalService {

    /**
     * Created on 2015年11月24
     * <p>Description:学生写周记</p>
     * @author 庞先海
     */
    void studentWriteWeeklyJournal(Long userId, Long beginDay, Long endDay, String notesContent);

    /**
     * Created on 2015年11月24
     * <p>Description:通过日记Id查询周记</p>
     * @author 庞先海
     */
    InternshipNotes getWeeklyJournalByDestId(String journalDestId);

    /**
     * Created on 2015年11月24
     * <p>Description:通过学生查询周记</p>
     * @author 庞先海
     */
    List<InternshipNotes> getWeeklyJournal(InternshipNotes internshipNotes, Page page);

    /**
     * Created on 2015年11月24
     * <p>Description:更新周记</p>
     * @author 庞先海
     */
    void updateWeeklyJournal(String destId, String journalContent, String updateBy);

    /**
     * Created on 2015年12月13
     * <p>Description:导师考评周记</p>
     * @author 庞先海
     */
    void tutorReviewWeeklyJournal(String internshipDesId, Long tutorUserId, String tutorRemark, String updateBy);

    /**
     * Created on 2015年12月13
     * <p>Description:导师考评周记</p>
     * @author 庞先海
     */
    void teacherReviewWeeklyJournal(String internshipDesId, Long teacherUserId, String teacherRemark, String updateBy);

    /**
     * Created on 2015年11月29
     * <p>Description:获取学生周记数</p>
     * @author 庞先海
     */
    Integer getStudentWeeklyJournalCount(Long studentId);

}
