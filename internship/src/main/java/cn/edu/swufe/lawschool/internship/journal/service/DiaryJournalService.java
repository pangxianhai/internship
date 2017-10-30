package cn.edu.swufe.lawschool.internship.journal.service;

import cn.edu.swufe.lawschool.internship.journal.model.InternshipNotes;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月23
 * <p>Title:       日记服务</p>
 * <p>Description: 日记服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface DiaryJournalService {

    /**
     * Created on 2015年11月23
     * <p>Description:学生写日记</p>
     * @author 庞先海
     */
    void studentWriteDiaryJournal(Long userId, Long day, String notesContent, String notesReview);

    /**
     * Created on 2015年11月23
     * <p>Description:通过日记Id查询日记</p>
     * @author 庞先海
     */
    InternshipNotes getDiaryJournalByDestId(String JournalDestId);

    /**
     * Created on 2015年11月23
     * <p>Description:通过学生查询日记</p>
     * @author 庞先海
     */
    List<InternshipNotes> getDiaryJournal(InternshipNotes internshipNotes, Page page);

    /**
     * Created on 2015年11月24
     * <p>Description:更新日记</p>
     * @author 庞先海
     */
    void updateDiaryJournal(String destId, String journalContent, String journalReview, String operator);

    /**
     * Created on 2015年11月29
     * <p>Description:获取学生的日志数</p>
     * @author 庞先海
     */
    Integer getStudentDiaryJournalCount(Long studentId);

    /**
     * Created on 2016年07月17
     * <p>Description:带队老师考评日记</p>
     * @author 庞先海
     */
    void teacherReviewDiaryJournal(String internshipDesId, Long teacherUserId, String teacherRemark, String operator);

}
