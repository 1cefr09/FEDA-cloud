package com.example.service.Impl;

import com.example.constant.RedisConstant;
import com.example.mapper.CategoryMapper;
import com.example.service.CategoryService;
import com.example.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String, List<CategoryVO>> redisTemplate;



    @Override
    public List<CategoryVO> categoryListQuery() {
        // 从缓存中获取
        List<CategoryVO> categories = redisTemplate.opsForValue().get(RedisConstant.CATEGORY_LIST_KEY);

        if (categories == null) {
            log.info("Redis缓存中没有数据，从数据库中获取");
            categories = categoryMapper.categoryListQuery();
            redisTemplate.opsForValue().set(RedisConstant.CATEGORY_LIST_KEY, categories,12, TimeUnit.HOURS);
        }

        return categories;
    }
}
