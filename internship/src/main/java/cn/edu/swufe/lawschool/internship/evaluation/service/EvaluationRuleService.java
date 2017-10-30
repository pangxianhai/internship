package cn.edu.swufe.lawschool.internship.evaluation.service;

import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationRule;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       学生质量评价规则服务</p>
 * <p>Description: 学生质量评价规则服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface EvaluationRuleService {

    /**
     * Created on 2015年11月29
     * <p>Description: 获取所有学生质量评测标准规则</p>
     * @return
     */
    List<EvaluationRule> getEvaluationRule();
}
