package edu.online.cms.dao;

import edu.online.Entity.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname CmsTemplateRepository
 * @Description TODO
 * @Date 2020/2/14 16:36
 * @Created by zhoutao
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate, String> {
    //根据站点id 模板名称 模板文件ID 唯一索引
    CmsTemplate findByTemplateFileIdAndTemplateNameAndSiteId(String a,String b,String c);
}
