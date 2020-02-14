package edu.online.cms.dao;

import edu.online.Entity.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsConfigRepository
 * @Description TODO
 * @Date 2020/2/14 14:23
 * @Created by zhoutao
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> {
}
