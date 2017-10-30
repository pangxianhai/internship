package cn.edu.swufe.lawschool.internship.file.mapper;

import cn.edu.swufe.lawschool.internship.file.model.FileRecord;

import java.util.List;

/**
 * Created on 2015年12月12
 * <p>Title:       文件mapper</p>
 * <p>Description: 文件mapper</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface FileRecordMapper {

    /**
     * Created on 2015年12月12
     * <p>Description:根据条件查询文件</p>
     * @author 庞先海
     */
    List<FileRecord> select(FileRecord fileRecord);

    /**
     * Created on 2015年12月12
     * <p>Description:插入文件</p>
     * @author 庞先海
     */
    int insert(FileRecord fileRecord);
}
