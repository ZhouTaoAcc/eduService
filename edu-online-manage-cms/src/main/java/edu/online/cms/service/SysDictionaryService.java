package edu.online.cms.service;

import edu.online.Entity.cms.SysDictionary;
import edu.online.Entity.cms.SysDictionaryValue;
import edu.online.Entity.cms.request.QueryDictionaryRequest;
import edu.online.Entity.cms.response.CmsCode;
import edu.online.cms.dao.SysDictionaryRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import edu.online.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Classname SysDictionaryService
 * @Description TODO
 * @Date 2020/2/21 16:08
 * @Created by zhoutao
 */
@Service
public class SysDictionaryService {
    @Autowired
    SysDictionaryRepository sysDictionaryRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    //分页模糊查询
    public QueryResponseResult findList(int pageNo, int pageSize, QueryDictionaryRequest queryDictionaryRequest) {
        Sort sort = Sort.by(Sort.Direction.ASC, "dType");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Query query = new Query();
        if (StringUtils.isNotEmpty(queryDictionaryRequest.getDName())) {
            Pattern pattern = Pattern.compile("^.*" + queryDictionaryRequest.getDName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("dName").regex(pattern));
        }
        if (StringUtils.isNotEmpty(queryDictionaryRequest.getDType())) {
            Pattern pattern = Pattern.compile("^.*" + queryDictionaryRequest.getDType() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("dType").regex(pattern));
        }
        long count = mongoTemplate.count(query, SysDictionary.class);
        List<SysDictionary> list = mongoTemplate.find(query.with(pageable), SysDictionary.class);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);
        queryResult.setTotal(count);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    //根据d_type值查询
    public SysDictionary findByType(String type) {
        return sysDictionaryRepository.findByDType(type);
    }

    //增加字典
    public ResponseResult addDictionary(QueryDictionaryRequest queryDictionaryRequest) {
        SysDictionary sd = findByType(queryDictionaryRequest.getDType());
        if(sd!=null){
            return new ResponseResult(CmsCode.SYS_DICTIONARY_EXISTS);
        }
        SysDictionary sysDictionary = new SysDictionary();
        SysDictionaryValue sysDictionaryValue = new SysDictionaryValue();
        sysDictionary.setDName(queryDictionaryRequest.getDName());
        sysDictionary.setDType(queryDictionaryRequest.getDType());
        sysDictionaryValue.setId(queryDictionaryRequest.getSdId());
        sysDictionaryValue.setSdName(queryDictionaryRequest.getSdName());
        sysDictionaryValue.setSdType(queryDictionaryRequest.getSdType());
        sysDictionaryValue.setSdStatus(queryDictionaryRequest.getSdStatus());
        List list = new ArrayList();
        list.add(sysDictionaryValue);
        sysDictionary.setChildren(list);
        mongoTemplate.save(sysDictionary);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //编辑字典
    public ResponseResult updateDictionary(String type, String id,QueryDictionaryRequest queryDictionaryRequest) {
        SysDictionary sd = findByType(type);
        if(sd==null){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
         List<SysDictionaryValue> children = sd.getChildren();
        for (SysDictionaryValue obj : children) {
            if (obj.getId().equals(id)) {
                obj.setSdName(queryDictionaryRequest.getSdName());
                obj.setSdStatus(queryDictionaryRequest.getSdStatus());
            }
        }
        mongoTemplate.save(sd);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    //根据type  childrenId设置状态
    public ResponseResult setStatusByType(String type, String id, String status) {
        SysDictionary sysDictionary = this.findByType(type);
        List<SysDictionaryValue> children = sysDictionary.getChildren();
        for (SysDictionaryValue obj : children) {
            if (obj.getId().equals(id)) {
                obj.setSdStatus(status);
            }
        }
        mongoTemplate.save(sysDictionary);
        return new ResponseResult(CommonCode.SUCCESS);
    }


    public ResponseResult deleteDictionary(String id){
        if(StringUtils.isEmpty(id)){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        sysDictionaryRepository.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
