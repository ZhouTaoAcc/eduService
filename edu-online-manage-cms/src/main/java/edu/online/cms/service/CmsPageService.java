package edu.online.cms.service;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.request.QueryPageRequest;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.cms.dao.CmsPageRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
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
import java.util.regex.Pattern;

/**
 * @Classname CmsPageService
 * @Description 增删改查  TODO 参考https://www.cnblogs.com/wslook/p/9275861.html
 * @Date 2019/12/8 18:31
 * @Created by zhoutao
 */
@Service
public class CmsPageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    /*1、分页查询业务逻辑*/
    public QueryResponseResult findList(int pageNo, int pageSize, QueryPageRequest queryPageRequest) {
/*===方法1、ExampleMatcher匹配器====*/
        /*if (queryPageRequest == null) {
            queryPageRequest = new QueryPageRequest();
        }
        //自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true) //改变默认大小写忽略方式：忽略大小写
                .withIgnorePaths("pageNum", "pageSize") //忽略属性，不参与查询
                .withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值对象
        CmsPage cmsPage = new CmsPage();
        BeanUtils.copyProperties(queryPageRequest,cmsPage);
        //设置条件值（站点id）
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        //设置模板id作为查询条件
        if (StringUtils.isNotEmpty(queryPageRequest.getTemplateId())) {
            cmsPage.setTemplateId(queryPageRequest.getTemplateId());
        }
        //设置页面别名作为查询条件
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }
        //定义条件对象Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
        //分页参数
        if (pageNo <= 0) {
            pageNo = 1;
        }
        pageNo = pageNo - 1;
        if (pageSize <= 0) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CmsPage> all = cmsPageRepository.findAll(example, pageable);//实现自定义条件查询并且分页查询
        System.out.println(all);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(all.getContent());//数据列表
        queryResult.setTotal(all.getTotalElements());//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;*/


 /*==方法2、MongoTemplate结合Query 动态查询===*/
        Sort sort = Sort.by(Sort.Direction.DESC, "pageCreateTime");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Query query = new Query();
        /*动态拼接查询条件*/
            //页面名称
        if (!StringUtils.isEmpty(queryPageRequest.getPageName())){
            Pattern pattern = Pattern.compile("^" + queryPageRequest.getPageName() + "$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("pageName").regex(pattern));
        }
        //站点ID
        if (!StringUtils.isEmpty(queryPageRequest.getSiteId())){
            Pattern pattern = Pattern.compile("^" + queryPageRequest.getSiteId() + "$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("siteId").regex(pattern));
        }
        //页面别称
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())){
            Pattern pattern = Pattern.compile("^.*" + queryPageRequest.getPageAliase() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("pageAliase").regex(pattern));
        }

        //计算总数
        long total = mongoTemplate.count(query, CmsPage.class);

        //查询结果集
        List<CmsPage> list = mongoTemplate.find(query.with(pageable), CmsPage.class);
        QueryResult queryResult = new QueryResult();
        queryResult.setList(list);//数据列表
        queryResult.setTotal(total);//数据总记录数
        QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS, queryResult);
        return queryResponseResult;
    }

    //2、添加页面业务逻辑*/
    public CmsResponseResult addPage(CmsPage cmsPage){

        return null;
    }

}
