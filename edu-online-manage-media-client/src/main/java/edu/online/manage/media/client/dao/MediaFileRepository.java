package edu.online.manage.media.client.dao;

import edu.online.Entity.media.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname MediaFileRepository
 * @Description TODO
 * @Date 2020/3/17 15:12
 * @Created by zhoutao
 */
public interface MediaFileRepository extends MongoRepository<MediaFile,String>{

}
