package edu.online.cms.service;

import edu.online.Entity.cms.CmsConfig;
import edu.online.cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Classname CmsConfigService
 * @Description TODO
 * @Date 2020/2/14 14:24
 * @Created by zhoutao
 */
@Service
public class CmsConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;
        public CmsConfig findById(String id){
            Optional<CmsConfig> byId = cmsConfigRepository.findById(id);
            if(byId.isPresent()){
                return  byId.get();
            }
            return null;
        }
}
