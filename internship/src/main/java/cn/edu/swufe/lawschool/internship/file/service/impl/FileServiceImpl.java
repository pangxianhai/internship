package cn.edu.swufe.lawschool.internship.file.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.file.mapper.FileRecordMapper;
import cn.edu.swufe.lawschool.internship.file.model.FileRecord;
import cn.edu.swufe.lawschool.internship.file.service.FileService;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.EncodeUtil;
import com.xavier.commons.util.encrypt.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2015年12月12
 * <p>Title:       文件服务</p>
 * <p>Description: 文件服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    @Autowired
    FileRecordMapper fileRecordMapper;

    public String saveFile (byte[] content, String suffix) {
        String fileName = HashUtil.md5Hash(content) + "." + suffix;
        if (getFileByName(fileName) != null) {
            return fileName;
        }
        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(fileName);
        fileRecord.setFileContent(EncodeUtil.base64Encode(content));
        fileRecord.setCreatedBy("sys_tem");
        fileRecord.setStatus(Status.VALID);
        fileRecordMapper.insert(fileRecord);
        return fileName;
    }

    public byte[] getFile (String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return null;
        }
        FileRecord fileRecord = getFileByName(fileName);
        return EncodeUtil.base64DecodeByte(fileRecord.getFileContent());
    }

    private FileRecord getFileByName (String fileName) {
        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(fileName);
        return selectOne(fileRecord);
    }

    private FileRecord selectOne (FileRecord fileRecord) {
        List<FileRecord> fileRecordList = fileRecordMapper.select(fileRecord);
        if (CollectionUtil.isNotEmpty(fileRecordList)) {
            return fileRecordList.get(0);
        }
        return null;
    }
}
