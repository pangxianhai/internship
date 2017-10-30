package cn.edu.swufe.lawschool.internship.university.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.UniversityDepartmentError;
import cn.edu.swufe.lawschool.internship.university.mapper.UniversityDepartmentMapper;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartment;
import cn.edu.swufe.lawschool.internship.university.model.UniversityDepartmentType;
import cn.edu.swufe.lawschool.internship.university.service.UniversityDepartmentService;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.*;
import com.xavier.commons.util.encrypt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 2017年03月18
 * <p>Title:       学校部门服务</p>
 * <p>Description: 学校部门服务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Service("universityDepartmentService")
public class UniversityDepartmentServiceImpl implements UniversityDepartmentService {

    private static final long SUPER_PARENT_ID = -1L;

    @Autowired
    UniversityDepartmentMapper universityDepartmentMapper;

    public List<UniversityDepartment> getUniversityDepartment (UniversityDepartment department) {
        return universityDepartmentMapper.select(department);
    }

    public List<UniversityDepartment> getUniversityDepartmentByParentId (Long parentId) {
        UniversityDepartment universityDepartment = new UniversityDepartment();
        if (parentId != null) {
            universityDepartment.setParentId(parentId);
        } else {
            universityDepartment.setDepartmentType(UniversityDepartmentType.UNIVERSITY);
        }
        return universityDepartmentMapper.select(universityDepartment,
                                                 new Page(Order.formString("department_name.ASC")));
    }

    public List<UniversityDepartment> getUniversityDepartmentByParentDesId (String parentDesId) {
        if (StringUtil.isBlank(parentDesId)) {
            return getUniversityDepartmentByParentId(SUPER_PARENT_ID);
        }
        return getUniversityDepartmentByParentId(NumberUtil.parseLong(AESUtil.decrypt(parentDesId)));
    }

    public UniversityDepartment getUniversityDepartmentById (Long departmentId) {
        if (departmentId == null || departmentId.longValue() == SUPER_PARENT_ID) {
            return null;
        }
        UniversityDepartment universityDepartment = new UniversityDepartment();
        universityDepartment.setId(departmentId);
        return selectOne(universityDepartment);
    }

    public UniversityDepartment getUniversityDepartmentByDesId (String departmentDesId) {
        if (StringUtil.isBlank(departmentDesId)) {
            return null;
        }
        UniversityDepartment universityDepartment = new UniversityDepartment();
        universityDepartment.setDesId(departmentDesId);
        return selectOne(universityDepartment);
    }

    public boolean checkDepartmentOrganization (List<Long> departmentIdList) {
        if (departmentIdList != null) {
            departmentIdList = departmentIdList.stream().filter((Long id) -> {
                return id != null;
            }).collect(Collectors.toList());
        }
        if (CollectionUtil.isEmpty(departmentIdList) || departmentIdList.size() == 1) {
            return true;
        }
        for (int i = 0; i < departmentIdList.size(); ++i) {
            if (i == 0) {
                continue;
            } else {
                String currentId = String.valueOf(departmentIdList.get(i));
                String parentId = String.valueOf(departmentIdList.get(i - 1));
                if (!currentId.startsWith(parentId)) {
                    throw new InternshipException(UniversityDepartmentError.ORGANIZATION_ERROR);
                }
            }
        }
        return true;
    }

    public void addUniversityDepartment (UniversityDepartment universityDepartment, String operator) {
        if (universityDepartment.getIsUniversity()) {
            universityDepartment.setParentId(SUPER_PARENT_ID);
        } else {
            if (universityDepartment.getParentId().longValue() == SUPER_PARENT_ID) {
                throw new InternshipException(UniversityDepartmentError.ORGANIZATION_ERROR);
            }
            UniversityDepartment parentDepartment = this.getUniversityDepartmentById(
                    universityDepartment.getParentId());
            if (parentDepartment == null) {
                throw new InternshipException(UniversityDepartmentError.ADD_DEPARTMENT_PARENT_NOT_EXIST);
            }
            if (universityDepartment.getIsCollege() && !parentDepartment.getIsUniversity()) {
                throw new InternshipException(UniversityDepartmentError.ORGANIZATION_ERROR);
            }
            if (universityDepartment.getDepartmentType().getCode() <=
                    parentDepartment.getDepartmentType().getCode()) {
                throw new InternshipException(UniversityDepartmentError.ORGANIZATION_ERROR);
            }
        }
        universityDepartment.setId(buildUniversityDepartmentId(universityDepartment));
        universityDepartment.setStatus(Status.VALID);
        universityDepartment.setCreatedBy(operator);
        universityDepartment.setCreatedTime(DateUtil.currentMilliseconds());
        int count = universityDepartmentMapper.insert(universityDepartment);
        if (count < 1) {
            throw new InternshipException(UniversityDepartmentError.ADD_DEPARTMENT_FAILED);
        }
    }

    public void updateUniversityDepartment (UniversityDepartment universityDepartment, String operator) {
        if (universityDepartment.getId() == null) {
            throw new InternshipException(UniversityDepartmentError.UPDATE_DEPARTMENT_ID_EMPTY);
        }
        universityDepartment.setUpdatedBy(operator);
        universityDepartment.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = universityDepartmentMapper.update(universityDepartment);
        if (count < 1) {
            throw new InternshipException(UniversityDepartmentError.UPDATE_DEPARTMENT_FAILED);
        }
    }

    /**
     * id的规则为  学校id(5位)学院id(3位)系ID(3位)专业id(3位) 如一个专业的ID为:10000100100100
     * 学校id只有前5位 如:10000  新加的学校 10001 10002 ...
     * 学院id只有前8位 如:10000100 新加的学院 10000101 10000102 10000103 .... 前5位对应学校ID不变(不同的学校的学院前5位学校ID不一样)
     * 系id为前11位 如: 10000100100  规则类推
     * 只有专业ID是全部的11位  规则类推
     * @return
     */
    private Long buildUniversityDepartmentId (UniversityDepartment universityDepartment) {
        UniversityDepartment departmentParam = new UniversityDepartment();
        departmentParam.setParentId(universityDepartment.getParentId());
        Page page = new Page(1, 1, Order.formString("id.DESC"));
        List<UniversityDepartment> departmentList = universityDepartmentMapper.select(departmentParam, page);
        if (CollectionUtil.isNotEmpty(departmentList)) {
            return departmentList.get(0).getId() + 1;
        } else {
            if (universityDepartment.getIsUniversity()) {
                return 10000L;
            } else {
                return NumberUtil.parseLong(universityDepartment.getParentId() + "100");
            }
        }
    }

    private UniversityDepartment selectOne (UniversityDepartment universityDepartment) {
        List<UniversityDepartment> departmentList = universityDepartmentMapper.select(universityDepartment);
        if (CollectionUtil.isNotEmpty(departmentList)) {
            return departmentList.get(0);
        } else {
            return null;
        }
    }
}
