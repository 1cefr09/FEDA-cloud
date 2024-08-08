package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.dto.AdminActionDTO;
import com.example.entity.AdminAction;
import com.example.exception.NoPermissionException;
import com.example.exception.TypeNotSameException;
import com.example.mapper.AdminActionMapper;
import com.example.mapper.CommentMapper;
import com.example.mapper.PostMapper;
import com.example.mapper.UserMapper;
import com.example.service.AdminActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

//在controller处做区分然后调用相应的服务，这里只需要取出id
@Service
public class AdminActionServiceImpl implements AdminActionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminActionMapper adminActionMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 用户权限的更改
     * @param adminActionDTO
     */
    @Override
    public void userToAdmin(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();
        String adminRole = userMapper.getUserRoleById(adminId);
        if (!adminRole.equals("ADMIN")){
            throw new NoPermissionException(MessageConstant.NO_PERMISSION);
        }

        //检查是否为相应类型
        if (!adminActionDTO.getActionType().equals("userToAdmin")){
            throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }

        String userRole = userMapper.getUserRoleById(adminActionDTO.getTargetId());
        if (userRole.equals("USER")){
            userMapper.updateUserRole(adminActionDTO.getTargetId(),"ADMIN");
        } else{
            userMapper.updateUserRole(adminActionDTO.getTargetId(),"USER");
        }
        AdminAction adminAction = new AdminAction();
        BeanUtils.copyProperties(adminActionDTO,adminAction);
        adminAction.setAdminId(adminId);
        adminActionMapper.insert(adminAction);


    }

    /**
     * 禁用或者启用用户
     * @param adminActionDTO
     */
    @Override
    public void banUser(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();
        String adminRole = userMapper.getUserRoleById(adminId);
        if (!adminRole.equals("ADMIN")){
            throw new NoPermissionException(MessageConstant.NO_PERMISSION);
        }

        //检查是否为相应类型
        if (!adminActionDTO.getActionType().equals("banUser")){
            throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }

        boolean userIsBanned = userMapper.getIsBanned(adminActionDTO.getTargetId());
        if (!userIsBanned){
            userMapper.updateUserBanned(adminActionDTO.getTargetId(),true);
        }else {
            userMapper.updateUserBanned(adminActionDTO.getTargetId(),false);
        }

        AdminAction adminAction = new AdminAction();
        BeanUtils.copyProperties(adminActionDTO,adminAction);
        adminAction.setAdminId(adminId);
        adminActionMapper.insert(adminAction);

    }

    @Override
    public void banPost(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();
        String adminRole = userMapper.getUserRoleById(adminId);
        if (!adminRole.equals("ADMIN")){
            throw new NoPermissionException(MessageConstant.NO_PERMISSION);
        }

        //检查是否为相应类型
        if (!adminActionDTO.getActionType().equals("banPost")){
            throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }

        boolean postIsBanned = postMapper.getIsBanned(adminActionDTO.getTargetId());
        if (!postIsBanned){
            postMapper.updatePostBanned(adminActionDTO.getTargetId(),true);
        }else {
            postMapper.updatePostBanned(adminActionDTO.getTargetId(),false);
        }

        AdminAction adminAction = new AdminAction();
        BeanUtils.copyProperties(adminActionDTO,adminAction);
        adminAction.setAdminId(adminId);
        adminActionMapper.insert(adminAction);

    }

    @Override
    public void banComment(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();
        String adminRole = userMapper.getUserRoleById(adminId);
        if (!adminRole.equals("ADMIN")){
            throw new NoPermissionException(MessageConstant.NO_PERMISSION);
        }

        //检查是否为相应类型
        if (!adminActionDTO.getActionType().equals("banComment")){
            throw new TypeNotSameException(MessageConstant.TYPE_NOT_SAME);
        }

        boolean commentIsBanned = commentMapper.getIsBanned(adminActionDTO.getTargetId());
        if (!commentIsBanned){
            commentMapper.updateCommentBanned(adminActionDTO.getTargetId(),true);
        }else {
            commentMapper.updateCommentBanned(adminActionDTO.getTargetId(),false);
        }

        AdminAction adminAction = new AdminAction();
        BeanUtils.copyProperties(adminActionDTO,adminAction);
        adminAction.setAdminId(adminId);
        adminActionMapper.insert(adminAction);

    }
}
