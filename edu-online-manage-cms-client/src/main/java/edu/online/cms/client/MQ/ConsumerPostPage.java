package edu.online.cms.client.MQ;

import com.alibaba.fastjson.JSON;
import edu.online.Entity.cms.CmsPage;
import edu.online.cms.service.CmsPageService;
import edu.online.cms.service.CmsSiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Classname ConsumerPostPage
 * @Description TODO 消息的消费者
 * @Date 2020/2/18 18:23
 * @Created by zhoutao
 */
@Component
public class ConsumerPostPage {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);
    @Autowired
    CmsPageService cmsPageService;
    @Autowired
    CmsSiteService cmsSiteService;

    @RabbitListener(queues = "queue_cms_postpage")//监听指定消息队列
    public void postPage(String msg){
        try{
            //解析消息
            LOGGER.debug("消息"+msg);
            Map map = JSON.parseObject(msg, Map.class);
            String pageId = map.get("pageId").toString();
            //验证页面是否存在
            CmsPage byId = cmsPageService.findById(pageId);
            if(byId==null){//说明不存在
                LOGGER.debug("page is not found");
            }
            //调用service方法将页面从GridFs中下载到服务器
            cmsPageService.savePageToServerPath(pageId);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
