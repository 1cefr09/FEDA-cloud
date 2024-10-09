package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.dto.MessageDTO;
import com.example.dto.MessagePageQueryDTO;
import com.example.entity.Message;
import com.example.exception.AccountNotFoundException;
import com.example.exception.ContentIsEmptyException;
import com.example.mapper.MessageMapper;
import com.example.mapper.UserMapper;
import com.example.result.PageResult;
import com.example.service.MessageService;
import com.example.vo.MessageVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Message sendMessage(MessageDTO messageDTO){
        System.out.println("私信传入DTO：" + messageDTO);
        String receiverName = messageDTO.getReceiverName();
        String senderName = userMapper.getUsernameById(messageDTO.getSenderId());
        if (receiverName == null || receiverName.isEmpty() || userMapper.getByUsername(receiverName) == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if (messageDTO.getContent() == null || messageDTO.getContent().isEmpty()){
            throw new ContentIsEmptyException(MessageConstant.CONTENT_EMPTY);
        }

        Message message = new Message();
        BeanUtils.copyProperties(messageDTO,message);
        message.setSenderName(senderName);
        message.setReceiverId(userMapper.getUserIdByName(receiverName));
        messageMapper.insert(message);
        return message;
    }

    @Override
    public PageResult getMessage(MessagePageQueryDTO messagePageQueryDTO){
        System.out.println("获取私信传入DTO：" + messagePageQueryDTO);
        PageHelper.startPage(messagePageQueryDTO.getPage(), messagePageQueryDTO.getPageSize());

        Page<MessageVO> page =messageMapper.getMessage(messagePageQueryDTO);

        long total = page.getTotal();
        List<MessageVO> messageList = page.getResult();
        return new PageResult(total,messageList);
    }

}
