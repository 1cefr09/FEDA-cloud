package com.example.service.Impl;

import com.example.annotation.CheckPermission;
import com.example.constant.MessageConstant;
import com.example.constant.UserRoleConstant;
import com.example.context.BaseContext;
import com.example.dto.AdminActionDTO;
import com.example.dto.CategoryDTO;
import com.example.entity.AdminAction;
import com.example.entity.Category;
import com.example.exception.NoPermissionException;
import com.example.exception.TypeNotSameException;
import com.example.mapper.*;
import com.example.service.AdminActionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 用户权限的更改
     * @param adminActionDTO
     */
    @Override
    @CheckPermission(roles = {UserRoleConstant.ROOT}, allowSelf = false)
    @Transactional
    public void userToAdmin(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();

        String userRole = userMapper.getUserRoleById(adminActionDTO.getTargetId());
        if (userRole.equals(UserRoleConstant.USER)){
            userMapper.updateUserRole(adminActionDTO.getTargetId(), UserRoleConstant.ADMIN);
        } else{
            userMapper.updateUserRole(adminActionDTO.getTargetId(),UserRoleConstant.USER);
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
    @CheckPermission(roles = {UserRoleConstant.ROOT,UserRoleConstant.ADMIN}, allowSelf = false)
    @Transactional
    public void banUser(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();

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
    @CheckPermission(roles = {UserRoleConstant.ROOT,UserRoleConstant.ADMIN}, allowSelf = true)
    @Transactional
    public void banPost(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();

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
    @CheckPermission(roles = {UserRoleConstant.ROOT,UserRoleConstant.ADMIN}, allowSelf = true)
    @Transactional
    public void banComment(AdminActionDTO adminActionDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();

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

    @Override
    @Transactional
    public void createCategory(CategoryDTO categoryDTO) {
        //检查是否有权限
        Long adminId = BaseContext.getCurrentId();
        String adminRole = userMapper.getUserRoleById(adminId);
        if (!Objects.equals(adminRole, UserRoleConstant.ROOT)){
            throw new NoPermissionException(MessageConstant.NO_PERMISSION);
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        categoryMapper.insert(category);
        AdminAction adminAction = new AdminAction();
        adminAction.setAdminId(adminId);
        adminAction.setActionType("createCategory");
        adminAction.setTargetId(categoryMapper.getCategoryIdByName(category.getCategoryName()));
        adminActionMapper.insert(adminAction);
    }

    @Override
    @Transactional
    @CheckPermission(roles = {UserRoleConstant.ROOT}, allowSelf = false)
    public void banCategory(AdminActionDTO adminActionDTO) {
        Long adminId = BaseContext.getCurrentId();
        boolean categoryIsBanned = categoryMapper.getIsBannedById(adminActionDTO.getTargetId());
        if (!categoryIsBanned){
            categoryMapper.updateCategoryBanned(adminActionDTO.getTargetId(),true);
        }else {
            categoryMapper.updateCategoryBanned(adminActionDTO.getTargetId(),false);
        }

        AdminAction adminAction = new AdminAction();
        BeanUtils.copyProperties(adminActionDTO,adminAction);
        adminAction.setAdminId(adminId);
        adminActionMapper.insert(adminAction);
    }

}
