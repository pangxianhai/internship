package cn.edu.swufe.lawschool.internship.server.interceptor;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.server.annotation.RopServiceUserAccess;
import cn.edu.swufe.lawschool.internship.server.service.RopLoginService;
import cn.edu.swufe.lawschool.internship.server.session.InternshipRopContext;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.model.Result;
import com.xavier.commons.util.annotation.AnnotationUtil;
import com.xavier.rop.exception.ErrorCode;
import com.xavier.rop.exception.ErrorException;
import com.xavier.rop.interceptor.RopInterceptor;
import com.xavier.rop.request.RopRequest;
import com.xavier.rop.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * Created on 2016年10月12
 * <p>Title:       rop 拦截器</p>
 * <p>Description: rop 拦截器</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipRopInterceptor implements RopInterceptor {

    @Autowired
    RopLoginService ropLoginService;

    public boolean beforeService(RopRequest request) {
        InternshipRopContext.init(request);
        Method method = request.getContext().getServiceMethodHandler().getHandlerMethod();
        RopServiceUserAccess ropServiceUserAccess = AnnotationUtil.findAnnotation(method, RopServiceUserAccess.class);
        if (ropServiceUserAccess != null) {
            UserInfo userInfo = ropLoginService.getLoginUserInfo();
            if (userInfo == null) {
                throw new ErrorException(ErrorCode.NEED_SESSION);
            }
            int[] types = ropServiceUserAccess.value();
            //只检测是否需要登陆
            if (types.length == 1 && types[0] == UserType.DEFAULT) {
                return true;
            }
            //针对具体用户类型
            for (int t : types) {
                if (t == userInfo.getUserType().getCode().intValue()) {
                    return true;
                }
            }
            throw new InternshipException(ErrorType.NO_ACCESS);

        }
        return true;
    }

    public boolean afterService(RopRequest request) {
        Object res = request.getContext().getRopResponse();
        Result result;
        if (res instanceof ErrorResponse) {
            ErrorResponse errorResponse = (ErrorResponse) res;
            result = new Result(false, errorResponse.getCode().intValue(), errorResponse.getMessage());
        } else {
            result = new Result(true, res);
        }
        request.getContext().setRopResponse(result);
        InternshipRopContext.clean();
        return true;
    }
}
