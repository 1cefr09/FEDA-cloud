package com.example.service;


import com.example.dto.AdminActionDTO;
import com.example.dto.CategoryDTO;

public interface AdminActionService {

    /**
     * 用户提升为管理员
     * @param adminActionDTO
     */
    void userToAdmin (AdminActionDTO adminActionDTO);

    void banUser(AdminActionDTO adminActionDTO);

    void banPost(AdminActionDTO adminActionDTO);

    void banComment(AdminActionDTO adminActionDTO);

    void createCategory(CategoryDTO categoryDTO);

}
