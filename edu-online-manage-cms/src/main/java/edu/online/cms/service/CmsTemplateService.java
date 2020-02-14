package edu.online.cms.service;

import edu.online.Entity.cms.CmsTemplate;
import edu.online.Entity.cms.request.QueryTemplateRequest;
import edu.online.Entity.cms.response.CmsCode;
import edu.online.Entity.cms.response.CmsTemplateResponseResult;
import edu.online.cms.dao.CmsTemplateRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Classname CmsTemplateService
 * @Description TODO
 * @Date 2020/2/14 16:37
 * @Created by zhoutao
 */
@Service
public class CmsTemplateService {
    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private static Logger logger = Logger.getLogger(CmsPageService.class); // 打印当前类日志

    List<CmsTemplate> list=new ArrayList<>();

    /*1、分页查询业务逻辑*/
    public QueryResponseResult findList(int pageNo, int pageSize, QueryTemplateRequest queryTemplateRequest) {
        Sort sort = Sort.by(Sort.Direction.DESC, "templateName");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Query query = new Query();
        /*动态拼接查询条件*/
        if (!StringUtils.isEmpty(queryTemplateRequest.getSiteId())) {
            Pattern pattern = Pattern.compile("^" + queryTemplateRequest.getSiteId() + "$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("siteId").regex(pattern));
        }
        if (!StringUtils.isEmpty(queryTemplateRequest.getTemplateFileId())) {
            Pattern pattern = Pattern.compile("^.*" + queryTemplateRequest.getTemplateFileId() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("templateFileId").regex(pattern));
        }
        if (!StringUtils.isEmpty(queryTemplateRequest.getTemplateName())) {
            Pattern pattern = Pattern.compile("^.*" + queryTemplateRequest.getTemplateName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("templateName").regex(pattern));
        }
        //计算总数
        long total = mongoTemplate.count(query, CmsTemplate.class);

        //查询结果集
        List<CmsTemplate> list = mongoTemplate.find(query.with(pageable), CmsTemplate.class);
        QueryResult<CmsTemplate> queryResult = new QueryResult();
        queryResult.setList(list);//数据列表
        queryResult.setTotal(total);//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    //2、添加模板业务逻辑*/
    public CmsTemplateResponseResult addTemplate(CmsTemplate cmsTemplate) {
        //先根据模板唯一索引判断模板是否存在
        CmsTemplate isNull = cmsTemplateRepository.findByTemplateFileIdAndTemplateNameAndSiteId(cmsTemplate.getTemplateFileId(), cmsTemplate.getTemplateName(), cmsTemplate.getSiteId());
        if (isNull == null) {
            cmsTemplateRepository.save(cmsTemplate);
            return new CmsTemplateResponseResult(CommonCode.SUCCESS, cmsTemplate);//新增成功
        } else if (isNull != null) {
            return new CmsTemplateResponseResult(CmsCode.CMS_GENERATEHTML_TEMPLATEI_EXIST, null);//模板已经存在
        }
        return new CmsTemplateResponseResult(CommonCode.SERVER_ERROR, null);//其他错误
    }

    //3.编辑模板
    public CmsTemplateResponseResult updateTemplate(String id, CmsTemplate cmsTemplate) {
        if (id != null) {//此时传递来的参数存在主键 说明是编辑
            CmsTemplateResponseResult byId = findById(id);
            if (byId.getCode() != 24008) {//表示模板存在
                cmsTemplate.setTemplateId(id);
                CmsTemplate save = cmsTemplateRepository.save(cmsTemplate);
                if (save != null) {
                    return new CmsTemplateResponseResult(CommonCode.SUCCESS, cmsTemplate);
                }
            }
        }else{
            return new CmsTemplateResponseResult(CommonCode.INVALID_PARAM, null);//非法参数
        }
        return new CmsTemplateResponseResult(CommonCode.SERVER_ERROR, null);//其他错误
    }

    //4.删除模板
    public CmsTemplateResponseResult deleteTemplate(String id) {
        CmsTemplateResponseResult byId = this.findById(id);
        if (byId.getCode() != 24000) {//存在  自定义24008表示模板不存在
            cmsTemplateRepository.deleteById(id);
            return new CmsTemplateResponseResult(CommonCode.SUCCESS, null);
        }
        return new CmsTemplateResponseResult(CommonCode.FAIL, null);
    }

    //5.根据Id查询模板
    public CmsTemplateResponseResult findById(String id) {
        //先判断模板是否存在
        Optional<CmsTemplate> byId = cmsTemplateRepository.findById(id);
        if (byId.isPresent()) {//java8 特性 判断当前对象是否为空
            return new CmsTemplateResponseResult(CommonCode.SUCCESS, byId.get());
        }else if(!byId.isPresent()){//否则返回模板不存在
            return new CmsTemplateResponseResult(CmsCode.CMS_FINDPAGE_NotEXISTSNAME, null);
        }
        return new CmsTemplateResponseResult(CommonCode.SERVER_ERROR, null);
    }
}
