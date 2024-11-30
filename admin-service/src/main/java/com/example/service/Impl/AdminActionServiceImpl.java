package com.example.service.Impl;

import com.example.annotation.CheckPermission;
import com.example.constant.MessageConstant;
import com.example.constant.RedisConstant;
import com.example.constant.UserRoleConstant;
import com.example.context.BaseContext;
import com.example.dto.AdminActionDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.AdminAction;
import com.example.entity.Category;
import com.example.exception.AlreadyExistException;
import com.example.exception.NoPermissionException;
import com.example.mapper.AdminActionMapper;
import com.example.service.AdminActionService;
import com.example.vo.CategoryVO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class AdminActionServiceImpl implements AdminActionService {



    @Autowired
    private AdminActionMapper adminActionMapper;


    @Autowired
    private RedisTemplate<String, List<CategoryVO>> redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 禁用或者启用用户
     * @param adminActionDTO
     */
    @Override
    @CheckPermission(roles = {UserRoleConstant.ROOT,UserRoleConstant.ADMIN}, allowSelf = false)
    @Transactional
    public void banUser(AdminActionDTO adminActionDTO) {
        String banTime = adminActionDTO.getBanTime();
        if("Permanently".equals(banTime)) {
            rabbitTemplate.convertAndSend("user.direct","user.ban",adminActionDTO);
        }else {
            Long duration = getBanTime(banTime);
            Long targetId = adminActionDTO.getTargetId();
            rabbitTemplate.convertAndSend("user.direct","user.ban",adminActionDTO);
            rabbitTemplate.convertAndSend("user.delay.direct","user.delay.unban",targetId, message -> {
                message.getMessageProperties().setDelay(duration.intValue());
                return message;
            });
        }
    }

    private Long getBanTime(String banTime) {
        long duration = 0;
        switch (banTime) {
            case "Minute":
                duration = 60 * 1000L;
                break;
            case "Day":
                duration = 24 * 60 * 60 * 1000L;
                break;
            case "Week":
                duration = 7 * 24 * 60 * 60 * 1000L;
                break;
            case "Month":
                duration = 30 * 24 * 60 * 60 * 1000L; // 这里假设一个月为30天
                break;
            case "Year":
                duration = 365 * 24 * 60 * 60 * 1000L; // 这里假设一年为365天
                break;
        }
//        return new Date(System.currentTimeMillis() + duration);
        return duration;
    }

    @Override
    public void unBanUser(Long id) {
        rabbitTemplate.convertAndSend("user.topic","user.unban",id);
    }


}
