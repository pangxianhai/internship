package cn.edu.swufe.lawschool.internship.file.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;

/**
 * Created on 2015年12月12
 * <p>Title:       文件</p>
 * <p>Description: 文件</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class FileRecord extends BaseDO {
    String fileName;
    String fileContent;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
