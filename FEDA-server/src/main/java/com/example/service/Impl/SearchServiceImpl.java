package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.entity.Post;
import com.example.service.SearchService;
import com.example.utils.InfoIsValidUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public SearchHits<Post> searchPosts(String keywords){
        //先检查用户输入是不是只有空格
        InfoIsValidUtil.isValidTitleOrKeywords(keywords);
        //创建es中的布尔构建查询器，用于复杂的查询条件，可以将多个条件组合并定义逻辑关系
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //向 BoolQueryBuilder 中添加一个 should 查询条件。这表示 title 字段必须包含给定的 keywords。
        boolQueryBuilder.should(QueryBuilders.matchQuery("title",keywords));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content",keywords));

        //将 BoolQueryBuilder 转换为 CriteriaQuery 对象
        //使用了 Criteria 类的 expression 方法将字符串形式的查询表达式封装到 CriteriaQuery
        //CriteriaQuery 是es中用于表示实际查询的对象
        Query query = new CriteriaQuery(new Criteria().expression(boolQueryBuilder.toString()));
        //调用elasticsearchRestTemplate的查询方法，查询结果映射到post类中
        SearchHits<Post> searchHits = elasticsearchRestTemplate.search(query,Post.class);
        return searchHits;
    }

}
