//package com.odk.elasticsearchspringbootstarter;
//
//import co.elastic.clients.elasticsearch.core.IndexRequest;
//import com.alibaba.fastjson.JSONObject;
//import org.elasticsearch.client.RequestOptions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * EsController
// *
// * @description:
// * @version: 1.0
// * @author: oubin on 2024/1/17
// */
//@RestController
//@RequestMapping("/es")
//public class EsController {
//
//
//    @GetMapping
//    public void add() {
//        createIndex("test");
//        System.out.println("123");
//    }
//
//    private void createIndex(String indexName) {
//       Map<String, String> es = new HashMap<>();
//       es.put("id", "1");
//       es.put("name", "Mingcheng");
//
//        IndexRequest indexRequest = new IndexRequest(indexName);
//        indexRequest.source(JSONObject.toJSON(es), XContentType.JSON);
//        try {
//            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}
