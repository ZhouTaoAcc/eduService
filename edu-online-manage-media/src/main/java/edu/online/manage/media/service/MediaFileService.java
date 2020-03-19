package edu.online.manage.media.service;

import edu.online.Entity.media.MediaFile;
import edu.online.Entity.media.request.QueryMediaFileRequest;
import edu.online.manage.media.dao.MediaFileRepository;
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
 * @Classname MediaService
 * @Description TODO
 * @Date 2020/3/17 14:02
 * @Created by zhoutao
 */
@Service
public class MediaFileService {

    @Autowired
    MediaFileRepository mediaFileRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public QueryResponseResult findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
        if (queryMediaFileRequest == null) {
            queryMediaFileRequest = new QueryMediaFileRequest();
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "uploadTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Query query = new Query();
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())) {
            Pattern pattern = Pattern.compile("^.*" + queryMediaFileRequest.getFileOriginalName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("fileOriginalName").regex(pattern));
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getTag())) {
            Pattern pattern = Pattern.compile("^.*" + queryMediaFileRequest.getTag() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("tag").regex(pattern));
        }
        if (StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())) {
            query.addCriteria(Criteria.where("processStatus").is(queryMediaFileRequest.getFileOriginalName()));
        }
        long count = mongoTemplate.count(query, MediaFile.class);
        List<MediaFile> mediaFiles = mongoTemplate.find(query.with(pageable), MediaFile.class);
        QueryResult queryResult = new QueryResult();
        queryResult.setTotal(count);
        queryResult.setList(mediaFiles);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }
}
