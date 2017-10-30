package cn.edu.swufe.lawschool.internship.evaluation.service.impl;

import cn.edu.swufe.lawschool.internship.evaluation.mapper.EvaluationRuleMapper;
import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationRule;
import cn.edu.swufe.lawschool.internship.evaluation.service.EvaluationRuleService;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       学生质量评价规则服务</p>
 * <p>Description: 学生质量评价规则服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("evaluationRuleService")
public class EvaluationRuleServiceImpl implements EvaluationRuleService {

    @Autowired
    EvaluationRuleMapper evaluationRuleMapper;

    public List<EvaluationRule> getEvaluationRule() {
        return evaluationRuleMapper.select(new EvaluationRule(), new Page(Order.formString("id.ASC")));
    }
}
