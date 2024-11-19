package com.example.service.Impl;

import com.example.client.PostClient;
import com.example.client.UserClient;
import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.dto.CommentDTO;
import com.example.dto.CommentPageQueryDTO;
import com.example.entity.Comment;
import com.example.exception.CantGetLockException;
import com.example.exception.ContentIsEmptyException;
import com.example.mapper.CommentMapper;
import com.example.result.PageResult;
import com.example.service.CommentService;
import com.example.vo.CommentVO;
import com.example.vo.UserVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.UserConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
//@Async
@Slf4j
public class CommentServiceImpl implements CommentService {
//这是一种线程池的使用方法，使用@Async注解，另一种是手动配置线程池，参考PostServiceImpl

    private final CommentMapper commentMapper;
//    private final PostMapper postMapper;
//    private final UserMapper userMapper;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;

    private final UserClient userClient;

    private final PostClient postClient;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper, StringRedisTemplate redisTemplate, RedissonClient redissonClient, UserClient userClient, PostClient postClient) {
        this.commentMapper = commentMapper;
        this.userClient = userClient;
        this.postClient = postClient;
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
    }

    /**
     * 用户回帖
     * @param commentDTO
     * @return
     */
    @Transactional
    @Override
//    @Async("taskExecutor")
    public Comment userComment(CommentDTO commentDTO){
        //TODO:分布式事务
//        System.out.println("回帖传入Service层的DTO" + commentDTO);
        //数据验证
        if (commentDTO.getContent() == null || commentDTO.getContent().isEmpty()){
            throw new ContentIsEmptyException(MessageConstant.CONTENT_EMPTY);
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO, comment);
        BaseContext.setCurrentId(commentDTO.getAuthorId());
        UserVO userVO = userClient.getUserInfo(commentDTO.getAuthorId(), null).getData();
        String authorName = userVO.getUsername();
        comment.setAuthorName(authorName);

        //生成Redis的锁的key并且细化颗粒度，针对每个帖子独立
        //String lockKey = "commentFloorLock" + commentDTO.getPostId();
//        //获取Redis锁
//        boolean lockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey,"locker",5, TimeUnit.SECONDS);
//
//        if (Boolean.TRUE.equals(lockAcquired)){
//            try{
//                Long maxFloor = commentMapper.getMaxFloorByPostId(commentDTO.getPostId());
//                Long newFloor = (maxFloor == null) ? 2L : maxFloor + 1L;
//                comment.setFloor(newFloor);
//
//                //保存实体到数据库
//                commentMapper.insert(comment);
//                postMapper.update(commentDTO.getPostId());
//                //返回保存后的实体
//                return comment;
//            } finally {
//                //释放锁
//                redisTemplate.delete(lockKey);
//            }
//        }else {
//            throw new CantGetLockException(MessageConstant.CANT_GET_LOCK);
//        }


        RLock lock = redissonClient.getLock("commentLock:" + commentDTO.getPostId());
        try {
//            log.info("Attempting to acquire lock for postId: {}", commentDTO.getPostId());
            // 尝试获取锁，等待时间为10秒，锁定时间为5秒
            if (lock.tryLock(10, 5, TimeUnit.SECONDS)) {
                try {
                    // 获取当前帖子的最高楼层号
                    Long maxFloor = commentMapper.getMaxFloorByPostId(commentDTO.getPostId());
                    Long newFloor = (maxFloor == null) ? 2L : maxFloor + 1L;
                    comment.setFloor(newFloor);
                    // 保存实体到数据库
                    commentMapper.insert(comment);
                    postClient.update(commentDTO.getPostId());
                } finally {
                    lock.unlock();
                }
            }
            else {
                throw new CantGetLockException(MessageConstant.CANT_GET_LOCK);
            }
        } catch (Exception e) {
            throw new CantGetLockException(MessageConstant.CANT_GET_LOCK);
        }

        // 返回保存后的实体
        return comment;

    }

    @Override
    public PageResult commentPageQuery(CommentPageQueryDTO commentPageQueryDTO){

        PageHelper.startPage(commentPageQueryDTO.getPage(),commentPageQueryDTO.getPageSize());
        Page<CommentVO> page = commentMapper.commentPageQuery(commentPageQueryDTO);

        long total = page.getTotal();
        List<CommentVO> records = page.getResult();

        return new PageResult(total,records);
    }


}
