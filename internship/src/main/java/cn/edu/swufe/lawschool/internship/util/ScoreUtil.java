package cn.edu.swufe.lawschool.internship.util;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import com.xavier.commons.util.StringUtil;

import java.text.NumberFormat;

/**
 * Created on 2015年12月01
 * <p>Title:       分数工具</p>
 * <p>Description: 分数工具</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ScoreUtil {

    /**
     * Created on 2015年12月01
     * <p>Description: 将用户输入的分数转换为数据库存放的值</p>
     * @author 庞先海
     */
    public static Integer textToScore(String text) {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        try {
            Float score = Float.parseFloat(text);
            return (int) (score * 100);
        } catch (Exception e) {
            throw new InternshipException(ErrorType.SCORE_TYPE_ERROR);
        }
    }

    /**
     * Created on 2015年12月01
     * <p>Description: 数据库分数格式成显示状态</p>
     * @author 庞先海
     */
    public static String scoreToText(Integer score) {
        if (score == null) {
            return null;
        }
        return NumberFormat.getInstance().format(score / 100.0);
    }
}
