package edu.online.course.service;

import edu.online.Entity.cms.CmsPage;
import edu.online.Entity.cms.CmsSite;
import edu.online.Entity.cms.response.CmsResponseResult;
import edu.online.Entity.course.CourseBase;
import edu.online.Entity.course.CourseMarket;
import edu.online.Entity.course.CoursePic;
import edu.online.Entity.course.response.CourseCode;
import edu.online.Entity.course.response.CourseResponseResult;
import edu.online.Entity.course.vo.CoursePlanVO;
import edu.online.Entity.course.vo.CourseVO;
import edu.online.course.dao.CourseBaseRepository;
import edu.online.course.dao.CourseMarketRepository;
import edu.online.course.dao.CoursePicRepository;
import edu.online.course.dao.CoursePlanMapper;
import edu.online.course.feign.CmsPageFeignClient;
import edu.online.model.response.CommonCode;
import edu.online.model.response.ResponseResult;
import edu.online.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @Classname CourseViewService
 * @Description TODO
 * @Date 2020/3/6 19:21
 * @Created by zhoutao
 */
@Service
public class CourseViewService {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CoursePicRepository coursePicRepository;
    @Autowired
    CourseMarketRepository courseMarketRepository;
    @SuppressWarnings("all")
    @Autowired
    CoursePlanMapper coursePlanMapper;
    //课程预览 发布配置文件
    @Value("${course-publish.dataUrlPre}")
    private String publish_dataUrlPre;
    @Value("${course-publish.pagePhysicalPath}")
    private String publish_page_physicalpath;
    @Value("${course-publish.pageWebPath}")
    private String publish_page_webpath;
    @Value("${course-publish.siteId}")
    private String publish_siteId;
    @Value("${course-publish.templateId}")
    private String publish_templateId;
    @Value("${course-publish.previewUrl}")
    private String previewUrl;
    @SuppressWarnings("all")
    @Autowired
    private CmsPageFeignClient cmsPageFeignClient;

    /**
     * @return edu.online.Entity.course.vo.CourseVO
     * @Description 课程视图
     * @Param [courseId]
     **/
    public CourseVO getCourseView(String courseId) {
        CourseVO courseVO = new CourseVO();
        //课程基础信息
        Optional<CourseBase> courseBase = courseBaseRepository.findById(courseId);
        if (courseBase.isPresent()) {
            courseVO.setCourseBase(courseBase.get());
        }
        //课程图片
        Optional<CoursePic> coursePic = coursePicRepository.findById(courseId);
        if (coursePic.isPresent()) {
            courseVO.setCoursePic(coursePic.get());
        }
        //课程计划
        CoursePlanVO coursePlanVO = coursePlanMapper.findPlanList(courseId);
        if (courseVO != null) {
            courseVO.setCoursePlanVO(coursePlanVO);
        }
        //课程营销
        Optional<CourseMarket> courseMarket = courseMarketRepository.findById(courseId);
        if (courseMarket.isPresent()) {
            courseVO.setCourseMarket(courseMarket.get());
        }
        return courseVO;
    }

    /**
     * @return CourseResponseResult
     * @Description 课程详情预览
     * 1.获取模板信息
     * 2.保存cms页面信息
     * 3.返回预览url(前端请求cms预览页面的接口url)
     * @Param [courseId]
     **/
    public CourseResponseResult coursePreView(String courseId) {
        //保存cms页面信息
        CmsPage page = this.saveOrUpdateCmsPage(courseId);
        if (page != null) {
            return new CourseResponseResult(CommonCode.SUCCESS, previewUrl + page.getPageId());
        }
        return new CourseResponseResult(CourseCode.COURSE_PUBLISH_CDETAILERROR, null);
    }

    /**
     * @return CourseResponseResult
     * @Description 课程发布
     * 1.保存cms页面信息
     * 2.页面静态化 （调用cms页面发布接口）
     * 3.把静态化文件存在gridfs中 （调用cms页面发布接口）
     * 4.发送MQ通知消费者把文件下载到页面的物理路径中（调用cms页面发布接口）
     * @Param [courseId]
     **/
    public CourseResponseResult coursePublish(String courseId) throws Exception {
        //保存cms页面信息
        CmsPage page = this.saveOrUpdateCmsPage(courseId);
        String pageUrl = null;
        if (page == null) {
            return new CourseResponseResult(CourseCode.COURSE_PUBLISH_CDETAILERROR, null);
        }
        //再次发布 删除上一次的旧文件
        if(page.getHtmlFileId()!=null){
            cmsPageFeignClient.deleteHtmlFile(page.getHtmlFileId());
        }
        //调用cms页面发布接口
        ResponseResult releasePage = cmsPageFeignClient.releasePage(page.getPageId());
        if (!releasePage.isSuccess()) {
            return new CourseResponseResult(CourseCode.COURSE_PUBLISH_VIEWERROR, null);
        }
        //返回课程页面的访问路径
        CmsSite site = cmsPageFeignClient.findSiteById(publish_siteId);
        pageUrl = site.getSiteDomain() + site.getSiteWebPath() + page.getPageWebPath() + page.getPageName();
       //更新课程状态为已发布
        saveCoursePubState(courseId);
        //更新页面状态为已发布
        cmsPageFeignClient.updateStatusById(page.getPageId(),"202002");
        return new CourseResponseResult(CommonCode.SUCCESS, pageUrl);
    }


    //私有方法 保存课程页面信息到cmsPage中 返回页面CmsPage
    private CmsPage saveOrUpdateCmsPage(String courseId) {
        //查询课程
        CourseVO courseVO = this.getCourseView(courseId);
        String name = courseVO.getCourseBase().getName();

        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId(publish_siteId);//站点id
        cmsPage.setDataUrl(publish_dataUrlPre + courseId);//数据模型url
        cmsPage.setPageName(courseId + ".html");//页面名称
        cmsPage.setPageAliase(name);//页面别名，就是课程名称
        cmsPage.setPagePhysicalPath(publish_page_physicalpath);//页面物理路径
        cmsPage.setPageWebPath(publish_page_webpath);//页面webpath
        cmsPage.setTemplateId(publish_templateId);//页面模板id
        cmsPage.setPageStatus("202001");//页面状态制作中
        cmsPage.setPageCreateTime(DateUtil.dateToISODate(new Date()));
        CmsResponseResult page = cmsPageFeignClient.saveCmsPage(cmsPage);//远程调用保存页面接口
        String pageId = page.getData().getPageId();
        if (page.getCode() == 24001) {//说明页面信息已经存在 更新即可
            if (StringUtils.isNotEmpty(pageId)) {
                cmsPageFeignClient.updateCmsPage(pageId, cmsPage);
            }
        }
        return page.getData();
    }

    //更新课程状态 页面状态为已发布 202002
    private CourseBase saveCoursePubState(String courseId) {
        Optional<CourseBase> courseBase = courseBaseRepository.findById(courseId);
        if (courseBase.isPresent()) {
            courseBase.get().setStatus("202002");
            courseBaseRepository.save(courseBase.get());
            return courseBase.get();
        }
        return null;
    }
}
