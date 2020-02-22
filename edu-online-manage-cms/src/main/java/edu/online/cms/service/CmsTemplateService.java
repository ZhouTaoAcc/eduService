package edu.online.cms.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import edu.online.Entity.cms.CmsTemplate;
import edu.online.Entity.cms.request.QueryTemplateRequest;
import edu.online.Entity.cms.response.CmsCode;
import edu.online.Entity.cms.response.CmsTemplateResponseResult;
import edu.online.cms.dao.CmsTemplateRepository;
import edu.online.model.response.CommonCode;
import edu.online.model.response.QueryResponseResult;
import edu.online.model.response.QueryResult;
import edu.online.model.response.ResponseResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
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
    @Autowired
    private GridFsTemplate gridFsTemplate;//用于对文件对象处理
    @Autowired
    GridFSBucket gridFSBucket;//用于处理流对象
    private static Logger logger = Logger.getLogger(CmsTemplateService.class); // 打印当前类日志

    /*1、分页查询业务逻辑*/
    public QueryResponseResult findList(int pageNo, int pageSize, QueryTemplateRequest queryTemplateRequest) {
        Sort sort = Sort.by(Sort.Direction.DESC, "templateFileId");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Query query = new Query();
        /*动态拼接查询条件*/
        if (!StringUtils.isEmpty(queryTemplateRequest.getSiteId())) {
            query.addCriteria(Criteria.where("siteId").is(queryTemplateRequest.getSiteId()));
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
        if (StringUtils.isNotEmpty(id)) {//此时传递来的参数存在主键 说明是编辑
            CmsTemplate template = this.findById(id);
            if (template!= null) {//存在
                cmsTemplate.setTemplateId(id);
                CmsTemplate save = cmsTemplateRepository.save(cmsTemplate);
                if (save != null) {
                    return new CmsTemplateResponseResult(CommonCode.SUCCESS, cmsTemplate);
                }
            }
        } else {
            return new CmsTemplateResponseResult(CommonCode.INVALID_PARAM, null);//非法参数
        }
        return new CmsTemplateResponseResult(CommonCode.SERVER_ERROR, null);//其他错误
    }

    //4.删除模板
    public CmsTemplateResponseResult deleteTemplate(String id) {
        CmsTemplate template = this.findById(id);
        if (template!= null) {//存在
            cmsTemplateRepository.deleteById(id);//删除模板信息
            this.deleteTemplateFile(template.getTemplateFileId());//删除模板文件
            return new CmsTemplateResponseResult(CommonCode.SUCCESS, null);
        }
        return new CmsTemplateResponseResult(CommonCode.FAIL, null);
    }

    //5.根据Id查询模板
    public CmsTemplate findById(String id) {
        //先判断模板是否存在
        Optional<CmsTemplate> byId = cmsTemplateRepository.findById(id);
        if (byId.isPresent()) {//java8 特性 判断当前对象是否为空
            return byId.get();
        }
        return null;
    }

    //6.上传模板文件到GridFS中
    public String uploadTemplateFile(String url) {
        if (StringUtils.isNotEmpty(url)) {
            try {
                //对路径中的中文处理
                String fileUrl = url.substring(0, url.lastIndexOf("."));
                String str[] = fileUrl.split("/");
                for (int i = 0; i < str.length; i++) {
                    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                    Matcher m = p.matcher(str[i]);
                    if (m.find()) {
                        url = url.replaceFirst(str[i], URLEncoder.encode(str[i], "UTF-8"));
                    }
                }
                //获取文件名
                int index = url.lastIndexOf('/');
                String fileName = url.substring(index + 1, url.length());
                File file = new File(url); //这里的url是正斜杠 /a/b
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectId id = gridFsTemplate.store(fileInputStream, fileName);//上传成功 返回文件id
                fileInputStream.close();
                if (StringUtils.isNotEmpty(id.toString())) {
                    return id.toString();
                }
            } catch (Exception e) {
                return JSONObject.toJSONString(new ResponseResult(CmsCode.CMS_UPLOAD_URL_ERROR));
            }
        } else {
            return JSONObject.toJSONString(new ResponseResult(CommonCode.INVALID_PARAM));
        }
        return null;
    }

    //7.读取模板文件内容
    public String readTemplateFile(String id, int type) throws IOException {
        //根据文件id查询文件对象 判断是否存在
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
        if (file != null) {
            //打开一个下载流对象
            GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(file.getObjectId());
            //创建GridFsResource对象，获取流
            GridFsResource gridFsResource = new GridFsResource(file, downloadStream);
            //使用Apache的IO工具包 从流中取数据
            String content = IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            if (type == 0) {//返回字符串
                return content;
            } else if (type == 1) {//下载文件
                InputStream inputStream = IOUtils.toInputStream(content,"utf-8");
                //导出到本地桌面
                FileSystemView fsv = FileSystemView.getFileSystemView();
                File com = fsv.getHomeDirectory();
                String absolutePath = com.getAbsolutePath();
                OutputStream outputStream = new FileOutputStream(new File(absolutePath + "/template_" + System.currentTimeMillis() + ".ftl"));
                int copy = IOUtils.copy(inputStream, outputStream);
                if (copy <= 0) {//保存失败
                    inputStream.close();
                    outputStream.close();
                    return JSONObject.toJSONString(new ResponseResult(CommonCode.EXPORT_fAIL));
                } else {
                    inputStream.close();
                    outputStream.close();
                    return JSONObject.toJSONString(new ResponseResult(CommonCode.EXPORT_SUCCESS));
                }
            }
        } else {
            return JSONObject.toJSONString(new ResponseResult(CmsCode.CMS_TEMPLATEFILE_NotEXISTS));
        }
        return null;
    }

    //8.删除模板文件
    public String deleteTemplateFile(String id) {
        //根据文件id删除fs.files和fs.chunks中的记录
        if (StringUtils.isNotEmpty(id)) {
            //根据文件id查询文件对象 判断是否存在
            GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));
            if (file != null) {
                gridFsTemplate.delete(Query.query(Criteria.where("_id").is(id)));
            } else {
                return JSONObject.toJSONString(new ResponseResult(CmsCode.CMS_TEMPLATEFILE_NotEXISTS));
            }
        } else {
            return JSONObject.toJSONString(new ResponseResult(CommonCode.INVALID_PARAM));
        }
        return null;
    }
}
