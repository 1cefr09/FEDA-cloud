package com.example.utils;

import com.example.constant.MessageConstant;
import com.example.exception.MessageInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class InfoIsValidUtil {
    /**
     * 用户名校验
     * @param username
     */
    public static void isValidUsername(String username) {
        String validSymbol = "^[a-zA-Z0-9_-]{1,20}$";
        boolean containsWhitespace = username.contains(" ");
        if (!(username.matches(validSymbol)) || containsWhitespace){
            throw new MessageInvalidException(MessageConstant.MESSAGE_INVALID);
        }
    }

    /**
     * 密码校验
     * @param password
     */
    public static void isValidPassword(String password){
        String validSymbol = "^[a-zA-Z0-9_-]{7,20}$";
        boolean containsWhitespace = password.contains(" ");
        if (!(password.matches(validSymbol)) || containsWhitespace){
            throw new MessageInvalidException(MessageConstant.MESSAGE_INVALID);
        }
    }

    /**
     * 标题校验
     * @param title
     */
    public static void isValidTitle(String title){
        boolean validLength = title.length()<=30;
        boolean onlyWhiteSpace = title.trim().isEmpty();
        if (!(validLength) || onlyWhiteSpace){
            throw new MessageInvalidException(MessageConstant.MESSAGE_INVALID);
        }
    }
}
