//package com.example.controller.web;
//
//import com.example.constant.MessageConstant;
//import com.example.entity.Post;
//import com.example.result.Result;
//import com.example.service.SearchService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/search")
//@Slf4j
//@Api(tags = "根据关键字搜索")
//public class SearchPostsController {
//    @Autowired
//    SearchService searchService;
//
//    @GetMapping("/searchPosts")
//    @ApiOperation("根据关键字搜索帖子")
//    public Result<List<Post>> searchPosts(@RequestParam String keywords){
//        log.info("根据关键字获取帖子:{}",keywords);
//        SearchHits<Post> searchHits = searchService.searchPosts(keywords);
//        //把Elasticsearch 查询中得到的 SearchHits<Post> 转换为一个包含 Post 对象的 List。
//        //SearchHits<Post> 是 Elasticsearch 查询的结果，包含了所有符合查询条件的 Post 实体。
//        //将 List<SearchHit<Post>> 转换为 Java Stream
//        //对流中的每个 SearchHit<Post> 对象应用 getContent 方法，将其转换为 Post 对象。
//        //collect(Collectors.toList())将流中的 Post 对象收集到一个 List<Post> 中
//        List<Post> posts = searchHits.getSearchHits().stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//        if (posts.isEmpty()){
//            log.warn("搜索结果为空:{}",keywords);
//            return Result.error(MessageConstant.SEARCG_EMPTY);
//        }
//        return Result.success(posts);
//    }
//}
