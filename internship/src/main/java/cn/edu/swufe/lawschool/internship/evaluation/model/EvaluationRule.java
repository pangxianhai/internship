package cn.edu.swufe.lawschool.internship.evaluation.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONPOJOBuilder;
import com.alibaba.fastjson.annotation.JSONType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2015年11月29
 * <p>Title:       学生质量评价规则</p>
 * <p>Description: 学生质量评价规则实体</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class EvaluationRule extends BaseDO {
    /**
     * 项目
     */
    String subject;

    /**
     * 评价准则
     */
    String evaluationRule;

    public String getSubject () {
        return subject;
    }

    public void setSubject (String subject) {
        this.subject = subject;
    }

    public String getEvaluationRule () {
        return evaluationRule;
    }

    public void setEvaluationRule (String evaluationRule) {
        this.evaluationRule = evaluationRule;
    }
}

