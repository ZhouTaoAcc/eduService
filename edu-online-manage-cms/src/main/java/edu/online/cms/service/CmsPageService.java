package edu.online.cms.service;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.request.QueryPageRequest;
import edu.online.Entity.cms.response.CmsCode;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.cms.dao.CmsPageRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import edu.online.utils.DateUtil;
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

import java.util.List;
import java.util.Optional;
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
    private static Logger logger = Logger.getLogger(CmsPageService.class); // 打印当前类日志

    /*1、分页查询业务逻辑*/
    public QueryResponseResult findList(int pageNo, int pageSize, String d, String d2, QueryPageRequest queryPageRequest) {
        /*===方法1、ExampleMatcher匹配器====*/
        /*1.ExampleMatcher exampleMatcher
        2.PageRequest.of(pageNo, pageSize)
        * 3. Example.of(cmsPage,exampleMatcher)
        * 4.cmsPageRepository.findAll(example, pageable)
        * */

        /*==方法2、MongoTemplate结合Query 动态查询===*/
        Sort sort = Sort.by(Sort.Direction.DESC, "pageCreateTime");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Query query = new Query();
        /*动态拼接查询条件*/
        //页面名称--模糊查询
        if (!StringUtils.isEmpty(queryPageRequest.getPageName())) {
            Pattern pattern = Pattern.compile("^.*" + queryPageRequest.getPageName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("pageName").regex(pattern));
        }
        //站点ID --精确查询
        if (!StringUtils.isEmpty(queryPageRequest.getSiteId())) {
            Pattern pattern = Pattern.compile("^" + queryPageRequest.getSiteId() + "$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("siteId").regex(pattern));
        }
        //页面别称--模糊查询
        if (!StringUtils.isEmpty(queryPageRequest.getPageAliase())) {
            Pattern pattern = Pattern.compile("^.*" + queryPageRequest.getPageAliase() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("pageAliase").regex(pattern));
        }
        if ((!StringUtils.isEmpty(d) && !StringUtils.isEmpty(d2))) {
            query.addCriteria(Criteria.where("pageCreateTime")
                    .gte(DateUtil.dateStrToISODate(d))//时间转换 CST->UTC
                    .lte(DateUtil.dateStrToISODate(d2)));
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
    public CmsResponseResult addPage(CmsPage cmsPage) {
        //先根据页面唯一索引判断页面是否存在
        CmsPage isCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPagePhysicalPath());
        if (isCmsPage == null) {
            cmsPageRepository.save(cmsPage);
            return new CmsResponseResult(CommonCode.SUCCESS, cmsPage);//新增成功
        } else {
            return new CmsResponseResult(CmsCode.CMS_ADDPAGE_EXISTSNAME, null);//页面以及存在
        }
    }

    //3.编辑页面
    public CmsResponseResult updatePage(String id, CmsPage cmsPage) {
        if (id != null) {//此时传递来的参数存在主键 说明是编辑
            CmsResponseResult byId = findById(id);
            if (byId.getCode() != 24000) {//表示页面存在
                cmsPage.setPageId(id);
                CmsPage save = cmsPageRepository.save(cmsPage);
                if (save != null) {
                    return new CmsResponseResult(CommonCode.SUCCESS, cmsPage);
                }
            }
        }
        return new CmsResponseResult(CommonCode.FAIL, null);
    }

    //4.删除页面
    public CmsResponseResult deletePage(String id) {
        CmsResponseResult byId = this.findById(id);
        if (byId.getCode() != 24000) {//自定义24000表示页面存在
            cmsPageRepository.deleteById(id);
            return new CmsResponseResult(CommonCode.SUCCESS, null);
        }
        return new CmsResponseResult(CommonCode.FAIL, null);
    }

    //5.根据Id查询页面
    public CmsResponseResult findById(String id) {
        //先判断页面是否存在
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()) {//java8 特性 判断当前对象是否为空
            return new CmsResponseResult(CommonCode.SUCCESS, byId.get());
        }//否则返回页面不存在
        return new CmsResponseResult(CmsCode.CMS_FINDPAGE_NotEXISTSNAME, null);
    }
}
