package cn.edu.swufe.lawschool.internship.tutor.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.TutorError;
import cn.edu.swufe.lawschool.internship.tutor.mapper.TutorMapper;
import cn.edu.swufe.lawschool.internship.tutor.model.Tutor;
import cn.edu.swufe.lawschool.internship.tutor.model.TutorType;
import cn.edu.swufe.lawschool.internship.tutor.service.TutorService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月09
 * <p>Title:       单位导师</p>
 * <p>Description: 单位导师服务类实习</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("tutorService")
public class TutorServiceImpl implements TutorService {

    @Autowired
    TutorMapper tutorMapper;

    @Autowired
    UserService userService;

    public Long addTutor(Tutor tutor) {
        UserInfo userInfo = tutor.getUserInfo();
        userInfo.setUserType(UserType.COMPANY_TUTOR);
        userInfo.setCreatedBy(tutor.getCreatedBy());
        Long userId = userService.addUser(userInfo);

        tutor.setStatus(Status.VALID);
        tutor.setUserId(userId);
        tutor.setName(userInfo.getName());
        tutor.setCreatedTime(DateUtil.currentMilliseconds());
        tutor.setUpdatedBy(tutor.getCreatedBy());
        tutor.setUpdatedTime(tutor.getCreatedTime());
        int count = tutorMapper.insert(tutor);
        if (count < 1) {
            throw new InternshipException(TutorError.ADD_TUTOR_ERROR);
        }
        return tutor.getId();
    }

    public List<Tutor> getTutor(Tutor tutor, Page page) {
        return tutorMapper.select(tutor, page);
    }

    public Tutor getTutorById(Long tutorId) {
        if (tutorId == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setId(tutorId);
        return selectOne(tutor);
    }

    public Tutor getTutorByDesId(String desId) {
        if (StringUtil.isEmpty(desId)) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setDesId(desId);
        return selectOne(tutor);
    }

    public Tutor getTutorByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setUserId(userId);
        return selectOne(tutor);
    }

    public void addStudent(Long tutorId, String operator) {
        Tutor tutor = getTutorById(tutorId);
        Tutor _tutor = new Tutor();
        _tutor.setId(tutor.getId());
        Integer sum = tutor.getStudentNumber();
        if (sum == null) {
            sum = 0;
        }
        _tutor.setStudentNumber(sum + 1);
        update(_tutor, operator);
    }

    public void minusStudent(Long tutorId, String operator) {
        Tutor tutor = getTutorById(tutorId);
        Tutor _tutor = new Tutor();
        _tutor.setId(tutor.getId());
        Integer num = tutor.getStudentNumber() - 1;
        if (num < 0) {
            num = 0;
        }
        _tutor.setStudentNumber(num);
        update(_tutor, operator);
    }

    public Tutor getCompanyLeader(Long companyId) {
        if (companyId == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setCompanyId(companyId);
        tutor.setTutorType(TutorType.LEADER);
        return selectOne(tutor);
    }

    public List<Tutor> getCompanyTutor(Long companyId) {
        if (companyId == null) {
            return null;
        }
        Tutor tutor = new Tutor();
        tutor.setCompanyId(companyId);
        return tutorMapper.select(tutor);
    }

    public void setLeader(Long tutorId, String operator) {
        Tutor tutor = new Tutor();
        tutor.setId(tutorId);
        tutor.setTutorType(TutorType.LEADER);
        update(tutor, operator);
    }

    public void setGeneral(Long tutorId, String operator) {
        Tutor tutor = new Tutor();
        tutor.setId(tutorId);
        tutor.setTutorType(TutorType.GENERAL);
        update(tutor, operator);
    }

    public void update(Tutor tutor, String operator) {
        if (tutor.getId() == null) {
            throw new InternshipException(TutorError.UPDATE_TUTOR_ID_EMPTY);
        }
        tutor.setUpdatedBy(operator);
        tutor.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = tutorMapper.update(tutor);
        if (count <= 0) {
            throw new InternshipException(TutorError.UPDATE_TUTOR_ERROR);
        }
    }

    public void delete(String destId) {
        if (StringUtil.isEmpty(destId)) {
            throw new InternshipException(TutorError.UPDATE_TUTOR_ID_EMPTY);
        }
        Tutor tutor = getTutorByDesId(destId);
        if (tutor == null) {
            throw new InternshipException(TutorError.TUTOR_NOT_EXIST);
        }
        if (tutor.getStudentNumber() != null && tutor.getStudentNumber().intValue() > 0) {
            throw new InternshipException(TutorError.DELETE_TUTOR_ERROR_BY_HAS_STUDENT);
        }
        tutorMapper.delete(tutor.getId());
        userService.delete(tutor.getUserId());
    }

    private Tutor selectOne(Tutor tutor) {
        List<Tutor> tutors = tutorMapper.select(tutor);
        if (CollectionUtil.isNotEmpty(tutors)) {
            return tutors.get(0);
        } else {
            return null;
        }
    }
}
