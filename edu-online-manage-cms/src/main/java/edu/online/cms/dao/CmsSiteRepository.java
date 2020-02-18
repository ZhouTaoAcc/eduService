package edu.online.cms.dao;

import edu.online.Entity.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsPageRepository
 * @Description TODO
 * @Date 2019/12/8 18:23
 * @Created by zhoutao
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
    //站点的唯一索引
    public CmsSite findBySiteDomainAndSitePort(String a,String b);
}
