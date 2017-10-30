package cn.edu.swufe.lawschool.internship.bulletin.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.bulletin.mapper.BulletinMapper;
import cn.edu.swufe.lawschool.internship.bulletin.model.BulletinInfo;
import cn.edu.swufe.lawschool.internship.bulletin.service.BulletinService;
import cn.edu.swufe.lawschool.internship.exception.BulletinError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created on 2016年06月24
 * <p>Title:       公告服务实现</p>
 * <p>Description: 公告服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("bulletinService")
public class BulletinServiceImpl implements BulletinService {

    @Autowired
    private BulletinMapper bulletinMapper;

    public Long addBulletinInfo(BulletinInfo bulletinInfo) {
        bulletinInfo.setUpdatedBy(bulletinInfo.getCreatedBy());
        bulletinInfo.setUpdatedDate(new Date());
        bulletinInfo.setCreatedDate(new Date());
        int count = bulletinMapper.insert(bulletinInfo);
        if (count < 1) {
            throw new InternshipException(BulletinError.ADD_BULLETIN_ERROR);
        }
        return bulletinInfo.getId();
    }

    public int updateBulletinInfo(Long bulletinId, Status status, String operator) {
        BulletinInfo bulletinInfo = new BulletinInfo();
        bulletinInfo.setId(bulletinId);
        bulletinInfo.setStatus(status);
        bulletinInfo.setUpdatedBy(operator);
        bulletinInfo.setUpdatedDate(new Date());
        return update(bulletinInfo);
    }

    public int deleteBulletinInfo(Long bulletinId) {
        return bulletinMapper.delete(bulletinId);
    }

    public List<BulletinInfo> getBulletinInfo(BulletinInfo bulletinInfo, Page page) {
        return bulletinMapper.select(bulletinInfo, page);
    }

    public BulletinInfo getBulletinInfoById(Long bulletinId) {
        if (bulletinId == null) {
            throw new InternshipException(BulletinError.BULLETIN_ID_EMPTY);
        }
        BulletinInfo bulletinInfo = new BulletinInfo();
        bulletinInfo.setId(bulletinId);
        return selectOne(bulletinInfo);
    }

    public BulletinInfo getBulletinByDestId(String bulletinDestId) {
        if (StringUtil.isEmpty(bulletinDestId)) {
            throw new InternshipException(BulletinError.BULLETIN_ID_EMPTY);
        }
        BulletinInfo bulletinInfo = new BulletinInfo();
        bulletinInfo.setDesId(bulletinDestId);
        return selectOne(bulletinInfo);
    }

    private BulletinInfo selectOne(BulletinInfo bulletinInfo) {
        List<BulletinInfo> bulletinInfoList = bulletinMapper.select(bulletinInfo);
        if (CollectionUtil.isNotEmpty(bulletinInfoList)) {
            return bulletinInfoList.get(0);
        }
        return null;
    }

    private int update(BulletinInfo bulletinInfo) {
        if (bulletinInfo.getId() == null) {
            throw new InternshipException(BulletinError.UPDATE_BULLETIN_ID_EMPTY);
        }
        int count = bulletinMapper.update(bulletinInfo);
        if (count < 1) {
            throw new InternshipException(BulletinError.UPDATE_BULLETIN_ERROR);
        }
        return count;
    }
}
