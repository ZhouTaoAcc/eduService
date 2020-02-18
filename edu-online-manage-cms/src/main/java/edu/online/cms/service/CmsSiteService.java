package edu.online.cms.service;

import edu.online.Entity.cms.CmsSite;
import edu.online.Entity.cms.request.QuerySiteRequest;
import edu.online.Entity.cms.response.CmsCode;
import edu.online.cms.dao.CmsSiteRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Classname CmsTemplateService
 * @Description TODO
 * @Date 2020/2/18 16:37
 * @Created by zhoutao
 */
@Service
public class CmsSiteService {
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public QueryResponseResult findList(int pageNo, int pageSize, QuerySiteRequest querySiteRequest) {
        //分页 排序
        Sort sort = Sort.by(Sort.Direction.DESC, "siteCreateTime");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        //条件查询
        Query query = new Query();
        //站点路径
        if (StringUtils.isNotEmpty(querySiteRequest.getSiteWebPath())) {
            Pattern pattern = Pattern.compile("^.*" + querySiteRequest.getSiteWebPath() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("siteWebPath").regex(pattern));
        }
        //站点名称
        if (StringUtils.isNotEmpty(querySiteRequest.getSiteName())) {
            Pattern pattern = Pattern.compile("^.*" + querySiteRequest.getSiteName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("siteName").regex(pattern));
        }
        //站点域名
        if (StringUtils.isNotEmpty(querySiteRequest.getSiteDomain())) {
            Pattern pattern = Pattern.compile("^.*" + querySiteRequest.getSiteDomain() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("siteDomain").regex(pattern));
        }
        long count = mongoTemplate.count(query, CmsSite.class);
        final List<CmsSite> cmsSites = mongoTemplate.find(query.with(pageable), CmsSite.class);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(cmsSites);
        queryResult.setTotal(count);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }

    public ResponseResult addSite(CmsSite cmsSite) {
        CmsSite site = cmsSiteRepository.findBySiteDomainAndSitePort(cmsSite.getSiteDomain(), cmsSite.getSitePort());
        if (site == null) {
            cmsSiteRepository.save(cmsSite);
            return new ResponseResult(CommonCode.SUCCESS);
        }else if(site != null){
            return new ResponseResult(CmsCode.CMS_Site_EXISTS);
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);//其他错误
    }
    public ResponseResult updateSite(String id, CmsSite cmsSite) {
        if (id != null) {//此时传递来的参数存在主键 说明是编辑
            ResponseResult byId = findById(id);
            if (byId.getCode() != 24014) {//表示站点存在 可以编辑
                cmsSite.setSiteId(id);
                CmsSite save = cmsSiteRepository.save(cmsSite);
                if (save != null) {
                    return new ResponseResult(CommonCode.SUCCESS);
                }
            }
        } else {
            return new ResponseResult(CommonCode.INVALID_PARAM);//非法参数
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);//其他错误
    }

    //4.删除站点
    public ResponseResult deleteSite(String id) {
        ResponseResult byId = this.findById(id);
        if (byId.getCode() != 24015) {//存在  24014表示站点存在
            cmsSiteRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //5.根据Id查询站点
    public ResponseResult findById(String id) {
        //先判断站点是否存在
        Optional<CmsSite> byId = cmsSiteRepository.findById(id);
        if (byId.isPresent()) {//java8 特性 判断当前对象是否为空
            return new ResponseResult(CommonCode.SUCCESS);
        } else if (!byId.isPresent()) {//否则返回站点不存在
            return new ResponseResult(CmsCode.CMS_Site_NotEXISTS);
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

}
