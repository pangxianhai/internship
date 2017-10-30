package cn.edu.swufe.lawschool.internship.evaluation.mapper;

import cn.edu.swufe.lawschool.internship.evaluation.model.EvaluationResult;

import java.util.List;

/**
 * Created on 2017年02月17
 * <p>Title:       学生质量评价结果</p>
 * <p>Description: 学生质量评价结果Mapper</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface EvaluationResultMapper {
    /**
     * Created on 2017年02月17
     * <p>Description: 根据条件查询质量评价结果</p>
     * @author 庞先海
     */
    List<EvaluationResult> select (EvaluationResult evaluationResult);

    /**
     * Created on 2017年02月17
     * <p>Description: 插入质量评价结果</p>
     * @author 庞先海
     */
    int insert (EvaluationResult evaluationResult);

    /**
     * Created on 2017年02月17
     * <p>Description: 更新质量评价结果</p>
     * @author 庞先海
     */
    int update (EvaluationResult evaluationResult);
}
