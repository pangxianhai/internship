package cn.edu.swufe.lawschool.internship.exception;

/**
 * Created on 2015年11月23
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipNotesError extends ErrorType {
    public final static ErrorType ADD_NOTES_ERROR = new InternshipNotesError(130000, "添加笔记失败");

    public final static ErrorType UPDATE_NOTES_ERROR = new InternshipNotesError(130001, "更新笔记失败");

    public final static ErrorType NOTES_ID_EMPTY = new InternshipNotesError(130002, "笔记ID为空");

    public final static ErrorType ADD_NOTES_DUPLICATE = new InternshipNotesError(130003, "重复添加");

    public final static ErrorType NOTES_NOT_EXIST = new InternshipNotesError(130004, "笔记不存在");

    public final static ErrorType UPDATE_NOTES_ERROR_BY_HAS_TUTOR_REVIEW = new InternshipNotesError(130005,
                                                                                                    "该笔记已经被指导老师考评，不能修改");

    public final static ErrorType UPDATE_NOTES_ERROR_BY_HAS_TEACHER_REVIEW = new InternshipNotesError(130006,
                                                                                                      "该笔记已经被带队老师考评，不能修改");

    public final static ErrorType REVIEW_CONTENT_EMPTY = new InternshipNotesError(130007, "考评内容不能为空");

    public final static ErrorType HAS_REVIEWED = new InternshipNotesError(130008, "已经考评了");

    protected InternshipNotesError (Integer code, String desc) {
        super(code, desc);
    }
}
