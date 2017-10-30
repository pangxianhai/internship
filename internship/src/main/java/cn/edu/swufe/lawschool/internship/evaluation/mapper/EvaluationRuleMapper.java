package cn.edu.swufe.lawschool.internship.evaluation.mapper;

import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationRule;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       评价规则Mapper</p>
 * <p>Description: 评价规则Mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface EvaluationRuleMapper {
    /**
     * Created on 2015年11月29
     * <p>Description: 根据条件查询评价规则</p>
     * @author 庞先海
     */
    List<EvaluationRule> select(EvaluationRule evaluationRule, Page page);
}
