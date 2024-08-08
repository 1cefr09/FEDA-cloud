package com.example.aspect;

import com.example.annotation.CheckPermission;
import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.dto.AdminActionDTO;
import com.example.exception.NoPermissionException;
import com.example.exception.TypeNotSameException;
import com.example.mapper.UserMapper;
import com.example.mapper.PostMapper;
import com.example.mapper.CommentMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Before("@annotation(checkPermission)")
    public void checkPermission(JoinPoint joinPoint, CheckPermission checkPermission) {
        Long adminId = BaseContext.getCurrentId();
        String adminRole = userMapper.getUserRoleById(adminId);
        Object[] args = joinPoint.getArgs();
        AdminActionDTO adminActionDTO = (AdminActionDTO) args[0];
        String methodName = joinPoint.getSignature().getName();

        //检查是不是允许的role类型，如果是直接放行
        boolean hasPermission = false;
        for (String role : checkPermission.roles()) {
            if (adminRole.equals(role)) {
                hasPermission = true;
                break;
            }
        }

        //检查是否为相应类型,如果不是抛出异常
        if (!adminActionDTO.getActionType().equals(methodName)) {
            throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }


        //检查是否允许对作者自己操作
        if (!hasPermission && checkPermission.allowSelf()) {
            if (methodName.equals("banPost")) {
                //查post的作者id
                Long authorId = postMapper.getAuthorIdByPostId(adminActionDTO.getTargetId());
                if (adminId.equals(authorId)) {
                    hasPermission = true;
                }
            } else if (methodName.equals("banComment")) {
                //查comment的作者id
                Long authorId = commentMapper.getAuthorIdByCommentId(adminActionDTO.getTargetId());
                if (adminId.equals(authorId)) {
                    hasPermission = true;
                }
            }
        }

        if (!hasPermission) {
            throw new NoPermissionException(MessageConstant.NO_PERMISSION);
        }
    }
}