package edu.online.fileServer.dao;

import edu.online.Entity.fileserver.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Classname FileServerRepository
 * @Description TODO
 * @Date 2020/2/27 19:44
 * @Created by zhoutao
 */
public interface FileServerRepository extends MongoRepository<FileSystem,String> {
}
