package cn.edu.swufe.lawschool.internship.server.interceptor;

import cn.edu.swufe.lawschool.common.logger.Logger;
import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.web.model.Result;
import com.xavier.rop.context.RopContext;
import com.xavier.rop.exception.ErrorCode;
import com.xavier.rop.interceptor.RopExceptionResolver;
import com.xavier.rop.response.ErrorResponse;
import org.apache.commons.logging.Log;

import java.text.MessageFormat;

/**
 * Created on 2016年10月12
 * <p>Title:       异常处理器</p>
 * <p>Description: 异常处理器</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipRopExceptionResolver implements RopExceptionResolver {

    @Logger
    Log log;


    public boolean resolveException(RopContext ropContext, Exception e) {
        if (e instanceof InternshipException) {
            if (((InternshipException) e).getCode().equals(ErrorType.SYS_ERROR.getCode())) {
                String message = MessageFormat.format("service:{0}({1}) failed", ropContext.getMethod(), ropContext.getVersion());
                log.error(message, e);
            }
            InternshipException ee = (InternshipException) e;
            ropContext.setRopResponse(new Result(false, ee.getCode(), ee.getDesc()));
            ropContext.writeResponse(ropContext.getRopResponse());
            return true;
        }
        ErrorResponse errorResponse = ropContext.getErrorResponse();
        if (errorResponse != null) {
            if (errorResponse.getCode().equals(ErrorCode.SERVICE_ERROR.getCode())) {
                String message = MessageFormat.format("service:{0}({1}) failed", ropContext.getMethod(), ropContext.getVersion());
                log.error(message, e);
            }
            ropContext.setRopResponse(new Result(false, errorResponse.getCode().intValue(), errorResponse.getMessage()));
            ropContext.writeResponse(ropContext.getRopResponse());
            return true;
        }

        return false;
    }
}
