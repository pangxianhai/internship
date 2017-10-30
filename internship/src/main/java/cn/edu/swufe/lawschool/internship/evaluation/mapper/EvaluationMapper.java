package cn.edu.swufe.lawschool.internship.evaluation.mapper;

import cn.edu.swufe.lawschool.internship.evaluation.model.Evaluation;

import java.util.List;

/**
 * Created on 2015年11月29
 * <p>Title:       学生质量评价Mapper</p>
 * <p>Description: 学生质量评价Mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface EvaluationMapper {
    /**
     * Created on 2015年11月29
     * <p>Description: 根据条件查询质量评价</p>
     * @author 庞先海
     */
    List<Evaluation> select(Evaluation evaluation);

    /**
     * Created on 2015年11月29
     * <p>Description: 插入质量评价</p>
     * @author 庞先海
     */
    int insert(Evaluation evaluation);

    /**
     * Created on 2015年11月29
     * <p>Description: 更新质量评价</p>
     * @author 庞先海
     */
    int update(Evaluation evaluation);
}
