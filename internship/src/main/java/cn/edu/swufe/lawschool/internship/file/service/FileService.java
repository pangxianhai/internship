package cn.edu.swufe.lawschool.internship.file.service;

/**
 * Created on 2015年12月12
 * <p>Title:       文件服务</p>
 * <p>Description: 文件服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface FileService {

    /**
     * Created on 2015年12月12
     * <p>Description: 保存文件</p>
     * @param content 文件内容
     * @param suffix  后缀
     * @return
     */
    String saveFile(byte[] content, String suffix);

    /**
     * Created on 2015年12月12
     * <p>Description: 读取文件</p>
     * @return
     */
    byte[] getFile(String fileName);
}
