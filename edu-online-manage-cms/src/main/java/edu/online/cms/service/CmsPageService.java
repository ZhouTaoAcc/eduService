package edu.online.cms.service;

import com.alibaba.fastjson.JSONObject;
import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.CmsSite;
import edu.online.Entity.cms.CmsTemplate;
import edu.online.Entity.cms.request.QueryPageRequest;
import edu.online.Entity.cms.response.CmsCode;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.cms.config.RabbitConfig;
import edu.online.cms.dao.CmsPageRepository;
import edu.online.exception.ExceptionCast;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import edu.online.model.response.ResponseResult;
import edu.online.utils.DateUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private MongoTemplate mongoTemplate;//mongodb数据库操作
    @Autowired
    private RestTemplate restTemplate;//远程请求
    @Autowired
    private CmsTemplateService cmsTemplateService;
    @Autowired
    private CmsSiteService cmsSiteService;
    @Autowired
    private GridFsTemplate gridFsTemplate;//gridfs文件操作
    @Autowired
    RabbitTemplate rabbitTemplate; //消息中间件

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
        CmsPage isCmsPage = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());
        if (isCmsPage == null) {
            cmsPageRepository.save(cmsPage);
            return new CmsResponseResult(CommonCode.SUCCESS, cmsPage);//新增成功
        } else if (isCmsPage != null) {
            return new CmsResponseResult(CmsCode.CMS_ADDPAGE_EXISTSNAME, null);//页面已经存在
        }
        return new CmsResponseResult(CommonCode.SERVER_ERROR, null);//其他错误
    }

    //3.编辑页面
    public CmsResponseResult updatePage(String id, CmsPage cmsPage) {
        if (id != null) {//此时传递来的参数存在主键 说明是编辑
            CmsPage byId = this.findById(id);
            if (byId != null) {//存在
                cmsPage.setPageId(id);
                CmsPage save = cmsPageRepository.save(cmsPage);
                if (save != null) {
                    return new CmsResponseResult(CommonCode.SUCCESS, cmsPage);
                }
            }
        } else {
            return new CmsResponseResult(CommonCode.INVALID_PARAM, null);//非法参数
        }
        return new CmsResponseResult(CommonCode.SERVER_ERROR, null);//其他错误
    }

    //4.删除页面
    public CmsResponseResult deletePage(String id) {
        CmsPage byId = this.findById(id);
        if (byId != null) {//存在
            cmsPageRepository.deleteById(id);
            return new CmsResponseResult(CommonCode.SUCCESS, null);
        }
        return new CmsResponseResult(CommonCode.FAIL, null);
    }

    //5.根据Id查询页面
    public CmsPage findById(String id) {
        //先判断页面是否存在
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()) {//java8 特性 判断当前对象是否为空
            return byId.get();
        }
        return null;
    }

    //6、页面静态化程序

    /**
     * 1.根据pageId获取页面的DataUrl 远程请求 得到数据模型
     * 2.根据pageId获取页面模板id 在通过gridfsTemplate读取模板。
     * 3.通过FreeMarkerTemplateUtils静态化程序 获得静态化html页面
     */
    //获取页面模型
    public Map getPageModelById(String pageId) {
        CmsPage page = this.findById(pageId);
        if (page != null) {//页面存在
            String dataUrl = page.getDataUrl();
            if (dataUrl != null) {
                //远程请求数据模型
                ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
                Map body = forEntity.getBody();
                if (body == null) {
                    ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
                }
                return body; //返回数据模型
            } else {
                //自定义异常 dataUrl为空
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
            }
        } else {
            //页面不存在
            ExceptionCast.cast(CmsCode.CMS_FINDPAGE_NotEXISTSNAME);
        }
        return null;
    }

    //获取模板内容
    public String getPageTempleById(String pageId) throws IOException {
        CmsPage page = this.findById(pageId);
        if (page != null) {//存在
            String templateId = page.getTemplateId();
            if (templateId != null) {
                CmsTemplate template = cmsTemplateService.findById(templateId);
                if (template != null) {
                    String templateFileId = template.getTemplateFileId();
                    //根据模板文件id去文件读取内容 第二个参数为0 表示返回字符串
                    String fileContent = cmsTemplateService.readTemplateFile(templateFileId, 0);
                    if (StringUtils.isNotEmpty(fileContent)) {
                        return fileContent;//返回文件内容
                    } else {
                        ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
                    }
                } else {
                    ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEI_NotEXIST);
                }
            } else {
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEI_NotEXIST);
            }
        } else {
            ExceptionCast.cast(CmsCode.CMS_FINDPAGE_NotEXISTSNAME);
        }
        return null;
    }

    //执行页面静态化
    public String generateHtml(String pageId) throws IOException, TemplateException {
        Map model = getPageModelById(pageId);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String TempleContent = getPageTempleById(pageId);
        if (TempleContent == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        //创建配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //创建模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template", TempleContent);
        //向configuration配置模板加载器
        configuration.setTemplateLoader(stringTemplateLoader);
        Template template = configuration.getTemplate("template");
        //调用api进行静态化
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        return html;
    }

    //7、页面发布

    /**
     * 1、调用页面静态化 生成静态html文件
     * 2.把生成的文件存到gridfs中 返回id存到cmsPage中
     * 3.给MQ 发消息 通知消费者下载文件到页面的物理路径中 并不是直接调用savePageToServerPath
     */
    public ResponseResult releasePage(String pageId) throws IOException, TemplateException {
        CmsPage page = this.findById(pageId);
        if (page == null) {
            return new ResponseResult(CmsCode.CMS_FINDPAGE_NotEXISTSNAME);
        }
        //页面静态化
        final String html = this.generateHtml(pageId);
        if (html == null) {//生成的静态html为空
            return new ResponseResult(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        //把字符串转输入流 上传到gridfs
        InputStream inputStream = IOUtils.toInputStream(html, "utf-8");
        ObjectId objectId = gridFsTemplate.store(inputStream, page.getPageName());
        //将html文件id更新到cmsPage中
        page.setHtmlFileId(objectId.toHexString());
        cmsPageRepository.save(page);
        //调用方法 向MQ发信息
        this.sendToMQ(pageId);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public void sendToMQ(String pageId) {
        //得到页面信息
        CmsPage cmsPage = this.findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //创建消息对象
        Map<String, String> msg = new HashMap<>();
        msg.put("pageId", pageId);
        //map转成json串
        String jsonString = JSONObject.toJSONString(msg);
        //站点id作为路由key
        String siteId = cmsPage.getSiteId();
        //消息发送给交换机 交换机根据指定路由key 转发给消息队列 然后被监听消息队列的消费者消费
        rabbitTemplate.convertAndSend(RabbitConfig.EX_ROUTING_CMS_POSTPAGE, siteId, jsonString);

    }
    //8、页面从GridFs中下载到服务器物理路径(消息消费者调用)

    /**
     * 监听到消息队列的发来的pageId之后
     * 1. 根据pageId查询cmsPage得到静态文件的id 站点id 页面物理路径 页面名称
     * 2.根据站点id得到站点物理路径
     * 3.根据静态文件的id从gridfs下载文件
     * 4.把文件保存到页面的物理路径中（这里的页面物理路径=站点物理路径+页面物理路径+页面名称）
     */
    public void savePageToServerPath(String pageId) {
        //根据pageId查询cmsPage
        CmsPage page = this.findById(pageId);
        //从cmsPage中获取静态文件id htmlFileId
        String htmlFileId = page.getHtmlFileId();
        String siteId = page.getSiteId();
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String htmlContent = cmsTemplateService.readTemplateFile(htmlFileId, 0);
            CmsSite site = cmsSiteService.findById(siteId);
            //页面物理路径
            String pagePath = site.getSitePhysicalPath() + page.getPagePhysicalPath() + page.getPageName();
            //把静态文件内容转入输入流中
            inputStream = IOUtils.toInputStream(htmlContent,"utf-8");
            fileOutputStream = new FileOutputStream(new File(pagePath));
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {//关流
                inputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
