package edu.online.cms.dao;

import edu.online.Entity.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsPageRepository
 * @Description TODO
 * @Date 2019/12/8 18:23
 * @Created by zhoutao
 */
public interface  CmsPageRepository extends MongoRepository<CmsPage, String> {
    //根据页面名称、站点id、页面访问路径查询
    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName, String siteId, String pageWebPath);
}
