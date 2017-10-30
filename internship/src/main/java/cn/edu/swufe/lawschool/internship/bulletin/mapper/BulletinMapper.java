package cn.edu.swufe.lawschool.internship.bulletin.mapper;

import cn.edu.swufe.lawschool.internship.bulletin.model.BulletinInfo;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2016年06月23
 * <p>Title:       公告Mapper</p>
 * <p>Description: </p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface BulletinMapper {

    /**
     * Created on 2016年06月23
     * <p>Description:根据条件查询公告信息</p>
     * @author 庞先海
     */
    List<BulletinInfo> select(BulletinInfo bulletinInfo);

    /**
     * Created on 2016年06月23
     * <p>Description:分页查询公告信息</p>
     * @author 庞先海
     */
    List<BulletinInfo> select(BulletinInfo bulletinInfo, Page page);

    /**
     * Created on 2016年06月23
     * <p>Description:插入公告信息</p>
     * @author 庞先海
     */
    int insert(BulletinInfo bulletinInfo);

    /**
     * Created on 2016年06月23
     * <p>Description:更新公告信息</p>
     * @author 庞先海
     */
    int update(BulletinInfo bulletinInfo);

    /**
     * Created on 2016年10月13
     * <p>Description:删除公告</p>
     * @author 庞先海
     */
    int delete(Long bulletinId);
}
