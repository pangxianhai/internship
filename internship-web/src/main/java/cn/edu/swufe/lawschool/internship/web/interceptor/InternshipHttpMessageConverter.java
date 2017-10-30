package cn.edu.swufe.lawschool.internship.web.interceptor;

import cn.edu.swufe.lawschool.internship.web.context.ServletContext;
import cn.edu.swufe.lawschool.internship.web.model.Result;
import cn.edu.swufe.lawschool.internship.web.util.ServletUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2015年09月25
 * <p>Title:       ResponseBody结果转换</p>
 * <p>Description: .json后缀的组装标准化</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class InternshipHttpMessageConverter implements HttpMessageConverter {

    private List<String> mediaTypes;

    public boolean canRead(Class clazz, MediaType mediaType) {
        return false;
    }

    public boolean canWrite(Class clazz, MediaType mediaType) {
        return true;
    }

    public List<MediaType> getSupportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        if (mediaTypes != null) {
            for (String mediaType : mediaTypes) {
                supportedMediaTypes.add(MediaType.valueOf(mediaType));
            }
        }
        supportedMediaTypes.add(MediaType.ALL);
        return supportedMediaTypes;
    }

    public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    public void write(Object object, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        Object result;
        if (!ServletUtil.isUploadImageLink(ServletContext.getRequest()) && ServletUtil.isJsonLink(ServletContext.getRequest())) {
            result = new Result(true, object);
        } else {
            result = object;
        }
        String text = ServletUtil.buildPrintJsonData(ServletContext.getRequest(), result);
        byte[] bytes = text.getBytes("UTF-8");
        out.write(bytes);
    }

    public List<String> getMediaTypes() {
        return mediaTypes;
    }

    public void setMediaTypes(List<String> mediaTypes) {
        this.mediaTypes = mediaTypes;
    }
}
