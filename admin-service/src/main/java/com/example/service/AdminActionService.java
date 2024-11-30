package com.example.service;


import com.example.dto.AdminActionDTO;
import com.example.dto.CategoryDTO;

public interface AdminActionService {


    void banUser(AdminActionDTO adminActionDTO);

    void unBanUser(Long id);



}
