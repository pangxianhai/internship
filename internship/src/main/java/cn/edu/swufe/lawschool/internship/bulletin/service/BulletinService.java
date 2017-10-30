package cn.edu.swufe.lawschool.internship.bulletin.service;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.bulletin.model.BulletinInfo;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2016年06月24
 * <p>Title:       公告服务</p>
 * <p>Description: 公告服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海
 * @version 1.0
 */
public interface BulletinService {

    /**
     * Created on 2016年06月24
     * <p>Description: 添加公告</p>
     * @param bulletinInfo 公告信息
     * @return 公告Id
     * @author 庞先海
     */
    Long addBulletinInfo(BulletinInfo bulletinInfo);

    /**
     * Created on 2016年06月24
     * <p>Description: 更新公告状态</p>
     * @param bulletinId 公告ID
     * @param status     状态
     * @param operator   操作者
     * @author 庞先海
     */
    int updateBulletinInfo(Long bulletinId, Status status, String operator);

    /**
     * Created on 2016年10月13
     * <p>Description: 删除公告</p>
     * @param bulletinId 公告ID
     * @author 庞先海
     */
    int deleteBulletinInfo(Long bulletinId);

    /**
     * Created on 2016年06月24
     * <p>Description: 查询公告</p>
     * @return 公告信息列表
     * @author 庞先海
     */
    List<BulletinInfo> getBulletinInfo(BulletinInfo bulletinInfo, Page page);

    /**
     * Created on 2016年06月24
     * <p>Description: 通过ID获取公告信息</p>
     * @param bulletinId 公告ID
     * @return
     */
    BulletinInfo getBulletinInfoById(Long bulletinId);

    /**
     * Created on 2016年06月24
     * <p>Description: 通过加密ID获取公告信息</p>
     * @param bulletinDestId 公告加密ID
     * @return
     */
    BulletinInfo getBulletinByDestId(String bulletinDestId);
}
