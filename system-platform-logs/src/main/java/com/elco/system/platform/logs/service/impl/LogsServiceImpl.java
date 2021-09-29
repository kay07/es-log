package com.elco.system.platform.logs.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.elco.platform.util.PageResult;
import com.elco.system.platform.logs.config.EsConfig;
import com.elco.system.platform.logs.entity.dto.SearchLog;
import com.elco.system.platform.logs.entity.vi.MsgLog;
import com.elco.system.platform.logs.service.LogsService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kay
 * @date 2021/9/26
 */
@Service
public class LogsServiceImpl implements LogsService {
    private final static String INDEX_ES="log-2021.09.27";
    @Resource
    private EsConfig esConfig;
    @Override
    public PageResult<List<MsgLog>> msg(SearchLog searchLog) {

        SearchRequest request=new SearchRequest(INDEX_ES);
        SearchSourceBuilder builder=new SearchSourceBuilder();
        builder.trackTotalHits(true);
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        if(!searchLog.getServiceName().equals("")){
            WildcardQueryBuilder    serviceNameBuilder=QueryBuilders.wildcardQuery("log.file.path", "*" + searchLog.getServiceName() + "*");
            boolQueryBuilder.must(serviceNameBuilder);
        }
        if(!searchLog.getHostName().equals("")){
            WildcardQueryBuilder  hostNameBuilder = QueryBuilders.wildcardQuery("host.name", "*" + searchLog.getHostName() + "*");
            boolQueryBuilder.must(hostNameBuilder);
        }
        if(!searchLog.getLevel().equals("")){
            WildcardQueryBuilder levelBuilder=QueryBuilders.wildcardQuery("message","*"+searchLog.getLevel()+"*");
            boolQueryBuilder.must(levelBuilder);
        }
        String start=searchLog.getStartTime();
        String end=searchLog.getEndTime();
        boolean bTime = (start != null) && (end != null) && (!"".equals(start)) && (!"".equals(end));
        if(bTime){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("@timestamp");
                    rangeQueryBuilder.format("yyyy-MM-dd HH:mm:ss");
                    rangeQueryBuilder.gte(start);
                    rangeQueryBuilder.lte(end);
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        builder.query(boolQueryBuilder);
        //分页
        int pageSize=10;
        builder.from(searchLog.getPage()*pageSize-pageSize);
        builder.size(pageSize);
        request.source(builder);
        SearchResponse search=null;
        try {
             search = esConfig.client().search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<MsgLog> list=new ArrayList<>();
        long total=search.getHits().getTotalHits().value;
        for (SearchHit hit : search.getHits().getHits()) {
            MsgLog msgLog=new MsgLog();
            String js=hit.getSourceAsString();
            JSONObject parse =(JSONObject) JSONObject.parse(js);
            String message = parse.getString("message");
            JSONObject host =(JSONObject) parse.get("host");
            String name = host.getString("name");
            JSONObject file =(JSONObject) ((JSONObject) parse.get("log")).get("file");
            String path = file.getString("path");
            String server="";
            if(path.contains("/")){
                String[] split = path.split("/");
                server=split[split.length-2];
            }
            String intoTime = parse.getString("@timestamp");
            intoTime=intoTime.replaceAll("T"," ");
            intoTime=intoTime.replaceAll("Z","");
            msgLog.setIntoTime(intoTime);
            msgLog.setMessage(message);
            msgLog.setNode(name);
            msgLog.setService(server);
            list.add(msgLog);
        }
        PageResult pageResult=new PageResult();
        pageResult.setDataList(list);
        pageResult.setTotalCount(total);
        int totalPage=(int)(total+pageSize-1)/pageSize;
        pageResult.setTotalPage(totalPage);
        return pageResult;
    }
}
