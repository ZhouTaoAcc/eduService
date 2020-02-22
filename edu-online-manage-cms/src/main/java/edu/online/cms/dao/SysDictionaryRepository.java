package edu.online.cms.dao;

import edu.online.Entity.cms.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname SysDictionaryRepository
 * @Description TODO
 * @Date 2020/2/21 16:07
 * @Created by zhoutao
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {
    public SysDictionary findByDType(String type); //支持findBy规范
 }
